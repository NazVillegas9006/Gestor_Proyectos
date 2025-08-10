package com.utn.gestor.ui;

import com.utn.gestor.modelo.Tarea;
import com.utn.gestor.servicio.TareaService;
import com.utn.gestor.servicio.ProyectoService;
import com.utn.gestor.modelo.Proyecto;
import com.utn.gestor.servicio.UsuarioService;
import com.utn.gestor.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TareaPanel extends JPanel {
    private final TareaService service = new TareaService();
    private final ProyectoService proyectoService = new ProyectoService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final JComboBox<Proyecto> cboProyecto = new JComboBox<>();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID","Título","Asignado","Prioridad","Estado","Avance","Vence","Desc"},0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);

    public TareaPanel(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Proyecto:"));
        top.add(cboProyecto);
        JButton add=new JButton("Agregar"), edit=new JButton("Editar"), del=new JButton("Eliminar"), ref=new JButton("Actualizar");
        top.add(add); top.add(edit); top.add(del); top.add(ref);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(e->agregar());
        edit.addActionListener(e->editar());
        del.addActionListener(e->eliminar());
        ref.addActionListener(e->cargarTareas());
        cboProyecto.addActionListener(e->cargarTareas());

        cargarProyectos();
    }

    private void cargarProyectos(){
        cboProyecto.removeAllItems();
        for (Proyecto p: proyectoService.listar()) cboProyecto.addItem(p);
        if (cboProyecto.getItemCount()>0) cboProyecto.setSelectedIndex(0);
        cargarTareas();
    }

    private void cargarTareas(){
        model.setRowCount(0);
        Proyecto sel = (Proyecto) cboProyecto.getSelectedItem();
        if (sel==null) return;
        List<Tarea> lista = service.listarPorProyecto(sel.getId());
        List<Usuario> usuarios = usuarioService.listar();
        for (Tarea t: lista){
            String asignado = "-";
            if (t.getAsignadoA()!=null){
                for (Usuario u: usuarios) if (u.getId().equals(t.getAsignadoA())) { asignado = u.getNombre(); break; }
            }
            model.addRow(new Object[]{ t.getId(), t.getTitulo(), asignado, t.getPrioridad(),
                    t.getEstado(), t.getAvance(), t.getFechaVencimiento(), t.getDescripcion() });
        }
    }

    private void agregar(){
        Proyecto sel = (Proyecto) cboProyecto.getSelectedItem();
        if (sel==null){ JOptionPane.showMessageDialog(this,"Cree un proyecto primero"); return; }
        Tarea t = pedir(null, sel.getId());
        if (t!=null && service.crear(t)) cargarTareas();
    }

    private void editar(){
        int r = table.getSelectedRow();
        if (r<0){ JOptionPane.showMessageDialog(this,"Seleccione una tarea"); return; }
        Proyecto sel = (Proyecto) cboProyecto.getSelectedItem();
        Tarea t = new Tarea();
        t.setId((Integer)table.getValueAt(r,0));
        t.setTitulo((String)table.getValueAt(r,1));
        t.setProyectoId(sel.getId());
        t.setPrioridad((String)table.getValueAt(r,3));
        t.setEstado((String)table.getValueAt(r,4));
        t.setAvance((Integer)table.getValueAt(r,5));
        Object fv = table.getValueAt(r,6);
        t.setFechaVencimiento(fv==null?null:LocalDate.parse(fv.toString()));
        t.setDescripcion((String)table.getValueAt(r,7));
        Tarea m = pedir(t, sel.getId());
        if (m!=null){ m.setId(t.getId()); if (service.actualizar(m)) cargarTareas(); }
    }

    private void eliminar(){
        int r = table.getSelectedRow();
        if (r<0){ JOptionPane.showMessageDialog(this,"Seleccione una tarea"); return; }
        int id = (Integer) table.getValueAt(r,0);
        if (JOptionPane.showConfirmDialog(this,"¿Eliminar tarea "+id+"?","Confirmar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            if (service.eliminar(id)) cargarTareas();
        }
    }

    private Tarea pedir(Tarea base, int proyectoId){
        List<Usuario> usuarios = usuarioService.listar();
        JComboBox<Usuario> cboUser = new JComboBox<>();
        cboUser.addItem(null);
        for (Usuario u: usuarios) cboUser.addItem(u);

        JTextField titulo = new JTextField(base==null?"":base.getTitulo());
        JTextField prioridad = new JTextField(base==null?"Media":base.getPrioridad());
        JTextField estado = new JTextField(base==null?"Pendiente":base.getEstado());
        JSpinner avance = new JSpinner(new SpinnerNumberModel(base==null?0:base.getAvance(),0,100,1));
        JTextField vence = new JTextField(base!=null && base.getFechaVencimiento()!=null ? base.getFechaVencimiento().toString() : "");
        JTextArea desc = new JTextArea(base==null?"":base.getDescripcion()); desc.setRows(4);

        JPanel p = new JPanel(new GridLayout(0,2,5,5));
        p.add(new JLabel("Asignado a:")); p.add(cboUser);
        p.add(new JLabel("Título:")); p.add(titulo);
        p.add(new JLabel("Prioridad:")); p.add(prioridad);
        p.add(new JLabel("Estado:")); p.add(estado);
        p.add(new JLabel("Avance:")); p.add(avance);
        p.add(new JLabel("Vence (YYYY-MM-DD):")); p.add(vence);
        p.add(new JLabel("Descripción:")); p.add(new JScrollPane(desc));

        int ok = JOptionPane.showConfirmDialog(this, p, base==null?"Agregar Tarea":"Editar Tarea", JOptionPane.OK_CANCEL_OPTION);
        if (ok==JOptionPane.OK_OPTION){
            if (titulo.getText().isBlank()){ JOptionPane.showMessageDialog(this,"Título requerido"); return null; }
            Tarea t = new Tarea();
            t.setProyectoId(proyectoId);
            Usuario sel = (Usuario) cboUser.getSelectedItem();
            t.setAsignadoA(sel==null?null:sel.getId());
            t.setTitulo(titulo.getText().trim());
            t.setPrioridad(prioridad.getText().trim());
            t.setEstado(estado.getText().trim());
            t.setAvance((Integer) avance.getValue());
            try { t.setFechaVencimiento(vence.getText().isBlank()?null:LocalDate.parse(vence.getText().trim())); } catch(Exception ex){}
            t.setDescripcion(desc.getText().trim());
            return t;
        }
        return null;
    }
}
