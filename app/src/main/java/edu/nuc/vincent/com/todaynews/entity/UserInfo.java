package edu.nuc.vincent.com.todaynews.entity;

/**
 * Created by Vincent on 2018/7/2.
 */

public class UserInfo {


    /**
     * msg : 获取用户信息成功
     * code : 502
     * telephone : 15735657418
     * user_icon : https://avatars2.githubusercontent.com/u/15951818?s=460&v=4
     * userId : 1
     * username : vincent
     */

    private String msg;
    private int code;
    private String telephone;
    private String user_icon;
    private int userId;
    private String username;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
