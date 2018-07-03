package edu.nuc.vincent.com.todaynews.entity;

import java.util.List;

/**
 * Created by Vincent on 2018/6/26.
 */

public class User {


    /**
     * appCode : toutiao
     * pageToken : null
     * dataType : profile
     * hasNext : false
     * retcode : 000000
     * data : [{"fansCount":74321745,"idVerifiedInfo":"中央电视台新闻中心官方帐号","idVerified":true,"viewCount":923319702,"url":"http://www.toutiao.com/c/user/4492956276/","postCount":null,"screenName":"央视新闻","followCount":28,"id":"4492956276_4492956276","videoCount":13946,"biography":"中央电视台新闻中心头条新闻官方帐号，是央视重大新闻、突发事件、重点报道的发布平台。","avatarUrl":"http://p1-xg.pstatp.com/large/6ee30000539441f6c83a","likeCount":null,"shareCount":8491240}]
     */

    private String appCode;
    private Object pageToken;
    private String dataType;
    private boolean hasNext;
    private String retcode;
    private List<DataBean> data;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Object getPageToken() {
        return pageToken;
    }

    public void setPageToken(Object pageToken) {
        this.pageToken = pageToken;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * fansCount : 74321745
         * idVerifiedInfo : 中央电视台新闻中心官方帐号
         * idVerified : true
         * viewCount : 923319702
         * url : http://www.toutiao.com/c/user/4492956276/
         * postCount : null
         * screenName : 央视新闻
         * followCount : 28
         * id : 4492956276_4492956276
         * videoCount : 13946
         * biography : 中央电视台新闻中心头条新闻官方帐号，是央视重大新闻、突发事件、重点报道的发布平台。
         * avatarUrl : http://p1-xg.pstatp.com/large/6ee30000539441f6c83a
         * likeCount : null
         * shareCount : 8491240
         */

        private int fansCount;
        private String idVerifiedInfo;
        private boolean idVerified;
        private int viewCount;
        private String url;
        private Object postCount;
        private String screenName;
        private int followCount;
        private String id;
        private int videoCount;
        private String biography;
        private String avatarUrl;
        private Object likeCount;
        private int shareCount;

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public String getIdVerifiedInfo() {
            return idVerifiedInfo;
        }

        public void setIdVerifiedInfo(String idVerifiedInfo) {
            this.idVerifiedInfo = idVerifiedInfo;
        }

        public boolean isIdVerified() {
            return idVerified;
        }

        public void setIdVerified(boolean idVerified) {
            this.idVerified = idVerified;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getPostCount() {
            return postCount;
        }

        public void setPostCount(Object postCount) {
            this.postCount = postCount;
        }

        public String getScreenName() {
            return screenName;
        }

        public void setScreenName(String screenName) {
            this.screenName = screenName;
        }

        public int getFollowCount() {
            return followCount;
        }

        public void setFollowCount(int followCount) {
            this.followCount = followCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(int videoCount) {
            this.videoCount = videoCount;
        }

        public String getBiography() {
            return biography;
        }

        public void setBiography(String biography) {
            this.biography = biography;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public Object getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(Object likeCount) {
            this.likeCount = likeCount;
        }

        public int getShareCount() {
            return shareCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }
    }
}
