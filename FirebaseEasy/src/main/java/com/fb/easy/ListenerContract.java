package com.fb.easy;

public interface ListenerContract {
    <T> Job getGeneric(final CallBack.Generic<T> listener);

    Job getMap(final CallBack.Map listener);

    Job getString(final CallBack.String listener);

    Job getDouble(final CallBack.Double listener);

    Job getLong(final CallBack.Long listener);

    Job getBoolean(final CallBack.Boolean listener);
}
