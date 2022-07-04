package com.fb.easy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DbUtils {

    @SuppressWarnings("unchecked")
     static <T> T parserToGeneric(DataSnapshot dataSnapshot, Class<T> clazz) throws Exception {
        if (clazz == JSONObject.class) {
            return (T) new JSONObject(String.valueOf(dataSnapshot.getValue()));
        }

        if (clazz == JSONArray.class) {
            return (T) new JSONObject(String.valueOf(dataSnapshot.getValue()));
        }

        if (clazz == HashMap.class) {
            return (T) dataSnapshot.getValue();
        }

        return dataSnapshot.getValue(clazz);
    }

    @NonNull
    static <T> ValueEventListener getEvent(@NonNull final CallBack.Generic<T> listener) {
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
    static  <T> ChildEventListener getListChildEvent(@NonNull final CallBack.ListGeneric<T> listener) {
        return new ChildEventListener() {

            private final Map<String, T> list = new HashMap<>();

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    list.put(dataSnapshot.getKey(), DbUtils.parserToGeneric(dataSnapshot, listener.clazz));
                    listener.success(new ArrayList<>(list.values()));
                } catch (Exception e) {
                    listener.error(e);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    list.put(dataSnapshot.getKey(), DbUtils.parserToGeneric(dataSnapshot, listener.clazz));
                    listener.success(new ArrayList<>(list.values()));
                } catch (Exception e) {
                    listener.error(e);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                try {
                    list.remove(dataSnapshot.getKey());
                    listener.success(new ArrayList<>(list.values()));
                } catch (Exception e) {
                    listener.error(e);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    list.put(dataSnapshot.getKey(), DbUtils.parserToGeneric(dataSnapshot, listener.clazz));
                    listener.success(new ArrayList<>(list.values()));
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
    static <T> ValueEventListener getListEvent(@NonNull final CallBack.ListGeneric<T> callback) {
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
