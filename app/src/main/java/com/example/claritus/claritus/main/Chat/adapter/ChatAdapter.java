package com.example.claritus.claritus.main.Chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.claritus.claritus.GlideApp;
import com.example.claritus.claritus.R;
import com.example.claritus.claritus.main.Chat.ChatInterface;
import com.example.claritus.claritus.main.Chat.ChatModal;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{
    private List<ChatModal> chatMsg;
    private Context context;
    private ChatInterface chatInterface;
    private String email;

    public ChatAdapter(Context context, List<ChatModal> chatMsg,String email, ChatInterface chatInterface) {
        this.context = context;
        this.chatMsg = chatMsg;
        this.email = email;
        this.chatInterface = chatInterface;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView msg;
        TextView time;
        ImageView image;
        ImageView download;
        ProgressBar progress;
        ChatViewHolder(View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.msgText);
            time = itemView.findViewById(R.id.msgTime);
            image = itemView.findViewById(R.id.msgImage);
            download = itemView.findViewById(R.id.downloadImg);
            progress = itemView.findViewById(R.id.chatMsgProgress);
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
        if (chatMsg.get(i).getType().equals("text"))
            itemViewHolder.msg.setText(chatMsg.get(i).getText());
        else {
            if (chatMsg.get(i).getLocalPath() != null || chatMsg.get(i).getLocalPath().isEmpty()) {
                itemViewHolder.download.setVisibility(GONE);
                GlideApp.with(context).load(new File(chatMsg.get(i).getLocalPath())).into(itemViewHolder.image);
//                itemViewHolder.image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } else {
                itemViewHolder.download.setVisibility(View.VISIBLE);
                GlideApp.with(context).load(chatMsg.get(i).getUrl()).into(itemViewHolder.image);
                itemViewHolder.download.setVisibility(View.VISIBLE);
            }
            itemViewHolder.image.setVisibility(View.VISIBLE);
            itemViewHolder.msg.setVisibility(GONE);
        }
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(chatMsg.get(i).getTime()));
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        itemViewHolder.time.setText(date);
        if (chatMsg.get(i).isProgress()) {itemViewHolder.progress.setVisibility(View.VISIBLE);}
        else {itemViewHolder.progress.setVisibility(GONE);}
        itemViewHolder.download.setOnClickListener(v -> chatInterface.onDownloadClick(v,i));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}