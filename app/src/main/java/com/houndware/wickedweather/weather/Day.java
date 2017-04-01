package com.houndware.wickedweather.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Day implements Parcelable {

    private long time;
    private String summary, icon, timezone;
    private double maxTemp;

    public Day() {

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

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getMaxTemp() {

        return (int) Math.round(maxTemp);
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getIconId() {
        return Forecast.getIconId(icon);
    }
    public String getDayOfTheWeek() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
        Date dateTime = new Date(time * 1000);
        return formatter.format(dateTime);
    }

    @Override
    public int describeContents() {
        return 0; // Not needed
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeString(summary);
        dest.writeDouble(maxTemp);
        dest.writeString(icon);
        dest.writeString(timezone);
    }

    private Day(Parcel in) {
        time = in.readLong();
        summary = in.readString();
        maxTemp = in.readDouble();
        icon = in.readString();
        timezone = in.readString();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}
