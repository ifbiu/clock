package com.ifbiu.entity;

public class DayInfoEntity {
    private String sign;
    private String temperature;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "DayInfo{" +
                "sign='" + sign + '\'' +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
