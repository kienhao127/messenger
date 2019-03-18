package com.example.messenger.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.messenger.R;
import com.example.messenger.model.Message;
import com.example.messenger.utils.TimeUtils;

import java.util.ArrayList;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Message> messages = new ArrayList<>();
    private OnItemLongClickListener mItemLongClickListener;

    public MessageRecyclerAdapter(ArrayList<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Kiểm tra type của tin nhắn tại vị trí i:
        switch (messages.get(i).type) {
            //nếu Type === TEXT
            case Message.TEXT: {
                //Nếu userID === currentUserID ? layout_message_text_owner : layout_message_text
                if (messages.get(i).user.id == 1) {
                    return new TextHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_text_owner, viewGroup, false));
                } else  {
                    return new TextHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_text, viewGroup, false));
                }

            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.d("onBindViewHolder", messages.get(i).content);
        switch (messages.get(i).type) {
            case Message.TEXT: {
                if (messages.get(i).user.id == 1) {
                    TextHolder textHolder = (TextHolder) viewHolder;
                    textHolder.textViewContent.setText(messages.get(i).content);
                    textHolder.textViewSendTime.setText(TimeUtils.getRelativeTimeSpanString(((Message) messages.get(i)).sendTime));
                    if ((i-1 >= 0 && TimeUtils.CompareDate(messages.get(i-1).sendTime, messages.get(i).sendTime, 15)) || i==0){
                        textHolder.textViewSendTime.setVisibility(View.VISIBLE);
                    }
                } else  {
                    TextHolder textHolder = (TextHolder) viewHolder;
//                    textHolder.imageViewAvatar
                    textHolder.textViewContent.setText(messages.get(i).content);
                    textHolder.textViewSendTime.setText(TimeUtils.getRelativeTimeSpanString(((Message) messages.get(i)).sendTime));
                    if (i-1 >= 0 && messages.get(i-1).user.id != 1){
                        textHolder.imageViewAvatar.setVisibility(View.INVISIBLE);
                    }
                    if ((i-1 >= 0 && TimeUtils.CompareDate(messages.get(i-1).sendTime, messages.get(i).sendTime, 15)) || i==0){
                        textHolder.textViewSendTime.setVisibility(View.VISIBLE);
                    }
                }
                return;
            }
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void SetOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }


    protected class TextHolder extends RecyclerView.ViewHolder {
        private TextView textViewSendTime;
        private ImageView imageViewAvatar;
        private TextView textViewContent;

        public TextHolder(final View view) {
            super(view);
            textViewSendTime = (TextView) view.findViewById(R.id.textViewSendTime);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewContent = (TextView) view.findViewById(R.id.textViewContent);
            textViewContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textViewSendTime.getVisibility() == View.VISIBLE){
                        textViewSendTime.setVisibility(View.GONE);
                    } else {
                        textViewSendTime.setVisibility(View.VISIBLE);
                    }
                }
            });
            textViewContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemLongClickListener!=null){
                        mItemLongClickListener.onItemLongClick(view, getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }
}
