package fes.aragon.compilador;

import javax.swing.*;

public class si extends JFrame {
    private String title;
    private Juego gridPanel;

    public si() {
        initComponents();
        init();
    }

    private void initComponents() {
        gridPanel = new Juego();

        // Configurar el layout del JFrame
        setLayout(new java.awt.BorderLayout());
        add(gridPanel, java.awt.BorderLayout.CENTER);
    }

    private void init() {
        title = "Juego:D";
        setLocationRelativeTo(null);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    // Método principal para probar la interfaz
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new si();
        });
    }
}