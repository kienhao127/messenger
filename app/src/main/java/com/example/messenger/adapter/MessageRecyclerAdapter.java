package com.example.messenger.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.messenger.R;
import com.example.messenger.model.Message;
import com.example.messenger.model.MessageFile;
import com.example.messenger.model.MessagePhoto;
import com.example.messenger.model.User;
import com.example.messenger.utils.ImageUtils;
import com.example.messenger.utils.TimeUtils;
import com.example.messenger.utils.UserUtils;

import java.io.File;
import java.util.ArrayList;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Message> messages = new ArrayList<>();
    private User currentUser = new User();
    private OnItemLongClickListener mItemLongClickListener;
    private OnItemClickListener mItemClickListener;
    private Context context;

    public MessageRecyclerAdapter(ArrayList<Message> messages, User currentUser, Context context) {
        this.messages = messages;
        this.currentUser = currentUser;
        this.context = context;
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
                if (messages.get(i).senderId == currentUser.id) {
                    return new TextHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_text_owner, viewGroup, false));
                } else  {
                    return new TextHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_text, viewGroup, false));
                }
            }
            case Message.IMAGE: {
                //Nếu userID === currentUserID ? layout_message_text_owner : layout_message_text
                if (messages.get(i).senderId == currentUser.id) {
                    return new ImageHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_image_owner, viewGroup, false));
                } else  {
                    return new ImageHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_image, viewGroup, false));
                }
            }
            case Message.FILE: {
                //Nếu userID === currentUserID ? layout_message_text_owner : layout_message_text
                if (messages.get(i).senderId == currentUser.id) {
                    return new FileHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_file_owner, viewGroup, false));
                } else  {
                    return new FileHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_file, viewGroup, false));
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
                TextHolder textHolder = (TextHolder) viewHolder;

                if (messages.get(i).senderId == currentUser.id) {
                    textHolder.textViewContent.setText(messages.get(i).content);
                    textHolder.textViewSendTime.setText(TimeUtils.getRelativeTimeSpanString(((Message) messages.get(i)).sendTime));
                    if ((i-1 >= 0 && TimeUtils.CompareDate(messages.get(i-1).sendTime, messages.get(i).sendTime, 15)) || i==0){
                        textHolder.textViewSendTime.setVisibility(View.VISIBLE);
                    }
                } else  {
                    if (!messages.get(i).avatar.isEmpty()){
                        Glide.with(context)
                                .load(messages.get(i).avatar)
                                .apply(RequestOptions.circleCropTransform())
                                .into(textHolder.imageViewAvatar);
                    }
                    textHolder.textViewContent.setText(messages.get(i).content);
                    textHolder.textViewSendTime.setText(TimeUtils.getRelativeTimeSpanString(((Message) messages.get(i)).sendTime));
                    if (i-1 >= 0 && messages.get(i-1).senderId == messages.get(i).senderId ){
                        textHolder.imageViewAvatar.setVisibility(View.INVISIBLE);
                    }
                    if ((i-1 >= 0 && TimeUtils.CompareDate(messages.get(i-1).sendTime, messages.get(i).sendTime, 15)) || i==0){
                        textHolder.textViewSendTime.setVisibility(View.VISIBLE);
                    }
                }
                return;
            }
            case Message.IMAGE: {
                ImageHolder imageHolder = (ImageHolder) viewHolder;
                if (messages.get(i).senderId == currentUser.id) {
                    imageHolder.messageImage.setClipToOutline(true);
                    if (!messages.get(i).content.isEmpty()){
                        imageHolder.messageImage.setImageBitmap(ImageUtils.base64ToBitmap(messages.get(i).content));
                    } else {
                        Glide.with(context)
                                .load(((MessagePhoto) messages.get(i)).photoURL)
                                .into(imageHolder.messageImage);
                    }
                    imageHolder.textViewSendTime.setText(TimeUtils.getRelativeTimeSpanString(((Message) messages.get(i)).sendTime));
                    if ((i-1 >= 0 && TimeUtils.CompareDate(messages.get(i-1).sendTime, messages.get(i).sendTime, 15)) || i==0){
                        imageHolder.textViewSendTime.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (!messages.get(i).avatar.isEmpty()){
                        Glide.with(context)
                                .load((messages.get(i)).avatar)
                                .apply(RequestOptions.circleCropTransform())
                                .into(imageHolder.imageViewAvatar);
                    }
                    imageHolder.messageImage.setClipToOutline(true);
                    if (!messages.get(i).content.isEmpty()) {
                        imageHolder.messageImage.setImageBitmap(ImageUtils.base64ToBitmap(messages.get(i).content));
                    } else {
                        Glide.with(context)
                                .load(((MessagePhoto) messages.get(i)).photoURL)
                                .into(imageHolder.messageImage);
                    }
                    imageHolder.textViewSendTime.setText(TimeUtils.getRelativeTimeSpanString(((Message) messages.get(i)).sendTime));
                    if (i-1 >= 0 && messages.get(i-1).senderId == messages.get(i).senderId ){
                        imageHolder.imageViewAvatar.setVisibility(View.INVISIBLE);
                    }
                    if ((i - 1 >= 0 && TimeUtils.CompareDate(messages.get(i - 1).sendTime, messages.get(i).sendTime, 15)) || i == 0) {
                        imageHolder.textViewSendTime.setVisibility(View.VISIBLE);
                    }
                }
                return;
            }
            case Message.FILE: {
                FileHolder fileHolder = (FileHolder) viewHolder;
                if (messages.get(i).senderId == currentUser.id) {
                    fileHolder.textViewContent.setText(((MessageFile) messages.get(i)).filename);
                    fileHolder.textViewContent.setPaintFlags(fileHolder.textViewContent.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                    fileHolder.textViewSendTime.setText(TimeUtils.getRelativeTimeSpanString((messages.get(i)).sendTime));
                    if ((i-1 >= 0 && TimeUtils.CompareDate(messages.get(i-1).sendTime, messages.get(i).sendTime, 15)) || i==0){
                        fileHolder.textViewSendTime.setVisibility(View.VISIBLE);
                    }
                } else  {
                    if (!messages.get(i).avatar.isEmpty()){
                        Glide.with(context)
                                .load((messages.get(i)).avatar)
                                .apply(RequestOptions.circleCropTransform())
                                .into(fileHolder.imageViewAvatar);
                    }
                    fileHolder.textViewContent.setText(((MessageFile) messages.get(i)).filename);
                    fileHolder.textViewContent.setPaintFlags(fileHolder.textViewContent.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                    fileHolder.textViewSendTime.setText(TimeUtils.getRelativeTimeSpanString((messages.get(i)).sendTime));
                    if (i-1 >= 0 && messages.get(i-1).senderId == messages.get(i).senderId ){
                        fileHolder.imageViewAvatar.setVisibility(View.INVISIBLE);
                    }
                    if ((i-1 >= 0 && TimeUtils.CompareDate(messages.get(i-1).sendTime, messages.get(i).sendTime, 15)) || i==0){
                        fileHolder.textViewSendTime.setVisibility(View.VISIBLE);
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

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
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

    protected class ImageHolder extends RecyclerView.ViewHolder {
        private TextView textViewSendTime;
        private ImageView imageViewAvatar;
        private ImageView messageImage;

        public ImageHolder(final View view) {
            super(view);
            textViewSendTime = (TextView) view.findViewById(R.id.textViewSendTime);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            messageImage = (ImageView) view.findViewById(R.id.messageImage);
            messageImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textViewSendTime.getVisibility() == View.VISIBLE){
                        textViewSendTime.setVisibility(View.GONE);
                    } else {
                        textViewSendTime.setVisibility(View.VISIBLE);
                    }
                }
            });
            messageImage.setOnLongClickListener(new View.OnLongClickListener() {
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

    protected class FileHolder extends RecyclerView.ViewHolder {
        private TextView textViewSendTime;
        private ImageView imageViewAvatar;
        private TextView textViewContent;

        public FileHolder(final View view) {
            super(view);
            textViewSendTime = (TextView) view.findViewById(R.id.textViewSendTime);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewContent = (TextView) view.findViewById(R.id.textViewContent);
            textViewContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener!=null){
                        mItemClickListener.onItemClick(view, getAdapterPosition());
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
