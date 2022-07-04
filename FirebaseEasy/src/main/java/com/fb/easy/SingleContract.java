package com.fb.easy;

public interface SingleContract {
    <T> void getGeneric(final CallBack.Generic<T> callback);

    <T> void getListGeneric(final CallBack.ListGeneric<T> callback);

    void getMap(final CallBack.Map callback);

    void getListMap(final CallBack.ListMap callback);

    void getString(final CallBack.String callback);

    void getListString(final CallBack.ListString callback);

    void getDouble(final CallBack.Double callback);

    void getListDouble(final CallBack.ListDouble callback);

    void getLong(final CallBack.Long callback);

    void getListLong(final CallBack.ListLong callback);

    void getBoolean(final CallBack.Boolean callback);

    void getListBoolean(final CallBack.ListBoolean callback);
}
