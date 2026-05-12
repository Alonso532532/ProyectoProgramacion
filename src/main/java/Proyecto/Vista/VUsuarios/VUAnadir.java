package Proyecto.Vista.VUsuarios;

import Proyecto.Controlador.CUsuarios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VUAnadir {
    private static JFrame fAnadir = new JFrame();
    // Este modelo sirve para actualizar la tabla de la vista
    private static DefaultTableModel modelo;

    private static JTextField tFC1 = new JTextField();
    private static JPasswordField tFC2 = new JPasswordField();
    private static JCheckBox cBC1 = new JCheckBox();

    // Este método inicializa todo de la ventana
    public static void construir() {
        // Hago que no se pueda cambiar el tamaño a la ventana
        fAnadir.setResizable(false);

        fAnadir.setTitle("Añadir entrada");
        fAnadir.setSize(500, 170);
        fAnadir.setLayout(new BorderLayout());

        JPanel panelC = new JPanel(new GridLayout(2, 3, 10, 5));

        JLabel labelC1 = new JLabel();
        JLabel labelC2 = new JLabel();
        JLabel labelC3 = new JLabel();

        labelC1.setText("Nombre");
        labelC2.setText("Contrasena");
        labelC3.setText("Admin");

        panelC.add(labelC1);
        panelC.add(labelC2);
        panelC.add(labelC3);

        panelC.add(tFC1);
        panelC.add(tFC2);
        panelC.add(cBC1);

        JPanel panelS = new JPanel(new FlowLayout());

        JButton botonAnadir = new JButton("Añadir");

        panelS.add(botonAnadir);

        panelC.setBorder(new EmptyBorder(15, 15, 0, 15));
        panelS.setBorder(new EmptyBorder(15, 15, 15, 15));
        fAnadir.add(panelC, BorderLayout.CENTER);
        fAnadir.add(panelS, BorderLayout.SOUTH);

        botonAnadir.addActionListener(a -> {
            // En cuanto se active al botón se comprueba que no hayan campos vacíos
            if (tFC1.getText().isEmpty() || tFC2.getText().isEmpty()) {
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
                        resp = CUsuarios.anadir(tFC1.getText(), tFC2.getText(), cBC1.isSelected()),
                        "Operación para añadir usuarios",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (resp.equals("Usuario introducido con éxito")) {
                    VUsuarios.actualizarTabla(modelo);
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
        cBC1.setSelected(false);
    }

    public static void ocultar(){
        fAnadir.setVisible(false);
    }
}
