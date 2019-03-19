package com.example.messenger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.messenger.adapter.TopicRecyclerAdapter;
import com.example.messenger.model.Topic;
import com.example.messenger.model.User;

import java.util.ArrayList;
import java.util.Date;

import androidx.navigation.Navigation;

public class ListTopicFragment extends Fragment {

    private RecyclerView rvListTopic;
    private TopicRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Topic> topics = new ArrayList<>();
    private ImageView addIcon;


    public ListTopicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        topics.add(new Topic(new User("", 2, "Nguyễn Văn A"), "Tin nhắn cuối cùng", new Date().getTime(), "1_2", false ));
        topics.add(new Topic(new User("", 3, "Nguyễn Văn B"), "Tin nhắn cuối cùng", new Date().getTime(), "1_3", false ));
        topics.add(new Topic(new User("", 4, "Nguyễn Văn C"), "Tin nhắn cuối cùng", new Date().getTime(), "1_4", false ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_topic, container, false);
    }

    private void initView(View view){
        rvListTopic = (RecyclerView) view.findViewById(R.id.rvListTopic);
        addIcon = (ImageView) view.findViewById(R.id.add_icon);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setTitle("Chats");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        layoutManager = new LinearLayoutManager(getContext());
        rvListTopic.setLayoutManager(layoutManager);
        adapter = new TopicRecyclerAdapter(topics);
        adapter.SetOnItemClickListener(new TopicRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("topicID", topics.get(position).topicID);
                Navigation.findNavController(view).navigate(R.id.messageFragment, bundle);
            }
        });
        rvListTopic.setAdapter(adapter);

        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.createGroupFragment);
            }
        });

    }
}
