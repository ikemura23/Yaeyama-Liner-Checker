package com.ikmr.banbara23.yaeyama_liner_checker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 天気
 */
public class Weather implements Parcelable {
    private String date;
    private String wave;
    private String weather;
    private String wind;
    private String high;
    private String low;

    protected Weather(Parcel in) {
        date = in.readString();
        wave = in.readString();
        weather = in.readString();
        wind = in.readString();
        high = in.readString();
        low = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(wave);
        dest.writeString(weather);
        dest.writeString(wind);
        dest.writeString(high);
        dest.writeString(low);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWave() {
        return wave;
    }

    public void setWave(String wave) {
        this.wave = wave;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }
}