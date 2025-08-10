package com.utn.gestor.dao;

import com.utn.gestor.config.DB;
import com.utn.gestor.modelo.LogError;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LogErrorDAO {
    public static void registrar(LogError log) {
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                 "INSERT INTO log_errores (origen, mensaje, traza) VALUES (?,?,?)")) {
            ps.setString(1, log.getOrigen());
            ps.setString(2, log.getMensaje());
            ps.setString(3, log.getTraza());
            ps.executeUpdate();
        } catch (Exception ignored) { /* último recurso: no romper UI si falla logging */ }
    }
}
