package com.fb.easy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fb.easy.callback.Listener;
import com.fb.easy.callback.Result;
import com.fb.easy.callback.Single;
import com.fb.easy.contract.Job;
import com.fb.easy.util.DbUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

@SuppressWarnings("unused")
public final class Db {

    //static fields

    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    //instance fields

    private final DatabaseReference ref;

    //constructors

    public Db(@NonNull String path) {
        this(database.getReference(path));
    }

    public Db(@NonNull DatabaseReference ref) {

        if (ref == null) throw new IllegalArgumentException("ref cannot be null");

        this.ref = ref;
    }

    //static methods

    public static Db path(@NonNull String path) {
        return new Db(path);
    }

    //instance methods

    public Db child(String child) {
        return new Db(ref.child(child));
    }

    public String getPushKey() {
        return ref.push().getKey();
    }

    public void set(@Nullable Object value) {
        set(value, null);
    }

    public void set(@Nullable Object value, @Nullable final Result.Set result) {
        ref.setValue(value).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (result == null) return;

                        if (task.isSuccessful()) {
                            result.onSuccess();
                        } else {
                            result.onFailure(task.getException());
                        }
                    }
                }
        );
    }

    public void update(@NonNull Object map) {
        update(map, null);
    }

    public void update(@NonNull Object obj, @Nullable final Result.Update result) {

        HashMap<String, Object> map;

        try {
            map = (HashMap<String, Object>) obj;
        } catch (Exception e) {
            Gson gson = new Gson();

            map = gson.fromJson(
                    gson.toJson(obj),
                    new TypeToken<HashMap<String, Object>>() {}.getType()
            );
        }

        ref.updateChildren(map).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (result == null) return;

                        if (task.isSuccessful()) {
                            result.onSuccess();
                        } else {
                            result.onFailure(task.getException());
                        }
                    }
                }
        );
    }

    public void post(@NonNull Object value) {
        post(value, null);
    }

    public void post(@NonNull Object value, @Nullable final Result.Post result) {

        if (value == null) throw new IllegalArgumentException("value cannot be null");

        final DatabaseReference push = ref.push();

        push.setValue(value).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (result == null) return;

                if (task.isSuccessful()) {
                    result.onSuccess();
                    result.onSuccess(push.getKey());
                } else {
                    result.onFailure(task.getException());
                }
            }
        });
    }

    //instance class

    public <T> void get(@NonNull Single.Generic<T> callback) {
        ref.addListenerForSingleValueEvent(DbUtils.getEvent(callback));
    }


    public <T> void get(@NonNull Single.ListGeneric<T> callback) {
        ref.addListenerForSingleValueEvent(DbUtils.getListEvent(callback));
    }

    //listener

    public <T> Job get(@NonNull Listener.Generic<T> listener) {
        final ValueEventListener valueEventListener =
                ref.addValueEventListener(DbUtils.getEvent(listener));

        return new Job() {
            @Override
            public void stop() {
                ref.removeEventListener(valueEventListener);
            }
        };
    }

    public <T> Job get(@NonNull Listener.ListGeneric<T> listener) {
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
    public <T> Job get(@NonNull Listener.Children.ListGeneric<T> listener) {
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
