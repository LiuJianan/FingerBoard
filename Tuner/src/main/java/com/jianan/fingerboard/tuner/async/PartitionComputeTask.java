package com.jianan.fingerboard.tuner.async;

import java.util.concurrent.RecursiveTask;

/**
 * @author by jianan.liu on 17/4/1.
 */
public class PartitionComputeTask extends RecursiveTask<Integer> {

    private int start;
    private int end;

    public PartitionComputeTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if ((start - end) < 10) {
            for (int i = start; i < end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;
            PartitionComputeTask left = new PartitionComputeTask(start, middle);
            PartitionComputeTask right = new PartitionComputeTask(middle + 1, end);
            left.fork();
            right.fork();

            sum = left.join() + right.join();
        }
        return sum;
    }
}
