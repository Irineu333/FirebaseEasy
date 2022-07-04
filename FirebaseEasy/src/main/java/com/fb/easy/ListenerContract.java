package com.fb.easy;

public interface ListenerContract {
    <T> Job listenerGeneric(final CallBack.Generic<T> listener);

    <T> Job listenerListGeneric(final CallBack.ListGeneric<T> listener);

    Job listenerMap(final CallBack.Map listener);

    Job listenerListMap(final CallBack.ListMap listener);

    Job listenerString(final CallBack.Map listener);

    Job listenerListString(final CallBack.ListMap listener);

    Job listenerDouble(final CallBack.Map listener);

    Job listenerListDouble(final CallBack.ListMap listener);

    Job listenerLong(final CallBack.Map listener);

    Job listenerListLong(final CallBack.ListMap listener);

    Job listenerBoolean(final CallBack.Map listener);

    Job listenerListBoolean(final CallBack.ListMap listener);
}
