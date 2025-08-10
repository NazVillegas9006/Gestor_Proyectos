package com.utn.gestor.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DB {
    private static String URL;
    private static String USER;
    private static String PASS;

    static {
        try {
            Properties props = new Properties();
            InputStream is = DB.class.getClassLoader().getResourceAsStream("config.properties");
            if (is != null) {
                props.load(is);
                URL = props.getProperty("db.url");
                USER = props.getProperty("db.user");
                PASS = props.getProperty("db.pass");
            } else {
                throw new RuntimeException("No se encontró config.properties");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error cargando configuración DB: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
