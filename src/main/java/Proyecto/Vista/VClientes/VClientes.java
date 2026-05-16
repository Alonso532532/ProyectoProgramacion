package Proyecto.Vista.VClientes;

import Proyecto.Controlador.CClientes;
import Proyecto.Controlador.CEntrada;
import Proyecto.Controlador.CVisita;
import Proyecto.Modelo.Clientes;
import Proyecto.Vista.Inicio;
import Proyecto.Vista.VAtracciones.VAtracciones;
import Proyecto.Vista.VEntradas.VEntradas;
import Proyecto.Vista.VUsuarios.VUsuarios;
import Proyecto.Vista.VVisitas.VVisitas;
import Proyecto.Vista.VZonas.VZonas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.regex.Pattern;

public class VClientes {
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
        botonN5.setBackground(new Color(189, 189, 189));

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

        String[] cabecea = new String[0];
        Object[][] datos = new Object[0][];

        try {
            // Para crear la tabla que voy a mostrar tengo que crear un array para la cabecera de la tabla y una matríz con las filas de la tabla
            cabecea = new String[]{"DNI", "Edad", "Nombre"};
            datos = new Object[CClientes.seleccionarTodo().size()][3];
            int cont = 0;

            // Inicializo la matríz
            for (Clientes i : CClientes.seleccionarTodo()) {
                datos[cont][0] = i.getDni();
                datos[cont][1] = i.getEdad();
                datos[cont][2] = i.getNombre();
                cont++;
            }
        } catch (RuntimeException e){
            // En cso de fallo al leer los datos de la BBDD
            JFrame mensaje = new JFrame("Operación de obtención de datos");
            JOptionPane.showMessageDialog(
                    mensaje,
                    "Ha ocurrido un error al obtener los datos de la BBDD",
                    "Error de obtención de datos",
                    JOptionPane.ERROR_MESSAGE
            );
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
        botonN1.addActionListener(a -> {
            Inicio.ejecutar();
            cerrarTodo(base);
        });

        botonN2.addActionListener(a -> {
            VAtracciones.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        botonN3.addActionListener(a -> {
            VZonas.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        botonN4.addActionListener(a -> {
            VVisitas.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        botonN6.addActionListener(a -> {
            VEntradas.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        botonN7.addActionListener(a->{
            VUsuarios.ejecutar(admin, base.getLocation(), base.getSize());
            cerrarTodo(base);
        });

        botonS1.addActionListener(a->{
            // Cada vez que lo muestro, le paso el modelo de la tabla para que pueda actualizarla
            VCAnadir.mostrar(base.getLocation(), modelo);
        });

        // Añado funcionalidad a los botónes de abajo
        // Este es el botón de "borrar selección"
        botonS2.addActionListener(a->{
            JFrame mensaje = new JFrame("Operación de eliminación");

            // Primero compruebo si ha seleccionado algo, si no se lo muestro mediante un pop up
            if  (tabla.getSelectedRow() != -1) {
                int filaModelo = tabla.convertRowIndexToModel(tabla.getSelectedRow());
                String dni = String.valueOf(modelo.getValueAt(filaModelo, 0));
                boolean eliminar = false;

                // Compruebo que no dependa ningún elemento de este
                if (!CEntrada.seleccionarPorDni(dni).isEmpty() || !CVisita.seleccionarPorDni(dni).isEmpty()) {

                    // Sí depende algún elemento le pregunto si quiere eliminarlo
                    int respuesta = JOptionPane.showConfirmDialog(
                            null,
                            "De este cliente dependen " + CEntrada.seleccionarPorDni(dni).size() + " entradas y "+CVisita.seleccionarPorDni(dni).size()+" visitas\n¿Quieres eliminarlas además de eliminar el cliente con DNi: "+modelo.getValueAt(filaModelo, 0)+"?",
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
                            "¿Estas seguro de que quieres eliminar el cliente con el numero: "+ modelo.getValueAt(filaModelo, 0) +"?",
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
                            resp = CClientes.eliminarPorDni(dni),
                            "Información sobre la operación",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    // Si se elmina bien actualizo
                    if (resp.equals("Cliente eliminado con éxito")) {
                        actualizarTabla(modelo);
                    }
                }
            }else {
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
                VCModificar.mostrar(base.getLocation(), modelo, String.valueOf(modelo.getValueAt(filaModelo, 0)), String.valueOf(modelo.getValueAt(filaModelo, 1)), String.valueOf(modelo.getValueAt(filaModelo, 2)));
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
        VCAnadir.ocultar();
        VCModificar.ocultar();
    }

    public static void actualizarTabla(DefaultTableModel modelo) {
        // Borro las filas antes de añadir las nuevas
        modelo.setRowCount(0);
        try {
            for (Clientes c : CClientes.seleccionarTodo()) {
                modelo.addRow(new Object[]{
                        c.getDni(),
                        c.getEdad(),
                        c.getNombre()
                });
            }
        } catch (RuntimeException e){
            // En cso de fallo al leer los datos de la BBDD
            JFrame mensaje = new JFrame("Operación de obtención de datos");
            JOptionPane.showMessageDialog(
                    mensaje,
                    "Ha ocurrido un error al obtener los datos de la BBDD",
                    "Error de obtención de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}