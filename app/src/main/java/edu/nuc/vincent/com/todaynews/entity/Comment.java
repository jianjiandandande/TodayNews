package edu.nuc.vincent.com.todaynews.entity;

import java.util.List;

/**
 * Created by Vincent on 2018/6/27.
 */

public class Comment {
    /**
     * dataType : comment
     * pageToken : null
     * data : [{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"已举报不谢","source":null,"publishDate":1530068324,"publishDateStr":"2018-06-27 10:58:44","referId":"6571429457637147150","commentCount":0,"commenterScreenName":"打伞的鱼10","tags":null,"imageUrls":null,"commenterId":"6911980678","likeCount":0,"id":"1604392922610717"},{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"老司机夺冠哪年就把湖人横扫，把科比按地上摩擦→_→摩擦，再赢热火！就凭这点，历史地位就超过科比了[捂脸][捂脸]","source":null,"publishDate":1530067836,"publishDateStr":"2018-06-27 10:50:36","referId":"6571429457637147150","commentCount":0,"commenterScreenName":"宏142327594","tags":null,"imageUrls":null,"commenterId":"3287751654","likeCount":0,"id":"1604392411143172"},{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"哈哈哈哈哈哈哈哈","source":null,"publishDate":1530067633,"publishDateStr":"2018-06-27 10:47:13","referId":"6571429457637147150","commentCount":0,"commenterScreenName":"呵呵不是我的留言","tags":null,"imageUrls":null,"commenterId":"4000614398","likeCount":0,"id":"1604392198192135"},{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"反正也比沾野种高","source":null,"publishDate":1530065035,"publishDateStr":"2018-06-27 10:03:55","referId":"6571429457637147150","commentCount":0,"commenterScreenName":"献母叛逃龌沾最强","tags":null,"imageUrls":null,"commenterId":"54226385663","likeCount":2,"id":"1604389473709070"},{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"吉诺比利我是服气的，当年干翻梦之队，还有诺维斯基，但是卡特，，，，，，","source":null,"publishDate":1530064887,"publishDateStr":"2018-06-27 10:01:27","referId":"6571429457637147150","commentCount":0,"commenterScreenName":"尼古拉夫佩奇","tags":null,"imageUrls":null,"commenterId":"61561328581","likeCount":3,"id":"1604389318670340"},{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"呵呵 不逊色于科比？","source":null,"publishDate":1530063479,"publishDateStr":"2018-06-27 09:37:59","referId":"6571429457637147150","commentCount":0,"commenterScreenName":"Damelillard0","tags":null,"imageUrls":null,"commenterId":"58143132369","likeCount":5,"id":"1604387842681863"},{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"哥们，你这标题容易举报！","source":null,"publishDate":1530063198,"publishDateStr":"2018-06-27 09:33:18","referId":"6571429457637147150","commentCount":0,"commenterScreenName":"须弥芥子16","tags":null,"imageUrls":null,"commenterId":"57163985852","likeCount":3,"id":"1604387547767811"},{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"你是来搞笑的还是拉仇恨的","source":null,"publishDate":1530059036,"publishDateStr":"2018-06-27 08:23:56","referId":"6571429457637147150","commentCount":1,"commenterScreenName":"明知是意外18","tags":null,"imageUrls":null,"commenterId":"53478182335","likeCount":4,"id":"1604383183752206"},{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"的确，诺维斯基和卡特分别是小牛队魂，马努是阿根廷英雄，比起一个强奸告母的软蛋强太多","source":null,"publishDate":1530058598,"publishDateStr":"2018-06-27 08:16:38","referId":"6571429457637147150","commentCount":0,"commenterScreenName":"理性科蜜","tags":null,"imageUrls":null,"commenterId":"97082632516","likeCount":4,"id":"1604382724621396"},{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"除了诺维茨基，都远逊于科比","source":null,"publishDate":1530057173,"publishDateStr":"2018-06-27 07:52:53","referId":"6571429457637147150","commentCount":1,"commenterScreenName":"十一月的期待","tags":null,"imageUrls":null,"commenterId":"73104425759","likeCount":2,"id":"1604381230442499"},{"url":"http://toutiao.com/group/6571429457637147150/","rating":null,"content":"不可能的","source":null,"publishDate":1530057009,"publishDateStr":"2018-06-27 07:50:09","referId":"6571429457637147150","commentCount":0,"commenterScreenName":"百年3号","tags":null,"imageUrls":null,"commenterId":"53482764401","likeCount":1,"id":"1604381058121742"}]
     * appCode : toutiao
     * hasNext : false
     * retcode : 000000
     */

    private String dataType;
    private Object pageToken;
    private String appCode;
    private boolean hasNext;
    private String retcode;
    private List<DataBean> data;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Object getPageToken() {
        return pageToken;
    }

    public void setPageToken(Object pageToken) {
        this.pageToken = pageToken;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
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
         * url : http://toutiao.com/group/6571429457637147150/
         * rating : null
         * content : 已举报不谢
         * source : null
         * publishDate : 1530068324
         * publishDateStr : 2018-06-27 10:58:44
         * referId : 6571429457637147150
         * commentCount : 0
         * commenterScreenName : 打伞的鱼10
         * tags : null
         * imageUrls : null
         * commenterId : 6911980678
         * likeCount : 0
         * id : 1604392922610717
         */

        private String url;
        private Object rating;
        private String content;
        private Object source;
        private int publishDate;
        private String publishDateStr;
        private String referId;
        private int commentCount;
        private String commenterScreenName;
        private Object tags;
        private Object imageUrls;
        private String commenterId;
        private int likeCount;
        private String id;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getRating() {
            return rating;
        }

        public void setRating(Object rating) {
            this.rating = rating;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
        }

        public int getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(int publishDate) {
            this.publishDate = publishDate;
        }

        public String getPublishDateStr() {
            return publishDateStr;
        }

        public void setPublishDateStr(String publishDateStr) {
            this.publishDateStr = publishDateStr;
        }

        public String getReferId() {
            return referId;
        }

        public void setReferId(String referId) {
            this.referId = referId;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getCommenterScreenName() {
            return commenterScreenName;
        }

        public void setCommenterScreenName(String commenterScreenName) {
            this.commenterScreenName = commenterScreenName;
        }

        public Object getTags() {
            return tags;
        }

        public void setTags(Object tags) {
            this.tags = tags;
        }

        public Object getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(Object imageUrls) {
            this.imageUrls = imageUrls;
        }

        public String getCommenterId() {
            return commenterId;
        }

        public void setCommenterId(String commenterId) {
            this.commenterId = commenterId;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
