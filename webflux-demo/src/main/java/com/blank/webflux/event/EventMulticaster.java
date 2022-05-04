package com.blank.webflux.event;

public interface EventMulticaster {
    void multicastEvent(WeatherEvent event);

    void addListener(WeatherListener listener);

    void removeListener(WeatherListener listener);
}
