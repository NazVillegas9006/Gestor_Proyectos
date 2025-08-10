package com.utn.gestor.dao;

import com.utn.gestor.config.DB;
import com.utn.gestor.modelo.Tarea;
import com.utn.gestor.modelo.LogError;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TareaDAO {
    public List<Tarea> listarPorProyecto(int proyectoId){
        List<Tarea> out=new ArrayList<>();
        String sql="SELECT id, proyecto_id, asignado_a, titulo, descripcion, prioridad, estado, avance, fecha_vencimiento FROM tareas WHERE proyecto_id=? ORDER BY id DESC";
        try(Connection cn=DB.getConnection();
            PreparedStatement ps=cn.prepareStatement(sql)){
            ps.setInt(1, proyectoId);
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    Tarea t=new Tarea();
                    t.setId(rs.getInt("id"));
                    t.setProyectoId(rs.getInt("proyecto_id"));
                    t.setAsignadoA((Integer)rs.getObject("asignado_a"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setDescripcion(rs.getString("descripcion"));
                    t.setPrioridad(rs.getString("prioridad"));
                    t.setEstado(rs.getString("estado"));
                    t.setAvance(rs.getInt("avance"));
                    Date fv = rs.getDate("fecha_vencimiento");
                    t.setFechaVencimiento(fv==null?null:fv.toLocalDate());
                    out.add(t);
                }
            }
        }catch(Exception e){ LogErrorDAO.registrar(new LogError("TareaDAO.listarPorProyecto", e.getMessage(), stack(e))); }
        return out;
    }

    public boolean crear(Tarea t){
        String sql="INSERT INTO tareas(proyecto_id, asignado_a, titulo, descripcion, prioridad, estado, avance, fecha_vencimiento) VALUES(?,?,?,?,?,?,?,?)";
        try(Connection cn=DB.getConnection();
            PreparedStatement ps=cn.prepareStatement(sql)){
            ps.setInt(1, t.getProyectoId());
            if (t.getAsignadoA()!=null) ps.setInt(2, t.getAsignadoA()); else ps.setNull(2, Types.INTEGER);
            ps.setString(3, t.getTitulo());
            ps.setString(4, t.getDescripcion());
            ps.setString(5, t.getPrioridad());
            ps.setString(6, t.getEstado());
            ps.setInt(7, t.getAvance());
            if (t.getFechaVencimiento()!=null) ps.setDate(8, Date.valueOf(t.getFechaVencimiento())); else ps.setNull(8, Types.DATE);
            return ps.executeUpdate()==1;
        }catch(Exception e){ LogErrorDAO.registrar(new LogError("TareaDAO.crear", e.getMessage(), stack(e))); return false; }
    }

    public boolean actualizar(Tarea t){
        String sql="UPDATE tareas SET asignado_a=?, titulo=?, descripcion=?, prioridad=?, estado=?, avance=?, fecha_vencimiento=? WHERE id=?";
        try(Connection cn=DB.getConnection();
            PreparedStatement ps=cn.prepareStatement(sql)){
            if (t.getAsignadoA()!=null) ps.setInt(1, t.getAsignadoA()); else ps.setNull(1, Types.INTEGER);
            ps.setString(2, t.getTitulo());
            ps.setString(3, t.getDescripcion());
            ps.setString(4, t.getPrioridad());
            ps.setString(5, t.getEstado());
            ps.setInt(6, t.getAvance());
            if (t.getFechaVencimiento()!=null) ps.setDate(7, Date.valueOf(t.getFechaVencimiento())); else ps.setNull(7, Types.DATE);
            ps.setInt(8, t.getId());
            return ps.executeUpdate()==1;
        }catch(Exception e){ LogErrorDAO.registrar(new LogError("TareaDAO.actualizar", e.getMessage(), stack(e))); return false; }
    }

    public boolean eliminar(int id){
        try(Connection cn=DB.getConnection();
            PreparedStatement ps=cn.prepareStatement("DELETE FROM tareas WHERE id=?")){
            ps.setInt(1,id); return ps.executeUpdate()==1;
        }catch(Exception e){ LogErrorDAO.registrar(new LogError("TareaDAO.eliminar", e.getMessage(), stack(e))); return false; }
    }

    private String stack(Exception e){
        StringBuilder sb=new StringBuilder();
        for (StackTraceElement el: e.getStackTrace()) sb.append(el.toString()).append("\n");
        return sb.toString();
    }
}
