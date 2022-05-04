package com.blank.webflux.event;

import org.springframework.stereotype.Component;

@Component
public class RainWeatherListener implements WeatherListener{
    @Override
    public void onWeatherEvent(WeatherEvent event) {
        if (event instanceof RainEvent) {
            System.out.println("event: " + event.event());
        }
    }
}
