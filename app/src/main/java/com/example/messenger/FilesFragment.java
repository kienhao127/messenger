package com.example.messenger;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messenger.adapter.FilesRecyclerAdapter;
import com.example.messenger.fragmentCallback.FilesFragmentCallback;
import com.example.messenger.fragmentCallback.GalleryFragmentCallback;
import com.example.messenger.model.LocalFile;
import com.example.messenger.model.SharedViewModel;
import com.example.messenger.utils.FileUtils;

import java.util.ArrayList;
import java.util.Collections;


public class FilesFragment extends Fragment {
    private RecyclerView filesRecyclerView;
    private ArrayList<LocalFile> files;
    private RecyclerView.LayoutManager layoutManager;
    private FilesRecyclerAdapter adapter;
    private SharedViewModel viewModel;

    public FilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        files = FileUtils.getAllFilesOfDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.setLocalFile(null);
        Collections.reverse(files);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_files, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setTitle("Thư viện");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        filesRecyclerView = (RecyclerView) view.findViewById(R.id.filesRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        filesRecyclerView.setLayoutManager(layoutManager);
        adapter = new FilesRecyclerAdapter(files, getContext());
        //set load more listener for the RecyclerView adapter
        adapter.setOnItemClickListener(new FilesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                viewModel.setLocalFile(files.get(position));
                getActivity().onBackPressed();
            }
        });

        filesRecyclerView.setAdapter(adapter);
    }
}
