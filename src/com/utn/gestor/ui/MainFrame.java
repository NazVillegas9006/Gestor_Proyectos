package com.utn.gestor.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(){
        setTitle("Gestor de Proyectos — UTN");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Proyectos", new ProyectoPanel());
        tabs.addTab("Tareas", new TareaPanel());
        tabs.addTab("Usuarios", new UsuarioPanel());

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
    }
}
