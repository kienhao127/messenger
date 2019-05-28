package com.example.messenger.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.messenger.R;
import com.example.messenger.model.Topic;
import com.example.messenger.model.User;
import com.example.messenger.utils.TimeUtils;

import java.util.ArrayList;

public class SearchResultRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private User[] users;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SearchResultRecyclerAdapter(User[] users) {
        this.users = users;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SearchUserHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_search_result_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SearchUserHolder searchUserHolder = (SearchUserHolder) viewHolder;
        searchUserHolder.textViewFullname.setText(users[i].fullname);
//        groupUserHolder.imageViewAvatar
    }

    @Override
    public int getItemCount() {
        return users.length;
    }

    protected class SearchUserHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewAvatar;
        private TextView textViewFullname;
        private ImageView sendButton;

        public SearchUserHolder(View view) {
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewFullname = (TextView) view.findViewById(R.id.textViewFullname);
            sendButton = (ImageView) view.findViewById(R.id.send_button);
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

        }
    }
}

