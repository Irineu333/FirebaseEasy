package com.fb.easy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //generic

        Job job = Db.path("users").listenerListGeneric(
                new CallBack.ListGeneric<User>(User.class) {
                    @Override
                    void error(Throwable throwable) {

                    }

                    @Override
                    void success(List<User> result) {

                    }
                });

        job.stop();
    }
}