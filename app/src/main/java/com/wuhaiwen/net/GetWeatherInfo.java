package com.wuhaiwen.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.wuhaiwen.bean.WeatherInfo;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Created by wuhaiwen on 2016/6/19.
 */
public class GetWeatherInfo {

    public static WeatherInfo getWeatherInfo(String city,Context context) {
        WeatherInfo weatherInfo = null;
        InputStream in;
        StringBuffer buffer = null;
        String text1 = null;
        try {
            buffer = GetWeatherString.getRequest(UrlConfig.getHttpUrl(city), context);
            //判断是否从网络取到数据
            if (buffer.toString().length() != 0) {
                in = new ByteArrayInputStream(buffer.toString().getBytes());
                Reader reader = new InputStreamReader(in, "utf-8");
                weatherInfo = new Gson().fromJson(reader, WeatherInfo.class);
                in.close();
//                text1 = weatherInfo.getAllInfos().get(0).getDaily_forecasts().toString();
            } else {
                //如果没取到，则从之前存的历史数据中读取天气信息
//                Log.d("gaga", "zhixing");
                in = context.openFileInput("weather");
                //int size = in.available();
                Reader reader = new InputStreamReader(in, "utf-8");
                weatherInfo = new Gson().fromJson(reader, WeatherInfo.class);
//                text1 = weatherInfo.getAllInfos().get(0).getBasic().toString();
                reader.close();
            }
//                            Log.d("gaga",info.getAllInfos().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherInfo;
    }
}
