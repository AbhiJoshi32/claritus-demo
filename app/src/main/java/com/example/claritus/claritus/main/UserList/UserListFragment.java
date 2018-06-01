package com.example.claritus.claritus.main.UserList;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.di.Injectable;
import com.example.claritus.claritus.main.MainNavigationController;
import com.example.claritus.claritus.main.UserList.adapter.UserAdapter;
import com.example.claritus.claritus.model.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


public class UserListFragment extends Fragment implements Injectable {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> users = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    @Inject
    MainNavigationController mainNavigationController;

    public UserListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Friends");
        recyclerView = view.findViewById(R.id.userListRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        userAdapter = new UserAdapter(getContext(),users, position -> {
            mainNavigationController.navigateToChat(users.get(position).getUid(),users.get(position).getEmail());
        });
        recyclerView.setAdapter(userAdapter);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        database.getReference().child("Users").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> retUsers = new ArrayList<>();
                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                            Timber.d(userSnapshot.getValue().toString());
                            User user = userSnapshot.getValue(User.class);
                            retUsers.add(user);
                        }
                        users.clear();
                        users.addAll(retUsers);
                        userAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}