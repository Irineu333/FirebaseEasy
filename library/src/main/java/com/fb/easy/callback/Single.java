package com.fb.easy.callback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        private final GenericTypeIndicator<T> type;

        public Generic(GenericTypeIndicator<T> type) {

            Objects.requireNonNull(type, "type cannot be null");

            this.type = type;
        }

        public abstract void onFailure(Exception e);

        public abstract void onResult(T result);

        public GenericTypeIndicator<T> getType() {
            return type;
        }
    }

    public abstract static class ListGeneric<T> extends Duration {

        @NonNull
        private final GenericTypeIndicator<T> type;

        @NonNull
        private final List<T> list;

        public ListGeneric(GenericTypeIndicator<T> type) {
            this(type, new ArrayList<T>());
        }

        public ListGeneric(@NonNull GenericTypeIndicator<T> type, @NonNull List<T> list) {

            Objects.requireNonNull(list, "list cannot be null");
            Objects.requireNonNull(type, "type cannot be null");

            list.clear();

            this.list = list;
            this.type = type;
        }

        public abstract void onFailure(Exception e);

        public void onResult(java.util.List<T> result) {
            //not implemented
        }

        public void onAdded(T child, int index, java.lang.String key) {
            //not implemented
        }

        @NonNull
        public List<T> getList() {
            return list;
        }

        @NonNull
        public GenericTypeIndicator<T> getType() {
            return type;
        }
    }

    //sketchware

    public abstract static class Map extends Generic<java.util.HashMap<java.lang.String, Object>> {
        public Map() {
            super(new GenericTypeIndicator<java.util.HashMap<java.lang.String, Object>>() {});
        }
    }

    public abstract static class ListMap extends ListGeneric<HashMap<java.lang.String, Object>> {

        public ListMap(List<HashMap<java.lang.String, Object>> list) {
            super(new GenericTypeIndicator<java.util.HashMap<java.lang.String, Object>>() {}, list);
        }

        public ListMap() {
            super(new GenericTypeIndicator<java.util.HashMap<java.lang.String, Object>>() {});
        }
    }

    //basics


    public abstract static class String extends Generic<java.lang.String> {
        public String() {
            super(new GenericTypeIndicator<java.lang.String>() {});
        }
    }

    public abstract static class Double extends Generic<java.lang.Double> {
        public Double() {
            super(new GenericTypeIndicator<java.lang.Double>() {});
        }
    }

    public abstract static class Long extends Generic<java.lang.Long> {
        public Long() {
            super(new GenericTypeIndicator<java.lang.Long>() {});
        }
    }

    public abstract static class Boolean extends Generic<java.lang.Boolean> {
        public Boolean() {
            super(new GenericTypeIndicator<java.lang.Boolean>() {});
        }
    }

    //basic list

    public abstract static class ListString extends ListGeneric<java.lang.String> {

        public ListString(List<java.lang.String> list) {
            super(new GenericTypeIndicator<java.lang.String>() {}, list);
        }

        public ListString() {
            super(new GenericTypeIndicator<java.lang.String>() {});
        }
    }

    public abstract static class ListDouble extends ListGeneric<java.lang.Double> {

        public ListDouble(List<java.lang.Double> list) {
            super(new GenericTypeIndicator<java.lang.Double>() {}, list);
        }

        public ListDouble() {
            super(new GenericTypeIndicator<java.lang.Double>() {});
        }
    }

    public abstract static class ListLong extends ListGeneric<java.lang.Long> {

        public ListLong(List<java.lang.Long> list) {
            super(new GenericTypeIndicator<java.lang.Long>() {}, list);
        }

        public ListLong() {
            super(new GenericTypeIndicator<java.lang.Long>() {});
        }
    }

    public abstract static class ListBoolean extends ListGeneric<java.lang.Boolean> {

        public ListBoolean(List<java.lang.Boolean> list) {
            super(new GenericTypeIndicator<java.lang.Boolean>() {}, list);
        }

        public ListBoolean() {
            super(new GenericTypeIndicator<java.lang.Boolean>() {});
        }
    }
}
