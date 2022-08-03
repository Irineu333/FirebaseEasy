package com.fb.easy;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fb.easy.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final List<HashMap<String, Object>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TestAdapter adapter = new TestAdapter(list);

        binding.rvList.setAdapter(adapter);

        Job job = Db.path("users").createJob(new Listener.Children.ListMap(list) {

            @Override
            public void onResult(List<HashMap<String, Object>> result) {
                Log.d("result", String.valueOf(result));
                binding.tvCount.setText(String.valueOf(result.size()));
            }

            @Override
            public void onAdded(HashMap<String, Object> child, int index, String key) {
                adapter.notifyItemInserted(index);
            }

            @Override
            public void onChanged(HashMap<String, Object> child, int index, String key) {
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onRemoved(HashMap<String, Object> child, int index, String key) {
                adapter.notifyItemRemoved(index);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("error", e.getMessage(), e);
            }
        });

        job.setOnStartListener(() -> Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show());

        job.setOnStopListener(() -> Toast.makeText(this, "Stoped", Toast.LENGTH_SHORT).show());

        binding.btnStart.setOnClickListener(v -> {
                    int size = list.size();
                    list.clear();
                    adapter.notifyItemRangeRemoved(0, size);
                    job.start();
                }
        );

        binding.btnStop.setOnClickListener(v -> job.stop());

        binding.btnStopAll.setOnClickListener(v -> Job.stopAll());
    }
}