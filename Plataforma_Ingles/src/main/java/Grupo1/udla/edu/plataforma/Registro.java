package Grupo1.udla.edu.plataforma;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Registro {
    // Lista para almacenar los maestros registrados
    protected List<Maestro> listaMaestros = new ArrayList<>();

    // Variable para manejar el estado del proceso (inicio de sesión o registro)
    protected int rep;

    // Constructor por defecto
    public Registro(){}

    // Constructor que recibe una lista de maestros
    public Registro(List<Maestro> listaMaestros1){
        this.listaMaestros = listaMaestros1;
    }

    /**
     * Metodo para validar las credenciales de un maestro.
     * Si no hay maestros registrados, se solicita que se registre primero.
     * Si las credenciales son correctas, se permite el inicio de sesión.**/

    public void validar(String nombreu, int idBanner, String password) {
        // Verifica si la lista de maestros está vacía o nula
        if(this.listaMaestros == null || this.listaMaestros.isEmpty()){
            // Si no hay maestros registrados, se solicita al usuario que se registre
            JOptionPane.showInputDialog(null,"NECESITA REGISTRARSE PRIMERO","",3);
            this.rep = 0; // Cambia el valor de 'rep' a 0 si no se puede iniciar sesión
        } else if(this.listaMaestros != null) {
            // Si la lista de maestros no está vacía, verifica las credenciales
            for (Maestro maestro : this.listaMaestros) {
                // Compara las credenciales ingresadas con las almacenadas
                if (maestro.nombreu.equals(nombreu) && maestro.passwword.equals(password) && maestro.idBanner == idBanner) {
                    System.out.println("INICIANDO SESIÓN");
                    this.rep = 3; // Si las credenciales son correctas, establece 'rep' a 3
                } else {
                    System.out.println("CREDENCIALES INCORRECTAS");
                    break; // Si las credenciales no coinciden, sale del ciclo
                }
            }
        }
    }

    /**
     * Método para registrar un nuevo maestro si la lista de maestros está vacía.
     * Si la lista no está vacía, verifica que el maestro no esté ya registrado.
     */
    public void inivalidar(){
        // Si no hay maestros registrados, solicita la información para registrar uno nuevo
        if(this.listaMaestros == null || this.listaMaestros.isEmpty()){
            String nombreu = JOptionPane.showInputDialog(null,"","INGRESE SU NUEVO NOMBRE DE USUARIO",2);
            int iDBanner = Integer.parseInt(JOptionPane.showInputDialog(null,"","INGRESE SU ID BANNER",2));
            String curso = JOptionPane.showInputDialog(null,"","INGRESE EL PARALELO ASIGNADO",2);
            String password = JOptionPane.showInputDialog(null,"","INGRESE SU CONTRASEÑA",2);
            // Crea un nuevo objeto Maestro con los datos ingresados
            Maestro maestro2 = new Maestro(iDBanner, nombreu, curso, password);
            // Agrega el maestro a la lista de maestros registrados
            this.listaMaestros.add(maestro2);
        } else if(this.listaMaestros != null) {
            // Si ya existen maestros registrados, verifica si el nuevo maestro ya está en la lista
            String nombreu = JOptionPane.showInputDialog(null,"","INGRESE SU NUEVO NOMBRE DE USUARIO",2);
            int iDBanner = Integer.parseInt(JOptionPane.showInputDialog(null,"","INGRESE SU ID BANNER",2));
            String curso = JOptionPane.showInputDialog(null,"","INGRESE EL PARALELO ASIGNADO",2);
            String password = JOptionPane.showInputDialog(null,"","INGRESE SU CONTRASEÑA",2);
            for (Maestro maestro : this.listaMaestros) {
                // Si el maestro ya está registrado, muestra un mensaje de advertencia
                if (maestro.nombreu.equals(nombreu) && maestro.passwword.equals(password) && maestro.idBanner == iDBanner) {
                    System.out.println("EL MAESTRO INGRESADO YA ESTA EN LISTA INGRESE OTRO MAESTRO");
                } else {
                    break; // Sale del ciclo si no hay coincidencias
                }
            }
        }
    }

    // Metodo para obtener el valor de 'rep'
    public int getRep() {
        return rep;
    }
}