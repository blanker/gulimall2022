package com.blank.webflux.event;

public class SnowEvent implements WeatherEvent{
    @Override
    public String event() {
        return "Snow";
    }
}
