package com.jumpraw.sms;

public class ModuleMessage {

    private int MessId;
    private int MessTid;
    private String address;
    private String Person;
    private long date;
    private int type;
    private String content;

    public ModuleMessage(int messId, int messTid, String address, String person, long date, int type, String content) {
        this.MessId = messId;
        this.MessTid = messTid;
        this.address = address;
        this.Person = person;
        this.date = date;
        this.type = type;
        this.content = content;
    }

    public int getMessId() {
        return MessId;
    }

    public void setMessId(int messId) {
        MessId = messId;
    }

    public int getMessTid() {
        return MessTid;
    }

    public void setMessTid(int messTid) {
        MessTid = messTid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPerson() {
        return Person;
    }

    public void setPerson(String person) {
        Person = person;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ModuleMessage{" +
                "MessId=" + MessId +
                ", MessTid=" + MessTid +
                ", address='" + address + '\'' +
                ", Person='" + Person + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
