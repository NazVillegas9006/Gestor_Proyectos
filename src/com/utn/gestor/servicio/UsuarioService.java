package com.utn.gestor.servicio;

import com.utn.gestor.dao.UsuarioDAO;
import com.utn.gestor.modelo.Usuario;
import java.util.List;

public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();
    public List<Usuario> listar(){ return dao.listar(); }
    public boolean crear(Usuario u){ return dao.crear(u); }
    public boolean actualizar(Usuario u){ return dao.actualizar(u); }
    public boolean eliminar(int id){ return dao.eliminar(id); }
}
