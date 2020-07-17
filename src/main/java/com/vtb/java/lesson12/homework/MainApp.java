package com.vtb.java.lesson12.homework;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        DatabaseConnect db = new DatabaseConnect();
        try {
            Connection conn = db.connect();
            ReflectionRepository<Student> rp = new ReflectionRepository<>(Student.class, conn);
            Student sOne = new Student("Vladislav", 90);
            Student sTwo = new Student("Boris", 100);
            Student sThree = new Student("Denis", 70);
//            List<Student> lst = new ArrayList<>(Arrays.asList(sOne, sTwo, sThree));
//            rp.insertObjects(lst);
//            rp.insertObject(sTwo);
//            rp.insertObject(sThree);
//            rp.deleteById(2);
//            rp.deleteAll();
//            Student sFour = rp.getById(46);
//            sFour.getInfo();
//            rp.deleteAll();

            List<Student> students = rp.getAll();
            for (Student s : students) {
                s.getInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
    }
}
