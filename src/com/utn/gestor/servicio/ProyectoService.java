package com.utn.gestor.servicio;

import com.utn.gestor.dao.ProyectoDAO;
import com.utn.gestor.modelo.Proyecto;
import java.util.List;

public class ProyectoService {
    private final ProyectoDAO dao = new ProyectoDAO();
    public List<Proyecto> listar(){ return dao.listar(); }
    public boolean crear(Proyecto p){ return dao.crear(p); }
    public boolean actualizar(Proyecto p){ return dao.actualizar(p); }
    public boolean eliminar(int id){ return dao.eliminar(id); }
}
