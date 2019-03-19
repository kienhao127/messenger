package com.example.messenger.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.messenger.R;
import com.example.messenger.model.Topic;
import com.example.messenger.utils.TimeUtils;

import java.util.ArrayList;

public class TopicRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Topic> topics = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TopicRecyclerAdapter(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TopicHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_topic, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TopicHolder topicHolder = (TopicHolder) viewHolder;
        topicHolder.textViewSendTime.setText(TimeUtils.getRelativeTimeSpanString(((Topic) topics.get(i)).sendTime));
//        topicHolder.imageViewAvatar
//        topicHolder.imageViewUnreadDot
        topicHolder.textViewName.setText(topics.get(i).user.name);
        topicHolder.textViewLastMess.setText(topics.get(i).lastMess);
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    protected class TopicHolder extends RecyclerView.ViewHolder {
        private RelativeLayout topicLayout;
        private ImageView imageViewAvatar;
        private TextView textViewName;
        private TextView textViewLastMess;
        private TextView textViewSendTime;
        private ImageView imageViewUnreadDot;

        public TopicHolder(View view) {
            super(view);
            topicLayout = (RelativeLayout) view.findViewById(R.id.topicLayout);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewName = (TextView) view.findViewById(R.id.textViewName);
            textViewLastMess = (TextView) view.findViewById(R.id.textViewLastMess);
            textViewSendTime = (TextView) view.findViewById(R.id.textViewSendTime);
            imageViewUnreadDot = (ImageView) view.findViewById(R.id.imageViewUnreadDot);

            topicLayout.setOnClickListener(new View.OnClickListener() {
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
