package com.example.messenger.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.messenger.R;
import com.example.messenger.model.LocalFile;
import com.example.messenger.utils.FileUtils;

import java.util.ArrayList;

public class FilesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<LocalFile> localFiles = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public FilesRecyclerAdapter(ArrayList<LocalFile> localFiles, Context context) {
        this.localFiles = localFiles;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FileHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_files_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FileHolder fileHolder = (FileHolder) viewHolder;
        fileHolder.filename.setText(localFiles.get(i).name);
        fileHolder.fileTypeIcon.setImageResource(FileUtils.getFileTypeIcon(localFiles.get(i).name));

    }

    @Override
    public int getItemCount() {
        return localFiles.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected class FileHolder extends RecyclerView.ViewHolder {
        private ImageView fileTypeIcon;
        private TextView filename;
        private RelativeLayout fileLayout;

        public FileHolder(final View view) {
            super(view);
            fileLayout = (RelativeLayout) view.findViewById(R.id.filesItemLayout);
            fileTypeIcon = (ImageView) view.findViewById(R.id.imageViewFileType);
            filename = (TextView) view.findViewById(R.id.textViewFilename);
            fileLayout.setOnClickListener(new View.OnClickListener() {
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
