package com.fb.easy.callback;

import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

import java.util.Objects;

public final class Single {

    private Single() {
        //sealed class
    }

    private abstract static class Duration {

        @Nullable
        private java.lang.Long startTimeMillis;

        @Nullable
        private java.lang.Long endTimeMillis;

        public long getDuration() {

            Objects.requireNonNull(startTimeMillis, "request not started");
            Objects.requireNonNull(endTimeMillis, "request not finished");

            return endTimeMillis - startTimeMillis;
        }

        public void setStartTimeMillis(long startTimeMillis) {

            if (this.startTimeMillis != null) {
                throw new IllegalStateException("request has already started");
            }

            this.startTimeMillis = startTimeMillis;
        }

        public void setEndTimeMillis(long endTimeMillis) {

            Objects.requireNonNull(startTimeMillis, "request not started");

            if (this.endTimeMillis != null) {
                throw new IllegalStateException("request has already finished");
            }

            this.endTimeMillis = endTimeMillis;
        }
    }

    //generic

    public abstract static class Generic<T> extends Duration {

        public final TypeToken<T> typeToken = new TypeToken<T>() {
        };

        public abstract void onFailure(Exception e);

        public abstract void onResult(T result);
    }

    public abstract static class ListGeneric<T> extends Duration {

        public final TypeToken<T> typeToken = new TypeToken<T>() {
        };

        public abstract void onFailure(Exception e);

        public void onResult(java.util.List<T> result) {
            //not implemented
        }

        public void onAdded(T child, java.lang.String key) {
            //not implemented
        }
    }

    //sketchware

    public abstract static class Map extends Generic<java.util.HashMap<java.lang.String, Object>> {
    }

    public abstract static class ListMap extends ListGeneric<java.util.HashMap<java.lang.String, Object>> {
    }

    //basics

    public abstract static class String extends Generic<java.lang.String> {
    }

    public abstract static class Double extends Generic<java.lang.Double> {
    }

    public abstract static class Long extends Generic<java.lang.Long> {
    }

    public abstract static class Boolean extends Generic<java.lang.Boolean> {
    }

    //basic list

    public abstract static class ListString extends ListGeneric<java.lang.String> {
    }

    public abstract static class ListDouble extends ListGeneric<java.lang.Double> {
    }

    public abstract static class ListLong extends ListGeneric<java.lang.Long> {
    }

    public abstract static class ListBoolean extends ListGeneric<java.lang.Boolean> {
    }
}
