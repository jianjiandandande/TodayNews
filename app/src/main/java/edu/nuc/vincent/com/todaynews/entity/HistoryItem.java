package edu.nuc.vincent.com.todaynews.entity;

/**
 * Created by Vincent on 2018/7/2.
 */

public class HistoryItem {

    private String id;
    private String uid;
    private String content;
    private String imageUrl;
    private String model;
    private String videoUrl;
    private String author;
    private String skimCount;
    private String loveCount;
    private String title;
    private String commentCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSkimCount() {
        return skimCount;
    }

    public void setSkimCount(String skimCount) {
        this.skimCount = skimCount;
    }

    public String getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(String loveCount) {
        this.loveCount = loveCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
