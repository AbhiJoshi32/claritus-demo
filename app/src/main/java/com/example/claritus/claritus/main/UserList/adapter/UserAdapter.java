package com.example.claritus.claritus.main.UserList.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.main.UserList.UserClickListener;
import com.example.claritus.claritus.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private List<User> users= new ArrayList<>();
    private UserClickListener userClickListener;
    private Context context;

    public UserAdapter(Context context, List<User> users, UserClickListener userClickListener) {
        this.context = context;
        this.users = users;
        this.userClickListener = userClickListener;
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView username;
        UserViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            userClickListener.viewClicked(getLayoutPosition());
        }
    }
    @Override
    public int getItemCount() {
        return users.size();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_card, viewGroup, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder itemViewHolder, int i) {
        itemViewHolder.username.setText(users.get(i).getFirstName());
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
