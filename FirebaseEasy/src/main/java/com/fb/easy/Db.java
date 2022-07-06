package com.fb.easy;

import com.fb.easy.core.CallBack;
import com.fb.easy.core.Job;
import com.fb.easy.util.DbUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

@SuppressWarnings("unused")
public final class Db {

    //static fields

    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    //instance fields

    private final DatabaseReference ref;

    public final Single SINGLE = new Single();
    public final Listener LISTENER = new Listener();

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
                DbUtils.parserToGeneric(map,
                        new TypeToken<Map<String, Object>>() {
                        }
                )
        );
    }

    public void post(Object value) {
        new Db(ref.push()).set(value);
    }

    //instance class

    class Single {

        public <T> void get(final CallBack.Generic<T> callback) {
            ref.addListenerForSingleValueEvent(DbUtils.getEvent(callback));
        }


        public <T> void getList(final CallBack.ListGeneric<T> callback) {
            ref.addListenerForSingleValueEvent(DbUtils.getListEvent(callback));
        }
    }

    class Listener {

        public final ChildrenListener CHILDREN = new ChildrenListener();

        public <T> Job get(final CallBack.Generic<T> listener) {
            final ValueEventListener valueEventListener =
                    ref.addValueEventListener(DbUtils.getEvent(listener));

            return new Job() {
                @Override
                public void stop() {
                    ref.removeEventListener(valueEventListener);
                }
            };
        }

        public <T> Job getList(CallBack.ListGeneric<T> listener) {
            final ValueEventListener valueEventListener =
                    ref.addValueEventListener(DbUtils.getListEvent(listener));

            return new Job() {
                @Override
                public void stop() {
                    ref.removeEventListener(valueEventListener);
                }
            };
        }

        class ChildrenListener {

            public <T> Job getList(CallBack.ListGeneric<T> listener) {
                final ChildEventListener valueEventListener =
                        ref.addChildEventListener(DbUtils.getListChildEvent(listener));

                return new Job() {
                    @Override
                    public void stop() {
                        ref.removeEventListener(valueEventListener);
                    }
                };
            }
        }
    }
}
