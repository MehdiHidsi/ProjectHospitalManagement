package com.example.hopital1.message;

public class MessageList {
    private String name,mobile,lastmessage;

    private int useenMessages;

    public MessageList(String name, String mobile, String lastmessage, int useenMessages) {
        this.name = name;
        this.mobile = mobile;
        this.lastmessage = lastmessage;
        this.useenMessages = useenMessages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public int getUseenMessages() {
        return useenMessages;
    }

    public void setUseenMessages(int useenMessages) {
        this.useenMessages = useenMessages;
    }
}
