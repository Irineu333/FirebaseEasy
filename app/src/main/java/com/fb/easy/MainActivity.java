package com.fb.easy;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fb.easy.callback.Listener;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Db.path("users").get(new Listener.Children.ListMap() {

            @Override
            public void onResult(List<HashMap<String, Object>> result) {
                Log.d("result", String.valueOf(result));
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("error", e.getMessage(), e);
            }
        });
    }
}