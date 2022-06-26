package com.example.socialmediaapp_1.Models;

public class FeedPost {

    private String profileName;
    private String caption;
    private int profileImage;
    private int postImage;
    private int likeCount;
    private int commentCount;
    private boolean bookmarked;
    private boolean liked;

    public FeedPost(String profileName, String caption, int profileImage, int postImage, int likeCount, int commentCount, boolean bookmarked, boolean liked) {
        this.profileName = profileName;
        this.caption = caption;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.bookmarked = bookmarked;
        this.liked = liked;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
