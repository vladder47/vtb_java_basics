package com.vtb.java.lesson9.homework;

import java.util.concurrent.RecursiveTask;

public class MaxElemSearch extends RecursiveTask<Integer> {
    private int[] data;
    private int start;
    private int end;

    public MaxElemSearch(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int length = end - start;
        if (length > 10000) {
            int offset = length / 2;
            MaxElemSearch left = new MaxElemSearch(data, start, start + offset);
            left.fork();
            MaxElemSearch right = new MaxElemSearch(data, start + offset, end);
            right.fork();

            return Math.max(left.join(), right.join());
        } else {
            return computeDirectly();
        }
    }

    private Integer computeDirectly() {
        int result = 0;
        for (int i = start; i < end; i++) {
            if (data[i] > result) {
                result = data[i];
            }
        }
        return result;
    }
}
