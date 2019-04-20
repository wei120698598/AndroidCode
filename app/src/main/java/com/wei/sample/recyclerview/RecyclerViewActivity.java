package com.wei.sample.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wei.sample.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019/4/9
 * @email weishuxin@icourt.cc
 */
public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        getSupportActionBar().setTitle("RecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        initRecyclerView(recyclerView);
    }

    public static void initRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(recyclerView.getContext().getResources().getDrawable(android.R.drawable.divider_horizontal_textfield));
        recyclerView.addItemDecoration(dividerItemDecoration);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            data.add("item " + i);
        }
        adapter.setData(data);
        recyclerView.setAdapter(adapter);
    }


    public static class RecyclerViewAdapter extends RecyclerView.Adapter<TestViewHolder> {
        private List<String> data = new ArrayList<>();

        public void setData(List<String> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_recyclerview, parent,false);
            return new TestViewHolder(contentView);
        }

        @Override
        public void onBindViewHolder(@NonNull TestViewHolder holder, final int position) {
            holder.textView.setText(data.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "点击：" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }


    public static class TestViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_text);
        }
    }
}
