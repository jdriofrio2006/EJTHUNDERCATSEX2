package Grupo1.udla.edu.plataforma;

import javax.swing.*;
import java.util.Scanner;

public class PlataformaDeIngles {

    //Metodo main que ejecuta la aplicación
    public static void main(String[] args) {

        // Creación de objetos necesarios para la interacción con la base de datos y registro de usuarios
        Scanner scanner = new Scanner(System.in);  // Objeto para leer datos desde la entrada estándar (console)
        Maestro maestro1 = new Maestro();  // Instancia de un objeto Maestro (No se utiliza en el código proporcionado)
        Registro registro = new Registro();  // Instancia de un objeto Registro para manejar validaciones y registro de usuarios

        jdbcPlataforma dtc = new jdbcPlataforma();  // Instancia para manejar la conexión a la base de datos

        // Mensajes de bienvenida al sistema
        System.out.println("-----------------------------------//-----------------------------------------------//----------------------");
        System.out.println("-------------BIENVENIDO A LA APP DE GESTIÓN DE ESTUDIANTES DE LA FACULTAD DE INGLES--------------------");
        System.out.println("-----------------------------------//-----------------------------------------------//----------------------");
        System.out.println("                                                      |");

        // Variable que controla la repetición de la sección de inicio de sesión o registro
        int rep = 0;

        // Ciclo para ofrecer la opción de iniciar sesión o registrarse
        while (rep != 3) {
            // Solicitar al usuario que elija entre iniciar sesión o registrarse
            int seleccion = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Ingrese 1 para iniciar sesión con su cuenta o 2 para registrarse si no tiene cuenta", "",1));

            // Si selecciona iniciar sesión
            if (seleccion == 1) {
                try {
                    // Solicitar al usuario el nombre de usuario, ID Banner y contraseña para iniciar sesión
                    String nombreu = JOptionPane.showInputDialog(null,  "INGRESE SU USUARIO", "",1);
                    int iDBanner = Integer.parseInt(JOptionPane.showInputDialog(null, "", "INGRESE SU ID BANNER", 2));
                    String password = JOptionPane.showInputDialog(null, "INGRESE SU CLAVE", ""  ,1);

                    // Llamar al método de validación para verificar las credenciales
                    registro.validar(nombreu, iDBanner, password);

                    // Obtener el valor de rep, que indica si el inicio de sesión fue exitoso
                    rep = registro.getRep();
                } catch (Exception e) {
                    // Capturar excepciones y mostrar el mensaje de error
                    System.out.println(e.getMessage());

                    // Volver a llamar al metodo principal si ocurre un error
                    main(args);
                }
            }
            // Si selecciona registrarse
            else if (seleccion == 2) {
                try {
                    // Llamar al metodo para iniciar el proceso de registro de un nuevo usuario
                    registro.inivalidar();
                } catch (Exception e) {
                    // Capturar excepciones y mostrar el mensaje de error
                    System.out.println(e.getMessage());

                    // Volver a llamar al metodo principal si ocurre un error
                    main(args);
                }
            } else {
                // Si la selección no es 1 ni 2, reiniciar la aplicación
                main(args);
            }
        }

        // Variable que controla el ciclo principal de gestión después de iniciar sesión
        int salir = 3;

        // Ciclo para que el usuario pueda realizar diversas acciones sobre los datos
        while (salir == 3) {

            // Solicitar al usuario qué acción desea realizar
            int numero = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "INGRESE 1 SI QUIERE AGREGAR :\n" +
                            "\t--NOTAS \n" +
                            "\t--ESTUDIANTES\n\n" +
                            "INGRESE 2 SI QUIERE MOSTRAR :\n" +
                            "\t--NOTAS\n" +
                            "\t--ESTUDIANTES\n\n" +
                            "INGRESE 3 SI QUIERE ACTUALIZAR\n" +
                            "INGRESE 4 PARA ELIMINAR\n" +
                            "\t--NOTAS\n" +
                            "\t--ESTUDIANTES", 2));

            // Llamar al metodo de conexión a la base de datos para procesar la acción seleccionada
            dtc.connecionjdbc(numero);
        }
    }
}