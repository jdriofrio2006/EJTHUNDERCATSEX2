package Grupo1.udla.edu.plataforma;

import java.util.List;

// Clase Maestro que representa a un maestro en la plataforma
public class Maestro {

    // Atributos de la clase Maestro
    protected int idBanner; // ID único del maestro
    protected String nombreu; // Nombre de usuario del maestro
    protected String curso; // Curso que imparte el maestro
    protected String passwword; // Contraseña del maestro
    protected List<Maestro> listaMaestros; // Lista de otros maestros (si se desea manejar varios maestros)

    // Constructor vacío
    public Maestro(){}

    // Constructor con parámetros para inicializar los atributos del maestro
    public Maestro(int idBanner, String nombreu, String curso, String passwword){
        this.idBanner = idBanner;
        this.nombreu = nombreu;
        this.curso = curso;
        this.passwword = passwword;
    }

    // Constructor para inicializar una lista de maestros
    public Maestro(List<Maestro> listaMaestros) {
        this.listaMaestros = listaMaestros;
    }

    // Métodos de acceso y modificación (getters y setters) pueden ser añadidos si es necesario

}