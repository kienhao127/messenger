package com.example.messenger;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messenger.adapter.MessageRecyclerAdapter;
import com.example.messenger.model.Message;
import com.example.messenger.model.User;

import java.util.ArrayList;
import java.util.Date;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class MessageFragment extends Fragment {


    private static final int REQUEST_PERMISSIONS = 100;
    private RecyclerView rvListMessage;
//    private TextView textViewLoading;
//    private TextView textViewNoMessage;
    private RelativeLayout textInputLayout;
    private ImageView imageViewEmoji;
    private ImageView imageViewAttachFile;
    private ImageView imageViewImage;
    private ImageView imageViewSend;
    private EditText editText;
    private MessageRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Message> messages = new ArrayList<>();


    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messages.add(new Message(1, 1, new User("", 1, "Hào"), "Tin nhắn 1", 1552905040998.0, "1_2"));
        messages.add(new Message(1, 1, new User("", 2, "Hào"), "Tin nhắn 2", 1552905040998.0, "1_2"));
        messages.add(new Message(1, 1, new User("", 2, "Hào"), "Tin nhắn 3", 1552905040998.0, "1_2"));
        messages.add(new Message(1, 1, new User("", 1, "Hào"), "Tin nhắn 4", 1552905040998.0, "1_2"));
        messages.add(new Message(1, 1, new User("", 1, "Hào"), "Tin nhắn 5", 1552905040998.0, "1_2"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    private void initView(View view){
        rvListMessage = (RecyclerView) view.findViewById(R.id.rvListMessage);
//        textViewLoading = (TextView) view.findViewById(R.id.textViewLoading);
//        textViewOver = (TextView) view.findViewById(R.id.textViewNoMessage);
        textInputLayout = (RelativeLayout) view.findViewById(R.id.textInputLayout);
        imageViewEmoji = (ImageView) view.findViewById(R.id.imageViewEmoji);
        imageViewAttachFile = (ImageView) view.findViewById(R.id.imageViewAttachFile);
        imageViewImage = (ImageView) view.findViewById(R.id.imageViewImage);
        imageViewSend = (ImageView) view.findViewById(R.id.imageViewSend);
        editText = (EditText) getView().findViewById(R.id.editText);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("LOG", s.toString().trim().length() + "");
                if (s.toString().trim().length() == 0){
                    imageViewAttachFile.setVisibility(View.VISIBLE);
                    imageViewImage.setVisibility(View.VISIBLE);
                    imageViewSend.setVisibility(View.GONE);
                } else {
                    imageViewAttachFile.setVisibility(View.GONE);
                    imageViewImage.setVisibility(View.GONE);
                    imageViewSend.setVisibility(View.VISIBLE);
                }
            }
        });
        imageViewEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Emoji", "Clicked");
            }
        });
        imageViewAttachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Attach", "Clicked");
            }
        });
        imageViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Image", "Clicked");
                hideKeyboard(v);
                if ((ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                }else {
                    Navigation.findNavController(getView()).navigate(R.id.galleryFragment);
                }
            }
        });
        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Send", "Clicked");
                String content = editText.getText().toString();
                messages.add(new Message(1, 1, new User("", 1, "Hào"), content, new Date().getTime(), "1_2"));
                adapter.notifyDataSetChanged();
                editText.setText("");
                rvListMessage.smoothScrollToPosition(messages.size()-1);
            }
        });


        layoutManager = new LinearLayoutManager(getContext());
        rvListMessage.setLayoutManager(layoutManager);
        adapter = new MessageRecyclerAdapter(messages);
        rvListMessage.scrollToPosition(adapter.getItemCount() - 1);
        rvListMessage.setAdapter(adapter);

        //Tap ngoài editext sẽ ẩn bàn phím
        rvListMessage.setOnTouchListener(new View.OnTouchListener() {
            float x1, x2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x1 = event.getX();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    x2 = event.getX();
                    if (x1 == x2) {
                        hideKeyboard(v);
                    }
                }
                return false;
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Navigation.findNavController(getView()).navigate(R.id.galleryFragment);
                    } else {
                        Toast.makeText(getContext(), "Xin vui lòng cấp quyền đọc dữ liệu để sử dụng chức năng này", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

}
