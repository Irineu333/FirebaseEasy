package com.fb.easy;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fb.easy.callback.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //single
        //request duration
        Db.path("users").get(new Listener.Children.ListMap() {

            @Override
            public void onResult(List<HashMap<String, Object>> result) {

            }

            @Override
            public void onAdded(HashMap<String, Object> child, int index, String key) {

            }

            @Override
            public void onChanged(HashMap<String, Object> child, int index, String key) {

            }

            @Override
            public void onRemoved(HashMap<String, Object> child, int index, String key) {

            }

            @Override
            public void onFailure(Exception e) {
                Log.e("error", e.getMessage(), e);
            }
        });
    }
}