package com.fb.easy;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fb.easy.callback.Listener;
import com.fb.easy.callback.Single;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Db.path("users").get(new Single.ListMap() {

            @Override
            public void result(List<Map<String, Object>> result) {
                Log.d("result", String.valueOf(result));
            }

            @Override
            public void error(Throwable throwable) {

            }
        });
    }
}