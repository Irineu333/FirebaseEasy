package com.fb.easy;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //generic

        Db.path("users").getListMap(
                new CallBack.ListMap() {
                    @Override
                    void success(List<Map<String, Object>> result) {
                        Log.d("result", String.valueOf(result));
                    }

                    @Override
                    void error(Throwable throwable) {
                        Log.e("result", throwable.getMessage(), throwable);
                    }
                });
    }
}