package com.example.messenger;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CreateGroupFragment extends Fragment {

    private Toolbar myToolbar;
    private TextView addIcon;
    private RecyclerView rvListUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }

    private void initView(View view){
        myToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        addIcon = (TextView) view.findViewById(R.id.add_icon);
        rvListUser = (RecyclerView) view.findViewById(R.id.rvListUser);
        myToolbar.setTitle("Tạo nhóm");
        ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.arrow_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);



    }

}
