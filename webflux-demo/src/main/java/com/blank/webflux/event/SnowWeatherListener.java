package com.blank.webflux.event;

import org.springframework.stereotype.Component;

@Component
public class SnowWeatherListener implements WeatherListener{
    @Override
    public void onWeatherEvent(WeatherEvent event) {
        if (event instanceof SnowEvent) {
            System.out.println("event: " + event.event());
        }
    }
}
