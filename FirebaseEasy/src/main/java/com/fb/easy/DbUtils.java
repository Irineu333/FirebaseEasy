package com.fb.easy;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class DbUtils {


    @SuppressWarnings("unchecked")
    private static <T> T parserToGeneric(DataSnapshot dataSnapshot, Class<T> clazz) throws Exception {
        if (clazz == JSONObject.class) {
            return (T) new JSONObject(String.valueOf(dataSnapshot.getValue()));
        }

        if (clazz == JSONArray.class) {
            return (T) new JSONObject(String.valueOf(dataSnapshot.getValue()));
        }
        return dataSnapshot.getValue(clazz);
    }

    @NonNull
    static <T> ValueEventListener getGenericEvent(@NonNull final CallBack.Generic<T> listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    listener.success(parserToGeneric(dataSnapshot, listener.clazz));
                } catch (Exception e) {
                    listener.error(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.error(databaseError.toException());
            }
        };
    }

    @NonNull
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
                try {
                    List<T> result = new ArrayList<>();

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        result.add(parserToGeneric(child, callback.clazz));
                    }

                    callback.success(result);
                } catch (Exception e) {
                    callback.error(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.error(databaseError.toException());
            }
        };
    }
}
