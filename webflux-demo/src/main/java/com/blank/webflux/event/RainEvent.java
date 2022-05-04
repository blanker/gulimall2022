package com.blank.webflux.event;

public class RainEvent implements WeatherEvent{
    @Override
    public String event() {
        return "Rain";
    }
}
