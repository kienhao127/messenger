package com.example.messenger.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.messenger.R;

import java.util.ArrayList;

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> images = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public GalleryRecyclerAdapter(ArrayList<String> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ImageHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_gallery_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ImageHolder imageHolder = (ImageHolder) viewHolder;
        Glide.with(context)
                .load(images.get(i))
                .into(imageHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImageHolder(final View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
        }
    }
}
