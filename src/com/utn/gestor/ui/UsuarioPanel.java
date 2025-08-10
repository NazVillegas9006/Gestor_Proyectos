package com.utn.gestor.ui;

import com.utn.gestor.modelo.Usuario;
import com.utn.gestor.servicio.UsuarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuarioPanel extends JPanel {
    private final UsuarioService service = new UsuarioService();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Nombre","Email","Rol"},0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);

    public UsuarioPanel(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add=new JButton("Agregar"), edit=new JButton("Editar"), del=new JButton("Eliminar"), ref=new JButton("Actualizar");
        top.add(add); top.add(edit); top.add(del); top.add(ref);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(e->agregar());
        edit.addActionListener(e->editar());
        del.addActionListener(e->eliminar());
        ref.addActionListener(e->cargar());
        cargar();
    }

    private void cargar(){
        model.setRowCount(0);
        List<Usuario> lista = service.listar();
        for (Usuario u: lista){
            model.addRow(new Object[]{ u.getId(), u.getNombre(), u.getEmail(), u.getRol() });
        }
    }

    private void agregar(){
        Usuario u = pedir(null);
        if (u!=null && service.crear(u)) cargar();
    }

    private void editar(){
        int r = table.getSelectedRow();
        if (r<0){ JOptionPane.showMessageDialog(this,"Seleccione un usuario"); return; }
        Usuario u = new Usuario();
        u.setId((Integer)table.getValueAt(r,0));
        u.setNombre((String)table.getValueAt(r,1));
        u.setEmail((String)table.getValueAt(r,2));
        u.setRol((String)table.getValueAt(r,3));
        Usuario m = pedir(u);
        if (m!=null){ m.setId(u.getId()); if (service.actualizar(m)) cargar(); }
    }

    private void eliminar(){
        int r = table.getSelectedRow();
        if (r<0){ JOptionPane.showMessageDialog(this,"Seleccione un usuario"); return; }
        int id = (Integer) table.getValueAt(r,0);
        if (JOptionPane.showConfirmDialog(this,"¿Eliminar usuario "+id+"?","Confirmar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            if (service.eliminar(id)) cargar();
        }
    }

    private Usuario pedir(Usuario base){
        JTextField nombre = new JTextField(base==null?"":base.getNombre());
        JTextField email = new JTextField(base==null?"":base.getEmail());
        JTextField rol = new JTextField(base==null?"USER":base.getRol());
        JPasswordField pass = new JPasswordField();

        JPanel p = new JPanel(new GridLayout(0,2,5,5));
        p.add(new JLabel("Nombre:")); p.add(nombre);
        p.add(new JLabel("Email:")); p.add(email);
        p.add(new JLabel("Rol:")); p.add(rol);
        p.add(new JLabel("Password:")); p.add(pass);

        int ok = JOptionPane.showConfirmDialog(this, p, base==null?"Agregar Usuario":"Editar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (ok==JOptionPane.OK_OPTION){
            if (nombre.getText().isBlank() || email.getText().isBlank()){ JOptionPane.showMessageDialog(this,"Campos requeridos"); return null; }
            Usuario u = new Usuario();
            u.setNombre(nombre.getText().trim());
            u.setEmail(email.getText().trim());
            u.setRol(rol.getText().trim());
            u.setPasswordHash(new String(pass.getPassword()).isBlank() && base!=null ? base.getPasswordHash() : new String(pass.getPassword()));
            return u;
        }
        return null;
    }
}
