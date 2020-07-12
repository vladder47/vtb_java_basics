package com.vtb.java.lesson11.homework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {
    public static void main(String[] args) {
        try {
            start(Tests.class);
        } catch (InvocationTargetException | IllegalAccessException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static void start(Class testClass) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = testClass.getDeclaredMethods();
        Map<Integer, List<Method>> mapper = new HashMap<>();
        boolean initialFlag = false;
        boolean finalFlag = false;
        Method initialMethod = null;
        Method finalMethod = null;
        for (Method m : methods) {
            if (m.getAnnotation(BeforeSuite.class) != null) {
                if (!initialFlag) {
                    initialFlag = true;
                    initialMethod = m;
                } else {
                    throw new RuntimeException("Слишком много методов BeforeSuite");
                }
            } else if (m.getAnnotation(Test.class) != null) {
                int priority = m.getAnnotation(Test.class).priority();
                List<Method> lm = mapper.getOrDefault(priority, new ArrayList<>());
                lm.add(m);
                mapper.put(priority, lm);
            } else if (m.getAnnotation(AfterSuite.class) != null) {
                if (!finalFlag) {
                    finalFlag = true;
                    finalMethod = m;
                } else {
                    throw new RuntimeException("Слишком много методов AfterSuite");
                }
            }
        }

        if (initialMethod != null) {
            initialMethod.invoke(testClass);
        }

        List<Integer> priorities = new ArrayList<>(mapper.keySet());
        priorities.sort(Collections.reverseOrder());
        for (Integer priority : priorities) {
            for (Method m : mapper.get(priority)) {
                m.invoke(testClass);
            }
        }

        if (finalMethod != null) {
            finalMethod.invoke(testClass);
        }
    }
}
