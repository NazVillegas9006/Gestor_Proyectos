package com.utn.gestor;

import com.utn.gestor.ui.MainFrame;

public class App {
    public static void main(String[] args) {
        System.out.println("Iniciando la interfaz gráfica...");

        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

