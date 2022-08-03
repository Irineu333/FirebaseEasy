package com.fb.easy;

public final class Result {

    private Result() {
        //sealed
    }

    public interface Set {
        void onSuccess();

        void onFailure(Exception exception);
    }

    public interface Update {
        void onSuccess();

        void onFailure(Exception exception);
    }

    public interface Delete {
        void onSuccess();

        void onFailure(Exception exception);
    }

    public abstract static class Post {
        public void onSuccess(String key) {
            //not implemented
        }

        public void onSuccess() {
            //not implemented
        }

        public abstract void onFailure(Exception exception);
    }
}
