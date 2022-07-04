package com.fb.easy;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public final class Db implements GetContract, ListenerContract {

    //static fields

    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    //instance fields

    private final DatabaseReference ref;

    //constructors

    public Db(String path) {
        this.ref = database.getReference(path);
    }

    //static methods

    public static Db path(String path) {
        return new Db(path);
    }

    //instance methods

    //get

    @Override
    public <T> void getGeneric(final CallBack.Generic<T> callback) {
        ref.addListenerForSingleValueEvent(DbUtils.getGenericEvent(callback));
    }

    @Override
    public <T> void getListGeneric(final CallBack.ListGeneric<T> callback) {
        ref.addListenerForSingleValueEvent(DbUtils.getListGenericEvent(callback));
    }

    @Override
    public void getMap(final CallBack.Map callback) {
        ref.addListenerForSingleValueEvent(DbUtils.getMapEvent(callback));
    }

    @Override
    public void getListMap(final CallBack.ListMap callback) {
        ref.addListenerForSingleValueEvent(DbUtils.getListMapEvent(callback));
    }

    //listener

    @Override
    public <T> Job listenerGeneric(final CallBack.Generic<T> listener) {
        final ValueEventListener valueEventListener =
                ref.addValueEventListener(DbUtils.getGenericEvent(listener));

        return new Job() {
            @Override
            public void stop() {
                ref.removeEventListener(valueEventListener);
            }
        };
    }

    @Override
    public <T> Job listenerListGeneric(CallBack.ListGeneric<T> listener) {
        final ValueEventListener valueEventListener =
                ref.addValueEventListener(DbUtils.getListGenericEvent(listener));

        return new Job() {
            @Override
            public void stop() {
                ref.removeEventListener(valueEventListener);
            }
        };
    }

    @Override
    public Job listenerMap(CallBack.Map listener) {
        final ValueEventListener valueEventListener =
                ref.addValueEventListener(DbUtils.getMapEvent(listener));

        return new Job() {
            @Override
            public void stop() {
                ref.removeEventListener(valueEventListener);
            }
        };
    }

    @Override
    public Job listenerListMap(CallBack.ListMap listener) {
        final ValueEventListener valueEventListener =
                ref.addValueEventListener(DbUtils.getListMapEvent(listener));

        return new Job() {
            @Override
            public void stop() {
                ref.removeEventListener(valueEventListener);
            }
        };
    }
}
