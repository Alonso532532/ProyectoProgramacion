package Proyecto.Vista.VVisitas;

import Proyecto.Controlador.CVisita;
import Proyecto.Controlador.CZonas;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class VVAnadir {
    private static JFrame fAnadir = new JFrame();
    // Este modelo sirve para actualizar la tabla de la vista
    private static DefaultTableModel modelo;

    private static JTextField tFC1 = new JTextField();
    // Creo el combobox y le añado las opciones
    private static JComboBox<String> cBC1 = new JComboBox<>();
    // Creo un desplegable para la fecha
    private static JDateChooser tFC2 = new JDateChooser();
    private static JTextField tFC3 = new JTextField();

    // Este método inicializa todo de la ventana
    public static void construir() {
        // Hago que no se pueda cambiar el tamaño a la ventana
        fAnadir.setResizable(false);

        fAnadir.setTitle("Añadir visita");
        fAnadir.setSize(500, 210);
        fAnadir.setLayout(new BorderLayout());

        JPanel panelC = new JPanel(new GridLayout(2, 4, 10, 5));

        // Le asigno un formato a la fecha
        tFC2.setDateFormatString("dd/MM/yyyy");


        JLabel labelC1 = new JLabel();
        JLabel labelC2 = new JLabel();
        JLabel labelC3 = new JLabel();
        JLabel labelC4 = new JLabel();

        labelC1.setText("DNI");
        labelC2.setText("Numero de zona");
        labelC3.setText("Fecha");
        labelC4.setText("Hora");

        panelC.add(labelC1);
        panelC.add(labelC2);
        panelC.add(labelC3);
        panelC.add(labelC4);

        panelC.add(tFC1);
        panelC.add(cBC1);
        panelC.add(tFC2);
        panelC.add(tFC3);

        JPanel panelS = new JPanel(new GridLayout(2,1,0,5));

        JButton botonAnadir = new JButton("Añadir");
        JLabel pista = new JLabel("La hora sigue el siguiente formato: hh:mm:ss");

        JPanel SCentrado1 = new JPanel(new FlowLayout());
        JPanel SCentrado2 = new JPanel(new FlowLayout());

        SCentrado1.add(botonAnadir);
        SCentrado2.add(pista);

        panelS.add(SCentrado1);
        panelS.add(SCentrado2);

        panelC.setBorder(new EmptyBorder(15, 15, 0, 15));
        panelS.setBorder(new EmptyBorder(15, 15, 15, 15));
        fAnadir.add(panelC, BorderLayout.CENTER);
        fAnadir.add(panelS, BorderLayout.SOUTH);


        botonAnadir.addActionListener(a -> {
            // En cuanto se active al botón se comprueba que no hayan campos vacíos
            // Si la fecha es incorrecta o está vacía el campo devuelve null
            if (tFC1.getText().isEmpty() || tFC2.getDate()==null || tFC3.getText().isEmpty()) {
                JFrame mensaje = new JFrame("Información sobre la operación");
                JOptionPane.showMessageDialog(
                        mensaje,
                        "Error, uno o varios campos están vacíos o la fecha no se a introducido correctamente",
                        "Error de formato",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                // Instancio una clase que me permite parsear el resultado que me da el desplegable de la fecha al formato que necesito
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                JFrame mensaje = new JFrame("Información sobre la operación");
                String resp;
                JOptionPane.showMessageDialog(
                        mensaje,
                        resp = CVisita.anadir(tFC1.getText(), cBC1.getSelectedItem().toString().substring(0,cBC1.getSelectedItem().toString().indexOf("-")), formato.format(tFC2.getDate())+"T"+tFC3.getText()),
                        "Operación para añadir visitas",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (resp.equals("Visita introducida con éxito")) {
                    VVisitas.actualizarTabla(modelo);
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
        tFC2.setDate(new Date());
        tFC3.setText(String.valueOf(LocalDateTime.now()).substring(11, 19));
        // Lo vacío y le introduzco nuevas opciónes
        cBC1.removeAllItems();
        for (String opcion : CZonas.seleccionarTodo().stream().map(a -> a.getNumeroDeZona() + "-" + a.getNombre()).toList().toArray(new String[0])) {
            cBC1.addItem(opcion);
        }
        // Selecciono la primera opción
        cBC1.setSelectedIndex(0);
    }

    public static void ocultar(){
        fAnadir.setVisible(false);
    }
}
