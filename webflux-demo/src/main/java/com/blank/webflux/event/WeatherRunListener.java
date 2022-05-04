package com.blank.webflux.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherRunListener {
    @Autowired
    private EventMulticaster multicaster;

    public void snow(){
        multicaster.multicastEvent(new SnowEvent());
    }

    public void rain(){
        multicaster.multicastEvent(new RainEvent());
    }
}
