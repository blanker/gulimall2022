package com.blank.webflux.event;

public interface WeatherListener {
    void onWeatherEvent(WeatherEvent event);
}
