package edu.nuc.vincent.com.todaynews.entity;

/**
 * Created by Vincent on 2018/7/4.
 */

public class AttentionItem {

    private int userId;
    private String uid;
    private String iconUrl;
    private String uname;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
