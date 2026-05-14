package Proyecto.Vista.VZonas;

import Proyecto.Controlador.CZonas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VZAnadir {
    private static JFrame fAnadir = new JFrame();
    // Este modelo sirve para actualizar la tabla de la vista
    private static DefaultTableModel modelo;

    private static JTextField tFC1 = new JTextField();

    // Este método inicializa todo de la ventana
    public static void construir() {
        // Hago que no se pueda cambiar el tamaño a la ventana
        fAnadir.setResizable(false);

        fAnadir.setTitle("Añadir zonas");
        fAnadir.setSize(500, 170);
        fAnadir.setLayout(new BorderLayout());

        JPanel panelC = new JPanel(new GridLayout(2, 1, 10, 5));

        JLabel labelC1 = new JLabel();
        labelC1.setText("Nombre");

        panelC.add(labelC1);
        panelC.add(tFC1);

        JPanel panelS = new JPanel(new FlowLayout());

        JButton botonAnadir = new JButton("Añadir");

        panelS.add(botonAnadir);

        panelC.setBorder(new EmptyBorder(15, 15, 0, 15));
        panelS.setBorder(new EmptyBorder(15, 15, 15, 15));
        fAnadir.add(panelC, BorderLayout.CENTER);
        fAnadir.add(panelS, BorderLayout.SOUTH);

        botonAnadir.addActionListener(a -> {
            // En cuanto se active al botón se comprueba que no hayan campos vacíos
            if (tFC1.getText().isEmpty()) {
                JFrame mensaje = new JFrame("Información sobre la operación");
                JOptionPane.showMessageDialog(
                        mensaje,
                        "Error, el campo está vacío",
                        "Error de formato",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                JFrame mensaje = new JFrame("Información sobre la operación");
                String resp;
                JOptionPane.showMessageDialog(
                        mensaje,
                        resp = CZonas.anadir(tFC1.getText()),
                        "Operación para añadir atracciones",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (resp.equals("Zona introducida con éxito")) {
                    VZonas.actualizarTabla(modelo);
                }
            }
        });
    }

    public static void mostrar(Point posicion, DefaultTableModel modeloNuevo){
        // Lo sitúo
        fAnadir.setLocation((int) posicion.getX()+250, (int) posicion.getY()+265);
        fAnadir.setVisible(true);

        modelo = modeloNuevo;

        tFC1.setText("");
    }

    public static void ocultar(){
        fAnadir.setVisible(false);
    }
}
