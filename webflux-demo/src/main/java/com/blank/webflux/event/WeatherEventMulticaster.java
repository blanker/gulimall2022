package com.blank.webflux.event;

import org.springframework.stereotype.Component;

@Component
public class WeatherEventMulticaster extends AbstractEventMulticaster{
    @Override
    void doStart() {
        System.out.println("begin WeatherEventMulticaster");
    }

    @Override
    void doEnd() {
        System.out.println("end WeatherEventMulticaster");
    }
}
