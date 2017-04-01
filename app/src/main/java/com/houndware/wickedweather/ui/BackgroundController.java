package com.houndware.wickedweather.ui;

import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

import com.houndware.wickedweather.R;
import com.houndware.wickedweather.weather.Current;

public class BackgroundController {

    public Drawable updateBackground(Current current, RelativeLayout relativeLayout) {

        int id;
        Drawable background;
        if (current.getIconId() !=  -1) {
            id = current.getIconId();
        }
        else {
            id = -1;
        }


        if (id == R.drawable.clear_day) {
            background = relativeLayout.getResources().getDrawable(R.drawable.clear_day);
        }
        else if (id == R.drawable.clear_night) {
            background = relativeLayout.getResources().getDrawable(R.drawable.clear_night_gradient);
        }
        else if (id == R.drawable.rain) {
            background = relativeLayout.getResources().getDrawable(R.drawable.rain_gradient);
        }
        else if (id == R.drawable.snow) {
            background = relativeLayout.getResources().getDrawable(R.drawable.snow_gradient);
        }
        else if (id == R.drawable.sleet) {
            background = relativeLayout.getResources().getDrawable(R.drawable.sleet_gradient);
        }
        else if (id == R.drawable.wind) {
            background = relativeLayout.getResources().getDrawable(R.drawable.wind_gradient);
        }
        else if (id == R.drawable.fog) {
            background = relativeLayout.getResources().getDrawable(R.drawable.fog_gradient);
        }
        else if (id == R.drawable.cloudy) {
            background = relativeLayout.getResources().getDrawable(R.drawable.cloudy_gradient);
        }
        else if (id == R.drawable.partly_cloudy) {
            background = relativeLayout.getResources().getDrawable(R.drawable.partly_cloudy_gradient);
        }
        else if (id == R.drawable.cloudy_night) {
            background = relativeLayout.getResources().getDrawable(R.drawable.cloudy_night_gradient);
        }
        else {
            background = relativeLayout.getResources().getDrawable(R.drawable.default_gradient);
        }
        return background;
    }
    public Drawable updateBackground(int backgroundId, RelativeLayout relativeLayout) {

        int id = backgroundId;
        Drawable background;

        if (id == 0) {
            background = relativeLayout.getResources().getDrawable(R.drawable.sunny_day_gradient);
        }
        else if (id == 1) {
            background = relativeLayout.getResources().getDrawable(R.drawable.clear_night_gradient);
        }
        else if (id == 2) {
            background = relativeLayout.getResources().getDrawable(R.drawable.rain_gradient);
        }
        else if (id == 3) {
            background = relativeLayout.getResources().getDrawable(R.drawable.snow_gradient);
        }
        else if (id == 4) {
            background = relativeLayout.getResources().getDrawable(R.drawable.sleet_gradient);
        }
        else if (id == 5) {
            background = relativeLayout.getResources().getDrawable(R.drawable.wind_gradient);
        }
        else if (id == 6) {
            background = relativeLayout.getResources().getDrawable(R.drawable.fog_gradient);
        }
        else if (id == 7) {
            background = relativeLayout.getResources().getDrawable(R.drawable.cloudy_gradient);
        }
        else if (id == 8) {
            background = relativeLayout.getResources().getDrawable(R.drawable.partly_cloudy_gradient);
        }
        else if (id == 9) {
            background = relativeLayout.getResources().getDrawable(R.drawable.cloudy_night_gradient);
        }
        else {
            background = relativeLayout.getResources().getDrawable(R.drawable.default_gradient);
        }
        return background;
    }

    public int getBackgroundId (Current current) {
        int id;
        Drawable background;
        if (current.getIconId() !=  -1) {
            id = current.getIconId();
        }
        else {
            id = -1;
        }


        if (id == R.drawable.clear_day) {
            id = 0;
        }
        else if (id == R.drawable.clear_night) {
            id = 1;
        }
        else if (id == R.drawable.rain) {
            id = 2;
        }
        else if (id == R.drawable.snow) {
            id = 3;
        }
        else if (id == R.drawable.sleet) {
            id = 4;
        }
        else if (id == R.drawable.wind) {
            id = 5;
        }
        else if (id == R.drawable.fog) {
            id = 6;
        }
        else if (id == R.drawable.cloudy) {
            id = 7;
        }
        else if (id == R.drawable.partly_cloudy) {
            id = 8;
        }
        else if (id == R.drawable.cloudy_night) {
            id = 9;
        }
        else {
            id = -1;
        }
        return id;


    }
}

