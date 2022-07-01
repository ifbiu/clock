package com.ifbiu.entity;

public class LoginEntity {
    String msg;
    String token;
    String userId;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LoginEntity{" +
                "msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
