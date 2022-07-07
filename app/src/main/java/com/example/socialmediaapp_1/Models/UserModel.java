package com.example.socialmediaapp_1.Models;

public class UserModel {

    private String name;
    private String username;
    private String imgurl;
    private String Uid;
//    private String status;
//    private String token;


    public UserModel() {
    }

    public UserModel(String name, String username, String imgurl, String Uid) {
        this.name = name;
        this.username = username;
        this.imgurl = imgurl;
        this.Uid = Uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
