package com.fb.easy;

public interface ListenerListContract {
    <T> Job getListGeneric(final CallBack.ListGeneric<T> listener);

    Job getListMap(final CallBack.ListMap listener);

    Job getListString(final CallBack.ListString listener);

    Job getListDouble(final CallBack.ListDouble listener);

    Job getListLong(final CallBack.ListLong listener);

    Job getListBoolean(final CallBack.ListBoolean listener);
}

