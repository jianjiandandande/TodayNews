package edu.nuc.vincent.com.todaynews.bean;

import android.net.Uri;

import java.util.Date;

/**
 * Created by Vincent on 2018/6/21.
 */

public class HomeItem {

    private Uri userIconUri;

    private String username;

    private Date sendDate;

    private String sendContent;

    private Uri sendImageUri;

    public HomeItem() {
    }

    public HomeItem(Uri userIconUri, String username, Date sendDate, String sendContent, Uri sendImageUri) {
        this.userIconUri = userIconUri;
        this.username = username;
        this.sendDate = sendDate;
        this.sendContent = sendContent;
        this.sendImageUri = sendImageUri;
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

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public Uri getSendImageUri() {
        return sendImageUri;
    }

    public void setSendImageUri(Uri sendImageUri) {
        this.sendImageUri = sendImageUri;
    }
}
