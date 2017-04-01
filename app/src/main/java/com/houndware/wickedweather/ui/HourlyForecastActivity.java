package com.houndware.wickedweather.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.houndware.wickedweather.R;
import com.houndware.wickedweather.adapters.HourAdapter;
import com.houndware.wickedweather.weather.Hour;

import java.util.Arrays;

public class HourlyForecastActivity extends AppCompatActivity {

    private Hour[] hours;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private RelativeLayout previousLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        hours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);

        HourAdapter adapter = new HourAdapter(this, hours);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true); // helps with performance when set size of list is known.

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout3);
        previousLayout = MainActivity.getRelativeLayout();
        relativeLayout.setBackgroundDrawable(previousLayout.getBackground());
    }
}
