package com.example.claritus.claritus.main.Chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.di.Injectable;
import com.example.claritus.claritus.main.Chat.adapter.ChatAdapter;
import com.example.claritus.claritus.main.MainNavigationController;
import com.example.claritus.claritus.model.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


public class ChatFragment extends Fragment implements Injectable {
    private RecyclerView recyclerView;
    private Button button;
    private ChatAdapter chatAdapter;
    private List<ChatModal> chatMsgs = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private EditText msg;
    private String uid;
    private String fname;
    @Inject
    MainNavigationController mainNavigationController;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        uid = getArguments().getString("uid");
        fname = getArguments().getString("name");
        recyclerView = view.findViewById(R.id.chatRecycler);
        button = view.findViewById(R.id.sendBtn);
        msg = view.findViewById(R.id.msgInput);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(getContext(), chatMsgs);
        recyclerView.setAdapter(chatAdapter);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        DatabaseReference msgRef = database.getReference().child("Messages").
                child(auth.getCurrentUser().getUid());
        DatabaseReference fmsgRef = database.getReference().child("Messages").
                child(uid);
        msgRef.
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<ChatModal> chatModals = new ArrayList<>();
                        for (DataSnapshot chatSnapshot: dataSnapshot.getChildren()) {
                            ChatModal chatModal = chatSnapshot.getValue(ChatModal.class);
                            chatModals.add(chatModal);
                        }
                        chatMsgs.clear();
                        chatMsgs.addAll(chatModals);
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        button.setOnClickListener(view1 -> {
            if (msg.getText()!= null && !msg.getText().toString().isEmpty()) {
                String key = msgRef.push().getKey();
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();
                ChatModal chatModal = new ChatModal(key,
                        msg.getText().toString(),
                        fname,
                        auth.getCurrentUser().getUid(),ts);
                msgRef.child(key).setValue(chatModal);
                fmsgRef.child(key).setValue(chatModal);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}