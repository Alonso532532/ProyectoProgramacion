package Proyecto.Vista.VZonas;

import Proyecto.Controlador.CZonas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VZModificar {
    private static JFrame fModificar = new JFrame();
    // Este modelo sirve para actualizar la tabla de la vista
    private static DefaultTableModel modelo;

    // Estas variables me sirven para saber si hay cambios
    private static String numeroDeZona = "";
    private static String nombreAnterior = "";

    private static JTextField tFC1 = new JTextField();
    private static JTextField tFC2 = new JTextField();

    // Este método inicializa todo de la ventana
    public static void construir() {
        // Hago que no se pueda cambiar el tamaño a la ventana
        fModificar.setResizable(false);

        fModificar.setTitle("Modificar zonas");
        fModificar.setSize(500, 170);
        fModificar.setLayout(new BorderLayout());

        JPanel panelC = new JPanel(new GridLayout(2, 2, 10, 5));

        JLabel labelC1 = new JLabel();
        JLabel labelC2 = new JLabel();

        labelC1.setText("Numero de zona");
        labelC2.setText("Nombre");

        tFC1.setFocusable(false);
        tFC1.setBackground(new Color(229, 229, 229));

        panelC.add(labelC1);
        panelC.add(labelC2);

        panelC.add(tFC1);
        panelC.add(tFC2);

        JPanel panelS = new JPanel(new FlowLayout());

        JButton botonModificar = new JButton("Modificar");

        panelS.add(botonModificar);

        panelC.setBorder(new EmptyBorder(15, 15, 0, 15));
        panelS.setBorder(new EmptyBorder(15, 15, 15, 15));
        fModificar.add(panelC, BorderLayout.CENTER);
        fModificar.add(panelS, BorderLayout.SOUTH);

        botonModificar.addActionListener(a -> {
            // Compruebo que el campo no esté vacío
            if (!tFC2.getText().isEmpty()) {
                // En cuanto se active al botón se comprueba que se haya modificado almenos un campo
                if (!tFC2.getText().equals(nombreAnterior)){
                    // Se mostrará el mensaje que responda la modificación, después asigno los nuevos valores "antiguos" y actualizo la tabla
                    JFrame mensaje = new JFrame("Información sobre la operación");
                    String resp;
                    JOptionPane.showMessageDialog(
                            mensaje,
                            resp = CZonas.modificar(numeroDeZona, nombreAnterior, tFC2.getText()),
                            "Proceso de modificación",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    if (resp.equals("Zona modificada con éxito")) {
                        numeroDeZona = tFC1.getText();
                        nombreAnterior = tFC2.getText();
                        VZonas.actualizarTabla(modelo);
                    }
                } else {
                    // Si no hay cambios en los campos
                    JFrame mensaje = new JFrame("Información sobre la operación");
                    JOptionPane.showMessageDialog(
                            mensaje,
                            "No han habido cambios en los valores",
                            "Sin cambios",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                // Si hay campos vacíos
                JFrame mensaje = new JFrame("Información sobre la operación");
                JOptionPane.showMessageDialog(
                        mensaje,
                        "El campo del nombre está vacío",
                        "Error de campos vacíos",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    public static void mostrar(Point posicion, DefaultTableModel modeloNuevo, String numeroDeZonaNuevo, String nombre){
        // Guardo los valores antiguos
        numeroDeZona = numeroDeZonaNuevo;
        nombreAnterior = nombre;

        // Asigno el valor de la fila seleccionada a los campos de texto
        tFC1.setText(numeroDeZona);
        tFC2.setText(nombre);

        // Sitúo la ventana
        fModificar.setLocation((int) posicion.getX()+250, (int) posicion.getY()+265);
        fModificar.setVisible(true);

        modelo = modeloNuevo;
    }

    public static void ocultar(){
        fModificar.setVisible(false);
    }
}
