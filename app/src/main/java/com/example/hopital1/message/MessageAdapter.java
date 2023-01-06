package com.example.hopital1.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopital1.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyviewHolder> {

    private final List<MessageAdapter> messageLists;
    private final Context context;

    public MessageAdapter(List<MessageAdapter> messageLists, Context context) {
        this.messageLists = messageLists;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messageLists.size();
    }

    static class MyviewHolder extends RecyclerView.ViewHolder {
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
