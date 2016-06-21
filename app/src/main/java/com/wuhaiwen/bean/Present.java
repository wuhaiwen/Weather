package com.wuhaiwen.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wuhaiwen on 2016/5/30.
 */
public class Present implements Serializable{

    private String city;
    //使用该方法字段可以跟数据中的字段名不相同
    @SerializedName("tmp")
    private String temperature;
    @SerializedName("wind")
    private String wind_speed;
    @SerializedName("cond")
    private String cloud;



    public Present(String city, String cloud, String temperature, String wind_speed) {
        this.city = city;
        this.cloud = cloud;
        this.temperature = temperature;
        this.wind_speed = wind_speed;
    }

    public Present() {
    }

    public String getCity() {
        return city;
    }

    public String getCloud() {
        return cloud;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    @Override
    public String toString() {
        return "Present{" +
                "city='" + city + '\'' +
                ", temperature='" + temperature + '\'' +
                ", wind_speed='" + wind_speed + '\'' +
                ", cloud='" + cloud + '\'' +
                '}';
    }
}
