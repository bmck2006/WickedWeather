package com.houndware.wickedweather.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Hour implements Parcelable{

    private long time;
    private String summary, icon, timezone;
    private double temperature;

    public Hour() {

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public int getIconId() {
        return Forecast.getIconId(icon);
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTemperature() {

        return (int) Math.round(temperature);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    @Override
    public int describeContents() {
        return 0; // not used
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeDouble(temperature);
        dest.writeString(summary);
        dest.writeString(icon);
        dest.writeString(timezone);
    }

    private Hour(Parcel in) {
        time = in.readLong();
        temperature = in.readDouble();
        summary = in.readString();
        icon = in.readString();
        timezone = in.readString();
    }

    public static final Creator<Hour> CREATOR = new Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel source) {
            return new Hour(source);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };
}
