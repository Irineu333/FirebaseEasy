package com.fb.easy;

import android.os.Bundle;
import android.util.ArrayMap;

import androidx.appcompat.app.AppCompatActivity;

import com.fb.easy.callback.Result;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Map<String, Object> map = new ArrayMap<>();

        map.put("name", "Aderson");

        Db.path("users").post(map, new Result.Post() {
            @Override
            public void onSuccess(String key) {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }
}