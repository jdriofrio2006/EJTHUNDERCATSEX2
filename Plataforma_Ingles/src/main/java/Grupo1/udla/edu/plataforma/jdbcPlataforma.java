package Grupo1.udla.edu.plataforma;

import javax.swing.*;
import java.sql.*;

public class jdbcPlataforma {

    // Atributos de conexión a la base de datos MySQL
    protected String url = "jdbc:mysql://localhost:3306/RegistroEstudiantes"; // URL de la base de datos
    protected String user = "root"; // Usuario de MySQL
    protected String password = "sasa"; // Contraseña de MySQL

    protected Connection conn; // Objeto para la conexión a la base de datos

    // Constructor de la clase (por defecto no realiza ninguna acción)
    public jdbcPlataforma() {}

    //Metodo para manejar las operaciones (insertar, mostrar, actualizar, eliminar)
    public void connecionjdbc(int selec) {
        try {
            // Establecer la conexión a la base de datos
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement(); // Crear un Statement para ejecutar consultas SQL

            // Operaciones dependiendo del valor de 'selec'
            switch (selec) {
                case 1: // Caso para agregar o insertar un nuevo registro
                    // Solicitar el nombre del estudiante al usuario
                    String estudiante = JOptionPane.showInputDialog(null,
                            "Ingresa el nombre del estudiante", "NOMBRE DEL ESTUDIANTE", JOptionPane.INFORMATION_MESSAGE);

                    // Preguntar qué columna se desea agregar o modificar
                    String columnaInsertar = JOptionPane.showInputDialog(null,
                            "¿Qué columna deseas agregar o modificar? (Opciones: Nota_TI, Nota_TG, Nota_Talleres, Nota_Controles, Nota_Examenes, Lecciones)",
                            "COLUMNA A MODIFICAR", JOptionPane.INFORMATION_MESSAGE);

                    // Solicitar el valor de la nota correspondiente
                    float valorNota = Float.parseFloat(JOptionPane.showInputDialog(null,
                            "Ingresa el valor de la " + columnaInsertar + " (como decimal)",
                            "VALOR NOTA", JOptionPane.INFORMATION_MESSAGE));

                    // Consulta SQL para insertar o actualizar el registro
                    String queryInsertar = "INSERT INTO Registrodbc (Estudiante, " + columnaInsertar + ") VALUES (?, ?) " +
                            "ON DUPLICATE KEY UPDATE " + columnaInsertar + " = ?";
                    PreparedStatement pstmtInsert = conn.prepareStatement(queryInsertar);
                    pstmtInsert.setString(1, estudiante); // Establecer el nombre del estudiante
                    pstmtInsert.setFloat(2, valorNota); // Establecer el valor de la nota
                    pstmtInsert.setFloat(3, valorNota); // Actualizar el valor de la nota si ya existe
                    pstmtInsert.executeUpdate(); // Ejecutar la actualización o inserción

                    System.out.println("Registro actualizado o creado con éxito.");
                    break;

                case 2: // Caso para mostrar los registros existentes
                    // Consulta SQL para seleccionar todos los registros
                    String queryMostrar = "SELECT * FROM Registrodbc";
                    ResultSet rs = stmt.executeQuery(queryMostrar); // Ejecutar la consulta

                    // Construir la tabla para mostrar los resultados
                    StringBuilder tabla = new StringBuilder();
                    tabla.append(String.format("%-20s%-15s%-15s%-15s%-15s%-15s%-15s\n",
                            "ESTUDIANTE", "Nota_TI", "Nota_TG", "Nota_Talleres",
                            "Nota_Controles", "Nota_Examenes", "Lecciones"));
                    tabla.append("---------------------------------------------------------------------------------------------------------\n");

                    // Recorrer los resultados y mostrar en formato tabular
                    while (rs.next()) {
                        tabla.append(String.format("%-20s%-15.2f%-15.2f%-15.2f%-15.2f%-15.2f%-15.2f\n",
                                rs.getString("Estudiante"), rs.getFloat("Nota_TI"),
                                rs.getFloat("Nota_TG"), rs.getFloat("Nota_Talleres"),
                                rs.getFloat("Nota_Controles"), rs.getFloat("Nota_Examenes"),
                                rs.getFloat("Lecciones")));
                    }

                    System.out.println(tabla); // Imprimir la tabla con los registros
                    break;

                case 3: // Caso para actualizar un registro existente
                    // Solicitar el nombre del estudiante a actualizar
                    String estudianteActualizar = JOptionPane.showInputDialog(null,
                            "Ingresa el nombre del estudiante a actualizar", "ACTUALIZAR ESTUDIANTE", JOptionPane.INFORMATION_MESSAGE);

                    // Preguntar qué columna se desea actualizar
                    String columnaActualizar = JOptionPane.showInputDialog(null,
                            "¿Qué columna deseas actualizar? (Opciones: Nota_TI, Nota_TG, Nota_Talleres, Nota_Controles, Nota_Examenes, Lecciones)",
                            "COLUMNA A ACTUALIZAR", JOptionPane.INFORMATION_MESSAGE);

                    // Solicitar el valor de la nueva nota para la columna
                    float nuevaNota = Float.parseFloat(JOptionPane.showInputDialog(null,
                            "Ingresa la nueva nota para " + columnaActualizar, "NUEVA NOTA", JOptionPane.INFORMATION_MESSAGE));

                    // Consulta SQL para actualizar el registro
                    String queryActualizar = "UPDATE Registrodbc SET " + columnaActualizar + " = ? WHERE Estudiante = ?";
                    PreparedStatement pstmtActualizar = conn.prepareStatement(queryActualizar);
                    pstmtActualizar.setFloat(1, nuevaNota); // Establecer la nueva nota
                    pstmtActualizar.setString(2, estudianteActualizar); // Establecer el estudiante a actualizar
                    int filasActualizadas = pstmtActualizar.executeUpdate(); // Ejecutar la actualización

                    // Verificar si se actualizó el registro correctamente
                    if (filasActualizadas > 0) {
                        System.out.println("Dato actualizado con éxito.");
                    } else {
                        System.out.println("No se encontró al estudiante.");
                    }
                    break;

                case 4: // Caso para eliminar un registro
                    // Preguntar si se desea eliminar el estudiante completo o solo una nota
                    String tipoEliminar = JOptionPane.showInputDialog(null,
                            "¿Deseas eliminar un estudiante completo o solo una nota? (Escribe 'estudiante' o 'nota')",
                            "TIPO DE ELIMINACIÓN", JOptionPane.INFORMATION_MESSAGE);

                    // Eliminar estudiante completo
                    if (tipoEliminar.equalsIgnoreCase("estudiante")) {
                        String estudianteEliminar = JOptionPane.showInputDialog(null,
                                "Ingresa el nombre del estudiante que deseas eliminar", "ELIMINAR ESTUDIANTE", JOptionPane.INFORMATION_MESSAGE);

                        // Consulta SQL para eliminar el estudiante
                        String queryEliminar = "DELETE FROM Registrodbc WHERE Estudiante = ?";
                        PreparedStatement pstmtEliminar = conn.prepareStatement(queryEliminar);
                        pstmtEliminar.setString(1, estudianteEliminar); // Establecer el estudiante a eliminar
                        int filasEliminadas = pstmtEliminar.executeUpdate(); // Ejecutar la eliminación

                        // Verificar si se eliminó correctamente
                        if (filasEliminadas > 0) {
                            System.out.println("Estudiante eliminado con éxito.");
                        } else {
                            System.out.println("No se encontró al estudiante.");
                        }
                    }
                    // Eliminar solo una nota
                    else if (tipoEliminar.equalsIgnoreCase("nota")) {
                        String estudianteEliminarNota = JOptionPane.showInputDialog(null,
                                "Ingresa el nombre del estudiante", "NOMBRE DEL ESTUDIANTE", JOptionPane.INFORMATION_MESSAGE);

                        // Preguntar qué columna se desea eliminar
                        String columnaEliminarNota = JOptionPane.showInputDialog(null,
                                "¿Qué columna deseas eliminar? (Opciones: Nota_TI, Nota_TG, Nota_Talleres, Nota_Controles, Nota_Examenes, Lecciones)",
                                "COLUMNA A ELIMINAR", JOptionPane.INFORMATION_MESSAGE);

                        // Consulta SQL para eliminar la nota del estudiante
                        String queryEliminarNota = "UPDATE Registrodbc SET " + columnaEliminarNota + " = NULL WHERE Estudiante = ?";
                        PreparedStatement pstmtEliminarNota = conn.prepareStatement(queryEliminarNota);
                        pstmtEliminarNota.setString(1, estudianteEliminarNota); // Establecer el estudiante
                        int filasEliminadasNota = pstmtEliminarNota.executeUpdate(); // Ejecutar la eliminación de la nota

                        // Verificar si se eliminó correctamente
                        if (filasEliminadasNota > 0) {
                            System.out.println("Nota eliminada con éxito.");
                        } else {
                            System.out.println("No se encontró al estudiante.");
                        }
                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;

                // Caso por defecto para opciones no válidas
                default:
                    System.out.println("Opción no válida.");
                    break;
            }

        } catch (SQLException e) {
            // Manejo de excepciones SQL
            System.err.println("Error SQL: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Manejo de excepciones de formato numérico
            System.err.println("Error en formato numérico: " + e.getMessage());
        } finally {
            // Asegurarse de cerrar la conexión en el bloque finally
            try {
                if (conn != null) conn.close(); // Cerrar la conexión
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}