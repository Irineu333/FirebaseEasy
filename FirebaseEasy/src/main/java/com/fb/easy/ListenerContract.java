package com.fb.easy;

public interface ListenerContract {
    <T> Job listenerGeneric(final CallBack.Generic<T> listener);

    <T> Job listenerListGeneric(final CallBack.ListGeneric<T> listener);

    Job listenerMap(final CallBack.Map listener);

    Job listenerListMap(final CallBack.ListMap listener);
}
