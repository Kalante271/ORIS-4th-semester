package ru.itis.dis403.lab_02.orm;

import ru.itis.dis403.lab_02.orm.annotation.Column;
import ru.itis.dis403.lab_02.orm.annotation.Id;
import ru.itis.dis403.lab_02.orm.annotation.ManyToOne;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityManagerImpl implements EntityManager, Closeable {

    private Connection connection;

    public EntityManagerImpl(Connection connection) {
        this.connection = connection;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Определяем имя таблицы по имени класса.toLower()
    // Ищем в классе поля (Id, Column, ManyToOne)
    // Строим SQL запрос: Insert (id = null), Update (id !=null)
    // выполняем через JDBC запрос
    @Override
    public <T> T save(T entity) {
        try {
            Class<?> clazz = entity.getClass();

            Field idField = findIdField(clazz);
            idField.setAccessible(true);
            Object idValue = idField.get(entity);

            if (idValue == null) {
                return insert(entity);
            } else {
                return update(entity);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Object entity) {
        try {
            Class<?> clazz = entity.getClass();
            String tableName = clazz.getSimpleName().toLowerCase();

            Field idField = findIdField(clazz);
            idField.setAccessible(true);
            Object idValue = idField.get(entity);

            String sql = "DELETE FROM " + tableName + " WHERE id=?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, idValue);
                statement.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // по имени класса получаем имя таблицы, фиксируем id
    // select * from tableName where id = key
    // если результат не пустой - создаем объект
    // Ищем в классе поля (Id, Column, ManyToOne)
    // Для каждого поля пытаемся получить значение из ResultSet по имени
    // задаем значения
    // возвращаем созданный объект
    @Override
    public <T> T find(Class<T> entityType, Object key) {
        String tableName = entityType.getSimpleName().toLowerCase();
        String sql = "SELECT * FROM " + tableName + " WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setObject(1, key);

            try (ResultSet resultSet = statement.executeQuery()){
                if (!resultSet.next()) {
                    return null;
                }

                T entity = entityType.getDeclaredConstructor().newInstance();

                Field[] fields = entityType.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);

                    if(field.isAnnotationPresent(Id.class) ||
                       field.isAnnotationPresent(Column.class)) {
                        Object value = resultSet.getObject(field.getName());
                        field.set(entity, value);
                    }

                    else if(field.isAnnotationPresent(ManyToOne.class)) {
                        Object foreignKey = resultSet.getObject(field.getName() + "_id");

                        if(foreignKey != null) {
                            Class<?> foreignObjectType = field.getType();
                            Object foreignObject = foreignObjectType.getDeclaredConstructor().newInstance();

                            Field foreignIdField = findIdField(foreignObjectType);
                            foreignIdField.setAccessible(true);
                            foreignIdField.set(foreignObject, foreignKey);

                            field.set(entity, foreignObject);
                        }
                    }
                }
                return entity;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> findAll(Class<T> entityType) {
        List<T> result = new ArrayList<>();

        String tableName = entityType.getSimpleName().toLowerCase();
        String sql = "SELECT * FROM " + tableName;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            Field[] fields = entityType.getDeclaredFields();
            while (resultSet.next()) {
                T entity = entityType.getDeclaredConstructor().newInstance();

                for(Field field : fields) {
                    field.setAccessible(true);

                    if (field.isAnnotationPresent(Id.class) ||
                        field.isAnnotationPresent(Column.class)) {
                        Object value = resultSet.getObject(field.getName());
                        field.set(entity, value);
                    }

                    else if(field.isAnnotationPresent(ManyToOne.class)) {
                        Object foreignKey = resultSet.getObject(field.getName() + "_id");

                        if(foreignKey != null) {
                            Class<?> foreignObjectType = field.getType();
                            Object foreignObject = foreignObjectType.getDeclaredConstructor().newInstance();

                            Field foreignIdField = findIdField(foreignObjectType);
                            foreignIdField.setAccessible(true);
                            foreignIdField.set(foreignObject, foreignKey);

                            field.set(entity, foreignObject);
                        }
                    }
                }
                result.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    private Field findIdField(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }

        throw new RuntimeException("No @Id field in " + entityClass.getName());
    }

    private <T> T insert(T entity) {
        try {
            Class<?> clazz = entity.getClass();
            String tableName = clazz.getSimpleName().toLowerCase();

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ").append(tableName).append(" (");

            List<Object> params = new ArrayList<>();

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {

                field.setAccessible(true);

                if (field.isAnnotationPresent(Column.class)) {
                    sql.append(field.getName()).append(",");
                    params.add(field.get(entity));
                }

                else if (field.isAnnotationPresent(ManyToOne.class)) {
                    sql.append(field.getName()).append("_id,");

                    Object object = field.get(entity);
                    if (object != null) {
                        Field foreignIdField = findIdField(object.getClass());
                        foreignIdField.setAccessible(true);
                        params.add(foreignIdField.get(object));
                    } else {
                        params.add(null);
                    }
                }
            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(") VALUES (");

            for (int i = 0; i < params.size(); i++) {
                sql.append("?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
            sql.append(" RETURNING id");

            try(PreparedStatement statement = connection.prepareStatement(sql.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {

                        Long id = resultSet.getLong("id");

                        Field idField = findIdField(clazz);
                        idField.setAccessible(true);
                        idField.set(entity, id);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    private <T> T update(T entity) {
        try {
            Class<?> clazz = entity.getClass();
            String tableName = clazz.getSimpleName().toLowerCase();

            Field idField = findIdField(clazz);
            idField.setAccessible(true);
            Object idValue = idField.get(entity);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(tableName).append(" SET ");

            List<Object> params = new ArrayList<>();

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {

                field.setAccessible(true);

                if (field.isAnnotationPresent(Column.class)) {
                    sql.append(field.getName()).append("=?,");
                    params.add(field.get(entity));
                }

                else if (field.isAnnotationPresent(ManyToOne.class)) {
                    sql.append(field.getName()).append("_id=?,");

                    Object object = field.get(entity);
                    if (object != null) {
                        Field foreignIdField = findIdField(object.getClass());
                        foreignIdField.setAccessible(true);
                        params.add(foreignIdField.get(object));
                    } else {
                        params.add(null);
                    }
                }
            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id=?");

            params.add(idValue);

            try(PreparedStatement statement = connection.prepareStatement(sql.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }
                statement.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entity;
    }
}
