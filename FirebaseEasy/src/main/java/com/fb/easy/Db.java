package com.fb.easy;

import com.fb.easy.contract.Job;
import com.fb.easy.callback.Listener;
import com.fb.easy.callback.Single;
import com.fb.easy.util.DbUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

@SuppressWarnings("unused")
public final class Db {

    //static fields

    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    //instance fields

    private final DatabaseReference ref;

    //constructors

    public Db(String path) {
        this(database.getReference(path));
    }

    public Db(DatabaseReference ref) {
        this.ref = ref;
    }

    //static methods

    public static Db path(String path) {
        return new Db(path);
    }

    //instance methods

    public Db child(String child) {
        return new Db(ref.child(child));
    }

    public String getPushKey() {
        return ref.push().getKey();
    }

    public void set(Object value) {
        ref.setValue(value);
    }

    public void update(Object map) {
        ref.updateChildren(
                DbUtils.parserToGeneric(
                        map,
                        new Gson(),
                        new TypeToken<Map<String, Object>>() {
                        }
                )
        );
    }

    public void post(Object value) {
        new Db(ref.push()).set(value);
    }

    //instance class

    public <T> void get(Single.Generic<T> callback) {
        ref.addListenerForSingleValueEvent(DbUtils.getEvent(callback));
    }


    public <T> void get(Single.ListGeneric<T> callback) {
        ref.addListenerForSingleValueEvent(DbUtils.getListEvent(callback));
    }

    //listener

    public <T> Job get(Listener.Generic<T> listener) {
        final ValueEventListener valueEventListener =
                ref.addValueEventListener(DbUtils.getEvent(listener));

        return new Job() {
            @Override
            public void stop() {
                ref.removeEventListener(valueEventListener);
            }
        };
    }

    public <T> Job get(Listener.ListGeneric<T> listener) {
        final ValueEventListener valueEventListener =
                ref.addValueEventListener(DbUtils.getListEvent(listener));

        return new Job() {
            @Override
            public void stop() {
                ref.removeEventListener(valueEventListener);
            }
        };
    }

    //children listener
    public <T> Job get(Listener.Children.ListGeneric<T> listener) {
        final ChildEventListener valueEventListener =
                ref.addChildEventListener(DbUtils.getListEvent(listener));

        return new Job() {
            @Override
            public void stop() {
                ref.removeEventListener(valueEventListener);
            }
        };
    }
}
