
package com.ikmr.banbara23.yaeyama_liner_checker.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {
    String date;
    // 天気
    String weather;
    // 気温
    String temperature;
    // 風
    String wind;
    // 波
    String wave;
    // リンクurl
    String url;

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public void setWave(String wave) {
        this.wave = wave;
    }

    public String getDate() {
        return date;
    }

    public String getWeather() {
        return weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWind() {
        return wind;
    }

    public String getWave() {
        return wave;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.weather);
        dest.writeString(this.temperature);
        dest.writeString(this.wind);
        dest.writeString(this.wave);
        dest.writeString(this.url);
    }

    public Weather() {
    }

    protected Weather(Parcel in) {
        this.date = in.readString();
        this.weather = in.readString();
        this.temperature = in.readString();
        this.wind = in.readString();
        this.wave = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}
