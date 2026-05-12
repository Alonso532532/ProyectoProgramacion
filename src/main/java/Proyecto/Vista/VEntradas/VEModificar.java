package Proyecto.Vista.VEntradas;

import Proyecto.Controlador.CEntrada;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VEModificar {
    private static JFrame fModificar = new JFrame();
    // Este modelo sirve para actualizar la tabla de la vista
    private static DefaultTableModel modelo;

    // Estas variables me sirven para saber si hay cambios
    private static String numeroDeEntrada = "";
    private static String tipoAnterior = "";
    private static String precioAnterior = "";
    private static String dniAnterior = "";

    private static TextField tFC1 = new TextField();

    //Creo el combobox
    private static String[] opciones = {"Normal", "Oferta", "Familia numerosa"};
    private static JComboBox<String> cBC1 = new JComboBox<>(opciones);

    private static TextField tFC3 = new TextField();
    private static TextField tFC4 = new TextField();

    // Este método inicializa todo de la ventana
    public static void construir() {
        // Hago que no se pueda cambiar el tamaño a la ventana
        fModificar.setResizable(false);

        fModificar.setTitle("Modificar entradas");
        fModificar.setSize(500, 170);
        fModificar.setLayout(new BorderLayout());

        JPanel panelC = new JPanel(new GridLayout(2, 3, 10, 5));

        JLabel labelC1 = new JLabel();
        JLabel labelC2 = new JLabel();
        JLabel labelC3 = new JLabel();
        JLabel labelC4 = new JLabel();

        labelC1.setText("Número");
        labelC2.setText("Tipo");
        labelC3.setText("Precio");
        labelC4.setText("DNI");

        tFC1.setFocusable(false);
        tFC1.setBackground(new Color(229, 229, 229));

        panelC.add(labelC1);
        panelC.add(labelC2);
        panelC.add(labelC3);
        panelC.add(labelC4);

        panelC.add(tFC1);
        panelC.add(cBC1);
        panelC.add(tFC3);
        panelC.add(tFC4);

        JPanel panelS = new JPanel(new FlowLayout());

        JButton botonModificar = new JButton("Modificar");

        panelS.add(botonModificar);

        panelC.setBorder(new EmptyBorder(15, 15, 0, 15));
        panelS.setBorder(new EmptyBorder(15, 15, 15, 15));
        fModificar.add(panelC, BorderLayout.CENTER);
        fModificar.add(panelS, BorderLayout.SOUTH);

        botonModificar.addActionListener(a -> {
            // En cuanto se active al botón se comprueba que se haya modificado almenos un campo
            if (!cBC1.getSelectedItem().equals(tipoAnterior) || !tFC3.getText().equals(precioAnterior) || !tFC4.getText().equals(dniAnterior)){

                // Se mostrará el mensaje que responda la modificación, después asigno los nuevos valores "antiguos" y actualizo la tabla
                JFrame mensaje = new JFrame("Proceso de modificación");
                String resp;
                JOptionPane.showMessageDialog(
                        mensaje,
                        resp = CEntrada.modificar(Integer.parseInt(numeroDeEntrada), tipoAnterior, precioAnterior, dniAnterior, (String) cBC1.getSelectedItem(), tFC3.getText(), tFC4.getText()),
                        "Información sobre la operación",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (resp.equals("Entrada modificada con éxito")) {
                    tipoAnterior = (String) cBC1.getSelectedItem();
                    precioAnterior = tFC3.getText();
                    dniAnterior = tFC4.getText();
                    VEntradas.actualizarTabla(modelo);
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
        });
    }

    public static void mostrar(Point posicion, DefaultTableModel modeloNuevo, String numeroDeEntrada, String tipo, String precio, String dni){
        // Guardo los valores antiguos
        VEModificar.numeroDeEntrada = numeroDeEntrada;
        tipoAnterior = tipo;
        precioAnterior = precio;
        dniAnterior = dni;

        // Asigno el valor de la fila seleccionada a los campos de texto y al combobox
        tFC1.setText(numeroDeEntrada);
        switch (tipo){
            case "Normal":
                cBC1.setSelectedIndex(0);
                break;
            case "Oferta":
                cBC1.setSelectedIndex(1);
                break;
            case "Familia numerosa":
                cBC1.setSelectedIndex(2);
                break;
        }
        tFC3.setText(precio);
        tFC4.setText(dni);

        // Sitúo la ventana
        fModificar.setLocation((int) posicion.getX()+250, (int) posicion.getY()+265);
        fModificar.setVisible(true);

        modelo = modeloNuevo;
    }

    public static void ocultar(){
        fModificar.dispose();
    }
}
