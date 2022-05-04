package com.blank.webflux.event;

public class Test {
    public static void main(String[] args) {
        WeatherEventMulticaster multicaster = new WeatherEventMulticaster();

        RainWeatherListener rainWeatherListener = new RainWeatherListener();
        SnowWeatherListener snowWeatherListener = new SnowWeatherListener();

        multicaster.addListener(rainWeatherListener);
        multicaster.addListener(snowWeatherListener);

        multicaster.multicastEvent(new RainEvent());
        multicaster.multicastEvent(new SnowEvent());

        System.out.println();
        multicaster.removeListener(snowWeatherListener);
        multicaster.multicastEvent(new RainEvent());
        multicaster.multicastEvent(new SnowEvent());
    }
}
