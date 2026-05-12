package Proyecto.Vista.VZonas;

import Proyecto.Controlador.CAtracciones;
import Proyecto.Controlador.CVisita;
import Proyecto.Controlador.CZonas;
import Proyecto.Modelo.Zonas;
import Proyecto.Vista.Inicio;
import Proyecto.Vista.VAtracciones.VAtracciones;
import Proyecto.Vista.VClientes.VClientes;
import Proyecto.Vista.VEntradas.VEntradas;
import Proyecto.Vista.VUsuarios.VUsuarios;
import Proyecto.Vista.VVisitas.VVisitas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class VZonas {
    public static void ejecutar(boolean admin, Point posicion, Dimension dimension) {

        // Creo el frame y lo configuro
        JFrame base = new JFrame("Clientes");
        base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        base.setSize(dimension);
        base.setLayout(new BorderLayout(0, 10));

        // Lo situo
        base.setLocation((int) posicion.getX(), (int) posicion.getY());

        // Creo el menú de arriba y los botones
        JPanel arriba = new JPanel();
        arriba.setLayout(new GridLayout(1, 10, 10, 10));
        JButton botonN1 = new JButton("Cerrar sesión");
        JButton botonN2 = new JButton("Atracciónes");
        JButton botonN3 = new JButton("Zonas");
        JButton botonN4 = new JButton("Visitas");
        JButton botonN5 = new JButton("Clientes");
        JButton botonN6 = new JButton("Entradas");
        JButton botonN7 = new JButton("Usuarios");


        botonN1.setBackground(new Color(255, 91, 91));
        botonN3.setBackground(new Color(189, 189, 189));

        arriba.add(botonN1);
        arriba.add(botonN2);
        arriba.add(botonN3);
        arriba.add(botonN4);
        arriba.add(botonN5);
        arriba.add(botonN6);
        if (admin){
            arriba.add(botonN7);
        }

        // Creo la zona del medio
        JPanel medio = new JPanel();
        medio.setLayout(new BorderLayout());

        // Para crear la tabla que voy a mostrar tengo que crear un array para la cabecera de la tabla y una matríz con las filas de la tabla
        String[] cabecea = {"Numero de zona", "Nombre"};
        Object[][] datos = new Object[CZonas.seleccionarTodo().size()][2];
        int cont = 0;

        // Inicializo la matríz
        for (Zonas i: CZonas.seleccionarTodo()){
            datos[cont][0] = i.getNumeroDeZona();
            datos[cont][1] = i.getNombre();
            cont++;
        }

        // Para evitar que se puedan modificar datos en la tabla, creo un objeto "DefaultTableModel" y sobreescribo uno de sus métodos para hacer que sea imposible editar los campos
        DefaultTableModel modelo = new DefaultTableModel(datos, cabecea) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Añado el modelo a la tabla
        JTable tabla = new JTable(modelo);

        // Hago que la tabla tenga una barra "scroll" cuando haga falta
        JScrollPane scroll = new JScrollPane(tabla);
        medio.add(scroll);

        // Hago que la tabla tenga un campo para buscar
        JPanel medioArriba = new JPanel(new BorderLayout());
        medioArriba.setBorder(new EmptyBorder(0, 0, 10, 0));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);
        JTextField filtro = new JTextField();

        filtro.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { filtrar(); }
            public void removeUpdate(DocumentEvent e) { filtrar(); }
            public void insertUpdate(DocumentEvent e) { filtrar(); }

            private void filtrar() {
                String texto = filtro.getText();
                if (texto.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });

        JLabel filtroL = new JLabel("Filtrar:");
        filtroL.setBorder(new EmptyBorder(0, 0, 0, 5));

        medioArriba.add(filtroL, BorderLayout.WEST);
        medioArriba.add(filtro, BorderLayout.CENTER);
        medio.add(medioArriba, BorderLayout.NORTH);

        // Creo la parte de abajo y sus botónes
        JPanel abajo = new JPanel();
        abajo.setLayout(new GridLayout(1, 10, 10, 10));
        JButton botonS1 = new JButton("Añadir");
        JButton botonS2 = new JButton("Borrar selección");
        JButton botonS3 = new JButton("Modificar");
        JButton botonS4 = new JButton("Actualizar tabla");

        if (admin) {
            abajo.add(botonS1);
            abajo.add(botonS2);
            abajo.add(botonS3);
        }
        abajo.add(botonS4);

        // Finalmente, añado todas las partes y muestro el frame
        arriba.setBorder(new EmptyBorder(10, 10, 10, 10));
        medio.setBorder(new EmptyBorder(0, 10, 0, 10));
        abajo.setBorder(new EmptyBorder(10, 10, 10, 10));
        base.add(arriba, BorderLayout.NORTH);
        base.add(medio, BorderLayout.CENTER);
        base.add(abajo, BorderLayout.SOUTH);

        base.setVisible(true);

        // Añado funcionalidad a los botónes de arriba, de las tablas
        botonN1.addActionListener(a->{
            Inicio.ejecutar();
            cerrarTodo(base);
        });

        botonN2.addActionListener(a->{
            VAtracciones.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        botonN4.addActionListener(a->{
            VVisitas.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        botonN5.addActionListener(a->{
            VClientes.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        botonN6.addActionListener(a->{
            VEntradas.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        botonN7.addActionListener(a->{
            VUsuarios.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        // Añado funcionalidad a los botónes de abajo
        botonS1.addActionListener(a->{
            // Cada vez que lo muestro, le paso el modelo de la tabla para que pueda actualizarla
            VZAnadir.mostrar(base.getLocation(), modelo);
        });

        // Este es el botón de "borrar selección"
        botonS2.addActionListener(a->{
            JFrame mensaje = new JFrame("Operación de eliminación");

            // Primero compruebo si ha seleccionado algo, si no se lo muestro mediante un pop up
            if  (tabla.getSelectedRow() != -1) {
                int filaVista = tabla.getSelectedRow();
                int filaModelo = tabla.convertRowIndexToModel(filaVista);
                Integer numeroZona = (Integer) modelo.getValueAt(filaModelo, 0);
                boolean eliminar = false;

                // Compruebo que no dependa ningún elemento de este
                if (!CVisita.seleccionarPorNumeroDeZona(numeroZona).isEmpty() || !CAtracciones.seleccionarPorNumeroDeZona(numeroZona).isEmpty()) {

                    // Sí depende algún elemento le pregunto si quiere eliminarlo
                    int respuesta = JOptionPane.showConfirmDialog(
                            null,
                            "De esta zona dependen " + CVisita.seleccionarPorNumeroDeZona(numeroZona).size() + " visitas y "+CAtracciones.seleccionarPorNumeroDeZona(numeroZona).size()+" atracciones\n¿Quieres eliminarlas además de eliminar la zona elemento con el numero: "+tabla.getValueAt(filaModelo, 0)+"?",
                            "Confirmación",
                            JOptionPane.YES_NO_OPTION
                    );

                    // Si selecciona si se eliminan automáticamente los elementos relacionados con este y se elimina el elemento
                    // Activo el borrado del elemento
                    // En caso de que haya seleccionado no o haya cerrado la ventana no se elmina nada
                    eliminar = respuesta == JOptionPane.YES_OPTION;


                } else {
                    int respuesta = JOptionPane.showConfirmDialog(
                            null,
                            "¿Estas seguro de que quieres eliminar la zona con el numero: "+ tabla.getValueAt(filaModelo, 0) +"?",
                            "Confirmación",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (respuesta == JOptionPane.YES_OPTION) {
                        eliminar = true;
                    }
                }

                // Elimino el elemento si hay que eliminarlo
                if (eliminar) {
                        String resp;
                        JOptionPane.showMessageDialog(
                                mensaje,
                                resp = CZonas.eliminarPorNumeroDeZona(numeroZona),
                                "Información sobre la operación",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    // Si se elmina bien actualizo
                        if (resp.equals("Zona eliminada con éxito")) {
                            actualizarTabla(modelo);
                        }
                }
            }else {
                JOptionPane.showMessageDialog(
                        mensaje,
                        "No hay nada seleccionado",
                        "Información sobre la operación",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        botonS3.addActionListener(a->{
            // Compruebo si ha seleccionado algo
            if  (tabla.getSelectedRow() != -1) {
                // Selecciono la fila que ha seleccionado
                int filaModelo = tabla.convertRowIndexToModel(tabla.getSelectedRow());
                VZModificar.mostrar(base.getLocation(), modelo, String.valueOf(modelo.getValueAt(filaModelo, 0)), String.valueOf(modelo.getValueAt(filaModelo, 1)));
            } else {
                JFrame mensaje = new JFrame("Operación de modificación");
                JOptionPane.showMessageDialog(
                        mensaje,
                        "No hay nada seleccionado",
                        "Información sobre la operación",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        botonS4.addActionListener(a->
            actualizarTabla(modelo)
        );
    }

    // Cierro todo al salir de la interfaz
    static void cerrarTodo(Frame frame){
        frame.dispose();
        VZAnadir.ocultar();
        VZModificar.ocultar();
    }

    // Botón de actualizar la tabla
    public static void actualizarTabla(DefaultTableModel modelo) {
        // Borro las filas antes de añadir las nuevas
        modelo.setRowCount(0);

        for (Zonas c : CZonas.seleccionarTodo()) {
            modelo.addRow(new Object[]{
                    c.getNumeroDeZona(),
                    c.getNombre()
            });
        }
    }
}