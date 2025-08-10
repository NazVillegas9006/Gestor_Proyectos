package com.utn.gestor.servicio;

import com.utn.gestor.dao.TareaDAO;
import com.utn.gestor.modelo.Tarea;
import java.util.List;

public class TareaService {
    private final TareaDAO dao = new TareaDAO();
    public List<Tarea> listarPorProyecto(int proyectoId){ return dao.listarPorProyecto(proyectoId); }
    public boolean crear(Tarea t){ return dao.crear(t); }
    public boolean actualizar(Tarea t){ return dao.actualizar(t); }
    public boolean eliminar(int id){ return dao.eliminar(id); }
}
