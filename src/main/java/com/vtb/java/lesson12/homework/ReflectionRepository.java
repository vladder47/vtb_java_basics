package com.vtb.java.lesson12.homework;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReflectionRepository<T> {
    private final Class<T> myClass;
    private final Connection conn;
    private PreparedStatement ps;
    private final Field[] fields;
    // поле, в котором хранится название поля с аннотацией @DbId
    private String idName;
    // поле, в котором хранится название таблицы
    private final String tableName;
    private PreparedStatement insertPrepStmt;

    public ReflectionRepository(Class<T> myClass, Connection conn) throws SQLException {
        checkAnnotations(myClass);
        this.myClass = myClass;
        this.conn = conn;
        this.fields = myClass.getDeclaredFields();
        this.tableName =  myClass.getAnnotation(DbTable.class).name();
        this.insertPrepStmt = buildQueryInsert();
        // узнаем имя поля с аннотацией @DbId
        getIdName();
    }

    // проверка наличия соответсвующих аннотаций в классе
    private void checkAnnotations(Class<T> myClass) {
        if (!myClass.isAnnotationPresent(DbTable.class)) {
            throw new RuntimeException("Для данного класса нельзя создать ReflectionRepository");
        }
        boolean checkId = false;
        boolean checkColumn = false;
        for (Field f : myClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(DbId.class)) {
                checkId = true;
            }
            if (f.isAnnotationPresent(DbColumn.class)) {
                checkColumn = true;
            }
        }
        if (!checkId) {
            throw new RuntimeException("В классе отсутствует поле с аннотацией @DbId");
        }
        if (!checkColumn) {
            throw new RuntimeException("В классе отсутствует поле с аннотацией @DbColumn");
        }
    }

    // добавление одной записи в БД
    public void insertObject(T obj) throws SQLException, IllegalAccessException {
        fillPreparedStatement(obj);
        insertPrepStmt.executeUpdate();
    }

    // добавление списка записей в БД
    public void insertObjects(List<T> objects) throws IllegalAccessException, SQLException {
        for (T obj : objects) {
            fillPreparedStatement(obj);
            insertPrepStmt.addBatch();
        }
        insertPrepStmt.executeBatch();
    }

    // формирование запроса для отправки записи в БД
    // в query формируется строка вида "INSERT INTO students (name, avgScore) VALUES "
    // в fieldsName - "(?, ?)"
    private PreparedStatement buildQueryInsert() throws SQLException {
        StringBuilder query = new StringBuilder(String.format("INSERT INTO %s(", tableName));
        StringBuilder fieldsName = new StringBuilder("(");
        for (Field f : fields) {
            if (f.isAnnotationPresent(DbColumn.class)) {
                query.append(f.getName()).append(", ");
                fieldsName.append("?, ");
            }
        }
        query.setLength(query.length() - 2);
        fieldsName.setLength(fieldsName.length() - 2);
        query.append(") VALUES");
        fieldsName.append(")");
        query.append(fieldsName);
        insertPrepStmt = conn.prepareStatement(query.toString());

        return insertPrepStmt;
    }

    private void fillPreparedStatement(T obj) throws IllegalAccessException, SQLException {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].isAnnotationPresent(DbColumn.class)) {
                if (fields[i].getType() == String.class) {
                    insertPrepStmt.setString(i, (String) fields[i].get(obj));
                } else {
                    insertPrepStmt.setInt(i, (int) fields[i].get(obj));
                }
            }
        }
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
        ps = conn.prepareStatement(String.format("SELECT * FROM %s", tableName));
        ResultSet rs = ps.executeQuery();
        List<T> objects = new ArrayList<>();
        while (rs.next()) {
            objects.add(createObject(rs));
        }
        return objects;
    }

    // создание объекта класса T
    private T createObject(ResultSet rs) throws SQLException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
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
}
