package com.blank.webflux.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEventMulticaster  implements  EventMulticaster{
    @Autowired
    List<WeatherListener> listeners = new ArrayList<>();

    @Override
    public void multicastEvent(WeatherEvent event) {
        doStart();
        listeners.forEach(listener -> listener.onWeatherEvent(event));
        doEnd();
    }

    @Override
    public void addListener(WeatherListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(WeatherListener listener) {
        listeners.remove(listener);
    }

    abstract void doStart();
    abstract void doEnd();
}
