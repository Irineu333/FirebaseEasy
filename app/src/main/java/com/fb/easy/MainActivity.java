package com.fb.easy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fb.easy.core.CallBack;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Db.path("users").LISTENER.CHILDREN.getListMap(
                new CallBack.ListMap() {
                    @Override
                    public void success(List<Map<String, Object>> result) {

                    }

                    @Override
                    public void error(Throwable throwable) {

                    }
                }
        );
    }
}