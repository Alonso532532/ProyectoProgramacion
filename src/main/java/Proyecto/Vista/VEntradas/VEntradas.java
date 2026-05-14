package Proyecto.Vista.VEntradas;

import Proyecto.Controlador.CEntrada;
import Proyecto.Modelo.Entrada;
import Proyecto.Vista.Inicio;
import Proyecto.Vista.VAtracciones.VAtracciones;
import Proyecto.Vista.VClientes.VClientes;
import Proyecto.Vista.VUsuarios.VUsuarios;
import Proyecto.Vista.VVisitas.VVisitas;
import Proyecto.Vista.VZonas.VZonas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.regex.Pattern;


public class VEntradas {


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
        arriba.setLayout(new GridLayout(1, 0, 10, 10));
        JButton botonN1 = new JButton("Cerrar sesión");
        JButton botonN2 = new JButton("Atracciónes");
        JButton botonN3 = new JButton("Zonas");
        JButton botonN4 = new JButton("Visitas");
        JButton botonN5 = new JButton("Clientes");
        JButton botonN6 = new JButton("Entradas");
        JButton botonN7 = new JButton("Usuarios");

        botonN1.setBackground(new Color(255, 91, 91));
        botonN6.setBackground(new Color(189, 189, 189));

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
        String[] cabecea = {"Numero de entrada", "Tipo", "Precio", "DNI"};
        Object[][] datos = new Object[CEntrada.seleccionarTodo().size()][4];
        int cont = 0;

        // Inicializo la matríz
        for (Entrada i: CEntrada.seleccionarTodo()){
            datos[cont][0] = i.getNumeroDeEntrada();
            datos[cont][1] = i.getTipo();
            datos[cont][2] = i.getPrecio();
            datos[cont][3] = i.getDni();
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

        filtro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }

            private void filtrar() {
                String texto = filtro.getText();
                if (texto.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto)));
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

        botonN3.addActionListener(a->{
            VZonas.ejecutar(admin, base.getLocation(), base.getSize());
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

        botonN7.addActionListener(a->{
            VUsuarios.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        // Añado funcionalidad a los botónes de abajo
        botonS1.addActionListener(a->{
            // Cada vez que lo muestro, le paso el modelo de la tabla para que pueda actualizarla
            VEAnadir.mostrar(base.getLocation(), modelo);
        });

        botonS2.addActionListener(a->{
            JFrame mensaje = new JFrame("Operación de eliminación");
            // Compruebo si ha seleccionado algo
            if  (tabla.getSelectedRow() != -1) {
                // Selecciono la fila que ha seleccionado
                int filaModelo = tabla.convertRowIndexToModel(tabla.getSelectedRow());
                // Le pregunto si quiere eliminarlo
                int respuesta = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estas seguro de que quieres eliminar la entrada con el numero: "+ modelo.getValueAt(filaModelo, 0) +"?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION
                );

                // Si selecciona si se eliminan automáticamente los elementos relacionados con este y se elimina el elemento
                if (respuesta == JOptionPane.YES_OPTION) {
                    String resp;
                    JOptionPane.showMessageDialog(
                            mensaje,
                            resp = CEntrada.eliminarPorNumeroDeEntrada((Integer) modelo.getValueAt(filaModelo, 0)),
                            "Información sobre la operación",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    // Si se elmina bien actualizo
                    if (resp.equals("Entrada eliminada con éxito")) {
                        actualizarTabla(modelo);
                    }
                }
                // En caso de que haya seleccionado no o haya cerrado la ventana no se elmina nada

            } else {
                JOptionPane.showMessageDialog(
                        mensaje,
                        "No hay nada seleccionado",
                        "Error de selección",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        botonS3.addActionListener(a->{
            // Compruebo si ha seleccionado algo
            if  (tabla.getSelectedRow() != -1) {
                // Selecciono la fila que ha seleccionado
                int filaModelo = tabla.convertRowIndexToModel(tabla.getSelectedRow());
                VEModificar.mostrar(base.getLocation(), modelo, String.valueOf(modelo.getValueAt(filaModelo, 0)), String.valueOf(modelo.getValueAt(filaModelo, 1)), String.valueOf(modelo.getValueAt(filaModelo, 2)), String.valueOf(modelo.getValueAt(filaModelo, 3)));
            } else {
                JFrame mensaje = new JFrame("Operación de modificación");
                JOptionPane.showMessageDialog(
                        mensaje,
                        "No hay nada seleccionado",
                        "Error de selección",
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
        VEAnadir.ocultar();
        VEModificar.ocultar();
    }

    // Botón de actualizar la tabla
    public static void actualizarTabla(DefaultTableModel modelo) {
        // Borro las filas antes de añadir las nuevas
        modelo.setRowCount(0);

        for (Entrada c : CEntrada.seleccionarTodo()) {
            modelo.addRow(new Object[]{
                    c.getNumeroDeEntrada(),
                    c.getTipo(),
                    c.getPrecio(),
                    c.getDni()
            });
        }
    }
}