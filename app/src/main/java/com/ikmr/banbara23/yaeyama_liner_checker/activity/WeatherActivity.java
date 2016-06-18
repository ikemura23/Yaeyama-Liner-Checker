
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Weather;
import com.ikmr.banbara23.yaeyama_liner_checker.util.CustomTabUtil;

public class WeatherActivity extends AppCompatActivity {
    private String weatherUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab == null) {
            return;
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCustomTab();
                // Snackbar.make(view, weatherUrl,
                // Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
            }
        });

        Weather weather = getIntent().getParcelableExtra(Weather.class.getCanonicalName());

        setTitle(weather.getDate() + "の天気");
        ((TextView) findViewById(R.id.activity_top_weather_temperature)).append(weather.getTemperature());
        ((TextView) findViewById(R.id.activity_top_weather)).append(weather.getWeather());
        ((TextView) findViewById(R.id.activity_top_weather_wind)).append(weather.getWind());
        ((TextView) findViewById(R.id.activity_top_weather_wave)).append(weather.getWave());
        weatherUrl = weather.getUrl();
    }

    private void startCustomTab() {
        CustomTabUtil.start(this, weatherUrl);
    }
}
