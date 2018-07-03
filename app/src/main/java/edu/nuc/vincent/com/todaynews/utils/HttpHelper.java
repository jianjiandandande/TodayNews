package edu.nuc.vincent.com.todaynews.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vincent on 2018/6/25.
 */

public class HttpHelper {

    private HttpHelper(){}


    public static class Builder{

        private String baseUrl;

        public Builder(){

        }

        public Builder baseUrl(String baseUrl){

            this.baseUrl = baseUrl;
            return this;

        }

        public  Retrofit build(){

            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(buildClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        public  OkHttpClient buildClient(){

            OkHttpClient client = new OkHttpClient.Builder().
                    connectTimeout(6, TimeUnit.SECONDS).
                    readTimeout(6, TimeUnit.SECONDS).
                    writeTimeout(6, TimeUnit.SECONDS).build();

            return client;
        }




    }


}
