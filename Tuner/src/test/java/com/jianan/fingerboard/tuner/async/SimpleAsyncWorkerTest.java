package com.jianan.fingerboard.tuner.async;

import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static org.junit.Assert.*;

/**
 * @author by jianan.liu on 17/4/1.
 */
public class SimpleAsyncWorkerTest {

    @Test
    public void create(){
        ExecutorService executorService1 = SimpleAsyncWorker.COMMON.create(10, 10);
        ExecutorService executorService2 = SimpleAsyncWorker.COMMON.create(10, 10);
        System.out.println(executorService1.hashCode());
        System.out.println(executorService2.hashCode());
    }

}