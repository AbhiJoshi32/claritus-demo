package com.example.claritus.claritus.main.Chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.main.Chat.ChatModal;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{
    private List<ChatModal> chatMsg= new ArrayList<>();
    private Context context;
    String email;

    public ChatAdapter(Context context, List<ChatModal> chatMsg,String email) {
        this.context = context;
        this.chatMsg = chatMsg;
        this.email = email;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView msg;
        TextView time;
        ChatViewHolder(View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.msgText);
            time = itemView.findViewById(R.id.msgTime);
        }
    }
    @Override
    public int getItemCount() {
        return chatMsg.size();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        if (chatMsg.get(i).getSender().equals(email))
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.right_chat_bubble, viewGroup, false);
        else v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.left_chat_bubble, viewGroup, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder itemViewHolder, int i) {
        itemViewHolder.msg.setText(chatMsg.get(i).getText());
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(chatMsg.get(i).getTime()));
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        itemViewHolder.time.setText(date);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}