package Proyecto.Vista.VUsuarios;

import Proyecto.Controlador.CUsuarios;
import Proyecto.Modelo.Usuario;
import Proyecto.Vista.Inicio;
import Proyecto.Vista.VAtracciones.VAtracciones;
import Proyecto.Vista.VClientes.VClientes;
import Proyecto.Vista.VEntradas.VEntradas;
import Proyecto.Vista.VVisitas.VVisitas;
import Proyecto.Vista.VZonas.VZonas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.regex.Pattern;

public class VUsuarios {
    public static void ejecutar(boolean admin, Point posicion, Dimension dimension) {

        // Creo el frame y lo configuro
        JFrame base = new JFrame("Usuarios");
        base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        base.setSize(dimension);
        base.setLayout(new BorderLayout(0, 10));

        // Lo sitúo
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
        botonN7.setBackground(new Color(189, 189, 189));

        arriba.add(botonN1);
        arriba.add(botonN2);
        arriba.add(botonN3);
        arriba.add(botonN4);
        arriba.add(botonN5);
        arriba.add(botonN6);
        arriba.add(botonN7);

        // Creo la zona del medio
        JPanel medio = new JPanel();
        medio.setLayout(new BorderLayout());

        // Para crear la tabla que voy a mostrar tengo que crear un array para la cabecera de la tabla y una matríz con las filas de la tabla
        String[] cabecea = {"Nombre", "Es Admin"};
        Object[][] datos = new Object[CUsuarios.seleccionarTodo().size()][2];
        int cont = 0;

        // Inicializo la matríz
        for (Usuario i: CUsuarios.seleccionarTodo()){
            datos[cont][0] = i.getNombre();
            datos[cont][1] = i.isEsAdmin();
            cont++;
        }

        // Para evitar que se puedan modificar datos en la tabla, creo un objeto "DefaultTableModel"
        // y sobreescribo uno de sus métodos para hacer que sea imposible editar los campos a la vez que la inicializo
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
        JButton botonS3 = new JButton("Modificar selección");
        JButton botonS4 = new JButton("Actualizar tabla");

        abajo.add(botonS1);
        abajo.add(botonS2);
        abajo.add(botonS3);
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

        botonN6.addActionListener(a->{
            VEntradas.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        botonS1.addActionListener(a->{
            // Cada vez que lo muestro, le paso el modelo de la tabla para que pueda actualizarla
            VUAnadir.mostrar(base.getLocation(), modelo);
        });

        // Añado funcionalidad a los botónes de abajo
        botonS2.addActionListener(a->{
            JFrame mensaje = new JFrame("Operación de eliminación");
            // Compruebo si ha seleccionado algo
            if  (tabla.getSelectedRow() != -1) {
                // Selecciono la fila que ha seleccionado
                int filaModelo = tabla.convertRowIndexToModel(tabla.getSelectedRow());
                // Sí depende algún elemento le pregunto si quiere eliminarlo
                int respuesta = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estas seguro de que quieres eliminar el usuario con el nombre: "+ modelo.getValueAt(filaModelo, 0) +"?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION
                );

                // Si selecciona si se eliminan automáticamente los elementos relacionados con este y se elimina el elemento
                if (respuesta == JOptionPane.YES_OPTION) {

                    String resp;
                    JOptionPane.showMessageDialog(
                            mensaje,
                            resp = CUsuarios.eliminarPorNombre((String) modelo.getValueAt(filaModelo, 0)),
                            "Información sobre la operación",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    // Si se elmina bien actualizo
                    if (resp.equals("Usuario eliminado con éxito")) {
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
                VUModificar.mostrar(base.getLocation(), modelo, String.valueOf(modelo.getValueAt(filaModelo, 0)), (boolean) modelo.getValueAt(filaModelo, 1));
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

        // Botón de actualizar la tabla
        botonS4.addActionListener(a->
            actualizarTabla(modelo)
        );
    }

    // Cierro todo al salir de la interfaz
    static void cerrarTodo(Frame frame){
        frame.dispose();
        VUAnadir.ocultar();
        VUModificar.ocultar();
    }

    public static void actualizarTabla(DefaultTableModel modelo) {
        // Borro las filas antes de añadir las nuevas
        modelo.setRowCount(0);

        for (Usuario c : CUsuarios.seleccionarTodo()) {
            modelo.addRow(new Object[]{
                    c.getNombre(),
                    c.isEsAdmin()
            });
        }
    }
}
