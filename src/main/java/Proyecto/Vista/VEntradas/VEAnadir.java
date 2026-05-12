package Proyecto.Vista.VEntradas;

import Proyecto.Controlador.CEntrada;
import Proyecto.Controlador.CZonas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VEAnadir {
    private static JFrame fAnadir = new JFrame();
    // Este modelo sirve para actualizar la tabla de la vista
    private static DefaultTableModel modelo;

    //Creo e inicializo el combobox
    private static JComboBox<String> cBC1 = new JComboBox<>();
    private static TextField tFC1 = new TextField();
    private static TextField tFC2 = new TextField();

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

        labelC1.setText("Tipo");
        labelC2.setText("Precio");
        labelC3.setText("DNI");

        panelC.add(labelC1);
        panelC.add(labelC2);
        panelC.add(labelC3);

        panelC.add(cBC1);
        panelC.add(tFC1);
        panelC.add(tFC2);

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
                JFrame mensaje = new JFrame("Error de formato");
                JOptionPane.showMessageDialog(
                        mensaje,
                        "Error, uno o varios campos están vacíos",
                        "Información sobre la operación",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                JFrame mensaje = new JFrame("Operación para añadir entradas");
                String resp;
                JOptionPane.showMessageDialog(
                        mensaje,
                        resp = CEntrada.anadir((String) cBC1.getSelectedItem(), tFC1.getText(), tFC2.getText()),
                        "Información sobre la operación",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (resp.equals("Entrada introducida con éxito")) {
                    VEntradas.actualizarTabla(modelo);
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
        // Lo vacío y le introduzco nuevas opciónes
        cBC1.removeAllItems();
        for (String opcion : CZonas.seleccionarTodo().stream().map(a -> a.getNumeroDeZona() + "-" + a.getNombre()).toList().toArray(new String[0])) {
            cBC1.addItem(opcion);
        }
        // Selecciono la primera opción
        cBC1.setSelectedIndex(0);
    }

    public static void ocultar(){
        fAnadir.dispose();
    }
}
