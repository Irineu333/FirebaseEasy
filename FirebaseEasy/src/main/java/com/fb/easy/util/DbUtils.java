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

import java.util.ArrayList;
import java.util.List;

public final class DbUtils {

    @NonNull
    public static <T> T parserToGeneric(
            @Nullable Object data,
            @NonNull Gson gson,
            @NonNull TypeToken<T> typeToken
    ) {

        String json = gson.toJson(data);

        return gson.fromJson(json, typeToken.getType());
    }

    @NonNull
    public static <T> ValueEventListener getEvent(@NonNull final Single.Generic<T> listener) {

        return new ValueEventListener() {

            private final Gson gson = new Gson();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.success(
                        parserToGeneric(
                                dataSnapshot.getValue(),
                                gson,
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
    public static <T> ValueEventListener getEvent(@NonNull final Listener.Generic<T> listener) {

        return new ValueEventListener() {

            private final Gson gson = new Gson();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.success(
                        parserToGeneric(
                                dataSnapshot.getValue(),
                                gson,
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
    public static <T> ValueEventListener getListEvent(@NonNull final Single.ListGeneric<T> callback) {
        return new ValueEventListener() {

            private final Gson gson = new Gson();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> result = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    T value = parserToGeneric(
                            child.getValue(),
                            gson,
                            callback.typeToken
                    );

                    callback.onAdded(value, child.getKey());

                    result.add(value);
                }

                callback.result(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.error(databaseError.toException());
            }
        };
    }

    @NonNull
    public static <T> ValueEventListener getListEvent(@NonNull final Listener.ListGeneric<T> callback) {
        return new ValueEventListener() {

            private final Gson gson = new Gson();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> result = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    T value = parserToGeneric(
                            child.getValue(),
                            gson,
                            callback.typeToken
                    );

                    callback.onAdded(value, child.getKey());

                    result.add(value);
                }

                callback.result(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.error(databaseError.toException());
            }
        };
    }

    @NonNull
    public static <T> ChildEventListener getListEvent(@NonNull final Listener.Children.ListGeneric<T> listener) {
        return new ChildEventListener() {

            private final Gson gson = new Gson();

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                listener.onAdded(
                        DbUtils.parserToGeneric(
                                dataSnapshot.getValue(),
                                gson,
                                listener.typeToken
                        ),
                        dataSnapshot.getKey()
                );
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                listener.onChange(
                        DbUtils.parserToGeneric(
                                dataSnapshot.getValue(),
                                gson,
                                listener.typeToken
                        ),
                        dataSnapshot.getKey()
                );
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                listener.onRemoved(
                        DbUtils.parserToGeneric(
                                dataSnapshot.getValue(),
                                gson,
                                listener.typeToken
                        ),
                        dataSnapshot.getKey()
                );
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
}
