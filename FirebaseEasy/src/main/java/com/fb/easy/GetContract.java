package com.fb.easy;

public interface GetContract {
    <T> void getGeneric(final CallBack.Generic<T> callback);

    <T> void getListGeneric(final CallBack.ListGeneric<T> callback);

    void getMap(final CallBack.Map callback);

    void getListMap(final CallBack.ListMap callback);
}
