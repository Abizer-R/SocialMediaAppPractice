package com.example.socialmediaapp_1.Models;

public class ActivityModel {

    private String profileName;
    private int profileImage;
    private int timestamp;
    private Integer storyPostImage;
    /**
     * Type 1: Someone Liked your story
     * Type 2: Someone Liked your post
     * Type 3: Someone started following you
     * Type 4: Someone commented on your post
     * Type 5 Someone requested to follow you
     * Type 6: Today / Yesterday / This Week / This Month / Earlier
     */
    private int activityType;
    private String activityText;

    public ActivityModel(String profileName, int profileImage, int timestamp, Integer storyPostImage, int notificationType, String commentText) {
        this.profileName = profileName;
        this.profileImage = profileImage;
        this.timestamp = timestamp;
        this.storyPostImage = storyPostImage;
        this.activityType = notificationType;
        this.activityText = commentText;
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

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getStoryPostImage() {
        return storyPostImage;
    }

    public void setStoryPostImage(int storyPostImage) {
        this.storyPostImage = storyPostImage;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getActivityText() {
        return activityText;
    }

    public void setActivityText(String activityText) {
        this.activityText = activityText;
    }
}
