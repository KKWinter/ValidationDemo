package com.jumpraw.litepal;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Song extends LitePalSupport {

    private int id;

    //单曲ID
    private String songID;

    //单曲名称
    private String name;

    //单曲描述
    private String des;

    //单曲图标
    private String icon;

    //单曲来源
    private String source;

    //单曲链接
    private String link;

    //单曲所属的歌单
    private List<Album> albumList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", songID='" + songID + '\'' +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", icon='" + icon + '\'' +
                ", source='" + source + '\'' +
                ", link='" + link + '\'' +
                ", albumList=" + albumList +
                '}';
    }
}
