package edu.nuc.vincent.com.todaynews;

import java.util.List;
import java.util.Map;

import edu.nuc.vincent.com.todaynews.bean.Comment;
import edu.nuc.vincent.com.todaynews.bean.Essay;
import edu.nuc.vincent.com.todaynews.bean.News;
import edu.nuc.vincent.com.todaynews.bean.Search;
import edu.nuc.vincent.com.todaynews.bean.User;
import edu.nuc.vincent.com.todaynews.bean.Video;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Vincent on 2018/6/23.
 */

public interface GetDatas {

    @GET("news/toutiao")
    Call<News> getNewsFromService(@QueryMap Map<String, String> params);

    @GET("post/toutiao")
    Call<Essay> getEssayFromService(@QueryMap Map<String, String> params);

    @GET("news/toutiao")
    Call<Search> getSearchFromService(@QueryMap Map<String, String> params);

    @GET("profile/toutiao")
    Call<User> getUserInfo(@QueryMap Map<String, String> params);

    @GET("profile/meipai")
    Call<User> getVideoUserInfo(@QueryMap Map<String, String> params);

    @GET("comment/toutiao")
    Call<Comment> getComments(@QueryMap Map<String, String> params);

    @GET("comment/meipai")
    Call<Comment> getVideoComments(@QueryMap Map<String, String> params);

    @GET("video/meipai")
    Call<Video> getVideos(@QueryMap Map<String, String> params);

}
