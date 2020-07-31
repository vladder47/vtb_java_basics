package com.vtb.java.lesson19.homework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class PrepareDataApp {
    public static void prepareData(DataBaseAPI dataBase) {
        try {
            String sql = Files.lines(Paths.get("create_tables_19.sql")).collect(Collectors.joining(" "));
            dataBase.beginTransaction();
            dataBase.createNativeQuery(sql);
            dataBase.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            dataBase.close();
        }
    }
}
