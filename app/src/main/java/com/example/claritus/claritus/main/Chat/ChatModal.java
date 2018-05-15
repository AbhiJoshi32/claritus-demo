package com.example.claritus.claritus.main.Chat;

public class ChatModal {
    private String id;
    private String text;
    private String sender;
    private String reciever;
    private String time;

    public ChatModal() {
    }

    public ChatModal(String id, String text, String sender, String reciever, String time) {
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.reciever = reciever;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}