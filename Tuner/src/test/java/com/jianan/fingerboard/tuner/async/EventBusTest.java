package com.jianan.fingerboard.tuner.async;

import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @author by jianan.liu on 17/4/10.
 */
public class EventBusTest {

    @Test
    public void testEventBus() {
        // given
        EventBus eventBus = new EventBus("test");
        eventBus.register(new Object() {
            @Subscribe
            void onMessage(Integer integer) {
                System.out.printf("%s from int%n", integer);
            }

            @Subscribe
            void onMessage(String str) {
                System.out.printf("%s from String %n", str);
            }

            @Subscribe
            void onMessage(Object obj) {
                System.out.printf("%s from Object %n", obj);
            }
        });
        eventBus.post(1);
        eventBus.post("A");
    }
}
