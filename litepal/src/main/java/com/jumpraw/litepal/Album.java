package com.jumpraw.litepal;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Album extends LitePalSupport {

    private int id;

    //歌单ID
    private String albumID;

    //歌单名称
    private String name;

    //歌单所有者
    private String owner;

    //歌单封面
    private String cover;

    //歌单类型
    private String type;

    //歌单中的单曲
    private List<Song> songList = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", albumID='" + albumID + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", cover='" + cover + '\'' +
                ", type='" + type + '\'' +
                ", songList=" + songList +
                '}';
    }
}
