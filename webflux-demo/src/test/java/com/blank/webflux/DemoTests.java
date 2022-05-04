package com.blank.webflux;


import com.blank.webflux.event.WeatherListener;
import com.blank.webflux.event.WeatherRunListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootTest
public class DemoTests {

    @Test
    public void testReaction() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5);
        flux.subscribe(
                System.out::println,
                System.err::println,
                ()->System.out.println("completed"));

        flux.map( i -> {
            log.info("1 current thread: {}", Thread.currentThread().getName());
            return i * 3;
        })
        .publishOn(Schedulers.boundedElastic())
        .map(i -> {
            log.info("2 current thread: {}", Thread.currentThread().getName());
            return i * 3;
        })
        .subscribeOn(Schedulers.parallel())
        .subscribe( i -> {
            log.info("3 current thread: {}", Thread.currentThread().getName());
        }, i -> {}, ()-> {
            log.info("completed");
            latch.countDown();
        });

        latch.await();
    }

    @Autowired
    WeatherRunListener weatherRunListener;
    @Test
    public void testEvent(){
        weatherRunListener.snow();
        weatherRunListener.rain();
    }
}
