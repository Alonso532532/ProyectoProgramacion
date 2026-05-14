package Proyecto.Vista.VClientes;

import Proyecto.Controlador.CClientes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VCAnadir {
    private static JFrame fAnadir = new JFrame();
    private static DefaultTableModel modelo;

    private static JTextField tFC1 = new JTextField();
    private static JTextField tFC2 = new JTextField();
    private static JTextField tFC3 = new JTextField();

    public static void construir() {
        fAnadir.setResizable(false);

        fAnadir.setTitle("Añadir clientes");
        fAnadir.setSize(500, 170);
        fAnadir.setLayout(new BorderLayout());

        JPanel panelC = new JPanel(new GridLayout(2, 3, 10, 5));

        JLabel labelC1 = new JLabel();
        JLabel labelC2 = new JLabel();
        JLabel labelC3 = new JLabel();

        labelC1.setText("DNI");
        labelC2.setText("Edad");
        labelC3.setText("Nombre");

        panelC.add(labelC1);
        panelC.add(labelC2);
        panelC.add(labelC3);

        panelC.add(tFC1);
        panelC.add(tFC2);
        panelC.add(tFC3);

        JPanel panelS = new JPanel(new FlowLayout());

        JButton botonAnadir = new JButton("Añadir");

        panelS.add(botonAnadir);

        panelC.setBorder(new EmptyBorder(15, 15, 0, 15));
        panelS.setBorder(new EmptyBorder(15, 15, 15, 15));
        fAnadir.add(panelC, BorderLayout.CENTER);
        fAnadir.add(panelS, BorderLayout.SOUTH);

        botonAnadir.addActionListener(a -> {
            if (tFC1.getText().isEmpty() || tFC2.getText().isEmpty() || tFC3.getText().isEmpty()) {
                JFrame mensaje = new JFrame("Información sobre la operación");
                JOptionPane.showMessageDialog(
                        mensaje,
                        "Error, uno o varios campos están vacíos",
                        "Error de formato",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                JFrame mensaje = new JFrame("Información sobre la operación");
                String resp;
                JOptionPane.showMessageDialog(
                        mensaje,
                        resp = CClientes.anadir(tFC1.getText(), tFC2.getText(), tFC3.getText()),
                        "Operación para añadir clientes",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (resp.equals("Cliente introducido con éxito")) {
                    VClientes.actualizarTabla(modelo);
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
        tFC2.setText("");
        tFC3.setText("");
    }

    public static void ocultar(){
        fAnadir.setVisible(false);
    }
}
