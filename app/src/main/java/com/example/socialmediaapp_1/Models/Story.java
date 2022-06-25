package com.example.socialmediaapp_1.Models;

public class Story {

    private String profileName;
    private int profileImage;
    private Boolean seen;   //TODO: implemented seen/unseen outer circle on story

    public Story(String profileName, int profileImage, Boolean seen) {
        this.profileName = profileName;
        this.profileImage = profileImage;
        this.seen = seen;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean isSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
}
