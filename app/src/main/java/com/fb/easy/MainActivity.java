package com.fb.easy;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fb.easy.callback.Single;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //single
        //request duration
        Db.path("users").get(new Single.ListMap() {

            @Override
            public void onResult(List<HashMap<String, Object>> result) {
                Log.d("result", String.valueOf(result));
                Log.d("duration", String.valueOf(getDuration()));
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("error", e.getMessage(), e);
            }
        });
    }
}