package com.fb.easy;

import java.util.List;

public final class CallBack {

    private CallBack() {
        //sealed class
    }

    abstract static class Generic<T> {

        final Class<T> clazz;

        public Generic(Class<T> clazz) {
            this.clazz = clazz;
        }

        abstract void error(Throwable throwable);

        abstract void success(T result);
    }

    abstract static class ListGeneric<T> {

        final Class<T> clazz;

        public ListGeneric(Class<T> clazz) {
            this.clazz = clazz;
        }

        abstract void error(Throwable throwable);

        abstract void success(java.util.List<T> result);
    }

    interface Map {
        void error(Throwable throwable);

        void success(java.util.Map<String, java.lang.Object> result);
    }

    interface ListMap {
        void error(Throwable throwable);

        void success(List<java.util.Map<String, java.lang.Object>> result);
    }
}
