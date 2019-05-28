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
import com.example.messenger.model.response.GetListFriendResponse;
import com.example.messenger.utils.HttpUtils;
import com.example.messenger.utils.UserUtils;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import cz.msebera.android.httpclient.Header;

public class CreateGroupFragment extends Fragment {

    private Toolbar myToolbar;
    private TextView okButton;
    private RecyclerView rvListUser;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textViewTotalChecked;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> resultUsers = new ArrayList<>();
    private ArrayList<User> checkedUsers = new ArrayList<>();
    private User currentUser;
    private GroupChatRecyclerAdapter adapter;
    private Gson gson;

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
        gson = new Gson();
        currentUser = UserUtils.getCurrentUser(getActivity());
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
        resultUsers = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        getListFriend(String.valueOf(currentUser.id));
        layoutManager = new LinearLayoutManager(getContext());
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                resultUsers.add(currentUser);
                bundle.putString("topicId", getTopicId(resultUsers));
                bundle.putString("topicName", getTopicName(resultUsers));
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

    private String getTopicId(ArrayList<User> users){
        Collections.sort(users, new UserComparator());
        String topicId = "";
        for (int i = 0; i < users.size(); i++){
            topicId += String.valueOf(users.get(i).id);
            if (i != users.size()-1){
                topicId += "_";
            }

        }
        return topicId;
    }

    private String getTopicName(ArrayList<User> users){
        Collections.sort(users, new UserComparator());
        String topicName = "";
        for (int i = 0; i < users.size(); i++){
            if (i != 0 && !users.get(i).fullname.equals(currentUser.fullname) && !topicName.isEmpty()){
                topicName += ", ";
            }
            if (!users.get(i).fullname.equals(currentUser.fullname)) {
                topicName += users.get(i).fullname;
            }
        }
        return topicName;
    }

    public class UserComparator implements Comparator<User>
    {
        public int compare(User left, User right) {
            return left.id - right.id;
        }
    }

    private void getListFriend(String userId) {
        RequestParams rp = new RequestParams();
        rp.add("userID", userId);

        HttpUtils.post("getListFriendsByUserId", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("GET_TOPIC_RESPONSE", String.valueOf(response));
                GetListFriendResponse getListFriendResponse = gson.fromJson(String.valueOf(response), GetListFriendResponse.class);
                users.clear();
                resultUsers.clear();
                users.addAll(Arrays.asList(getListFriendResponse.response));
                layoutManager = new LinearLayoutManager(getContext());
                rvListUser.setLayoutManager(layoutManager);
                adapter = new GroupChatRecyclerAdapter(users);
                adapter.SetOnItemCheckedListener(new GroupChatRecyclerAdapter.OnItemCheckedListener() {
                    @Override
                    public void onItemChecked(View view, int position, boolean isChecked) {
                        if (isChecked){
                            resultUsers.add(users.get(position));
                        } else {
                            resultUsers.remove(users.get(position));
                        }
                        Log.d("CHECKED USER LENGTH", String.valueOf(resultUsers.size()));
                    }
                });
                rvListUser.setAdapter(adapter);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });
    }
}
