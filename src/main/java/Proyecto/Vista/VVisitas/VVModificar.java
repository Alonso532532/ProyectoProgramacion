package Proyecto.Vista.VVisitas;

import Proyecto.Controlador.CVisita;
import Proyecto.Controlador.CZonas;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class VVModificar {
    private static JFrame fModificar = new JFrame();
    // Este modelo sirve para actualizar la tabla de la vista
    private static DefaultTableModel modelo;

    // Estas variables me sirven para saber si hay cambios
    private static String dniAnterior = "";
    private static String numeroDeZonaAnterior = "";
    private static Date fechaAnterior = new Date();
    private static String horaAnterior = "";

    private static TextField tFC1 = new TextField();
    //Creo el combobox
    private static JComboBox<String> cBC1 = new JComboBox<>();
    // Creo un desplegable para la fecha
    private static JDateChooser tFC2 = new JDateChooser();
    private static TextField tFC3 = new TextField();

    // Este método inicializa todo de la ventana
    public static void construir() {
        // Hago que no se pueda cambiar el tamaño a la ventana
        fModificar.setResizable(false);

        fModificar.setTitle("Modificar visitas");
        fModificar.setSize(500, 210);
        fModificar.setLayout(new BorderLayout());

        JPanel panelC = new JPanel(new GridLayout(2, 4, 10, 5));

        JLabel labelC1 = new JLabel();
        JLabel labelC2 = new JLabel();
        JLabel labelC3 = new JLabel();
        JLabel labelC4 = new JLabel();


        labelC1.setText("DNI");
        labelC2.setText("Numero de zona");
        labelC3.setText("Fecha");
        labelC4.setText("Hora");

        // Le asigno un formato al desplegable de la fecha
        tFC2.setDateFormatString("dd/MM/yyyy");

        panelC.add(labelC1);
        panelC.add(labelC2);
        panelC.add(labelC3);
        panelC.add(labelC4);

        panelC.add(tFC1);
        panelC.add(cBC1);
        panelC.add(tFC2);
        panelC.add(tFC3);

        JPanel panelS = new JPanel(new GridLayout(2,1,0,5));

        JButton botonModificar = new JButton("Modificar");
        JLabel pista = new JLabel("La hora sigue el siguiente formato: hh:mm:ss");

        JPanel SCentrado1 = new JPanel(new FlowLayout());
        JPanel SCentrado2 = new JPanel(new FlowLayout());

        SCentrado1.add(botonModificar);
        SCentrado2.add(pista);

        panelC.setBorder(new EmptyBorder(15, 15, 0, 15));
        panelS.setBorder(new EmptyBorder(15, 15, 15, 15));

        panelS.add(SCentrado1);
        panelS.add(SCentrado2);

        fModificar.add(panelC, BorderLayout.CENTER);
        fModificar.add(panelS, BorderLayout.SOUTH);

        botonModificar.addActionListener(a -> {
            if (tFC2.getDate()!=null) {
                // En cuanto se active al botón se comprueba que se haya modificado almenos un campo
                if (!tFC1.getText().equals(dniAnterior) || !cBC1.getSelectedItem().toString().contains(numeroDeZonaAnterior) || !tFC2.getDate().equals(fechaAnterior) || !tFC3.getText().equals(horaAnterior)) {

                    // Instancio una clase que me permite parsear el resultado que me da el desplegable de la fecha al formato que necesito
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                    // Se mostrará el mensaje que responda la modificación, después asigno los nuevos valores "antiguos" y actualizo la tabla
                    JFrame mensaje = new JFrame("Proceso de modificación");
                    String resp;
                    JOptionPane.showMessageDialog(
                            mensaje,
                            resp = CVisita.modificar(dniAnterior, numeroDeZonaAnterior, formato.format(fechaAnterior) + "T" + horaAnterior, tFC1.getText(), cBC1.getSelectedItem().toString().substring(0, 1), formato.format(tFC2.getDate()) + "T" + tFC3.getText()),
                            "Información sobre la operación",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    if (resp.equals("Visita modificada con éxito")) {
                        dniAnterior = tFC1.getText();
                        numeroDeZonaAnterior = cBC1.getSelectedItem().toString().substring(0, 1);
                        fechaAnterior = tFC2.getDate();
                        horaAnterior = tFC3.getText();
                        VVisitas.actualizarTabla(modelo);
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
                // Si no hay cambios en los campos
                JFrame mensaje = new JFrame("Información sobre la operación");
                JOptionPane.showMessageDialog(
                        mensaje,
                        "La fecha está vacía o es inválida",
                        "Sin cambios",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    public static void mostrar(Point posicion, DefaultTableModel modeloNuevo, String dni, String numeroDeZona, String fecha){
        // Guardo los valores antiguos
        dniAnterior = dni;
        numeroDeZonaAnterior = numeroDeZona;
        // Para convertir el string de la fecha que traigo primero saco la fecha, luego con "atStartOfDay" le asigno una hora
        // (aunque no la vaya a utilizar), porque el "Date" necesita una hora, "ZoneId" junto con el "toInstant" le da formato
        // para que "Date" lo entienda
        fechaAnterior = Date.from(LocalDate.parse(fecha.substring(0,10)).atStartOfDay(ZoneId.systemDefault()).toInstant());
        horaAnterior = fecha.substring(11, 19);

        // Asigno el valor de la fila seleccionada a los campos de texto
        tFC1.setText(dni);
        // Inicializo el combobox cada vez que se muestra la vista con las zonas
        String[] opciones = CZonas.seleccionarTodo().stream().map(a -> a.getNumeroDeZona() + "-" + a.getNombre()).toList().toArray(new String[0]);
        cBC1.removeAllItems();

        for (String opcion : opciones) {
            cBC1.addItem(opcion);
        }

        // Busco la opción para poner por defécto
        int index = 0;
        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i].contains(numeroDeZona)) index = i;
        }

        cBC1.setSelectedIndex(index);

        // Parseo la fecha igual que arriba
        tFC2.setDate(Date.from(LocalDate.parse(fecha.substring(0,10)).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        tFC3.setText(fecha.substring(11, 19));

        // Sitúo la ventana
        fModificar.setLocation((int) posicion.getX()+250, (int) posicion.getY()+265);
        fModificar.setVisible(true);

        modelo = modeloNuevo;
    }

    public static void ocultar(){
        fModificar.dispose();
    }
}
