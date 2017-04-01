package com.houndware.wickedweather.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.Toast;

import com.houndware.wickedweather.R;
import com.houndware.wickedweather.adapters.DayAdapter;

import com.houndware.wickedweather.weather.Day;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity
{
    private Day[] days;
    private RelativeLayout relativeLayout;
    private RelativeLayout previousLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        days = Arrays.copyOf(parcelables, parcelables.length, Day[].class);

        DayAdapter adapter = new DayAdapter(this, days);
        setListAdapter(adapter);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout2);
        previousLayout = MainActivity.getRelativeLayout();
        relativeLayout.setBackgroundDrawable(previousLayout.getBackground());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String dayOfTheWeek = days[position].getDayOfTheWeek();
        String conditions = days[position].getSummary();
        String highTemp = String.valueOf(days[position].getMaxTemp());
        String message = String.format("%s\nHigh Temp: %s\n%s", dayOfTheWeek, highTemp, conditions);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
}
