package com.fb.easy.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fb.easy.core.CallBack;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DbUtils {

    static <T> T parserToGeneric(DataSnapshot dataSnapshot, TypeToken<T> typeToken) {

        Gson gson = new Gson();

        String json = gson.toJson(dataSnapshot.getValue());

        return gson.fromJson(json, typeToken.getType());
    }

    @NonNull
    public static <T> ValueEventListener getEvent(@NonNull final CallBack.Generic<T> listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.success(
                        parserToGeneric(
                                dataSnapshot,
                                listener.typeToken
                        )
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.error(databaseError.toException());
            }
        };
    }

    @NonNull
    public static <T> ChildEventListener getListChildEvent(@NonNull final CallBack.ListGeneric<T> listener) {
        return new ChildEventListener() {

            private final Map<String, T> list = new HashMap<>();

            private void put(@NonNull DataSnapshot dataSnapshot) {
                list.put(
                        dataSnapshot.getKey(),
                        DbUtils.parserToGeneric(
                                dataSnapshot,
                                listener.typeToken
                        ));
            }

            private void updated() {
                listener.success(new ArrayList<>(list.values()));
            }

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                put(dataSnapshot);
                updated();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                put(dataSnapshot);
                updated();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                list.remove(dataSnapshot.getKey());
                updated();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.error(databaseError.toException());
            }
        };
    }

    @NonNull
    public static <T> ValueEventListener getListEvent(@NonNull final CallBack.ListGeneric<T> callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> result = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    result.add(parserToGeneric(child, callback.typeToken));
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
