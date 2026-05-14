package Proyecto.Vista.VUsuarios;

import Proyecto.Controlador.CUsuarios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VUModificar {
    private static JFrame fModificar = new JFrame();
    // Este modelo sirve para actualizar la tabla de la vista
    private static DefaultTableModel modelo;

    // Estas variables me sirven para saber si hay cambios
    private static String nombreAnterior = "";
    private static boolean esAdminAnterior = false;

    private static JTextField tFC1 = new JTextField();
    private static JPasswordField tFC2 = new JPasswordField();
    private static JCheckBox tFC3 = new JCheckBox();

    // Este método inicializa todo de la ventana
    public static void construir() {
        // Hago que no se pueda cambiar el tamaño a la ventana
        fModificar.setResizable(false);

        fModificar.setTitle("Modificar clientes");
        fModificar.setSize(500, 210);
        fModificar.setLayout(new BorderLayout());

        JPanel panelC = new JPanel(new GridLayout(2, 3, 10, 5));

        JLabel labelC1 = new JLabel();
        JLabel labelC2 = new JLabel();
        JLabel labelC3 = new JLabel();

        labelC1.setText("Nombre");
        labelC2.setText("Contrasena");
        labelC3.setText("Es admin");

        panelC.add(labelC1);
        panelC.add(labelC2);
        panelC.add(labelC3);

        panelC.add(tFC1);
        panelC.add(tFC2);
        panelC.add(tFC3);

        JPanel panelS = new JPanel(new GridLayout(2,1,0,5));

        JButton botonModificar = new JButton("Modificar");
        JLabel pista = new JLabel("Los cambios del usuario actual tendrán efecto al volver a iniciar sesión");

        JPanel SCentrado1 = new JPanel(new FlowLayout());
        JPanel SCentrado2 = new JPanel(new FlowLayout());

        SCentrado1.add(botonModificar);
        SCentrado2.add(pista);

        panelS.add(SCentrado1);
        panelS.add(SCentrado2);

        panelC.setBorder(new EmptyBorder(15, 15, 0, 15));
        panelS.setBorder(new EmptyBorder(15, 15, 15, 15));
        fModificar.add(panelC, BorderLayout.CENTER);
        fModificar.add(panelS, BorderLayout.SOUTH);

        botonModificar.addActionListener(a -> {
            // En cuanto se active al botón se comprueba que se haya modificado al menos un campo, la contraseña no la cuento para que no se sepa si es igual a la introducida
            if (!tFC1.getText().equals(nombreAnterior) || !tFC2.getText().isEmpty() || tFC3.isSelected() != esAdminAnterior){

                // Se mostrará el mensaje que responda la modificación, después asigno los nuevos valores "antiguos" y actualizo la tabla
                JFrame mensaje = new JFrame("Información sobre la operación");
                String resp;
                JOptionPane.showMessageDialog(
                        mensaje,
                        resp = CUsuarios.modificar(nombreAnterior, esAdminAnterior, tFC1.getText(), tFC2.getText(), tFC3.isSelected()),
                        "Proceso de modificación",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (resp.equals("Usuario modificado con éxito")) {
                    nombreAnterior = tFC1.getText();
                    tFC2.setText("");
                    esAdminAnterior = tFC3.isSelected();
                    VUsuarios.actualizarTabla(modelo);
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

    public static void mostrar(Point posicion, DefaultTableModel modeloNuevo, String nombre, boolean esAdmin){
        // Guardo los valores antiguos
        nombreAnterior = nombre;
        esAdminAnterior = esAdmin;

        // Asigno el valor de la fila seleccionada a los campos de texto
        tFC1.setText(nombre);
        tFC3.setSelected(esAdmin);

        // Sitúo la ventana
        fModificar.setLocation((int) posicion.getX()+250, (int) posicion.getY()+265);
        fModificar.setVisible(true);

        modelo = modeloNuevo;
    }

    public static void ocultar(){
        fModificar.setVisible(false);
    }
}
