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

public class GroupChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<User> users = new ArrayList<>();
    private OnItemCheckedListener onItemCheckedListener;

    public interface OnItemCheckedListener {
        void onItemChecked(View view, int position, boolean isChecked);
    }

    public void SetOnItemCheckedListener(final OnItemCheckedListener onItemCheckedListener) {
        this.onItemCheckedListener = onItemCheckedListener;
    }

    public GroupChatRecyclerAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GroupUserHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_group_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        GroupUserHolder groupUserHolder = (GroupUserHolder) viewHolder;
        groupUserHolder.textViewFullname.setText(users.get(i).name);
//        groupUserHolder.imageViewAvatar
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    protected class GroupUserHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewAvatar;
        private TextView textViewFullname;
        private CheckBox checkboxUser;

        public GroupUserHolder(View view) {
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewFullname = (TextView) view.findViewById(R.id.textViewFullname);
            checkboxUser = (CheckBox) view.findViewById(R.id.checkboxUser);
            checkboxUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (onItemCheckedListener!=null){
                        onItemCheckedListener.onItemChecked(buttonView, getAdapterPosition(), isChecked);
                    }
                }
            });
        }
    }
}
