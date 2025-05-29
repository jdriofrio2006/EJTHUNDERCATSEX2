// ===============================
// GUI Principal para Thundercats
// ===============================

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ThundercatsGUI extends JFrame {
    private ListaFelinos lista = new ListaFelinos();

    private JTextField txtId = new JTextField();
    private JTextField txtAlias = new JTextField();
    private JComboBox<String> cbDestreza = new JComboBox<>(new String[]{
            "Espada del Augurio", "Defensa Felina", "Agilidad Sobrehumana", "Estrategia Mental", "Control Elemental"
    });
    private JComboBox<Integer> cbCombate = new JComboBox<>();
    private JComboBox<String> cbZona = new JComboBox<>(ListaFelinos.zonas);
    private JTable tabla = new JTable();
    private JTextArea txtArea = new JTextArea();
    private DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Alias", "Destreza", "Nivel", "Zona"}, 0
    );
    private JPanel panelMain;
    private JTextArea txtxArea;
    private JButton btnAgregar;
    private JButton btnBuscar;
    private JButton btnFiltrar;
    private JButton btnContar;

    public ThundercatsGUI() {
        setTitle("Thundercats - Control de Fuerzas Felinas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Thundercat"));
        panelForm.add(new JLabel("ID:")); panelForm.add(txtId);
        panelForm.add(new JLabel("Alias:")); panelForm.add(txtAlias);
        panelForm.add(new JLabel("Destreza:")); panelForm.add(cbDestreza);
        panelForm.add(new JLabel("Nivel de Combate:"));
        for (int i = 1; i <= 10; i++) cbCombate.addItem(i);
        panelForm.add(cbCombate);
        panelForm.add(new JLabel("Zona de Batalla:")); panelForm.add(cbZona);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnFiltrar = new JButton("Filtrar por Destreza");
        JButton btnContar = new JButton("Conteo por Zona");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnFiltrar);
        panelBotones.add(btnContar);

        // Tabla y Área
        tabla.setModel(model);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Thundercats"));

        txtArea.setEditable(false);
        JScrollPane scrollArea = new JScrollPane(txtArea);
        scrollArea.setBorder(BorderFactory.createTitledBorder("Conteo por Zona"));
        scrollArea.setPreferredSize(new Dimension(200, 100));

        // Panel principal
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(scrollTabla, BorderLayout.CENTER);
        panelCentro.add(scrollArea, BorderLayout.EAST);

        // Agregar a la ventana
        add(panelForm, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);
        add(panelCentro, BorderLayout.CENTER);

        // Eventos
        btnAgregar.addActionListener(e -> agregarThundercat());
        btnBuscar.addActionListener(e -> buscarThundercat());
        btnFiltrar.addActionListener(e -> filtrarPorDestreza());
        btnContar.addActionListener(e -> txtArea.setText(lista.contarPorZona()));
    }

    private void agregarThundercat() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String alias = txtAlias.getText().trim();
            String destreza = (String) cbDestreza.getSelectedItem();
            int combate = (int) cbCombate.getSelectedItem();
            String zona = (String) cbZona.getSelectedItem();

            FuerzaFelina f = new FuerzaFelina(id, alias, destreza, combate, zona);
            if (lista.insertarOrdenado(f)) {
                model.addRow(new Object[]{id, alias, destreza, combate, zona});
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error: ID duplicado.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser un número entero.");
        }
    }

    private void buscarThundercat() {
        try {
            String input = JOptionPane.showInputDialog(this, "Ingrese el ID a buscar:");
            if (input == null || input.isEmpty()) return;
            int id = Integer.parseInt(input);
            FuerzaFelina f = lista.buscar(id);

            if (f != null) {
                txtId.setText(String.valueOf(f.id));
                txtAlias.setText(f.alias);
                cbDestreza.setSelectedItem(f.destreza);
                cbCombate.setSelectedItem(f.combate);
                cbZona.setSelectedItem(f.zona);
            } else {
                JOptionPane.showMessageDialog(this, "Thundercat no encontrado.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void filtrarPorDestreza() {
        String filtro = (String) cbDestreza.getSelectedItem();
        ListaFelinos filtrada = lista.filtrarYOrdenar(filtro);

        model.setRowCount(0);
        NodoFelino aux = filtrada.head;
        while (aux != null) {
            FuerzaFelina f = aux.data;
            model.addRow(new Object[]{f.id, f.alias, f.destreza, f.combate, f.zona});
            aux = aux.next;
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtAlias.setText("");
        cbDestreza.setSelectedIndex(0);
        cbCombate.setSelectedIndex(0);
        cbZona.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThundercatsGUI().setVisible(true));
    }
}