package com.fb.easy.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fb.easy.callback.Listener;
import com.fb.easy.callback.Single;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;

public final class DbUtils {

    @NonNull
    public static <T> ValueEventListener getEvent(@NonNull final Single.Generic<T> callback) {

        callback.setStartTimeMillis(System.currentTimeMillis());

        return new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.setEndTimeMillis(System.currentTimeMillis());
                callback.onResult(dataSnapshot.getValue(callback.getType()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.setEndTimeMillis(System.currentTimeMillis());
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

    @NonNull
    public static <T> ValueEventListener getListEvent(@NonNull final Single.ListGeneric<T> callback) {

        callback.setStartTimeMillis(System.currentTimeMillis());

        return new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                callback.setEndTimeMillis(System.currentTimeMillis());

                final List<T> result = callback.getList();

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    T value = child.getValue(callback.getType());

                    callback.onAdded(value, child.getKey());

                    result.add(value);
                }

                callback.onResult(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.setEndTimeMillis(System.currentTimeMillis());
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

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    T value = child.getValue(callback.getType());

                    callback.onAdded(value, child.getKey());

                    result.add(value);
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
            private final LinkedHashMap<String, T> linkedMap = new LinkedHashMap<>();

            private void onResult() {
                listener.onResult(listener.getList());
            }

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                T child = dataSnapshot.getValue(listener.getType());

                String key = dataSnapshot.getKey();

                int index = linkedMap.size();

                linkedMap.put(key, child);

                result.add(child);

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

                int index = 0;
                for (String _key : linkedMap.keySet()) {
                    if (_key.equals(key)) break;
                    index++;
                }

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

                linkedMap.remove(key);

                int index = linkedMap.size();

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
