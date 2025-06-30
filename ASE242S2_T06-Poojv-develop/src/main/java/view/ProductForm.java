package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import controller.ProductController;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

// CAMBIO CRÍTICO: Ahora extiende JPanel en lugar de JFrame
public class ProductForm extends JPanel implements ActionListener {

    // Componentes de la interfaz
    private JLabel lblProductName, lblProductType, lblBrand, lblPrice,
            lblHasStock, lblStock, lblRegistrationDate;
    private JTextField txtProductName, txtBrand, txtPrice;
    private JComboBox<String> cmbProductType;
    private JCheckBox chkYesStock, chkNoStock;
    private JSpinner spnStock;
    private JButton btnRegister, btnModify, btnDelete;
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPaneTable;

    // Componente para la fecha (JDateChooser)
    private JDateChooser dateChooser;

    private ProductController controller;

    public ProductForm() {
        initializeComponents();
        // configureWindow() se elimina porque ya no es un JFrame, AppLauncher lo manejará
        arrangeComponents();
        addListeners();
        // CAMBIO: Inicializa el controlador aquí
        controller = new ProductController(this);
    }

    private void initializeComponents() {
        lblProductName = new JLabel("Nombre del producto:");
        lblProductType = new JLabel("Tipo de producto:");
        lblBrand = new JLabel("Marca:");
        lblPrice = new JLabel("Precio:");
        lblHasStock = new JLabel("¿Hay en stock?");
        lblStock = new JLabel("Stock:");
        lblRegistrationDate = new JLabel("Fecha de Registro:");

        txtProductName = new JTextField(30);
        txtBrand = new JTextField(30);
        txtPrice = new JTextField(15);

        // Opciones del JComboBox actualizadas según tu última petición
        cmbProductType = new JComboBox<>(new String[]{"Para moto", "Para carro", "Para bicicleta", "Para camión", "Otros"});
        cmbProductType.setPreferredSize(new Dimension(200, cmbProductType.getPreferredSize().height));

        chkYesStock = new JCheckBox("Sí");
        chkNoStock = new JCheckBox("No");
        ButtonGroup stockGroup = new ButtonGroup();
        stockGroup.add(chkYesStock);
        stockGroup.add(chkNoStock);
        chkNoStock.setSelected(true); // Por defecto, sin stock

        spnStock = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        spnStock.setEnabled(false); // Deshabilitado por defecto
        spnStock.setPreferredSize(new Dimension(100, spnStock.getPreferredSize().height));

        btnRegister = new JButton("Registrar");
        btnModify = new JButton("Modificar");
        btnDelete = new JButton("Eliminar");

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Tipo", "Marca", "Precio", "¿En Stock?", "Stock", "Fecha Registro"}, 0);
        productsTable = new JTable(tableModel);
        scrollPaneTable = new JScrollPane(productsTable);
        scrollPaneTable.setBorder(BorderFactory.createTitledBorder("Lista de Productos"));

        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(120, dateChooser.getPreferredSize().height));

        // Carga de iconos y configuración de colores
        try {
            ImageIcon originalIconRegistrar = new ImageIcon(getClass().getResource("/img/registrar.png"));
            Image imageRegistrar = originalIconRegistrar.getImage();
            Image scaledImageRegistrar = imageRegistrar.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon iconRegistrar = new ImageIcon(scaledImageRegistrar);
            btnRegister.setIcon(iconRegistrar);

            ImageIcon originalIconModificar = new ImageIcon(getClass().getResource("/img/modificar.png"));
            Image imageModificar = originalIconModificar.getImage();
            Image scaledImageModificar = imageModificar.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon iconModificar = new ImageIcon(scaledImageModificar);
            btnModify.setIcon(iconModificar);

            ImageIcon originalIconEliminar = new ImageIcon(getClass().getResource("/img/eliminar.png"));
            Image imageEliminar = originalIconEliminar.getImage();
            Image scaledImageEliminar = imageEliminar.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon iconEliminar = new ImageIcon(scaledImageEliminar);
            btnDelete.setIcon(iconEliminar);

        } catch (Exception e) {
            System.err.println("Error al cargar o escalar las imágenes de los botones: " + e.getMessage());
            e.printStackTrace();
        }

        btnRegister.setBackground(new Color(144, 238, 144)); // Verde claro
        btnModify.setBackground(new Color(255, 255, 153)); // Amarillo claro
        btnDelete.setBackground(new Color(255, 153, 153)); // Rojo claro
    }



    private void arrangeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Para que el panel ocupe el ancho

        JPanel registrationPanel = new JPanel(new GridBagLayout());
        registrationPanel.setBorder(BorderFactory.createTitledBorder("Registro de Producto"));
        GridBagConstraints gbcRegistration = new GridBagConstraints();
        gbcRegistration.insets = new Insets(5, 5, 5, 5);
        gbcRegistration.anchor = GridBagConstraints.WEST;
        gbcRegistration.fill = GridBagConstraints.HORIZONTAL;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 0;
        registrationPanel.add(lblProductName, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(txtProductName, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 1;
        registrationPanel.add(lblProductType, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.weightx = 1.0;
        registrationPanel.add(cmbProductType, gbcRegistration);
        gbcRegistration.weightx = 0.0;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 2;
        registrationPanel.add(lblBrand, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(txtBrand, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 3;
        registrationPanel.add(lblPrice, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(txtPrice, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 4;
        registrationPanel.add(lblHasStock, gbcRegistration);
        JPanel stockCheckPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stockCheckPanel.add(chkYesStock);
        stockCheckPanel.add(chkNoStock);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(stockCheckPanel, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 5;
        registrationPanel.add(lblStock, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(spnStock, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 6;
        registrationPanel.add(lblRegistrationDate, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(dateChooser, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(btnRegister);
        buttonsPanel.add(btnModify);
        buttonsPanel.add(btnDelete);
        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 7;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        gbcRegistration.anchor = GridBagConstraints.CENTER;
        gbcRegistration.fill = GridBagConstraints.NONE;
        registrationPanel.add(buttonsPanel, gbcRegistration);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(registrationPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPaneTable, gbc);

    }

    private void addListeners() {
        btnRegister.addActionListener(this);
        btnModify.addActionListener(this);
        btnDelete.addActionListener(this);

        // Usar lambdas para simplificar los listeners de los JCheckBox
        chkYesStock.addActionListener(e -> {
            spnStock.setEnabled(chkYesStock.isSelected());
            chkNoStock.setSelected(!chkYesStock.isSelected());
        });
        chkNoStock.addActionListener(e -> {
            spnStock.setEnabled(!chkNoStock.isSelected());
            chkYesStock.setSelected(!chkNoStock.isSelected());
            if (chkNoStock.isSelected()) {
                spnStock.setValue(0);
            }
        });

        productsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (controller != null) {
                    // Llamar a un nuevo método en el controlador para cargar los datos
                    controller.loadSelectedProductToForm();
                }
            }
        });
    }

    // Este método setController ya es correcto para ProductController
    public void setController(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (controller != null) {
            if (e.getSource() == btnRegister) {
                controller.registerProductInDatabase();
            } else if (e.getSource() == btnModify) {
                controller.updateProductInDatabase();
            } else if (e.getSource() == btnDelete) {
                controller.deleteProductFromDatabase(); // Usar el nuevo nombre del método en el controlador
            }
            // Los listeners de chkYesStock y chkNoStock se manejan con lambdas directamente
        }
    }

    // Renombrado clearForm() a clearFields() para consistencia
    public void clearFields() {
        txtProductName.setText("");
        txtBrand.setText("");
        txtPrice.setText("");
        cmbProductType.setSelectedIndex(0);
        chkNoStock.setSelected(true);
        spnStock.setValue(0);
        spnStock.setEnabled(false);
        dateChooser.setDate(null);
        productsTable.clearSelection();
    }

    // Nuevo método para cargar datos en los campos del formulario desde el controlador
    public void loadProductToForm(String name, String type, String brand, double price, boolean hasStock, int stock, Date registrationDate) {
        txtProductName.setText(name);
        cmbProductType.setSelectedItem(type);
        txtBrand.setText(brand);
        txtPrice.setText(String.valueOf(price));

        chkYesStock.setSelected(hasStock);
        chkNoStock.setSelected(!hasStock);
        spnStock.setValue(stock);
        spnStock.setEnabled(hasStock);

        if (registrationDate != null) {
            dateChooser.setDate(registrationDate);
        } else {
            dateChooser.setDate(null);
        }
    }

    // Métodos getter para los componentes (necesarios para el controlador)
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRegistrar() { return btnRegister; }
    public JButton getBtnModificar() { return btnModify; }
    public JButton getBtnEliminar() { return btnDelete; }
    public JTextField getTxtNombreProducto() { return txtProductName; }
    public JComboBox<String> getCmbTipoProducto() { return cmbProductType; }
    public JTextField getTxtMarca() { return txtBrand; }
    public JTextField getTxtPrecio() { return txtPrice; }
    public JCheckBox getChkSiStock() { return chkYesStock; }
    public JCheckBox getChkNoStock() { return chkNoStock; }
    public JSpinner getSpnStock() { return spnStock; }
    public JTable getTablaProductos() { return productsTable; }

    public String getTxtFechaRegistro() {
        Date date = dateChooser.getDate();
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(date);
        } else {
            return "";
        }
    }
}