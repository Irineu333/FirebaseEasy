package com.fb.easy;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class DbUtils {
    @NonNull
    static <T> ValueEventListener getGenericEvent(@NonNull final CallBack.Generic<T> listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.success(dataSnapshot.getValue(listener.clazz));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.error(databaseError.toException());
            }
        };
    }

    @NonNull
    static ValueEventListener getListMapEvent(@NonNull final CallBack.ListMap callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Map<String, Object>> result = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    result.add((Map<String, Object>) child.getValue());
                }

                callback.success(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.error(databaseError.toException());
            }
        };
    }

    @NonNull
    static ValueEventListener getMapEvent(@NonNull final CallBack.Map callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.success((Map<String, Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.error(databaseError.toException());
            }
        };
    }

    @NonNull
    static <T> ValueEventListener getListGenericEvent(@NonNull final CallBack.ListGeneric<T> callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> result = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    result.add(child.getValue(callback.clazz));
                }

                callback.success(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.error(databaseError.toException());
            }
        };
    }
}
