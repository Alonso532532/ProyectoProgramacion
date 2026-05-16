package Proyecto.Vista.VAtracciones;

import Proyecto.Controlador.CAtracciones;
import Proyecto.Controlador.CZonas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VAModificar {
    private static JFrame fModificar = new JFrame();
    // Este modelo sirve para actualizar la tabla de la vista
    private static DefaultTableModel modelo;

    // Estas variables me sirven para saber si hay cambios
    private static String nombreAnterior = "";
    private static String numeroDeZonaAnterior = "";

    private static JTextField tFC1 = new JTextField();
    private static JTextField tFC2 = new JTextField();
    //Creo el combobox
    private static JComboBox<String> cBC1 = new JComboBox<>();

    // Este método inicializa todo de la ventana
    public static void construir() {
        // Hago que no se pueda cambiar el tamaño a la ventana
        fModificar.setResizable(false);

        fModificar.setTitle("Modificar atracciones");
        fModificar.setSize(500, 170);
        fModificar.setLayout(new BorderLayout());

        JPanel panelC = new JPanel(new GridLayout(2, 3, 10, 5));

        JLabel labelC1 = new JLabel();
        JLabel labelC2 = new JLabel();
        JLabel labelC3 = new JLabel();

        labelC1.setText("Numero de atraccion");
        labelC2.setText("Nombre");
        labelC3.setText("Numero de zona");

        panelC.add(labelC1);
        panelC.add(labelC2);
        panelC.add(labelC3);

        tFC1.setFocusable(false);
        tFC1.setBackground(new Color(229, 229, 229));

        panelC.add(tFC1);
        panelC.add(tFC2);
        panelC.add(cBC1);

        JPanel panelS = new JPanel(new FlowLayout());

        JButton botonModificar = new JButton("Modificar");

        panelS.add(botonModificar);

        panelC.setBorder(new EmptyBorder(15, 15, 0, 15));
        panelS.setBorder(new EmptyBorder(15, 15, 15, 15));
        fModificar.add(panelC, BorderLayout.CENTER);
        fModificar.add(panelS, BorderLayout.SOUTH);

        botonModificar.addActionListener(a -> {
            // Compruebo que todos los campos tengan valores
            if (!tFC1.getText().isEmpty() && !tFC2.getText().isEmpty()) {
                // En cuanto se active al botón se comprueba que se haya modificado almenos un campo
                if (!tFC2.getText().equals(nombreAnterior) || !cBC1.getSelectedItem().toString().substring(0, cBC1.getSelectedItem().toString().indexOf("-")).equals(numeroDeZonaAnterior)){
                    // Se mostrará el mensaje que responda la modificación, después asigno los nuevos valores "antiguos" y actualizo la tabla
                    JFrame mensaje = new JFrame("Proceso de modificación");
                    String resp;
                    JOptionPane.showMessageDialog(
                            mensaje,
                            resp = CAtracciones.modificar(tFC1.getText(), nombreAnterior, numeroDeZonaAnterior, tFC2.getText(), cBC1.getSelectedItem().toString().substring(0, cBC1.getSelectedItem().toString().indexOf("-"))),
                            "Información sobre la operación",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    if (resp.equals("Atracción modificada con éxito")) {
                        nombreAnterior = tFC2.getText();
                        numeroDeZonaAnterior = cBC1.getSelectedItem().toString().substring(0, cBC1.getSelectedItem().toString().indexOf("-"));
                        VAtracciones.actualizarTabla(modelo);
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
                        "Hay campos vacíos",
                        "Error de campos vacíos",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    public static void mostrar(Point posicion, DefaultTableModel modeloNuevo, String numeroDeAtraccion, String nombre, String numeroDeZona){
        // Guardo los valores antiguos
        nombreAnterior = nombre;
        numeroDeZonaAnterior = numeroDeZona;

        // Asigno el valor de la fila seleccionada a los campos de texto
        tFC1.setText(numeroDeAtraccion);
        tFC2.setText(nombre);

        // Inicializo el combobox cada vez que se muestra la vista con las zonas
        String[] opciones = CZonas.seleccionarTodo().stream().map(a -> a.getNumeroDeZona() + "-" + a.getNombre()).toList().toArray(new String[0]);
        cBC1.removeAllItems();

        for (String opcion : opciones) {
            cBC1.addItem(opcion);
        }

        // Busco la opción para poner por defécto
        int index = 0;
        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i].toString().substring(0, opciones[i].indexOf("-")).equals(numeroDeZona)) index = i;
        }

        cBC1.setSelectedIndex(index);

        // Sitúo la ventana
        fModificar.setLocation((int) posicion.getX()+250, (int) posicion.getY()+265);
        fModificar.setVisible(true);

        modelo = modeloNuevo;
    }

    public static void ocultar(){
        fModificar.setVisible(false);
    }
}
