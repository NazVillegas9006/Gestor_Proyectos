package com.utn.gestor.dao;

import com.utn.gestor.config.DB;
import com.utn.gestor.modelo.Usuario;
import com.utn.gestor.modelo.LogError;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public List<Usuario> listar(){
        List<Usuario> out=new ArrayList<>();
        try(Connection cn=DB.getConnection();
            PreparedStatement ps=cn.prepareStatement("SELECT id,nombre,email,rol,password_hash FROM usuarios ORDER BY id DESC");
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                out.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        }catch(Exception e){ LogErrorDAO.registrar(new LogError("UsuarioDAO.listar", e.getMessage(), stack(e))); }
        return out;
    }

    public boolean crear(Usuario u){
        try(Connection cn=DB.getConnection();
            PreparedStatement ps=cn.prepareStatement("INSERT INTO usuarios(nombre,email,rol,password_hash) VALUES(?,?,?,?)")){
            ps.setString(1,u.getNombre()); ps.setString(2,u.getEmail()); ps.setString(3,u.getRol()); ps.setString(4,u.getPasswordHash());
            return ps.executeUpdate()==1;
        }catch(Exception e){ LogErrorDAO.registrar(new LogError("UsuarioDAO.crear", e.getMessage(), stack(e))); return false; }
    }

    public boolean actualizar(Usuario u){
        try(Connection cn=DB.getConnection();
            PreparedStatement ps=cn.prepareStatement("UPDATE usuarios SET nombre=?,email=?,rol=?,password_hash=? WHERE id=?")){
            ps.setString(1,u.getNombre()); ps.setString(2,u.getEmail()); ps.setString(3,u.getRol()); ps.setString(4,u.getPasswordHash()); ps.setInt(5,u.getId());
            return ps.executeUpdate()==1;
        }catch(Exception e){ LogErrorDAO.registrar(new LogError("UsuarioDAO.actualizar", e.getMessage(), stack(e))); return false; }
    }

    public boolean eliminar(int id){
        try(Connection cn=DB.getConnection();
            PreparedStatement ps=cn.prepareStatement("DELETE FROM usuarios WHERE id=?")){
            ps.setInt(1,id); return ps.executeUpdate()==1;
        }catch(Exception e){ LogErrorDAO.registrar(new LogError("UsuarioDAO.eliminar", e.getMessage(), stack(e))); return false; }
    }

    private String stack(Exception e){
        StringBuilder sb=new StringBuilder();
        for (StackTraceElement el: e.getStackTrace()) sb.append(el.toString()).append("\n");
        return sb.toString();
    }
}
