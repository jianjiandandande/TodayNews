package edu.nuc.vincent.com.todaynews.entity;

import android.net.Uri;

import java.util.Date;

/**
 * Created by Vincent on 2018/6/22.
 */

public class VideoItem {

    private String videoUri;

    private Uri userIconUri;

    private String username;

    private Date sendDate;

    private String videoTitle;

    private Uri firstImage;

    private String playCount;

    private String setLoveCount;

    private String commentCount;

    public VideoItem() {
    }

    public VideoItem(String videoUri, Uri userIconUri, String username, Date sendDate, String videoTitle, Uri firstImage,
                     String playCount, String setLoveCount, String commentCount) {
        this.videoUri = videoUri;
        this.userIconUri = userIconUri;
        this.username = username;
        this.sendDate = sendDate;
        this.videoTitle = videoTitle;
        this.firstImage = firstImage;
        this.playCount = playCount;
        this.setLoveCount = setLoveCount;
        this.commentCount = commentCount;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public Uri getUserIconUri() {
        return userIconUri;
    }

    public void setUserIconUri(Uri userIconUri) {
        this.userIconUri = userIconUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public Uri getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(Uri firstImage) {
        this.firstImage = firstImage;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getSetLoveCount() {
        return setLoveCount;
    }

    public void setSetLoveCount(String setLoveCount) {
        this.setLoveCount = setLoveCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
