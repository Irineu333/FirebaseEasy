package com.fb.easy;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fb.easy.util.DbUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

        Objects.requireNonNull(ref, "ref cannot be null");

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

    @SuppressWarnings("unchecked")
    public void update(@NonNull Object obj, @Nullable final Result.Update result) {

        Objects.requireNonNull(obj, "value cannot be null");

        HashMap<String, Object> map;

        try {
            map = (HashMap<String, Object>) obj;
        } catch (ClassCastException e) {
            Gson gson = new Gson();

            map = gson.fromJson(
                    gson.toJson(obj),
                    new TypeToken<HashMap<String, Object>>() {
                    }.getType()
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

        Objects.requireNonNull(value, "value cannot be null");

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

    public void delete() {
        delete(null);
    }

    public void delete(@Nullable final Result.Delete result) {
        ref.removeValue().addOnCompleteListener(
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

    //instance class

    public <T> void get(@NonNull Single.Generic<T> callback) {
        ref.addListenerForSingleValueEvent(DbUtils.getEvent(callback));
    }


    public <T> void get(@NonNull Single.ListGeneric<T> callback) {
        ref.addListenerForSingleValueEvent(DbUtils.getListEvent(callback));
    }

    //listener

    public <T> Job get(@NonNull Listener.Generic<T> listener) {
        Job job = createJob(listener);

        job.start();

        return job;
    }

    public <T> Job createJob(@NonNull Listener.Generic<T> listener) {
        final ValueEventListener valueEventListener = DbUtils.getEvent(listener);

        return new Job() {

            private boolean isRunning = false;

            @Override
            public void stop() {
                if (!isRunning) return;

                ref.removeEventListener(valueEventListener);
                isRunning = false;
                removeJob(this);

                callStopListener();
            }

            @Override
            public void start() {
                if (isRunning) return;

                ref.addValueEventListener(valueEventListener);
                isRunning = true;
                addJob(this);

                callStartListener();
            }

            @Override
            public boolean isRunning() {
                return isRunning;
            }
        };
    }

    public <T> Job get(@NonNull Listener.ListGeneric<T> listener) {
        Job job = createJob(listener);

        job.start();

        return job;
    }

    public <T> Job createJob(@NonNull Listener.ListGeneric<T> listener) {
        final ValueEventListener valueEventListener = DbUtils.getListEvent(listener);

        return new Job() {

            private boolean isRunning = false;

            @Override
            public void stop() {
                if (!isRunning) return;

                ref.removeEventListener(valueEventListener);
                isRunning = false;
                removeJob(this);

                callStopListener();
            }

            @Override
            public void start() {
                if (isRunning) return;

                ref.addValueEventListener(valueEventListener);
                isRunning = true;
                addJob(this);

                callStartListener();
            }

            @Override
            public boolean isRunning() {
                return isRunning;
            }
        };
    }

    //children listener
    public <T> Job get(@NonNull Listener.Children.ListGeneric<T> listener) {
        Job job = createJob(listener);

        job.start();

        return job;
    }

    public <T> Job createJob(@NonNull Listener.Children.ListGeneric<T> listener) {

        final ChildEventListener valueEventListener = DbUtils.getListEvent(listener);

        return new Job() {

            private boolean isRunning = false;

            @Override
            public void stop() {
                if (!isRunning) return;

                ref.removeEventListener(valueEventListener);
                isRunning = false;
                removeJob(this);

                callStopListener();
            }

            @Override
            public void start() {
                if (isRunning) return;

                ref.addChildEventListener(valueEventListener);
                isRunning = true;
                addJob(this);

                callStartListener();
            }

            @Override
            public boolean isRunning() {
                return isRunning;
            }
        };
    }

    public void getTimestamp(final Single.Long callback) {
        Map<String, Object> sendTimestamp = new HashMap<>();

        set(ServerValue.TIMESTAMP, new Result.Set() {
            @Override
            public void onSuccess() {
                get(new Single.Long() {
                    @Override
                    public void onResult(Long result) {
                        delete();
                        callback.onResult(result);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
