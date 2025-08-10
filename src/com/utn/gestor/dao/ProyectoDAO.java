package com.utn.gestor.dao;

import com.utn.gestor.config.DB;
import com.utn.gestor.modelo.Proyecto;
import com.utn.gestor.modelo.LogError;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDAO {

    public List<Proyecto> listar() {
        List<Proyecto> out = new ArrayList<>();
        String sql = "SELECT id,nombre,descripcion,fecha_inicio,fecha_fin,estado,porcentaje FROM proyectos ORDER BY id DESC";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proyecto p = new Proyecto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha_inicio")==null?null:rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin")==null?null:rs.getDate("fecha_fin").toLocalDate(),
                        rs.getString("estado"),
                        rs.getInt("porcentaje")
                );
                out.add(p);
            }
        } catch (Exception e) {
            LogErrorDAO.registrar(new LogError("ProyectoDAO.listar", e.getMessage(), stack(e)));
        }
        return out;
    }

    public boolean crear(Proyecto p) {
        String sql = "INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado, porcentaje) VALUES (?,?,?,?,?,?)";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            if (p.getFechaInicio()!=null) ps.setDate(3, Date.valueOf(p.getFechaInicio())); else ps.setNull(3, Types.DATE);
            if (p.getFechaFin()!=null) ps.setDate(4, Date.valueOf(p.getFechaFin())); else ps.setNull(4, Types.DATE);
            ps.setString(5, p.getEstado());
            ps.setInt(6, p.getPorcentaje());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            LogErrorDAO.registrar(new LogError("ProyectoDAO.crear", e.getMessage(), stack(e))); return false;
        }
    }

    public boolean actualizar(Proyecto p) {
        String sql = "UPDATE proyectos SET nombre=?, descripcion=?, fecha_inicio=?, fecha_fin=?, estado=?, porcentaje=? WHERE id=?";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            if (p.getFechaInicio()!=null) ps.setDate(3, Date.valueOf(p.getFechaInicio())); else ps.setNull(3, Types.DATE);
            if (p.getFechaFin()!=null) ps.setDate(4, Date.valueOf(p.getFechaFin())); else ps.setNull(4, Types.DATE);
            ps.setString(5, p.getEstado());
            ps.setInt(6, p.getPorcentaje());
            ps.setInt(7, p.getId());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            LogErrorDAO.registrar(new LogError("ProyectoDAO.actualizar", e.getMessage(), stack(e))); return false;
        }
    }

    public boolean eliminar(int id) {
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM proyectos WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            LogErrorDAO.registrar(new LogError("ProyectoDAO.eliminar", e.getMessage(), stack(e))); return false;
        }
    }

    private String stack(Exception e){
        StringBuilder sb=new StringBuilder();
        for (StackTraceElement el: e.getStackTrace()) sb.append(el.toString()).append("\n");
        return sb.toString();
    }
}
