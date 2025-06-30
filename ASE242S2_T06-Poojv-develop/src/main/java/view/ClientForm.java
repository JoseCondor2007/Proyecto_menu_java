package view;

import controller.ClientController;
import model.Client;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClientForm extends JPanel {
    private final ClientController clientController;
    private JPanel mainPanel, clientListPanel;
    private JTextField searchField;
    private JLabel counterLabel;
    private JLabel statusLabel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private boolean showActiveClientsOnly = true;
    private JButton toggleActiveButton;

    public ClientForm(ClientController controller) {
        this.clientController = controller;
        initUI();
        loadClients();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        mainPanel = new JPanel(new BorderLayout(16, 0));
        mainPanel.setBorder(new EmptyBorder(24, 32, 24, 32));
        mainPanel.setBackground(Color.WHITE);

        // Panel superior con botones y búsqueda
        JPanel topPanel = new JPanel(new BorderLayout(12, 0));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(0, 0, 24, 0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton addClientButton = new JButton("Agregar Cliente");
        styleMainButton(addClientButton, new Color(33, 150, 243));
        addClientButton.addActionListener(e -> openAddClientDialog());

        toggleActiveButton = new JButton("Mostrar Inactivos");
        styleMainButton(toggleActiveButton, new Color(107, 114, 128));
        toggleActiveButton.addActionListener(e -> {
            showActiveClientsOnly = !showActiveClientsOnly;
            toggleActiveButton.setText(showActiveClientsOnly ? "Mostrar Inactivos" : "Mostrar Activos");
            loadClients();
        });

        buttonPanel.add(addClientButton);
        buttonPanel.add(toggleActiveButton);
        topPanel.add(buttonPanel, BorderLayout.WEST);

        searchField = new JTextField("Buscar por nombre, apellido, documento o correo...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setForeground(new Color(120, 120, 120));
        searchField.setMargin(new Insets(8, 12, 8, 12));
        searchField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Buscar por nombre, apellido, documento o correo...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setForeground(new Color(120, 120, 120));
                    searchField.setText("Buscar por nombre, apellido, documento o correo...");
                }
            }
        });
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                loadClients();
            }
        });

        topPanel.add(searchField, BorderLayout.CENTER);

        counterLabel = new JLabel("0 de 0 clientes");
        counterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        counterLabel.setForeground(new Color(107, 114, 128));
        counterLabel.setBorder(new EmptyBorder(0, 12, 0, 0));
        topPanel.add(counterLabel, BorderLayout.EAST);

        statusLabel = new JLabel("Mostrando clientes activos");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statusLabel.setForeground(new Color(33, 150, 243));
        statusLabel.setBorder(new EmptyBorder(6, 0, 0, 0));
        topPanel.add(statusLabel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Lista de clientes
        clientListPanel = new JPanel();
        clientListPanel.setLayout(new BoxLayout(clientListPanel, BoxLayout.Y_AXIS));
        clientListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(clientListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.WHITE);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void styleMainButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 24, 10, 24));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    private void loadClients() {
        List<Client> allClients = clientController.obtenerTodosLosClientesConEstado();
        List<Client> filteredClients;
        if (showActiveClientsOnly) {
            filteredClients = allClients.stream().filter(Client::isEstado).toList();
            statusLabel.setText("Mostrando clientes activos");
        } else {
            filteredClients = allClients.stream().filter(c -> !c.isEstado()).toList();
            statusLabel.setText("Mostrando clientes inactivos");
        }

        String text = searchField.getText();
        if (text != null && !text.trim().isEmpty() &&
                !text.equalsIgnoreCase("Buscar por nombre, apellido, documento o correo...")) {
            String searchText = text.toLowerCase();
            filteredClients = filteredClients.stream()
                    .filter(c -> c.getNombre().toLowerCase().contains(searchText) ||
                            c.getApellido().toLowerCase().contains(searchText) ||
                            c.getNumeroDocumento().toLowerCase().contains(searchText) ||
                            c.getCorreo().toLowerCase().contains(searchText))
                    .toList();
        }

        updateClientList(filteredClients);
        counterLabel.setText(String.format("%d de %d clientes", filteredClients.size(), allClients.size()));
    }

    private void updateClientList(List<Client> clients) {
        clientListPanel.removeAll();
        if (clients.isEmpty()) {
            JLabel emptyLabel = new JLabel("No se encontraron clientes.");
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            emptyLabel.setForeground(new Color(140, 140, 140));
            emptyLabel.setBorder(new EmptyBorder(60, 0, 60, 0));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            clientListPanel.add(emptyLabel);
        } else {
            for (Client client : clients) {
                JPanel card = createClientCard(client);
                clientListPanel.add(card);
                clientListPanel.add(Box.createRigidArea(new Dimension(0, 16)));
            }
        }
        clientListPanel.revalidate();
        clientListPanel.repaint();
    }

    private JPanel createClientCard(Client client) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(20, 24, 20, 24)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));
        card.setLayout(new BorderLayout(12, 12));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel(client.getNombre() + " " + client.getApellido());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        nameLabel.setForeground(new Color(33, 33, 33));
        JLabel docLabel = new JLabel(client.getTipoDocumento() + ": " + client.getNumeroDocumento());
        docLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        docLabel.setForeground(new Color(115, 115, 115));
        docLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        header.add(nameLabel);
        header.add(docLabel);
        card.add(header, BorderLayout.NORTH);

        JPanel details = new JPanel();
        details.setBackground(Color.WHITE);
        details.setLayout(new GridLayout(5, 2, 10, 4));
        addDetail(details, "Email:", client.getCorreo());
        addDetail(details, "Teléfono:", client.getTelefono());
        addDetail(details, "Sexo:", client.getSexo());
        addDetail(details, "Nacimiento:", client.getFecha() != null ? dateFormat.format(client.getFecha()) : "N/A");
        addDetail(details, "Dirección:", client.getDireccion());
        card.add(details, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        buttons.setBackground(Color.WHITE);

        JButton editBtn = new JButton("Editar");
        styleActionButton(editBtn, new Color(56, 142, 60));
        editBtn.addActionListener(e -> openEditClientDialog(client));

        JButton toggleBtn = new JButton(client.isEstado() ? "Desactivar" : "Activar");
        styleActionButton(toggleBtn, new Color(211, 47, 47));
        toggleBtn.addActionListener(e -> toggleClientStatus(client));

        JButton deleteBtn = new JButton("Eliminar");
        styleActionButton(deleteBtn, new Color(255, 87, 34));
        deleteBtn.addActionListener(e -> deleteClient(client));

        buttons.add(editBtn);
        buttons.add(toggleBtn);
        buttons.add(deleteBtn);
        card.add(buttons, BorderLayout.SOUTH);

        return card;
    }

    private void addDetail(JPanel panel, String label, String value) {
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelComp.setForeground(new Color(55, 65, 81));
        JLabel valueComp = new JLabel(value != null && !value.isEmpty() ? value : "N/A");
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valueComp.setForeground(new Color(80, 80, 80));
        panel.add(labelComp);
        panel.add(valueComp);
    }

    private void toggleClientStatus(Client client) {
        if (client.isEstado()) {
            clientController.eliminarClienteLogicoPorId(client.getIdCliente());
        } else {
            clientController.activarClienteLogicoPorId(client.getIdCliente());
        }
        loadClients();
    }

    private void deleteClient(Client client) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar este cliente?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            clientController.eliminarClienteLogicoPorId(client.getIdCliente());
            loadClients();
        }
    }

    private void openAddClientDialog() {
        ClientFormDialog dialog = new ClientFormDialog(SwingUtilities.getWindowAncestor(this), "Agregar Nuevo Cliente", null);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            loadClients();
        }
    }

    private void openEditClientDialog(Client client) {
        ClientFormDialog dialog = new ClientFormDialog(SwingUtilities.getWindowAncestor(this), "Editar Cliente", client);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            loadClients();
        }
    }

    private class ClientFormDialog extends JDialog {
        private JTextField nombreField;
        private JTextField apellidoField;
        private JTextField numeroDocumentoField;
        private JTextField correoField;
        private JTextField telefonoField;
        private JCheckBox sexoMasculino;
        private JCheckBox sexoFemenino;
        private JComboBox<String> tipoDocumentoComboBox;
        private JFormattedTextField fechaNacimientoField;
        private JTextField direccionField;
        private JCheckBox confirmCheckBox;
        private boolean succeeded = false;
        private Client editingClient;
        private SimpleDateFormat dialogDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        public ClientFormDialog(Window parent, String title, Client client) {
            super(parent instanceof Frame ? (Frame) parent : null, title, true);
            this.editingClient = client;
            initForm();
            if (client != null) {
                fillForm(client);
            }
            pack();
            setMinimumSize(new Dimension(480, 580));
            setLocationRelativeTo(parent);
        }

        private void initForm() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(new EmptyBorder(24, 28, 24, 28));
            panel.setBackground(Color.WHITE);

            JLabel headerLabel = new JLabel(getTitle());
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
            headerLabel.setForeground(new Color(33, 33, 33));
            headerLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
            panel.add(headerLabel, BorderLayout.NORTH);

            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(12, 8, 12, 8);
            gbc.anchor = GridBagConstraints.WEST;
            int y = 0;

            gbc.gridx = 0;
            gbc.gridy = y;
            formPanel.add(createLabel("Nombre:"), gbc);
            gbc.gridx = 1;
            nombreField = new JTextField(22);
            estiloCampoTexto(nombreField);
            formPanel.add(nombreField, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            formPanel.add(createLabel("Apellido:"), gbc);
            gbc.gridx = 1;
            apellidoField = new JTextField(22);
            estiloCampoTexto(apellidoField);
            formPanel.add(apellidoField, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            formPanel.add(createLabel("Número Documento:"), gbc);
            gbc.gridx = 1;
            numeroDocumentoField = new JTextField(22);
            estiloCampoTexto(numeroDocumentoField);
            formPanel.add(numeroDocumentoField, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            formPanel.add(createLabel("Correo:"), gbc);
            gbc.gridx = 1;
            correoField = new JTextField(22);
            estiloCampoTexto(correoField);
            formPanel.add(correoField, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            formPanel.add(createLabel("Teléfono:"), gbc);
            gbc.gridx = 1;
            telefonoField = new JTextField(22);
            estiloCampoTexto(telefonoField);
            formPanel.add(telefonoField, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            formPanel.add(createLabel("Sexo:"), gbc);
            gbc.gridx = 1;
            JPanel sexoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
            sexoPanel.setBackground(Color.WHITE);
            sexoMasculino = new JCheckBox("Masculino");
            sexoFemenino = new JCheckBox("Femenino");
            estiloCasilla(sexoMasculino);
            estiloCasilla(sexoFemenino);

            ItemListener sexoListener = e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JCheckBox source = (JCheckBox) e.getSource();
                    if (source != sexoMasculino) sexoMasculino.setSelected(false);
                    if (source != sexoFemenino) sexoFemenino.setSelected(false);
                }
            };
            sexoMasculino.addItemListener(sexoListener);
            sexoFemenino.addItemListener(sexoListener);
            sexoPanel.add(sexoMasculino);
            sexoPanel.add(sexoFemenino);
            formPanel.add(sexoPanel, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            formPanel.add(createLabel("Tipo de Documento:"), gbc);
            gbc.gridx = 1;
            tipoDocumentoComboBox = new JComboBox<>(new String[]{"DNI", "Pasaporte", "Otro"});
            estiloCampoTexto(tipoDocumentoComboBox);
            formPanel.add(tipoDocumentoComboBox, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            formPanel.add(createLabel("Fecha Nacimiento:"), gbc);
            gbc.gridx = 1;
            try {
                MaskFormatter dateMask = new MaskFormatter("##/##/####");
                dateMask.setPlaceholderCharacter('_');
                fechaNacimientoField = new JFormattedTextField(dateMask);
                fechaNacimientoField.setColumns(22);
                estiloCampoTexto(fechaNacimientoField);
            } catch (ParseException ex) {
                fechaNacimientoField = new JFormattedTextField();
            }
            formPanel.add(fechaNacimientoField, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            formPanel.add(createLabel("Dirección:"), gbc);
            gbc.gridx = 1;
            direccionField = new JTextField(22);
            estiloCampoTexto(direccionField);
            formPanel.add(direccionField, gbc);

            panel.add(formPanel, BorderLayout.CENTER);

            confirmCheckBox = new JCheckBox("Confirmo que los datos son correctos");
            confirmCheckBox.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            confirmCheckBox.setForeground(new Color(107, 114, 128));
            confirmCheckBox.setBorder(new EmptyBorder(18, 0, 14, 0));
            panel.add(confirmCheckBox, BorderLayout.SOUTH);

            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 8));
            buttonsPanel.setBackground(Color.WHITE);
            JButton submitButton = new JButton(editingClient == null ? "Registrar" : "Actualizar");
            styleActionButton(submitButton, new Color(33, 150, 243));
            submitButton.addActionListener(e -> onSubmit());
            JButton cancelButton = new JButton("Cancelar");
            styleActionButton(cancelButton, new Color(156, 163, 175));
            cancelButton.addActionListener(e -> {
                succeeded = false;
                dispose();
            });
            buttonsPanel.add(cancelButton);
            buttonsPanel.add(submitButton);
            panel.add(buttonsPanel, BorderLayout.PAGE_END);

            setContentPane(panel);
            getRootPane().setDefaultButton(submitButton);
        }

        private void estiloCampoTexto(JComponent campo) {
            campo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            if (campo instanceof JTextField) {
                campo.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                        BorderFactory.createEmptyBorder(6, 10, 6, 10)
                ));
            } else if (campo instanceof JComboBox) {
                campo.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                        BorderFactory.createEmptyBorder(2, 6, 2, 6)
                ));
            }
        }

        private void estiloCasilla(JCheckBox checkbox) {
            checkbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            checkbox.setBackground(Color.WHITE);
            checkbox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            label.setForeground(new Color(55, 65, 81));
            return label;
        }

        private void fillForm(Client client) {
            nombreField.setText(client.getNombre());
            apellidoField.setText(client.getApellido());
            numeroDocumentoField.setText(client.getNumeroDocumento());
            correoField.setText(client.getCorreo());
            telefonoField.setText(client.getTelefono());
            direccionField.setText(client.getDireccion());
            sexoMasculino.setSelected(false);
            sexoFemenino.setSelected(false);
            if ("Masculino".equalsIgnoreCase(client.getSexo())) sexoMasculino.setSelected(true);
            else if ("Femenino".equalsIgnoreCase(client.getSexo())) sexoFemenino.setSelected(true);
            tipoDocumentoComboBox.setSelectedItem(
                    client.getTipoDocumento() != null ? client.getTipoDocumento() : "DNI"
            );
            if (client.getFecha() != null) {
                fechaNacimientoField.setText(dateFormat.format(client.getFecha()));
            } else {
                fechaNacimientoField.setValue(null);
                fechaNacimientoField.setText("");
            }
            confirmCheckBox.setSelected(false);
        }

        private void onSubmit() {
            String nombre = nombreField.getText().trim();
            String apellido = apellidoField.getText().trim();
            String numDoc = numeroDocumentoField.getText().trim();
            String correo = correoField.getText().trim();
            String telefono = telefonoField.getText().trim();
            String direccion = direccionField.getText().trim();
            String sexo = null;
            if (sexoMasculino.isSelected()) sexo = "Masculino";
            else if (sexoFemenino.isSelected()) sexo = "Femenino";
            String tipoDocumento = tipoDocumentoComboBox.getSelectedItem() != null ?
                    tipoDocumentoComboBox.getSelectedItem().toString() : "DNI";
            String fechaTexto = fechaNacimientoField.getText().trim();
            Date fecha = null;

            if (nombre.isEmpty() || apellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y Apellido son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (sexo == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una opción de Sexo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!fechaTexto.isEmpty() && !fechaTexto.contains("_")) {
                try {
                    fecha = dateFormat.parse(fechaTexto);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "Fecha de nacimiento con formato inválido (dd/MM/yyyy).", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (editingClient == null) {
                clientController.registrarNuevoCliente(
                        nombre, apellido, telefono, correo, direccion, fecha, sexo, tipoDocumento, numDoc, false
                );
                JOptionPane.showMessageDialog(this, "Cliente registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                clientController.actualizarClienteExistente(
                        editingClient.getIdCliente(), nombre, apellido, telefono, correo, direccion,
                        fechaTexto.isEmpty() ? null : convertToISODateStr(fechaTexto), sexo, tipoDocumento, numDoc, false
                );
                JOptionPane.showMessageDialog(this, "Datos del cliente actualizados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            succeeded = true;
            dispose();
        }

        private String convertToISODateStr(String ddMMyyyy) {
            try {
                Date date = dateFormat.parse(ddMMyyyy);
                return new SimpleDateFormat("yyyy-MM-dd").format(date);
            } catch (ParseException e) {
                return null;
            }
        }

        public boolean isSucceeded() {
            return succeeded;
        }
    }

    private void styleActionButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 20, 8, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }
}