/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UsoComponente;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Calendar;

public class Validador {
    
    public static boolean validarCorreo(String correo, JLabel lblError) {
        correo = correo.toLowerCase().trim();
        String dominios = "gmail|hotmail|yahoo|outlook|itoaxaca|live|icloud|aol";
        String terminaciones = "com|mx|edu.mx|org|net|gov|info";
        String regex = "^[a-zA-Z0-9._%+-]+@(" + dominios + ")\\.(" + terminaciones + ")$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);

        if (!matcher.matches()) {
            mostrarError(lblError, "Correo inválido.Usa un dominio y terminación válida.");
            return false;
        }

        lblError.setText(""); 
        return true;
    }

    
    public static void confirmarSalida(JFrame frame) {
        int opcion = JOptionPane.showConfirmDialog(
            frame,
            "¿Deseas salir del programa?",
            "Confirmación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0); 
        }
    }
  

    public static boolean validarNombre(String nombre, JLabel lblError) {
        if (nombre == null || nombre.trim().isEmpty()) {
            lblError.setText("El nombre no puede estar vacío.");
            return false;
        }

        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            lblError.setText("El nombre solo debe contener letras.");
            return false;
        }

        lblError.setText("");
        return true;
    }

    
    public static void mostrarError(JLabel lblError, String mensaje) {
        lblError.setText(mensaje);
        lblError.setForeground(Color.RED);
    }
    

    public static int calcularEdad(Date fechaNacimiento) {
        if (fechaNacimiento == null) return -1; 

        Calendar fechaNac = Calendar.getInstance();
        fechaNac.setTime(fechaNacimiento);

        Calendar hoy = Calendar.getInstance();
        
        int edad = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);

        if (hoy.get(Calendar.DAY_OF_YEAR) < fechaNac.get(Calendar.DAY_OF_YEAR)) {
            edad--;
        }

        return edad;
    }
    public static Date convertirFecha(String fechaStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse(fechaStr); 
        } catch (Exception e) {
            return null;  
        }
    }

     public static class FechaSelector extends JDialog {
        private JCalendar calendario;
        private JButton btnAceptar;
        private JLabel txtFechaN;

        public FechaSelector(JFrame parent, JLabel txtFechaN) {
            super(parent, "Seleccionar Fecha", true);
            this.txtFechaN = txtFechaN;

            calendario = new JCalendar();
            calendario.setPreferredSize(new Dimension(400, 300));

            // Botón Aceptar
            btnAceptar = new JButton("Aceptar");
            btnAceptar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Date fechaSeleccionada = calendario.getDate(); 
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String fechaStr = sdf.format(fechaSeleccionada);
                    txtFechaN.setText(fechaStr);
                    dispose();
                }
            });

            setLayout(new BorderLayout());
            add(calendario, BorderLayout.CENTER);
            add(btnAceptar, BorderLayout.SOUTH);

            setSize(400, 400);
            setLocationRelativeTo(parent);
        }
    }
}


