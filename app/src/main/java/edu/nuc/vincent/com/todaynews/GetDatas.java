package edu.nuc.vincent.com.todaynews;

import java.util.List;
import java.util.Map;

import edu.nuc.vincent.com.todaynews.entity.AttentionItem;
import edu.nuc.vincent.com.todaynews.entity.Comment;
import edu.nuc.vincent.com.todaynews.entity.Essay;
import edu.nuc.vincent.com.todaynews.entity.HistoryItem;
import edu.nuc.vincent.com.todaynews.entity.News;
import edu.nuc.vincent.com.todaynews.entity.Result;
import edu.nuc.vincent.com.todaynews.entity.Search;
import edu.nuc.vincent.com.todaynews.entity.User;
import edu.nuc.vincent.com.todaynews.entity.UserInfo;
import edu.nuc.vincent.com.todaynews.entity.Video;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("login")
    Call<Result> checkLogin(@QueryMap Map<String,String> params);

    @POST("register")
    Call<Result> checkRegister(@QueryMap Map<String,String> params);

    @GET("userInfo")
    Call<UserInfo> getUserInfoFromWeb(@QueryMap Map<String,String> params);

    @GET("addHistory")
    Call<Result> addHistoryToWeb(@QueryMap Map<String,String> params);

    @GET("deleteHistory")
    Call<Result> deleteHistory(@QueryMap Map<String,String> params);

    @GET("queryHistory")
    Call<List<HistoryItem>> queryHistory(@QueryMap Map<String,String> params);

    @GET("addCollection")
    Call<Result> addCollectionToWeb(@QueryMap Map<String,String> params);

    @GET("deleteCollection")
    Call<Result> deleteCollection(@QueryMap Map<String,String> params);

    @GET("queryCollection")
    Call<List<HistoryItem>> queryCollection(@QueryMap Map<String,String> params);

    @GET("addAttention")
    Call<Result> addAttentionToWeb(@QueryMap Map<String,String> params);

    @GET("deleteAttention")
    Call<Result> deleteAttention(@QueryMap Map<String,String> params);

    @GET("queryAttention")
    Call<List<AttentionItem>> queryAttention(@QueryMap Map<String,String> params);

}
