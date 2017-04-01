package com.jianan.fingerboard.tuner.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * @author by jianan.liu on 17/4/1.
 */
public class PartitionComputeTaskTest {

    @Test
    public void testCompute() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Integer> result = forkJoinPool.submit(new PartitionComputeTask(0, 10000));
        System.out.println(result.get());
    }

}