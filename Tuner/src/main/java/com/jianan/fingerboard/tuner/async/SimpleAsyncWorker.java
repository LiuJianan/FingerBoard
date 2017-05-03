package com.jianan.fingerboard.tuner.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @author by jianan.liu on 17/3/30.
 */
public class SimpleAsyncWorker {
    /**
     * thread pool worker with bounded queue
     */
    public static SimpleAsyncWorker LIMIT = new SimpleAsyncWorker(true);
    /**
     * thread pool worker with unbounded queue
     */
    public static SimpleAsyncWorker COMMON = new SimpleAsyncWorker(false);

    private boolean limitFlag;
    private int limitQueueSize = 10000;

    public SimpleAsyncWorker(boolean limitFlag) {
        this.limitFlag = limitFlag;
    }

    public SimpleAsyncWorker(boolean limitFlag, int limitQueueSize) {
        this.limitFlag = limitFlag;
        this.limitQueueSize = limitQueueSize;
    }

    public ExecutorService create(int size) {
        return create(size, size);
    }

    public ExecutorService create(int coreSize, int maxSize) {
        if (limitFlag) {
            return createLimit(coreSize, maxSize, limitQueueSize);
        } else {
            return createCommon(coreSize, maxSize);
        }
    }

    private ExecutorService createCommon(int coreSize, int maxSize) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(//
                coreSize, //
                maxSize, //
                1000 * 60, TimeUnit.MILLISECONDS, //
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat("common-task-%d").build());
        executorService.prestartAllCoreThreads();
        return executorService;
    }

    private ExecutorService createLimit(int coreSize, int maxSize, int queueSize) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                coreSize,//
                maxSize,//
                1000 * 60, //
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(queueSize),
                new ThreadFactoryBuilder().setNameFormat("limit-task-%d").build());
        executorService.prestartAllCoreThreads();
        return executorService;
    }

    public SimpleAsyncWorker setLimitQueueSize(int limitQueueSize) {
        this.limitQueueSize = limitQueueSize;
        return this;
    }
}
