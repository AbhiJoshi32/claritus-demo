package com.example.claritus.claritus.main.Chat;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ChatModal {
    private String id;
    private String text;
    private String sender;
    private String reciever;
    private String time;
    private String type;
    private String url;
    private String localPath;
    private String imageRef;
    private boolean progress = false;

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ChatModal() {
    }

    public ChatModal(String id, String text, String sender, String reciever, String time,String type,String url) {
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.type = type;
        this.url = url;
        this.reciever = reciever;
        this.time = time;
    }

    public ChatModal(String id, String text, String sender, String reciever, String time, String type, String url, String localPath,String imageRef) {
        this.imageRef = imageRef;
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.reciever = reciever;
        this.time = time;
        this.type = type;
        this.url = url;
        this.localPath = localPath;
    }



    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
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