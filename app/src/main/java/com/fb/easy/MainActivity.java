package com.fb.easy;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fb.easy.core.CallBack;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Db.path("users").SINGLE.getGeneric(
                new CallBack.Map() {
                    @Override
                    public void success(Map<String, Object> result) {
                        Log.d("result", String.valueOf(result));
                    }

                    @Override
                    public void error(Throwable throwable) {
                        Log.e("result", throwable.getMessage(), throwable);
                    }
                });
    }
}