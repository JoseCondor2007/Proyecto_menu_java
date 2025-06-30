package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Properties;
import org.jdatepicker.impl.*;
import controller.FormsController;

public class ServiceForm extends JPanel {
    private JDatePickerImpl datePicker;
    private JComboBox<String> comboServicio;
    private JTextField txtComentario;
    private JRadioButton leve, medio, grave;
    private JButton btnRegistrar, btnModificar, btnEliminar;
    private JTable tablaDatos;
    private DefaultTableModel modeloTabla;
    private int filaSeleccionada = -1;
    private FormsController controller; // Referencia al controlador
    private JTextField txtPrecio; // Nuevo campo para el precio

    public ServiceForm() {
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Tipo de Servicio", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel principal para los campos de entrada
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Fecha del servicio:"), gbc);
        gbc.gridx = 1;
        datePicker = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), new Properties()), new DateLabelFormatter());
        panelFormulario.add(datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Tipo de servicio:"), gbc);
        gbc.gridx = 1;
        comboServicio = new JComboBox<>(new String[]{"Revisión", "Cambio de llanta", "Parche", "Venta de cámara", "Otros"});
        panelFormulario.add(comboServicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Comentario adicional:"), gbc);
        gbc.gridx = 1;
        txtComentario = new JTextField();
        panelFormulario.add(txtComentario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Nivel de urgencia:"), gbc);
        gbc.gridx = 1;
        JPanel urgenciaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leve = new JRadioButton("Leve");
        medio = new JRadioButton("Medio");
        grave = new JRadioButton("Grave");

        leve.setBackground(Color.YELLOW);
        medio.setBackground(Color.ORANGE);
        grave.setBackground(Color.RED);

        ButtonGroup urgenciaGroup = new ButtonGroup();
        urgenciaGroup.add(leve);
        urgenciaGroup.add(medio);
        urgenciaGroup.add(grave);
        urgenciaPanel.add(leve);
        urgenciaPanel.add(medio);
        urgenciaPanel.add(grave);
        panelFormulario.add(urgenciaPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio, gbc);  // Campo de texto para el precio

        // Botones de acción
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(0, 153, 204));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setOpaque(true); // <--- AÑADIR ESTA LÍNEA
        btnRegistrar.setBorderPainted(false); // <--- AÑADIR ESTA LÍNEA (opcional, para eliminar el borde)

        btnModificar = new JButton("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.setBackground(new Color(255, 153, 0));
        btnModificar.setFocusPainted(false);
        btnModificar.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificar.setOpaque(true); // <--- AÑADIR ESTA LÍNEA
        btnModificar.setBorderPainted(false); // <--- AÑADIR ESTA LÍNEA (opcional, para eliminar el borde)

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.setBackground(new Color(255, 0, 0));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setOpaque(true); // <--- AÑADIR ESTA LÍNEA
        btnEliminar.setBorderPainted(false); // <--- AÑADIR ESTA LÍNEA (opcional, para eliminar el borde)

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        // Tabla para mostrar los datos
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Servicio");
        modeloTabla.addColumn("Comentario");
        modeloTabla.addColumn("Urgencia");
        modeloTabla.addColumn("Precio"); // Agregamos la columna Precio

        tablaDatos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaDatos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Servicios"));

        // Agregar formulario y tabla a un panel contenedor
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.add(panelFormulario);
        contenedor.add(scrollPane);

        add(contenedor, BorderLayout.CENTER);

        // Inicializar el controlador
        controller = new FormsController(this);
        btnRegistrar.addActionListener(controller);
        btnModificar.addActionListener(controller);
        btnEliminar.addActionListener(controller);
        tablaDatos.getSelectionModel().addListSelectionListener(controller);
    }

    public JDatePickerImpl getDatePicker() {
        return datePicker;
    }

    public JComboBox<String> getComboServicio() {
        return comboServicio;
    }

    public JTextField getTxtComentario() {
        return txtComentario;
    }

    public JRadioButton getLeve() {
        return leve;
    }

    public JRadioButton getMedio() {
        return medio;
    }

    public JRadioButton getGrave() {
        return grave;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JTable getTablaDatos() {
        return tablaDatos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public int getFilaSeleccionada() {
        return filaSeleccionada;
    }

    public void setFilaSeleccionada(int filaSeleccionada) {
        this.filaSeleccionada = filaSeleccionada;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setController(FormsController formsController) {
    }

    // Formateador para la fecha
    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat("dd/MM/yyyy");

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws java.text.ParseException {
            if (value != null) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}