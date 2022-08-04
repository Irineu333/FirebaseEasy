package com.fb.easy;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fb.easy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTest.setOnClickListener(view ->
                Db.path("timestamp").getTimestamp(
                        new Single.Long() {
                            @Override
                            public void onResult(Long result) {

                                Log.d("Test", String.valueOf(result));
                            }

                            @Override
                            public void onFailure(Exception e) {
                            }
                        }
                ));

    }
}