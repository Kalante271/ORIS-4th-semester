package ru.itis.dis403.lab_02.orm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class SchemaUtil {

    public static Set<String> getTables(Connection connection) throws Exception {
        String sql = """
        SELECT table_name
        FROM information_schema.tables
        WHERE table_type = 'BASE TABLE'
        AND table_schema NOT IN ('pg_catalog', 'information_schema')
       """;

        Set<String> tables = new HashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSets = statement.executeQuery()) {

            while (resultSets.next()) {
                tables.add(resultSets.getString("table_name"));
            }
        }

        return tables;
    }

    public static Set<String> getColumns(Connection connection, String tableName) throws Exception {
        String sql = """
        SELECT a.attname
        FROM pg_catalog.pg_attribute a
        WHERE a.attrelid = (
            SELECT c.oid
            FROM pg_catalog.pg_class c
            LEFT JOIN pg_catalog.pg_namespace n
            ON n.oid = c.relnamespace
            WHERE pg_catalog.pg_table_is_visible(c.oid)
            AND c.relname = ?
        )
        AND a.attnum > 0 AND NOT a.attisdropped
        """;

        Set<String> columns = new HashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tableName);

            try (ResultSet resultSets = statement.executeQuery()) {
                while (resultSets.next()) {
                    columns.add(resultSets.getString("attname"));
                }
            }
        }

        return columns;
    }
}
