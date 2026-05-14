package Proyecto.Vista.VClientes;

import Proyecto.Controlador.CClientes;
import Proyecto.Controlador.CEntrada;
import Proyecto.Controlador.CVisita;
import Proyecto.Modelo.Clientes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VCModificar {
    private static JFrame fModificar = new JFrame();
    // Este modelo sirve para actualizar la tabla de la vista
    private static DefaultTableModel modelo;

    // Estas variables me sirven para saber si hay cambios
    private static String dniAnterior = "";
    private static String edadAnterior = "";
    private static String nombreAnterior = "";

    private static JTextField tFC1 = new JTextField();
    private static JTextField tFC2 = new JTextField();
    private static JTextField tFC3 = new JTextField();

    // Este método inicializa todo de la ventana
    public static void construir() {
        // Hago que no se pueda cambiar el tamaño a la ventana
        fModificar.setResizable(false);

        fModificar.setTitle("Modificar clientes");
        fModificar.setSize(500, 170);
        fModificar.setLayout(new BorderLayout());

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

        JButton botonModificar = new JButton("Modificar");

        panelS.add(botonModificar);

        panelC.setBorder(new EmptyBorder(15, 15, 0, 15));
        panelS.setBorder(new EmptyBorder(15, 15, 15, 15));
        fModificar.add(panelC, BorderLayout.CENTER);
        fModificar.add(panelS, BorderLayout.SOUTH);

        botonModificar.addActionListener(a -> {
            // En cuanto se active al botón se comprueba que se haya modificado al menos un campo
            if (!tFC1.getText().equals(dniAnterior) || !tFC2.getText().equals(edadAnterior) || !tFC3.getText().equals(nombreAnterior)){
                boolean modificar;
                // Compruebo que no dependa ningún elemento de este en caso de que se modifique su clave primaria
                if ((!CEntrada.seleccionarPorDni(dniAnterior).isEmpty() || !CVisita.seleccionarPorDni(dniAnterior).isEmpty()) && !tFC1.getText().equals(dniAnterior)) {
                    try {
                        // Creo un cliente para validar que el cliente es válido antes de preguntarle, si ha modificado el dni también compruebo concurrencia
                        if (tFC1.getText().equals(dniAnterior)){
                            Clientes comprobar = new Clientes(tFC1.getText(), tFC2.getText(), tFC3.getText(), false);
                        } else {
                            Clientes comprobar = new Clientes(tFC1.getText(), tFC2.getText(), tFC3.getText(), true);
                        }

                        // Sí depende algún elemento le pregunto si quiere eliminarlo
                        int respuesta = JOptionPane.showConfirmDialog(
                                null,
                                "De este cliente dependen " + CEntrada.seleccionarPorDni(dniAnterior).size() + " entradas y " + CVisita.seleccionarPorDni(dniAnterior).size() + " visitas\n¿Quieres modificarlas también?",
                                "Confirmación",
                                JOptionPane.YES_NO_OPTION
                        );

                        // Si selecciona si al modificarse se modifican los elementos que dependan de este
                        if (respuesta == JOptionPane.YES_OPTION) {
                            // Activo el borrado del elemento
                            modificar = true;
                        } else {
                            // En caso de que haya seleccionado no o haya cerrado la ventana no se elmina nada
                            modificar = false;
                        }
                    } catch (IllegalArgumentException e){
                        JFrame mensaje = new JFrame("Información sobre la operación");
                        JOptionPane.showMessageDialog(
                                mensaje,
                                "Han ocurrido errores con los datos del cliente, causa:\n"+e.getMessage(),
                                "Error de formato",
                                JOptionPane.ERROR_MESSAGE
                        );
                        modificar = false;
                    }
                } else {
                    modificar = true;
                }
                if (modificar) {
                    // Se mostrará el mensaje que responda la modificación, después asigno los nuevos valores "antiguos" y actualizo la tabla
                    JFrame mensaje = new JFrame("Información sobre la operación");
                    String resp;
                    JOptionPane.showMessageDialog(
                            mensaje,
                            resp = CClientes.modificar(dniAnterior, edadAnterior, nombreAnterior, tFC1.getText(), tFC2.getText(), tFC3.getText()),
                            "Proceso de modificación",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    if (resp.equals("Cliente modificado con éxito")) {
                        dniAnterior = tFC1.getText();
                        edadAnterior = tFC2.getText();
                        nombreAnterior = tFC3.getText();
                        VClientes.actualizarTabla(modelo);
                    }
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

    public static void mostrar(Point posicion, DefaultTableModel modeloNuevo, String dni, String edad, String nombre){
        // Guardo los valores antiguos
        dniAnterior = dni;
        edadAnterior = edad;
        nombreAnterior = nombre;

        // Asigno el valor de la fila seleccionada a los campos de texto
        tFC1.setText(dni);
        tFC2.setText(edad);
        tFC3.setText(nombre);

        // Sitúo la ventana
        fModificar.setLocation((int) posicion.getX()+250, (int) posicion.getY()+265);
        fModificar.setVisible(true);

        modelo = modeloNuevo;
    }

    public static void ocultar(){
        fModificar.setVisible(false);
    }
}
