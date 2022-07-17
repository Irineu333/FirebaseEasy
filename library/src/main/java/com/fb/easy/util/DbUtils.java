package com.fb.easy.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fb.easy.Intercept;
import com.fb.easy.callback.Listener;
import com.fb.easy.callback.Single;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public final class DbUtils {

    private static void initRequestDuration(Single.Duration duration) {
        duration.setStartTimeMillis(System.currentTimeMillis());
    }

    private static void endRequestDuration(Single.Duration  duration) {
        duration.setEndTimeMillis(System.currentTimeMillis());
    }

    //object

    @NonNull
    public static <T> ValueEventListener getEvent(@NonNull final Single.Generic<T> callback) {
        return new ValueEventListener() {

            {
                initRequestDuration(callback);
            }

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                endRequestDuration(callback);

                callback.onResult(dataSnapshot.getValue(callback.getType()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                endRequestDuration(callback);

                callback.onFailure(databaseError.toException());
            }
        };
    }

    @NonNull
    public static <T> ValueEventListener getEvent(@NonNull final Listener.Generic<T> listener) {

        return new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onResult(dataSnapshot.getValue(listener.getType()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure(databaseError.toException());
            }
        };
    }

    //list

    @NonNull
    public static <T> ValueEventListener getListEvent(@NonNull final Single.ListGeneric<T> callback) {

        return new ValueEventListener() {

            {
                initRequestDuration(callback);
            }

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               endRequestDuration(callback);

                final List<T> result = callback.getList();

                int index = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    T value = child.getValue(callback.getType());

                    callback.onAdded(value, index, child.getKey());

                    result.add(index, value);

                    index++;
                }

                callback.onResult(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                endRequestDuration(callback);

                callback.onFailure(databaseError.toException());
            }
        };
    }

    @NonNull
    public static <T> ValueEventListener getListEvent(@NonNull final Listener.ListGeneric<T> callback) {
        return new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<T> result = callback.getList();

                int index = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    T value = child.getValue(callback.getType());

                    callback.onAdded(value, index, child.getKey());

                    result.add(index, value);
                    index++;
                }

                callback.onResult(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        };
    }

    @NonNull
    public static <T> ChildEventListener getListEvent(@NonNull final Listener.Children.ListGeneric<T> listener) {
        return new ChildEventListener() {

            private final List<T> result = listener.getList();
            private final SortedMap<String, T> linkedMap = new TreeMap<>();

            private void onResult() {
                listener.onResult(listener.getList());
            }

            private int getIndex(String key) {
                int index = 0;
                for (String _key : linkedMap.keySet()) {
                    if (_key.equals(key)) {
                        return index;
                    }
                    index++;
                }
                return -1;
            }

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                T child = dataSnapshot.getValue(listener.getType());

                String key = dataSnapshot.getKey();

                linkedMap.put(key, child);

                int index = getIndex(key);

                result.add(index, child);

                onResult();

                listener.onAdded(
                        child,
                        index,
                        key
                );
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String key = dataSnapshot.getKey();

                T child = dataSnapshot.getValue(listener.getType());

                linkedMap.put(key, child);

                int index = getIndex(key);

                result.remove(index);
                result.add(index, child);

                onResult();

                listener.onChanged(
                        child,
                        index,
                        dataSnapshot.getKey()
                );
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                T child = dataSnapshot.getValue(listener.getType());

                String key = dataSnapshot.getKey();

                int index = getIndex(key);

                linkedMap.remove(key);
                result.remove(index);

                onResult();

                listener.onRemoved(
                        child,
                        index,
                        dataSnapshot.getKey()
                );
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure(databaseError.toException());
            }
        };
    }
}
