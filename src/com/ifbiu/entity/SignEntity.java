package com.ifbiu.entity;

public class SignEntity {
    private String msg;
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SignEntity{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
