package Proyecto.Vista;

import Proyecto.Controlador.CUsuarios;
import Proyecto.Modelo.Usuario;
import Proyecto.Vista.VAtracciones.VAAnadir;
import Proyecto.Vista.VAtracciones.VAModificar;
import Proyecto.Vista.VAtracciones.VAtracciones;
import Proyecto.Vista.VClientes.VCAnadir;
import Proyecto.Vista.VClientes.VCModificar;
import Proyecto.Vista.VEntradas.VEAnadir;
import Proyecto.Vista.VEntradas.VEModificar;
import Proyecto.Vista.VUsuarios.VUAnadir;
import Proyecto.Vista.VUsuarios.VUModificar;
import Proyecto.Vista.VVisitas.VVAnadir;
import Proyecto.Vista.VVisitas.VVModificar;
import Proyecto.Vista.VZonas.VZAnadir;
import Proyecto.Vista.VZonas.VZModificar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class Inicio {
    static boolean primero = true;

    public static void ejecutar() {
        // Creo el frame y lo configuro
        JFrame base = new JFrame("Inicio");
        base.setLocationRelativeTo(null);
        base.setLocation((int) base.getLocation().getX()-500, (int) base.getLocation().getY()-350);
        base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        base.setSize(1000, 700);
        base.setLayout(new GridLayout(6, 1));

        // La parte de arriba del inicio de sesión
        JPanel tituloInicio = new JPanel();

        JLabel inicioDeSesion = new JLabel("Inicio de sesión");
        inicioDeSesion.setFont(new Font("Times new roman", 3, 50));
        tituloInicio.add(inicioDeSesion);

        SpringLayout layoutNI = new SpringLayout();
        layoutNI.putConstraint(SpringLayout.VERTICAL_CENTER, inicioDeSesion, 0, SpringLayout.VERTICAL_CENTER, tituloInicio);
        layoutNI.putConstraint(SpringLayout.HORIZONTAL_CENTER, inicioDeSesion, 0, SpringLayout.HORIZONTAL_CENTER, tituloInicio);
        tituloInicio.setLayout(layoutNI);

        // La parte del centro del inicio de sesión
        JPanel medioI = new JPanel(new GridLayout(1, 2));

        // Parte izquierda (usuario)
        JPanel usuarioI = new JPanel(new GridLayout(2, 1));
        usuarioI.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel labelUI = new JLabel("Usuario");
        labelUI.setFont(new Font("", 0, 25));
        JTextField campoUI = new JTextField();
        campoUI.setFont(new Font("", 0, 20));

        JPanel labelPUI = new JPanel(new BorderLayout());
        labelPUI.add(labelUI, BorderLayout.SOUTH);

        JPanel campoPU = new JPanel(new BorderLayout());
        campoPU.add(campoUI, BorderLayout.NORTH);

        usuarioI.add(labelPUI);
        usuarioI.add(campoPU);

        // Parte derecha (contraseña)
        JPanel contrasenaI = new JPanel(new GridLayout(2, 1));
        contrasenaI.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPasswordField campoCI = new JPasswordField();
        campoCI.setFont(new Font("", 0, 20));
        JLabel labelCI = new JLabel("Contraseña");
        labelCI.setFont(new Font("", 0, 25));

        JPanel labelPCI = new JPanel(new BorderLayout());
        labelPCI.add(labelCI, BorderLayout.SOUTH);

        JPanel campoPCI = new JPanel(new BorderLayout());
        campoPCI.add(campoCI, BorderLayout.NORTH);

        contrasenaI.add(labelPCI);
        contrasenaI.add(campoPCI);

        medioI.add(usuarioI);
        medioI.add(contrasenaI);

        // La parte de abajo del inicio de sesión
        JPanel abajoI = new JPanel();

        JButton botonI = new JButton("Entrar");
        botonI.setMargin(new Insets(10,25,10,25));
        abajoI.add(botonI);

        SpringLayout layoutSI = new SpringLayout();
        layoutSI.putConstraint(SpringLayout.VERTICAL_CENTER, botonI, 0, SpringLayout.VERTICAL_CENTER, abajoI);
        layoutSI.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonI, 0, SpringLayout.HORIZONTAL_CENTER, abajoI);
        abajoI.setLayout(layoutSI);

        base.add(tituloInicio);
        base.add(medioI);
        base.add(abajoI);

        campoUI.setText("Admin");
        campoCI.setText("Sor2425$");

        // Parte de registro de nuevos usuarios
        // El título
        JPanel tituloRegistro = getJPanel();

        // Los campos
        JPanel medioR = new JPanel(new GridLayout(1, 3));

        // Parte izquierda (usuario)
        JPanel usuarioR = new JPanel(new GridLayout(2, 1));
        usuarioR.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel labelUR = new JLabel("Usuario");
        labelUR.setFont(new Font("", 0, 25));
        JTextField campoUR = new JTextField();
        campoUR.setFont(new Font("", 0, 20));

        JPanel labelPUR = new JPanel(new BorderLayout());
        labelPUR.add(labelUR, BorderLayout.SOUTH);

        JPanel campoPUR = new JPanel(new BorderLayout());
        campoPUR.add(campoUR, BorderLayout.NORTH);

        usuarioR.add(labelPUR);
        usuarioR.add(campoPUR);

        // Parte medio (contraseña)
        JPanel contrasenaR = new JPanel(new GridLayout(2, 1));
        contrasenaR.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPasswordField campoCR = new JPasswordField();
        campoCR.setFont(new Font("", 0, 20));
        JLabel labelCR = new JLabel("Contraseña");
        labelCR.setFont(new Font("", 0, 25));

        JPanel labelPCR = new JPanel(new BorderLayout());
        labelPCR.add(labelCR, BorderLayout.SOUTH);

        JPanel campoPCR = new JPanel(new BorderLayout());
        campoPCR.add(campoCR, BorderLayout.NORTH);

        contrasenaR.add(labelPCR);
        contrasenaR.add(campoPCR);

        // Parte derecha (admin)
        JPanel adminR = new JPanel(new GridLayout(2, 1));
        adminR.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Texto
        JLabel labelAR = new JLabel("¿Es admin?");
        labelAR.setFont(new Font("", 0, 25));

        JPanel labelPAR = new JPanel(new BorderLayout());
        labelPAR.add(labelAR, BorderLayout.SOUTH);

        // Botón y checkbox

        JButton botonAR = new JButton("Dar privilegio");

        JCheckBox checkBoxAR = new JCheckBox();
        checkBoxAR.setEnabled(false);

        // Centro el botón y el checkbox
        JPanel centrarBAR = new JPanel();
        centrarBAR.add(botonAR);

        SpringLayout layoutABR = new SpringLayout();
        layoutABR.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonAR, 0, SpringLayout.HORIZONTAL_CENTER, centrarBAR);
        centrarBAR.setLayout(layoutABR);

        JPanel centrarACBR = new JPanel();
        centrarACBR.add(checkBoxAR);

        SpringLayout layoutACBR = new SpringLayout();
        centrarACBR.setLayout(layoutACBR);

        JPanel campoPAR = new JPanel(new GridLayout(1, 2));
        campoPAR.add(centrarBAR);
        campoPAR.add(centrarACBR);

        adminR.add(labelPAR);
        adminR.add(campoPAR);

        // Lo junto todo en "medioR"

        medioR.add(usuarioR);
        medioR.add(contrasenaR);
        medioR.add(adminR);

        // Creo el botón de añadir usuarios
        JPanel abajoR = new JPanel();

        JButton botonR = new JButton("Añadir usuario");
        botonR.setMargin(new Insets(10,25,10,25));

        JLabel informacion = new JLabel("Es necesario rellenar los campos del inicio de sesión con un usuario administrador para podér dar privilegios de administrador");

        abajoR.add(informacion);
        abajoR.add(botonR);

        SpringLayout layoutSR = new SpringLayout();
        layoutSR.putConstraint(SpringLayout.NORTH, informacion, 0, SpringLayout.NORTH, abajoR);
        layoutSR.putConstraint(SpringLayout.HORIZONTAL_CENTER, informacion, 0, SpringLayout.HORIZONTAL_CENTER, abajoR);

        layoutSR.putConstraint(SpringLayout.VERTICAL_CENTER, botonR, 0, SpringLayout.VERTICAL_CENTER, abajoR);
        layoutSR.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonR, 0, SpringLayout.HORIZONTAL_CENTER, abajoR);

        abajoR.setLayout(layoutSR);

        // Añado finalmente la parte del registro

        base.add(tituloRegistro);
        base.add(medioR);
        base.add(abajoR);

        // Registro el inicio del programa junto con la fecha y hora en el archivo "src/main/java/Proyecto/Conexiones"
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/java/Proyecto/Conexiones", true))){
            if (primero){
                bufferedWriter.write("<----------- Inicio del programa, Hora: "+ LocalDateTime.now().toString().substring(0,19)+" ----------->\n");
            }
        }catch (IOException e){
            JFrame mensaje = new JFrame("Información sobre la operación");
            JOptionPane.showMessageDialog(
                    mensaje,
                    "Error al registrar el inicio del programa",
                    "Error de escritura",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        botonI.addActionListener(a->{
            if (!campoCI.getText().isEmpty() && !campoUI.getText().isEmpty()){
                try {
                    VAtracciones.ejecutar(CUsuarios.comprobarInicioDeSesion(new Usuario(campoUI.getText(), campoCI.getText(), false, false)), base.getLocation(), base.getSize());

                    // Guardo el inicio de sesión con la hora y el usuario en el archivo "src/main/java/Proyecto/Conexiones"
                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/java/Proyecto/Conexiones", true))){
                        bufferedWriter.write("Inicio de sesión, Usuario: "+campoUI.getText()+", Hora: "+ LocalDateTime.now().toString().substring(0,19)+"\n");
                    }catch (IOException e){
                        JFrame mensaje = new JFrame("Información sobre la operación");
                        JOptionPane.showMessageDialog(
                                mensaje,
                                "Error al registrar el inicio de sesión",
                                "Error de escritura",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    // Inicializo todas las vitas que es necesario inicializar solo una vez
                    if (primero) {
                        VAModificar.construir();
                        VCModificar.construir();
                        VEModificar.construir();
                        VVModificar.construir();
                        VZModificar.construir();
                        VUModificar.construir();

                        VAAnadir.construir();
                        VCAnadir.construir();
                        VEAnadir.construir();
                        VUAnadir.construir();
                        VVAnadir.construir();
                        VZAnadir.construir();
                        primero = false;
                    }
                    base.setVisible(false);
                } catch (IllegalArgumentException e){
                    JFrame mensaje = new JFrame("Información sobre el início de sesión");
                    JOptionPane.showMessageDialog(
                            mensaje,
                            "Han ocurrido uno o varios errores al intentar iniciar sesión:\n"+e.getMessage(),
                            "Error de inicio de sesión",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }else {
                JFrame mensaje = new JFrame("Error de inicio de sesión");
                JOptionPane.showMessageDialog(
                        mensaje,
                        "Hay campos vacíos",
                        "Información sobre el início de sesión",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        botonR.addActionListener(a->{
            if (!campoUR.getText().isEmpty() && !campoCR.getText().isEmpty()){
                try {
                    JFrame mensaje = new JFrame("Información sobre el início de sesión");
                    JOptionPane.showMessageDialog(
                            mensaje,
                            CUsuarios.anadir(campoUR.getText(), campoCR.getText(), checkBoxAR.isSelected()),
                            "Registro de un nuevo usuario",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (IllegalArgumentException e){
                    JFrame mensaje = new JFrame("Información sobre el início de sesión");
                    JOptionPane.showMessageDialog(
                            mensaje,
                            "Han ocurrido uno o varios errores al intentar iniciar sesión:\n"+e.getMessage(),
                            "Error de inicio de sesión",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JFrame mensaje = new JFrame("Error de registro");
                JOptionPane.showMessageDialog(
                        mensaje,
                        "Hay campos vacíos",
                        "Información sobre el registro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Para no pedirle todo el rato si es o no administrador, si demuestra que lo es ya no se lo pido más mientras la ventana de inicio esté abierta
        final boolean[] admin = {false};
        botonAR.addActionListener(a->{
            if (botonAR.getText().equals("Dar privilegio")){
                if (!admin[0]){
                    // Compruebo que no hayan campos vacíos
                    if (campoCI.getText().isEmpty() || campoUI.getText().isEmpty()){
                        JFrame mensaje = new JFrame("Información sobre la operación");
                        JOptionPane.showMessageDialog(
                                mensaje,
                                "Rellena los campos de inicio de sesión con valores de un usuario administrador para poder dar privilegios",
                                "Fallo de autenticación",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        try {
                            if (CUsuarios.comprobarInicioDeSesion(new Usuario(campoUI.getText(), campoCI.getText(), false, false))){
                                admin[0] = true;
                            } else {
                                JFrame mensaje = new JFrame("Información sobre el início de sesión");
                                JOptionPane.showMessageDialog(
                                        mensaje,
                                        "El usuario introducido en el inicio de sesión no es administrador",
                                        "Error de inicio de sesión",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        } catch (IllegalArgumentException e){
                            JFrame mensaje = new JFrame("Información sobre el início de sesión");
                            JOptionPane.showMessageDialog(
                                    mensaje,
                                    "Ha ocurrido uno o varios errores con los datos de los campos de inicio de sesión:\n"+e.getMessage(),
                                    "Error de inicio de sesión",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                }
                if (admin[0]) {
                    botonAR.setText("Quitar privilegio");
                    checkBoxAR.setSelected(true);
                }
            } else {
                botonAR.setText("Dar privilegio");
                checkBoxAR.setSelected(false);
            }

        });

        base.setVisible(true);
    }

    private static JPanel getJPanel() {
        JPanel tituloRegistro = new JPanel();
        tituloRegistro.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));

        JLabel registro = new JLabel("Registrarse");
        registro.setFont(new Font("Times new roman", 3, 50));
        tituloRegistro.add(registro);

        SpringLayout layoutNR = new SpringLayout();
        layoutNR.putConstraint(SpringLayout.VERTICAL_CENTER, registro, 0, SpringLayout.VERTICAL_CENTER, tituloRegistro);
        layoutNR.putConstraint(SpringLayout.HORIZONTAL_CENTER, registro, 0, SpringLayout.HORIZONTAL_CENTER, tituloRegistro);
        tituloRegistro.setLayout(layoutNR);
        return tituloRegistro;
    }
}
