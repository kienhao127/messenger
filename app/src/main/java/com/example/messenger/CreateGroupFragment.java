package com.example.messenger;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.messenger.adapter.GroupChatRecyclerAdapter;
import com.example.messenger.model.User;

import java.util.ArrayList;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

public class CreateGroupFragment extends Fragment {

    private Toolbar myToolbar;
    private TextView okButton;
    private RecyclerView rvListUser;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textViewTotalChecked;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> resultUsers = new ArrayList<>();
    private ArrayList<User> checkedUsers = new ArrayList<>();
    GroupChatRecyclerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        users.add(new User("", 2, "Nguyễn Văn A"));
        users.add(new User("", 3, "Nguyễn Văn B"));
        users.add(new User("", 4, "Nguyễn Văn C"));
        users.add(new User("", 5, "Nguyễn Văn D"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }

    private void initView(View view) {
        myToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        okButton = (TextView) view.findViewById(R.id.ok_button);
        rvListUser = (RecyclerView) view.findViewById(R.id.rvListUser);
        textViewTotalChecked = (TextView) view.findViewById(R.id.textViewTotalChecked);
        textViewTotalChecked.setText(String.valueOf(checkedUsers.size()));
        myToolbar.setTitle("Tạo nhóm");
        ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.arrow_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        resultUsers = (ArrayList<User>) users.clone();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);


        layoutManager = new LinearLayoutManager(getContext());
        rvListUser.setLayoutManager(layoutManager);
        adapter = new GroupChatRecyclerAdapter(resultUsers);
        adapter.SetOnItemCheckedListener(new GroupChatRecyclerAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(View view, int position, boolean isChecked) {
                if (isChecked) {
                    checkedUsers.add(resultUsers.get(position));
                } else {
                    checkedUsers.remove(resultUsers.get(position));
                }
                textViewTotalChecked.setText(String.valueOf(checkedUsers.size()));
            }
        });
        rvListUser.setAdapter(adapter);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("topicId", "2_12_22");
                bundle.putString("topicName", "Tên nhóm");
                Navigation.findNavController(getView())
                        .navigate(
                                R.id.action_createGroupFragment_to_messageFragment,
                                bundle,
                                new NavOptions.Builder()
                                        .setPopUpTo(R.id.createGroupFragment,
                                                true).build());
            }
        });
    }
}
