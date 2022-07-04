package com.fb.easy;

import com.fb.easy.contract.ListenerContract;
import com.fb.easy.contract.ListenerListContract;
import com.fb.easy.contract.SingleContract;
import com.fb.easy.core.CallBack;
import com.fb.easy.core.Job;
import com.fb.easy.util.DbUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        this.ref = database.getReference(path);
    }

    //static methods

    public static Db path(String path) {
        return new Db(path);
    }

    //instance methods

    class Single implements SingleContract {
        @Override
        public <T> void getGeneric(final CallBack.Generic<T> callback) {
            ref.addListenerForSingleValueEvent(DbUtils.getEvent(callback));
        }

        @Override
        public <T> void getListGeneric(final CallBack.ListGeneric<T> callback) {
            ref.addListenerForSingleValueEvent(DbUtils.getListEvent(callback));
        }

        @Override
        public void getMap(final CallBack.Map callback) {
            getGeneric(callback);
        }

        @Override
        public void getListMap(final CallBack.ListMap callback) {
            getListGeneric(callback);
        }

        @Override
        public void getString(CallBack.String callback) {
            getGeneric(callback);
        }

        @Override
        public void getListString(CallBack.ListString callback) {
            getListGeneric(callback);
        }

        @Override
        public void getDouble(CallBack.Double callback) {
            getGeneric(callback);
        }

        @Override
        public void getListDouble(CallBack.ListDouble callback) {
            getListGeneric(callback);
        }

        @Override
        public void getLong(CallBack.Long callback) {
            getGeneric(callback);
        }

        @Override
        public void getListLong(CallBack.ListLong callback) {
            getListGeneric(callback);
        }

        @Override
        public void getBoolean(CallBack.Boolean callback) {
            getGeneric(callback);
        }

        @Override
        public void getListBoolean(CallBack.ListBoolean callback) {
            getListGeneric(callback);
        }
    }

    class Listener implements ListenerListContract, ListenerContract {

        public final ChildrenListener CHILDREN = new ChildrenListener();

        @Override
        public <T> Job getGeneric(final CallBack.Generic<T> listener) {
            final ValueEventListener valueEventListener =
                    ref.addValueEventListener(DbUtils.getEvent(listener));

            return new Job() {
                @Override
                public void stop() {
                    ref.removeEventListener(valueEventListener);
                }
            };
        }

        @Override
        public <T> Job getListGeneric(CallBack.ListGeneric<T> listener) {
            final ValueEventListener valueEventListener =
                    ref.addValueEventListener(DbUtils.getListEvent(listener));

            return new Job() {
                @Override
                public void stop() {
                    ref.removeEventListener(valueEventListener);
                }
            };
        }

        @Override
        public Job getMap(CallBack.Map listener) {
            return getGeneric(listener);
        }

        @Override
        public Job getListMap(CallBack.ListMap listener) {
            return getListGeneric(listener);
        }

        @Override
        public Job getString(CallBack.String listener) {
            return getGeneric(listener);
        }

        @Override
        public Job getListString(CallBack.ListString listener) {
            return getListGeneric(listener);
        }

        @Override
        public Job getDouble(CallBack.Double listener) {
            return getGeneric(listener);
        }

        @Override
        public Job getListDouble(CallBack.ListDouble listener) {
            return getListGeneric(listener);
        }

        @Override
        public Job getLong(CallBack.Long listener) {
            return getGeneric(listener);
        }

        @Override
        public Job getListLong(CallBack.ListLong listener) {
            return getListGeneric(listener);
        }

        @Override
        public Job getBoolean(CallBack.Boolean listener) {
            return getGeneric(listener);
        }

        @Override
        public Job getListBoolean(CallBack.ListBoolean listener) {
            return getListGeneric(listener);
        }

        class ChildrenListener implements ListenerListContract {

            @Override
            public <T> Job getListGeneric(CallBack.ListGeneric<T> listener) {
                final ChildEventListener valueEventListener =
                        ref.addChildEventListener(DbUtils.getListChildEvent(listener));

                return new Job() {
                    @Override
                    public void stop() {
                        ref.removeEventListener(valueEventListener);
                    }
                };
            }

            @Override
            public Job getListMap(CallBack.ListMap listener) {
                return getListGeneric(listener);
            }

            @Override
            public Job getListString(CallBack.ListString listener) {
                return getListGeneric(listener);
            }

            @Override
            public Job getListDouble(CallBack.ListDouble listener) {
                return getListGeneric(listener);
            }

            @Override
            public Job getListLong(CallBack.ListLong listener) {
                return getListGeneric(listener);
            }

            @Override
            public Job getListBoolean(CallBack.ListBoolean listener) {
                return getListGeneric(listener);
            }
        }
    }
}
