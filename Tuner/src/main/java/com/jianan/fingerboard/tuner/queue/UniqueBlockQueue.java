package com.jianan.fingerboard.tuner.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Maps;

/**
 * Duplicate removal queue
 *
 * @author by jianan.liu on 16/11/30.
 */
public class UniqueBlockQueue<T> {
    private ConcurrentHashMap<T, ActionStatus> statusRecord = new ConcurrentHashMap();
    private ConcurrentMap<Integer, ReentrantLock> lockMap = Maps.newConcurrentMap();
    private BlockingQueue<T> queue = new LinkedBlockingQueue<T>();
    private int segments = 32;

    public UniqueBlockQueue() {
        for (int i = 0; i < segments; i++) {
            lockMap.put(i, new ReentrantLock(false));
        }
    }

    public static class ActionStatus {
        private AtomicInteger status;

        public ActionStatus(AtomicInteger status) {
            this.status = status;
        }

        public ActionStatus() {
            status = new AtomicInteger(0);
        }

        public int update() {
            return status.addAndGet(1);
        }

        public static ActionStatus init() {
            return new ActionStatus(new AtomicInteger(0));
        }
    }

    public boolean enQueue(T key) {
        ReentrantLock lock = lockMap.get((key.hashCode() >>> 1) % segments);
        lock.lock();
        ActionStatus actionStatus = statusRecord.putIfAbsent(key, new ActionStatus());
        if(actionStatus == null){
            queue.offer(key);
        }
        lock.unlock();
        return true;
    }

    public T deQueue(){
        try {
            T key = queue.take();
            ReentrantLock lock = lockMap.get((key.hashCode() >>> 1) % segments);
            lock.lock();
            statusRecord.remove(key);
            lock.unlock();
            return key;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

}
