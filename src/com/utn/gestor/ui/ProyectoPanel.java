package com.utn.gestor.ui;

import com.utn.gestor.modelo.Proyecto;
import com.utn.gestor.servicio.ProyectoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ProyectoPanel extends JPanel {
    private final ProyectoService service = new ProyectoService();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID","Nombre","Estado","%","Inicio","Fin","Descripción"}, 0){
        public boolean isCellEditable(int r,int c){ return false;}
    };
    private final JTable table = new JTable(model);

    public ProyectoPanel(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd=new JButton("Agregar");
        JButton btnEdit=new JButton("Editar");
        JButton btnDel=new JButton("Eliminar");
        JButton btnRef=new JButton("Actualizar");

        top.add(btnAdd); top.add(btnEdit); top.add(btnDel); top.add(btnRef);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> agregar());
        btnEdit.addActionListener(e -> editar());
        btnDel.addActionListener(e -> eliminar());
        btnRef.addActionListener(e -> cargar());

        cargar();
    }

    private void cargar(){
        model.setRowCount(0);
        List<Proyecto> lista = service.listar();
        for (Proyecto p : lista){
            model.addRow(new Object[]{ p.getId(), p.getNombre(), p.getEstado(), p.getPorcentaje(),
                    p.getFechaInicio(), p.getFechaFin(), p.getDescripcion() });
        }
    }

    private void agregar(){
        Proyecto p = pedirProyecto(null);
        if (p!=null && service.crear(p)) cargar();
    }
    private void editar(){
        int r = table.getSelectedRow();
        if (r<0){ JOptionPane.showMessageDialog(this,"Seleccione un proyecto"); return; }
        Proyecto p = new Proyecto();
        p.setId((Integer) table.getValueAt(r,0));
        p.setNombre((String) table.getValueAt(r,1));
        p.setEstado((String) table.getValueAt(r,2));
        p.setPorcentaje((Integer) table.getValueAt(r,3));
        Object fi = table.getValueAt(r,4);
        Object ff = table.getValueAt(r,5);
        p.setFechaInicio(fi==null?null:LocalDate.parse(fi.toString()));
        p.setFechaFin(ff==null?null:LocalDate.parse(ff.toString()));
        p.setDescripcion((String) table.getValueAt(r,6));

        Proyecto mod = pedirProyecto(p);
        if (mod!=null){ mod.setId(p.getId()); if (service.actualizar(mod)) cargar(); }
    }
    private void eliminar(){
        int r = table.getSelectedRow();
        if (r<0){ JOptionPane.showMessageDialog(this,"Seleccione un proyecto"); return; }
        int id = (Integer) table.getValueAt(r,0);
        if (JOptionPane.showConfirmDialog(this,"¿Eliminar proyecto "+id+"?","Confirmar",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            if (service.eliminar(id)) cargar();
        }
    }

    private Proyecto pedirProyecto(Proyecto base){
        JTextField txtNombre = new JTextField(base==null?"":base.getNombre());
        JTextField txtEstado = new JTextField(base==null?"Planeado":base.getEstado());
        JSpinner spPct = new JSpinner(new SpinnerNumberModel(base==null?0:base.getPorcentaje(),0,100,1));
        JTextField txtInicio = new JTextField(base!=null && base.getFechaInicio()!=null? base.getFechaInicio().toString() : "");
        JTextField txtFin = new JTextField(base!=null && base.getFechaFin()!=null? base.getFechaFin().toString() : "");
        JTextArea txtDesc = new JTextArea(base==null?"":base.getDescripcion()); txtDesc.setRows(4);

        JPanel p = new JPanel(new GridLayout(0,2,5,5));
        p.add(new JLabel("Nombre:")); p.add(txtNombre);
        p.add(new JLabel("Estado:")); p.add(txtEstado);
        p.add(new JLabel("Porcentaje:")); p.add(spPct);
        p.add(new JLabel("Fecha Inicio (YYYY-MM-DD):")); p.add(txtInicio);
        p.add(new JLabel("Fecha Fin (YYYY-MM-DD):")); p.add(txtFin);
        p.add(new JLabel("Descripción:")); p.add(new JScrollPane(txtDesc));

        int ok = JOptionPane.showConfirmDialog(this, p, base==null?"Agregar Proyecto":"Editar Proyecto", JOptionPane.OK_CANCEL_OPTION);
        if (ok==JOptionPane.OK_OPTION){
            if (txtNombre.getText().isBlank()){ JOptionPane.showMessageDialog(this,"Nombre requerido"); return null; }
            Proyecto pr = new Proyecto();
            pr.setNombre(txtNombre.getText().trim());
            pr.setEstado(txtEstado.getText().trim());
            pr.setPorcentaje((Integer) spPct.getValue());
            pr.setDescripcion(txtDesc.getText().trim());
            try { pr.setFechaInicio(txtInicio.getText().isBlank()?null:LocalDate.parse(txtInicio.getText().trim())); } catch(Exception ex){}
            try { pr.setFechaFin(txtFin.getText().isBlank()?null:LocalDate.parse(txtFin.getText().trim())); } catch(Exception ex){}
            return pr;
        }
        return null;
    }
}
