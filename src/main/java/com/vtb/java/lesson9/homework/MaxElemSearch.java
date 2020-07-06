package com.vtb.java.lesson9.homework;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class MaxElemSearch extends RecursiveTask<Integer> {
    private int[] data;

    public MaxElemSearch(int[] data) {
        this.data = data;
    }

    protected Integer compute() {
        if (this.data.length > 2) {
            List<MaxElemSearch> subtasks = createSubtasks();
            for (MaxElemSearch subtask : subtasks) {
                subtask.fork();
            }
            int result = -1;
            for (MaxElemSearch subtask : subtasks) {
                int temp = subtask.join();
                if (temp > result) {
                    result = temp;
                }
            }
            return result;
        } else {
            // первый вариант использует Arrays.stream()
            return Arrays.stream(data).reduce(0, Integer::max);
//             второй вариант без использования Arrays.stream()
//            if (data.length == 2) {
//                return Math.max(data[0], data[1]);
//            } else {
//                return data[0];
//            }
        }
    }

    private List<MaxElemSearch> createSubtasks() {
        return new ArrayList<>(Arrays.asList(
                new MaxElemSearch(Arrays.copyOfRange(data, 0, data.length / 2)),
                new MaxElemSearch(Arrays.copyOfRange(data, data.length / 2, data.length))
        ));
    }
}
