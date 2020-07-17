package com.vtb.java.lesson12.homework;

@DbTable(name="students")
public class Student {
    @DbId
    private long studId;

    @DbColumn
    private String name;

    @DbColumn
    private int avgScore;

    public Student() {}

    public Student(String name, int avgScore) {
        this.studId = 0L;
        this.name = name;
        this.avgScore = avgScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void getInfo() {
        System.out.printf("Студент ID: %d\tИмя: %s\tСредний балл: %d\n", studId, name, avgScore);
    }
}
