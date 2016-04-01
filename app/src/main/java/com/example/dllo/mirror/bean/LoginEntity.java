package com.example.dllo.mirror.bean;

/**
 * Created by jialiang on 16/4/1.
 * 登录 得到回值得 实体类
 */
public class LoginEntity {
    private String result;
    private String msg;
    private String data;
    private String token;
    private String uid;


    public LoginEntity() {
        super();
    }

    public LoginEntity(String result, String msg, String data, String token, String uid) {
        this.result = result;
        this.msg = msg;
        this.data = data;
        this.token = token;
        this.uid = uid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
