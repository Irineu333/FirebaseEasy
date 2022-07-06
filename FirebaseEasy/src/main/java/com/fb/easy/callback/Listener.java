package com.fb.easy.callback;

import com.google.gson.reflect.TypeToken;

public final class Listener {

    private Listener() {
        //sealed class
    }

    //generic

    public abstract static class Generic<T> {

        public final TypeToken<T> typeToken = new TypeToken<T>() {
        };

        public abstract void error(Throwable throwable);

        public abstract void success(T result);
    }

    public abstract static class ListGeneric<T> {

        public final TypeToken<T> typeToken = new TypeToken<T>() {
        };

        public abstract void error(Throwable throwable);

        public void success(java.util.List<T> result) {
            //not implemented
        }

        public void onAdded(T result, java.lang.String key) {
            //not implemented
        }
    }

    //sketchware

    public abstract static class Map extends Generic<java.util.Map<java.lang.String, Object>> {
    }

    public abstract static class ListMap extends ListGeneric<java.util.Map<java.lang.String, Object>> {
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

    public static final class Children {
        //generic

        private Children() {
            //sealed class
        }

        public abstract static class Generic<T> {

            public abstract void error(Throwable throwable);

            public abstract void success(T result);
        }

        public abstract static class ListGeneric<T> {

            public final TypeToken<T> typeToken = new TypeToken<T>() {
            };

            public abstract void error(Throwable throwable);


            public void onAdded(T result, java.lang.String key) {
                //not implemented
            }

            public void onChange(T result, java.lang.String key) {
                //not implemented
            }

            public void onRemoved(T result, java.lang.String key) {
                //not implemented
            }
        }

        //sketchware

        public abstract static class Map extends Generic<java.util.Map<java.lang.String, Object>> {
        }

        public abstract static class ListMap extends ListGeneric<java.util.Map<java.lang.String, Object>> {
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
}
