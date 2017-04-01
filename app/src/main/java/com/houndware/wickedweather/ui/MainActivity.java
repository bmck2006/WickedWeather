package com.houndware.wickedweather.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import com.houndware.wickedweather.R;
import com.houndware.wickedweather.weather.Current;
import com.houndware.wickedweather.weather.Day;
import com.houndware.wickedweather.weather.Forecast;
import com.houndware.wickedweather.weather.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String forecastUrl, apiKey;
    private double latitude, longitude;

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
    public static final String CURRENT_FORECAST = "CURRENT_FORECAST";

    private Forecast forecast;
    private BackgroundController backgroundController;

    public static int backgroundId;

    //Optional: Butterknife UI creation
    /*
    @BindView(R.id.timeLabel) TextView timeLabel;
    @BindView(R.id.temperatureLabel) TextView temperatureLabel;
    @BindView(R.id.humidityValue) TextView humidityValue;
    @BindView(R.id.precipValue) TextView precipValue;
    @BindView(R.id.summaryLabel) TextView summaryLabel;
    @BindView(R.id.iconImageView) ImageView iconImageView;
    @BindView(R.id.refreshImageView) ImageView refreshImageView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    */

    //Standard UI declarations:
    public static RelativeLayout relativeLayout;
    private TextView timeLabel;
    private TextView temperatureLabel;
    private TextView humidityValue;
    private TextView precipValue;
    private TextView summaryLabel;
    private ImageView iconImageView;
    private ImageView refreshImageView;
    private ProgressBar progressBar;
    private Button hourlyButton;
    private Button dailyButton;

    public static RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Optional ButterKnife initialize for UI:
        // ButterKnife.bind(this); //Butterknife used to quickly setup UI.

        //Standard initialize without butterknife:
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        temperatureLabel = (TextView) findViewById(R.id.temperatureLabel);
        timeLabel = (TextView) findViewById(R.id.timeLabel);
        humidityValue = (TextView) findViewById(R.id.humidityValue);
        precipValue = (TextView) findViewById(R.id.precipValue);
        summaryLabel = (TextView) findViewById(R.id.summaryLabel);
        iconImageView = (ImageView) findViewById(R.id.iconImageView);
        refreshImageView = (ImageView) findViewById(R.id.refreshImageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        hourlyButton = (Button) findViewById(R.id.hourlyButton);
        dailyButton = (Button) findViewById(R.id.dailyButton);

        progressBar.setVisibility(View.INVISIBLE);


        /*
        BUTTON LISTENERS:
         */
        refreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude);
            }
        });
        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDailyActivity();
            }
        });
        hourlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHourlyActivity();
            }
        });


        backgroundController = new BackgroundController();
        Log.d(TAG, "Main UI code is running!");

        //Worcester, MA default coordinates
        latitude = 42.2515836;
        longitude = -71.781627;
        getForecast(latitude, longitude);

    }

    public void getForecast(double latitude, double longitude) {

        // forecast.io API
        apiKey = "e363bfcb5678123003a2e4df7fc81248";
        forecastUrl = "https://api.darksky.net/forecast/" + apiKey + "/" + latitude + "," + longitude;

        if(isNetworkAvailable()) {

            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            forecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                    relativeLayout.setBackgroundDrawable(backgroundController.updateBackground(forecast.getCurrent(), relativeLayout));
                                    backgroundId = backgroundController.getBackgroundId(forecast.getCurrent());

                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }

                }
            });
        }
        else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }
    private void startDailyActivity() {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, forecast.getDailyForecast());
        intent.putExtra(CURRENT_FORECAST, String.valueOf(forecast.getCurrent()));
        startActivity(intent);
    }
    private void startHourlyActivity() {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST, forecast.getHourlyForecast());
        startActivity(intent);
    }


    private void toggleRefresh() {
        if(progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            refreshImageView.setVisibility(View.INVISIBLE);
        }
        else {
            progressBar.setVisibility(View.INVISIBLE);
            refreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        Current current = forecast.getCurrent();
        temperatureLabel.setText(String.valueOf(current.getTemp()));
        timeLabel.setText("At " + current.getFormattedTime() + " it will be");
        humidityValue.setText(String.valueOf(current.getHumidity()));
        precipValue.setText(current.getPrecipChance() + "%");
        summaryLabel.setText(current.getSummary());

        Drawable drawable = getResources().getDrawable(current.getIconId());
        iconImageView.setImageDrawable(drawable);
    }
    private Forecast parseForecastDetails(String jsonData) throws JSONException{
        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));

        return forecast;

    }
    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for(int i = 0; i < data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon((jsonHour.getString("icon")));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;
        }
        return hours;
    }
    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setMaxTemp(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;
        }
        return days;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();

        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemp(currently.getDouble("temperature"));
        current.setTimeZone(timezone);

        Log.d(TAG, current.getFormattedTime());

        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}

