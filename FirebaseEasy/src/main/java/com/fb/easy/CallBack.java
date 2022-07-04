package com.fb.easy;

import androidx.annotation.NonNull;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.HashMap;

public final class CallBack {

    private CallBack() {
        //sealed class
    }

    //generic

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

    //sketchware

    @SuppressWarnings("unchecked")
    abstract static class Map extends Generic<java.util.Map<java.lang.String, Object>> {
        public Map() {
            super((Class<java.util.Map<java.lang.String, Object>>) getMapClass());
        }

        @NonNull
        private static Class<?> getMapClass() {
            return HashMap.class;
        }
    }

    @SuppressWarnings("unchecked")
    abstract static class ListMap extends ListGeneric<java.util.Map<java.lang.String, Object>> {
        public ListMap() {
            super((Class<java.util.Map<java.lang.String, Object>>) getMapClass());
        }

        @NonNull
        private static Class<?> getMapClass() {
            return HashMap.class;
        }
    }

    //basics

    abstract static class String extends Generic<java.lang.String> {
        public String() {
            super(java.lang.String.class);
        }
    }

    abstract static class Double extends Generic<java.lang.Double> {
        public Double() {
            super(java.lang.Double.class);
        }
    }

    abstract static class Long extends Generic<java.lang.Long> {
        public Long() {
            super(java.lang.Long.class);
        }
    }

    abstract static class Boolean extends Generic<java.lang.Boolean> {
        public Boolean() {
            super(java.lang.Boolean.class);
        }
    }

    //basic list

    abstract static class ListString extends ListGeneric<java.lang.String> {
        public ListString() {
            super(java.lang.String.class);
        }
    }

    abstract static class ListDouble extends ListGeneric<java.lang.Double> {
        public ListDouble() {
            super(java.lang.Double.class);
        }
    }

    abstract static class ListLong extends ListGeneric<java.lang.Long> {
        public ListLong() {
            super(java.lang.Long.class);
        }
    }

    abstract static class ListBoolean extends ListGeneric<java.lang.Boolean> {
        public ListBoolean() {
            super(java.lang.Boolean.class);
        }
    }
}
