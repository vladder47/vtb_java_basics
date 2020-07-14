package com.vtb.java.lesson12.homework;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReflectionRepository<T> {
    private Class<T> myClass;
    private Connection conn;
    private Statement stmt;
    private PreparedStatement ps;
    // поле, в котором хранится название поля с аннотацией @DbId
    private String idName;
    // поле, в котором хранится название таблицы
    private String tableName;

    public ReflectionRepository(Class<T> myClass) {
        if (!myClass.isAnnotationPresent(DbTable.class)) {
            throw new RuntimeException("Для данного класса нельзя создать ReflectionRepository");
        }
        this.myClass = myClass;
        this.tableName =  myClass.getAnnotation(DbTable.class).name();

    }

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:students.db");
        stmt = conn.createStatement();
    }

    // добавление одной записи в БД
    public void insertObject(T obj) throws SQLException, IllegalAccessException {
        stmt.executeUpdate(buildQueryInsert(obj));
    }

    // добавление списка записей в БД
    public void insertObjects(List<T> objects) throws IllegalAccessException, SQLException {
        for (T obj : objects) {
            stmt.addBatch(buildQueryInsert(obj));
        }
        stmt.executeBatch();
    }

    // формирование запроса для отправки записи в БД
    // в query формируется строка вида "INSERT INTO students (name, avgScore) VALUES "
    // в fieldsName - "('Vladislav', 90)"
    private String buildQueryInsert(T obj) throws IllegalAccessException {
        Field[] fields = myClass.getDeclaredFields();
        StringBuilder query = new StringBuilder(String.format("INSERT INTO %s(", tableName));
        StringBuilder fieldsName = new StringBuilder("(");
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(DbColumn.class)) {
                query.append(f.getName()).append(", ");
                if (f.getType() == String.class) {
                    fieldsName.append(String.format("'%s', ", f.get(obj)));
                } else {
                    fieldsName.append(f.get(obj)).append(", ");
                }
            }
        }
        query.setLength(query.length() - 2);
        fieldsName.setLength(fieldsName.length() - 2);
        query.append(") VALUES");
        fieldsName.append(")");
        query.append(fieldsName);

        return query.toString();
    }

    // удаление по ID
    public void deleteById(long id) throws SQLException {
        ps = conn.prepareStatement(String.format("DELETE FROM %s WHERE id=?", tableName));
        ps.setLong(1, id);
        ps.executeUpdate();
    }


    // удаление всех записей
    public void deleteAll() throws SQLException {
        ps = conn.prepareStatement(String.format("DELETE FROM %s", tableName));
        ps.executeUpdate();
    }

    // получение записи по ID
    public T getById(long id) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        ps = conn.prepareStatement(String.format("SELECT * FROM %s WHERE id=?", tableName));
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        return createObject(rs);
    }

    // получение всех записей
    public List<T> getAll() throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s", tableName));
        List<T> objects = new ArrayList<>();
        while (rs.next()) {
            objects.add(createObject(rs));
        }
        return objects;
    }

    // создание объекта класса T
    private T createObject(ResultSet rs) throws SQLException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        // узнаем названия поля с аннотацией @DbId
        if (idName == null) {
            getIdName();
        }
        Field column;
        T obj = myClass.getConstructor().newInstance();
        int columns = rs.getMetaData().getColumnCount();
        for (int i = 1; i < columns + 1; i++) {
            String columnName = rs.getMetaData().getColumnName(i);
            if (columnName.equals("id")) {
                column = myClass.getDeclaredField(idName);
            } else {
                column = myClass.getDeclaredField(columnName);
            }
            column.setAccessible(true);
            // 4 соответствует типу int
            if (rs.getMetaData().getColumnType(i) == 4) {
                column.set(obj, rs.getInt(i));
            } else {
                column.set(obj, rs.getString(i));
            }
        }
        return obj;
    }

    // метод для определения поля с аннотацией @DbId
    private void getIdName() {
        Field[] fields = myClass.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(DbId.class)) {
                this.idName = f.getName();
            }
        }
    }

    public void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
