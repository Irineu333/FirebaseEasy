package com.fb.easy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.Holder> {

    private List<HashMap<String, Object>> list;

    public TestAdapter(List<HashMap<String, Object>> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(
                        android.R.layout.simple_list_item_1,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        HashMap<String, Object> user = list.get(position);

        holder.name.setText((String) user.get("name"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final TextView name;

        public Holder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(android.R.id.text1);
        }
    }
}
