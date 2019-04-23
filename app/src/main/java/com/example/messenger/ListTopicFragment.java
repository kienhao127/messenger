package com.example.messenger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.messenger.adapter.SearchResultRecyclerAdapter;
import com.example.messenger.adapter.TopicRecyclerAdapter;
import com.example.messenger.model.Topic;
import com.example.messenger.model.User;
import com.example.messenger.model.response.GetTopicResponse;
import com.example.messenger.model.response.SearchUserResponse;
import com.example.messenger.utils.ConstUtils;
import com.example.messenger.utils.HttpUtils;
import com.example.messenger.utils.UserUtils;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import androidx.navigation.Navigation;
import cz.msebera.android.httpclient.Header;

public class ListTopicFragment extends Fragment {

    private RecyclerView rvListTopic;
    private TopicRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Topic> topics = new ArrayList<>();
    private ImageView addIcon;

    private RecyclerView.LayoutManager searchLayoutManager;
    private SearchResultRecyclerAdapter searchAdapter;
    private RecyclerView rvSearchResult;

    private User[] users;
    private AlertDialog dialog;

    private User currentUser;
    Gson gson;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(ConstUtils.BASE_URL);
        } catch (URISyntaxException e) {
            Log.e("Socket Exception", e.toString());
        }
    }

    public ListTopicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        currentUser = UserUtils.getCurrentUser(getActivity());
        mSocket.connect();
        mSocket.on("TOPIC_FROM_SERVER", onNewTopic);

        String[] name1 = {"Nguyễn Văn A", "Nguyễn Văn B"};
        String[] name2 = {"Nguyễn Văn C", "Nguyễn Văn D"};
        String[] name3 = {"Nguyễn Văn E"};
        topics.add(new Topic(name1, "Tin nhắn cuối cùng", new Date().getTime(), "1_2", false ));
        topics.add(new Topic(name2, "Tin nhắn cuối cùng", new Date().getTime(), "1_2", false ));
        topics.add(new Topic(name3, "Tin nhắn cuối cùng", new Date().getTime(), "1_2", false ));

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
        adapter = new TopicRecyclerAdapter(getActivity(), topics);
        adapter.SetOnItemClickListener(new TopicRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String topicName =getTopicName(topics.get(position).name);
                String topicId = topics.get(position).topicId;
                Bundle bundle = new Bundle();
                bundle.putString("topicId", topicId);
                bundle.putString("topicName", topicName);
                Navigation.findNavController(getView()).navigate(R.id.messageFragment, bundle);
            }
        });
        rvListTopic.setAdapter(adapter);

        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu dropDownMenu = new PopupMenu(getContext(), addIcon);
                dropDownMenu.getMenuInflater().inflate(R.menu.add_menu, dropDownMenu.getMenu());
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.add_friend){
                            onAddFriendClick();
                        }
                        if (menuItem.getItemId() == R.id.create_room){
                            Navigation.findNavController(view).navigate(R.id.createGroupFragment);
                        }
                        return true;
                    }
                });
                dropDownMenu.show();
            }
        });

    }

    private void onAddFriendClick(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_friend, null);
        final EditText emailEditText = mView.findViewById(R.id.email_edit_text);

        rvSearchResult = (RecyclerView) mView.findViewById(R.id.rvSearchResult);
        Button searchButton = mView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailEditText.getText().toString().isEmpty()){
                    onSearchUserPress(emailEditText.getText().toString());
                }
            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }

    private void onSearchUserPress(String keyword){
        RequestParams rp = new RequestParams();
        rp.add("email", keyword);

        HttpUtils.post("user/searchUser", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("SEARCH_USER_RESPONSE", String.valueOf(response));

                SearchUserResponse searchUserResponse = gson.fromJson(String.valueOf(response), SearchUserResponse.class);
                searchLayoutManager = new LinearLayoutManager(getContext());
                rvSearchResult.setLayoutManager(searchLayoutManager);
                searchAdapter = new SearchResultRecyclerAdapter(searchUserResponse.users);
                users = searchUserResponse.users;
                searchAdapter.SetOnItemClickListener(new SearchResultRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        User currentUser = UserUtils.getCurrentUser(getActivity());
                        int userId[] = {currentUser.id, users[position].id};
                        String topicId = createTopicId(userId);
                        Toast.makeText(getContext(), topicId, Toast.LENGTH_SHORT).show();
                        onSearchResultPress(topicId, users[position]);
                        dialog.dismiss();
                    }
                });
                rvSearchResult.setAdapter(searchAdapter);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });
    }

    private String createTopicId(int userId[]){
        Arrays.sort(userId);
        String topicId = "";
        for (int i = 0; i < userId.length; i++){
            topicId += String.valueOf(userId[i]);
            if (i < userId.length-1){
                topicId += "_";
            }
        }
        return topicId;
    }

    private void onSearchResultPress(String topicId, final User user){
        RequestParams rp = new RequestParams();
        rp.add("topicId", topicId);

        HttpUtils.post("chat/getTopic", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("GET_TOPIC_RESPONSE", String.valueOf(response));

                GetTopicResponse getTopicResponse = gson.fromJson(String.valueOf(response), GetTopicResponse.class);
                User currentUser = UserUtils.getCurrentUser(getActivity());
                if (getTopicResponse.returnCode == 0){
                    int userId[] = {currentUser.id, user.id};
                    String topicId = createTopicId(userId);
                    String topicName = user.name;
                    Bundle bundle = new Bundle();
                    bundle.putString("topicId", topicId);
                    bundle.putString("topicName", topicName);
                    Navigation.findNavController(getView()).navigate(R.id.messageFragment, bundle);
                } else {
                    String topicName =getTopicName(getTopicResponse.topic.name);
                    String topicId = getTopicResponse.topic.topicId;
                    Bundle bundle = new Bundle();
                    bundle.putString("topicId", topicId);
                    bundle.putString("topicName", topicName);
                    Navigation.findNavController(getView()).navigate(R.id.messageFragment, bundle);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });
    }

    private String getTopicName(String[] name){
        String topicName = "";
        for (int i = 0; i < name.length; i++){
            if (i != 0 && !name[i].equals(UserUtils.getCurrentUser(getActivity()).name) && !topicName.isEmpty()){
                topicName += ", ";
            }
            if (!name[i].equals(UserUtils.getCurrentUser(getActivity()).name)) {
                topicName += name[i];
            }
        }
        return topicName;
    }


    private Emitter.Listener onNewTopic = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = String.valueOf(args[0]);
                    Log.d("Message", data);
                    Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
                    Topic topic = gson.fromJson(data, Topic.class);
                    for (int i = 0; i < topics.size(); i++){
                        if (topics.get(i).topicId.equals(topic.topicId)){
                            topics.remove(i);
                            topics.add(0, topic);
                            adapter.notifyDataSetChanged();
                            rvListTopic.smoothScrollToPosition(0);
                            return;
                        }
                    }
                    topics.add(0, topic);
                    adapter.notifyDataSetChanged();
                    rvListTopic.smoothScrollToPosition(0);
                }
            });
        }
    };
}
