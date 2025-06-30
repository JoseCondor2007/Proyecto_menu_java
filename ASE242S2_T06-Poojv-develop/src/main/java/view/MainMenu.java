package view;

import controller.ClientController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainMenu extends JFrame {

    // =================================================================================
    // Sistema de Usuarios Múltiples
    // =================================================================================
    private static final Map<String, UserInfo> USUARIOS = new HashMap<>();
    static {
        USUARIOS.put("Anghelo", new UserInfo("Anghelo", "admin", "Anghelo Goicochea"));
        USUARIOS.put("Jose", new UserInfo("Jose", "admin", "Jose Condor"));
        USUARIOS.put("Luciana", new UserInfo("Luciana", "admin", "Luciana Ruiz"));
    }

    private String currentUserFullName = "";

    // Clase interna para información de usuario
    private static class UserInfo {
        String username;
        String password;
        String fullName;

        UserInfo(String username, String password, String fullName) {
            this.username = username;
            this.password = password;
            this.fullName = fullName;
        }
    }

    // =================================================================================
    // Paleta de Colores Negros Lujosos - Tema Oscuro Premium
    // =================================================================================

    // Colores Principales - Negro Lujoso con Acentos Dorados
    private static final Color COLOR_PRIMARIO = new Color(212, 175, 55);        // Oro elegante
    private static final Color COLOR_PRIMARIO_HOVER = new Color(255, 193, 7);   // Oro brillante
    private static final Color COLOR_PRIMARIO_LIGHT = new Color(255, 235, 59);  // Oro claro
    private static final Color COLOR_SECUNDARIO = new Color(158, 158, 158);     // Plata
    private static final Color COLOR_ACENTO = new Color(255, 215, 0);           // Oro puro

    // Fondos y Superficies - Sistema de Capas Oscuras
    private static final Color COLOR_FONDO_APP = new Color(246, 243, 243, 247);         // Negro profundo
    private static final Color COLOR_SUPERFICIE_1 = new Color(28, 28, 28);      // Negro carbón
    private static final Color COLOR_SUPERFICIE_2 = new Color(38, 38, 38);      // Negro grafito
    private static final Color COLOR_SUPERFICIE_3 = new Color(48, 48, 48);      // Negro pizarra
    private static final Color COLOR_CARD_BACKGROUND = new Color(33, 33, 33);   // Negro tarjeta

    // Textos - Jerarquía Visual Clara en Tema Oscuro
    private static final Color COLOR_TEXTO_PRIMARIO = new Color(255, 255, 255); // Blanco puro
    private static final Color COLOR_TEXTO_SECUNDARIO = new Color(189, 189, 189); // Gris claro
    private static final Color COLOR_TEXTO_MUTED = new Color(117, 117, 117);    // Gris medio
    private static final Color COLOR_TEXTO_DARK = new Color(255, 255, 255);     // Blanco para contraste
    private static final Color COLOR_PLACEHOLDER = new Color(117, 117, 117);   // Gris placeholder

    // Estados y Feedback
    private static final Color COLOR_SUCCESS = new Color(76, 175, 80);          // Verde elegante
    private static final Color COLOR_WARNING = new Color(255, 152, 0);          // Naranja premium
    private static final Color COLOR_ERROR = new Color(244, 67, 54);            // Rojo elegante
    private static final Color COLOR_INFO = new Color(33, 150, 243);            // Azul información

    // Bordes y Divisores
    private static final Color COLOR_BORDER = new Color(66, 66, 66);            // Borde oscuro
    private static final Color COLOR_BORDER_LIGHT = new Color(55, 55, 55);      // Borde sutil
    private static final Color COLOR_DIVIDER = new Color(44, 44, 44);           // Divisor oscuro

    // =================================================================================
    // Tipografía Elegante
    // =================================================================================
    private static final String FONT_FAMILY = "Segoe UI";
    private static final Font FONT_DISPLAY = new Font(FONT_FAMILY, Font.BOLD, 32);
    private static final Font FONT_HEADING_1 = new Font(FONT_FAMILY, Font.BOLD, 24);
    private static final Font FONT_HEADING_2 = new Font(FONT_FAMILY, Font.BOLD, 20);
    private static final Font FONT_HEADING_3 = new Font(FONT_FAMILY, Font.BOLD, 18);
    private static final Font FONT_BODY_LARGE = new Font(FONT_FAMILY, Font.PLAIN, 16);
    private static final Font FONT_BODY = new Font(FONT_FAMILY, Font.PLAIN, 14);
    private static final Font FONT_BODY_SMALL = new Font(FONT_FAMILY, Font.PLAIN, 12);
    private static final Font FONT_BUTTON = new Font(FONT_FAMILY, Font.BOLD, 14);
    private static final Font FONT_CAPTION = new Font(FONT_FAMILY, Font.PLAIN, 11);

    // =================================================================================
    // Espaciado y Dimensiones
    // =================================================================================
    private static final int SPACING_XS = 4;
    private static final int SPACING_SM = 8;
    private static final int SPACING_MD = 16;
    private static final int SPACING_LG = 24;
    private static final int SPACING_XL = 32;
    private static final int SPACING_2XL = 48;

    private static final int BORDER_RADIUS_SM = 6;
    private static final int BORDER_RADIUS_MD = 8;
    private static final int BORDER_RADIUS_LG = 12;
    private static final int BORDER_RADIUS_XL = 16;

    // =================================================================================
    // Miembros de la Clase
    // =================================================================================
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton currentSelectedButton;
    private ClientForm clientForm;
    private ServiceForm serviceForm;
    private ProductForm productForm;

    public MainMenu() {
        configurarVentana();
        aplicarLookAndFeelModerno();
        mostrarPantallaCarga();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión Premium - Vulcanizadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setMinimumSize(new Dimension(1200, 800));
        setLocationRelativeTo(null);

        // Configurar icono de la aplicación
        try {
            setIconImage(createAppIcon());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono de la aplicación");
        }
    }

    private Image createAppIcon() {
        int size = 32;
        Image icon = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) icon.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo circular negro con borde dorado
        g2d.setColor(COLOR_FONDO_APP);
        g2d.fillOval(2, 2, size - 4, size - 4);
        g2d.setColor(COLOR_PRIMARIO);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(2, 2, size - 4, size - 4);

        // Letra "V" estilizada en dorado
        g2d.setColor(COLOR_PRIMARIO);
        g2d.setFont(new Font(FONT_FAMILY, Font.BOLD, 18));
        FontMetrics fm = g2d.getFontMetrics();
        int x = (size - fm.stringWidth("V")) / 2;
        int y = (size - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString("V", x, y);

        g2d.dispose();
        return icon;
    }

    private void aplicarLookAndFeelModerno() {
        try {
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Personalizar colores globales para tema oscuro
            UIManager.put("Panel.background", COLOR_FONDO_APP);
            UIManager.put("OptionPane.background", COLOR_SUPERFICIE_1);
            UIManager.put("OptionPane.messageForeground", COLOR_TEXTO_PRIMARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =================================================================================
    // FLUJO DE LA APLICACIÓN: Carga -> Login -> Menú Principal
    // =================================================================================

    private void mostrarPantallaCarga() {
        JPanel panelCarga = new JPanel(new GridBagLayout());
        panelCarga.setBackground(COLOR_FONDO_APP);

        LuxuryLoadingSpinner spinner = new LuxuryLoadingSpinner();
        spinner.setPreferredSize(new Dimension(80, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        panelCarga.add(spinner, gbc);

        JLabel lblCargando = new JLabel("Inicializando Sistema Premium...");
        lblCargando.setFont(FONT_BODY_LARGE);
        lblCargando.setForeground(COLOR_PRIMARIO);
        gbc.gridy = 1;
        gbc.insets = new Insets(SPACING_LG, 0, 0, 0);
        panelCarga.add(lblCargando, gbc);

        // Barra de progreso elegante
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(300, 4));
        progressBar.setBackground(COLOR_SUPERFICIE_2);
        progressBar.setForeground(COLOR_PRIMARIO);
        progressBar.setBorderPainted(false);
        gbc.gridy = 2;
        gbc.insets = new Insets(SPACING_MD, 0, 0, 0);
        panelCarga.add(progressBar, gbc);

        setContentPane(panelCarga);
        revalidate();

        Timer timer = new Timer(3000, e -> {
            mostrarLogin();
            ((Timer) e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void mostrarLogin() {
        // Panel principal con degradado negro lujoso
        JPanel backgroundPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();

                // Degradado negro elegante con toques dorados
                Color color1 = new Color(10, 10, 10);    // Negro profundo
                Color color2 = new Color(25, 25, 25);    // Negro carbón
                Color color3 = new Color(40, 35, 20);    // Negro con toque dorado

                // Crear degradado radial desde el centro
                RadialGradientPaint rgp = new RadialGradientPaint(
                        w/2f, h/2f, Math.max(w, h)/2f,
                        new float[]{0f, 0.7f, 1f},
                        new Color[]{color3, color2, color1}
                );
                g2d.setPaint(rgp);
                g2d.fillRect(0, 0, w, h);

                // Añadir patrón sutil de puntos dorados
                g2d.setColor(new Color(COLOR_PRIMARIO.getRed(), COLOR_PRIMARIO.getGreen(), COLOR_PRIMARIO.getBlue(), 20));
                for (int i = 0; i < w; i += 100) {
                    for (int j = 0; j < h; j += 100) {
                        g2d.fillOval(i, j, 2, 2);
                    }
                }
            }
        };

        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setOpaque(false);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(COLOR_SUPERFICIE_1);
        formPanel.setBorder(new LuxuryLoginCardBorder());
        formPanel.setMaximumSize(new Dimension(480, 600));
        formPanel.setPreferredSize(new Dimension(480, 600));
        formPanel.setMinimumSize(new Dimension(480, 600));

        // Header premium con degradado dorado
        JPanel headerPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();

                // Degradado dorado elegante
                GradientPaint gp = new GradientPaint(0, 0, COLOR_PRIMARIO, w, h, COLOR_PRIMARIO_HOVER);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, w, h + BORDER_RADIUS_LG, BORDER_RADIUS_LG, BORDER_RADIUS_LG);

                // Añadir brillo sutil
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillRoundRect(0, 0, w, h/3, BORDER_RADIUS_LG, BORDER_RADIUS_LG);
            }
        };
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(480, 180));
        headerPanel.setMaximumSize(new Dimension(480, 180));

        // Icono de la aplicación premium
        JLabel lblAppIcon = new JLabel();
        ImageIcon appIcon = loadImageIcon("img/app_icon.png", 70, 70);
        if (appIcon != null) {
            lblAppIcon.setIcon(appIcon);
        } else {
            lblAppIcon.setText("💎");
            lblAppIcon.setFont(new Font(FONT_FAMILY, Font.BOLD, 48));
            lblAppIcon.setForeground(COLOR_FONDO_APP);
        }
        lblAppIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbcHeader = new GridBagConstraints();
        gbcHeader.gridx = 0;
        gbcHeader.gridy = 0;
        gbcHeader.insets = new Insets(SPACING_LG, 0, SPACING_SM, 0);
        headerPanel.add(lblAppIcon, gbcHeader);

        JLabel lblTitulo = new JLabel("VULCANIZADORA PREMIUM", SwingConstants.CENTER);
        lblTitulo.setFont(FONT_HEADING_1);
        lblTitulo.setForeground(COLOR_FONDO_APP);
        gbcHeader.gridy = 1;
        gbcHeader.insets = new Insets(0, 0, SPACING_XS, 0);
        headerPanel.add(lblTitulo, gbcHeader);

        JLabel lblSubtitulo = new JLabel("Sistema de Gestión Empresarial", SwingConstants.CENTER);
        lblSubtitulo.setFont(FONT_BODY);
        lblSubtitulo.setForeground(new Color(COLOR_FONDO_APP.getRed(), COLOR_FONDO_APP.getGreen(), COLOR_FONDO_APP.getBlue(), 180));
        gbcHeader.gridy = 2;
        gbcHeader.insets = new Insets(0, 0, SPACING_LG, 0);
        headerPanel.add(lblSubtitulo, gbcHeader);

        // Campos de entrada premium
        JTextField txtUsuario = UIFactory.createLuxuryTextField();
        JPasswordField pwdContraseña = UIFactory.createLuxuryPasswordField();
        UIFactory.addLuxuryPlaceholder(txtUsuario, "Nombre de Usuario");
        UIFactory.addLuxuryPlaceholder(pwdContraseña, "Contraseña");

        // Botón principal premium
        JButton btnIniciar = UIFactory.createLuxuryPrimaryButton("ACCEDER AL SISTEMA");
        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label de error premium
        JLabel lblError = new JLabel(" ");
        lblError.setForeground(COLOR_ERROR);
        lblError.setFont(FONT_BODY_SMALL);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label de bienvenida
        JLabel lblBienvenida = new JLabel(" ");
        lblBienvenida.setForeground(COLOR_SUCCESS);
        lblBienvenida.setFont(FONT_BODY);
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ensamblar el formulario
        formPanel.add(headerPanel);
        formPanel.add(Box.createVerticalStrut(SPACING_XL));
        formPanel.add(txtUsuario);
        formPanel.add(Box.createVerticalStrut(SPACING_MD));
        formPanel.add(pwdContraseña);
        formPanel.add(Box.createVerticalStrut(SPACING_SM));
        formPanel.add(lblError);
        formPanel.add(lblBienvenida);
        formPanel.add(Box.createVerticalStrut(SPACING_LG));
        formPanel.add(btnIniciar);
        formPanel.add(Box.createVerticalStrut(SPACING_XL));

        // Información de usuarios disponibles
        JLabel lblInfo = new JLabel("<html><center><b>Usuarios Disponibles:</b><br>Anghelo, Jose, Luciana<br><i>Contraseña: admin</i></center></html>", SwingConstants.CENTER);
        lblInfo.setFont(FONT_CAPTION);
        lblInfo.setForeground(COLOR_TEXTO_MUTED);
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(lblInfo);

        btnIniciar.addActionListener(e -> {
            String usuario = txtUsuario.getText().trim();
            String contraseña = new String(pwdContraseña.getPassword());

            lblError.setText(" ");
            lblBienvenida.setText(" ");

            if (usuario.isEmpty() || contraseña.isEmpty() ||
                    usuario.equals("Nombre de Usuario") || contraseña.equals("Contraseña")) {
                lblError.setText("Por favor, ingrese sus credenciales.");
                return;
            }

            // Verificar credenciales
            UserInfo userInfo = USUARIOS.get(usuario);
            if (userInfo != null && userInfo.password.equals(contraseña)) {
                currentUserFullName = userInfo.fullName;
                lblBienvenida.setText("¡Bienvenido " + userInfo.fullName + "!");

                // Pequeña pausa para mostrar el mensaje de bienvenida
                Timer welcomeTimer = new Timer(1500, evt -> {
                    mostrarMenuPrincipal();
                    ((Timer) evt.getSource()).stop();
                });
                welcomeTimer.setRepeats(false);
                welcomeTimer.start();
            } else {
                lblError.setText("Usuario o contraseña incorrectos.");
            }
        });

        backgroundPanel.add(formPanel, new GridBagConstraints());
        setContentPane(backgroundPanel);
        revalidate();
        repaint();
    }

    private void mostrarMenuPrincipal() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO_APP);

        // Sidebar premium
        JPanel sidebarPanel = crearSidebarLujoso();
        sidebarPanel.setPreferredSize(new Dimension(300, getHeight()));

        // Área de contenido
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(COLOR_FONDO_APP);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(SPACING_LG, SPACING_LG, SPACING_LG, SPACING_LG));

        // Inicializar los formularios
        try {
            clientForm = new ClientForm(new ClientController());
            serviceForm = new ServiceForm();
            productForm = new ProductForm();

            contentPanel.add(clientForm, "CLIENTES");
            contentPanel.add(serviceForm, "SERVICIOS");
            contentPanel.add(productForm, "PRODUCTOS");
        } catch (Exception e) {
            // Si no existen las clases, crear paneles placeholder
            contentPanel.add(createLuxuryPlaceholderPanel("Gestión de Clientes", "👥"), "CLIENTES");
            contentPanel.add(createLuxuryPlaceholderPanel("Gestión de Servicios", "🔧"), "SERVICIOS");
            contentPanel.add(createLuxuryPlaceholderPanel("Gestión de Productos", "📦"), "PRODUCTOS");
        }

        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Mostrar vista inicial
        cardLayout.show(MainMenu.this.contentPanel, "CLIENTES");

        revalidate();
        repaint();
    }

    // =================================================================================
    // COMPONENTES DEL SIDEBAR LUJOSO
    // =================================================================================

    private JPanel crearSidebarLujoso() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(COLOR_SUPERFICIE_1);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_BORDER));

        // Header premium del sidebar
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();

                // Degradado dorado sutil
                GradientPaint gp = new GradientPaint(0, 0, COLOR_SUPERFICIE_1, 0, h, COLOR_SUPERFICIE_2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);

                // Línea dorada en la parte inferior
                g2d.setColor(COLOR_PRIMARIO);
                g2d.fillRect(0, h-2, w, 2);
            }
        };
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(SPACING_LG, SPACING_LG, SPACING_LG, SPACING_LG));

        JLabel lblLogo = new JLabel("💎 VULCANIZADORA", SwingConstants.LEFT);
        lblLogo.setFont(FONT_HEADING_2);
        lblLogo.setForeground(COLOR_PRIMARIO);

        JLabel lblUser = new JLabel("Usuario: " + currentUserFullName, SwingConstants.LEFT);
        lblUser.setFont(FONT_CAPTION);
        lblUser.setForeground(COLOR_TEXTO_SECUNDARIO);

        JLabel lblVersion = new JLabel("Premium v2.0", SwingConstants.LEFT);
        lblVersion.setFont(FONT_CAPTION);
        lblVersion.setForeground(COLOR_TEXTO_MUTED);

        JPanel userInfoPanel = new JPanel(new BorderLayout());
        userInfoPanel.setOpaque(false);
        userInfoPanel.add(lblUser, BorderLayout.NORTH);
        userInfoPanel.add(lblVersion, BorderLayout.SOUTH);

        headerPanel.add(lblLogo, BorderLayout.NORTH);
        headerPanel.add(userInfoPanel, BorderLayout.SOUTH);

        // Navegación principal
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(COLOR_SUPERFICIE_1);
        navPanel.setBorder(BorderFactory.createEmptyBorder(0, SPACING_MD, SPACING_LG, SPACING_MD));

        JLabel lblSeccionPrincipal = new JLabel("MÓDULOS PRINCIPALES");
        lblSeccionPrincipal.setFont(FONT_CAPTION);
        lblSeccionPrincipal.setForeground(COLOR_PRIMARIO);
        lblSeccionPrincipal.setBorder(BorderFactory.createEmptyBorder(SPACING_MD, SPACING_SM, SPACING_SM, 0));
        navPanel.add(lblSeccionPrincipal);

        JButton btnClientes = createLuxuryNavButton(loadImageIcon("img/cliente.png", 20, 20), "👥", "Gestión de Clientes", "CLIENTES");
        JButton btnServicios = createLuxuryNavButton(loadImageIcon("img/servicio.png", 20, 20), "🔧", "Gestión de Servicios", "SERVICIOS");
        JButton btnProductos = createLuxuryNavButton(loadImageIcon("img/product.png", 20, 20), "📦", "Gestión de Productos", "PRODUCTOS");

        navPanel.add(btnClientes);
        navPanel.add(Box.createVerticalStrut(SPACING_XS));
        navPanel.add(btnServicios);
        navPanel.add(Box.createVerticalStrut(SPACING_XS));
        navPanel.add(btnProductos);

        navPanel.add(Box.createVerticalGlue());

        // Botón de Cerrar Sesión premium
        JButton btnCerrarSesion = createLuxuryNavButton(loadImageIcon("img/cerrar.png", 20, 20), "🚪", "Cerrar Sesión", "CERRAR_SESION");
        navPanel.add(Box.createVerticalStrut(SPACING_LG));
        navPanel.add(btnCerrarSesion);
        navPanel.add(Box.createVerticalStrut(SPACING_MD));

        sidebar.add(headerPanel, BorderLayout.NORTH);
        sidebar.add(navPanel, BorderLayout.CENTER);

        // Funcionalidad del botón de cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que quieres cerrar la sesión actual?",
                    "Cerrar Sesión",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (dialogResult == JOptionPane.YES_OPTION) {
                currentUserFullName = "";
                mostrarLogin();
            }
        });

        return sidebar;
    }

    private JButton createLuxuryNavButton(ImageIcon customIcon, String defaultIcon, String text, String actionCommand) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout(SPACING_SM, 0));
        button.setActionCommand(actionCommand);
        button.setBackground(COLOR_SUPERFICIE_1);
        button.setForeground(COLOR_TEXTO_SECUNDARIO);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(270, 50));
        button.setMaximumSize(new Dimension(270, 50));

        JPanel contentPanelInterno = new JPanel(new BorderLayout(SPACING_SM, 0));
        contentPanelInterno.setOpaque(false);
        contentPanelInterno.setBorder(BorderFactory.createEmptyBorder(SPACING_SM, SPACING_MD, SPACING_SM, SPACING_MD));

        // Usar icono personalizado o emoji por defecto
        JLabel iconLabel = new JLabel(customIcon != null ? "" : defaultIcon);
        if (customIcon != null) {
            iconLabel.setIcon(customIcon);
        } else {
            iconLabel.setFont(FONT_BODY_LARGE);
        }

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(FONT_BODY);

        contentPanelInterno.add(iconLabel, BorderLayout.WEST);
        contentPanelInterno.add(textLabel, BorderLayout.CENTER);

        button.add(contentPanelInterno, BorderLayout.CENTER);

        // Bordes premium
        Border defaultBorder = new LuxuryButtonBorder(COLOR_SUPERFICIE_1, false);
        Border selectedBorder = new LuxuryButtonBorder(COLOR_SUPERFICIE_2, true);
        button.setBorder(defaultBorder);

        // Establecer botón inicial seleccionado
        if (actionCommand.equals("CLIENTES")) {
            button.setBackground(COLOR_SUPERFICIE_2);
            button.setBorder(selectedBorder);
            textLabel.setForeground(COLOR_PRIMARIO);
            currentSelectedButton = button;
        } else {
            textLabel.setForeground(COLOR_TEXTO_SECUNDARIO);
        }

        button.addActionListener(e -> {
            if (!e.getActionCommand().equals("CERRAR_SESION")) {
                cardLayout.show(MainMenu.this.contentPanel, e.getActionCommand());

                if (currentSelectedButton != null && !currentSelectedButton.getActionCommand().equals("CERRAR_SESION")) {
                    currentSelectedButton.setBackground(COLOR_SUPERFICIE_1);
                    currentSelectedButton.setBorder(defaultBorder);
                    Component[] comps = ((JPanel) currentSelectedButton.getComponent(0)).getComponents();
                    for (Component comp : comps) {
                        if (comp instanceof JLabel && ((JLabel) comp).getIcon() == null) {
                            ((JLabel) comp).setForeground(COLOR_TEXTO_SECUNDARIO);
                            break;
                        }
                    }
                }

                currentSelectedButton = (JButton) e.getSource();
                currentSelectedButton.setBackground(COLOR_SUPERFICIE_2);
                currentSelectedButton.setBorder(selectedBorder);
                Component[] comps = ((JPanel) currentSelectedButton.getComponent(0)).getComponents();
                for (Component comp : comps) {
                    if (comp instanceof JLabel && ((JLabel) comp).getIcon() == null) {
                        ((JLabel) comp).setForeground(COLOR_PRIMARIO);
                        break;
                    }
                }
            }
        });

        // Efectos de hover premium
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (button != currentSelectedButton) {
                    button.setBackground(COLOR_SUPERFICIE_2);
                    Component[] comps = ((JPanel) button.getComponent(0)).getComponents();
                    for (Component comp : comps) {
                        if (comp instanceof JLabel && ((JLabel) comp).getIcon() == null) {
                            ((JLabel) comp).setForeground(COLOR_PRIMARIO);
                            break;
                        }
                    }
                }
            }
            public void mouseExited(MouseEvent e) {
                if (button != currentSelectedButton) {
                    button.setBackground(COLOR_SUPERFICIE_1);
                    Component[] comps = ((JPanel) button.getComponent(0)).getComponents();
                    for (Component comp : comps) {
                        if (comp instanceof JLabel && ((JLabel) comp).getIcon() == null) {
                            ((JLabel) comp).setForeground(COLOR_TEXTO_SECUNDARIO);
                            break;
                        }
                    }
                }
            }
        });

        return button;
    }

    private JPanel createLuxuryPlaceholderPanel(String title, String icon) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_SUPERFICIE_1);
        panel.setBorder(new LuxuryCardBorder());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_SUPERFICIE_2);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(SPACING_LG, SPACING_LG, SPACING_LG, SPACING_LG));

        JLabel lblTitle = new JLabel(icon + " " + title);
        lblTitle.setFont(FONT_HEADING_1);
        lblTitle.setForeground(COLOR_PRIMARIO);

        headerPanel.add(lblTitle, BorderLayout.CENTER);

        JPanel contentArea = new JPanel(new GridBagLayout());
        contentArea.setBackground(COLOR_SUPERFICIE_1);

        JLabel placeholderIcon = new JLabel("🏗️");
        placeholderIcon.setFont(new Font(FONT_FAMILY, Font.PLAIN, 64));

        JLabel placeholderTitle = new JLabel("Módulo en Desarrollo");
        placeholderTitle.setFont(FONT_HEADING_2);
        placeholderTitle.setForeground(COLOR_TEXTO_PRIMARIO);

        JLabel placeholderText = new JLabel("<html><center>Este módulo premium estará disponible próximamente.<br>Funcionalidad completa de " + title.toLowerCase() + ".</center></html>");
        placeholderText.setFont(FONT_BODY);
        placeholderText.setForeground(COLOR_TEXTO_SECUNDARIO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(SPACING_MD, 0, SPACING_SM, 0);

        contentArea.add(placeholderIcon, gbc);
        contentArea.add(placeholderTitle, gbc);
        gbc.insets = new Insets(SPACING_SM, 0, 0, 0);
        contentArea.add(placeholderText, gbc);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentArea, BorderLayout.CENTER);

        return panel;
    }

    private ImageIcon loadImageIcon(String path, int width, int height) {
        URL imgURL = getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL);
            Image image = originalIcon.getImage();
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("No se pudo encontrar el archivo de icono: " + path);
            return null;
        }
    }

    // =================================================================================
    // CLASES AUXILIARES PARA UI LUJOSA
    // =================================================================================

    static class LuxuryLoadingSpinner extends JPanel {
        private int angle = 0;
        private Timer timer;

        public LuxuryLoadingSpinner() {
            setOpaque(false);
            timer = new Timer(16, e -> {
                angle = (angle + 4) % 360;
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight()) - 8;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            // Círculo de fondo oscuro
            g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(COLOR_SUPERFICIE_2);
            g2d.drawOval(x, y, size, size);

            // Arco animado dorado
            g2d.setColor(COLOR_PRIMARIO);
            g2d.drawArc(x, y, size, size, angle, 120);

            // Brillo interior
            g2d.setColor(new Color(COLOR_PRIMARIO.getRed(), COLOR_PRIMARIO.getGreen(), COLOR_PRIMARIO.getBlue(), 50));
            g2d.drawArc(x + 2, y + 2, size - 4, size - 4, angle + 10, 100);

            g2d.dispose();
        }

        public void stopAnimation() {
            if (timer != null) {
                timer.stop();
            }
        }
    }

    static class LuxuryLoginCardBorder implements Border {
        private int shadowSize = 12;
        private Color shadowColor = new Color(0, 0, 0, 80);
        private int borderRadius = BORDER_RADIUS_XL;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            // Sombra profunda
            for (int i = 0; i < shadowSize; i++) {
                g2d.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(),
                        shadowColor.getAlpha() / (shadowSize - i + 1)));
                g2d.drawRoundRect(x + i, y + i, w - (i * 2) - 1, h - (i * 2) - 1,
                        borderRadius, borderRadius);
            }

            // Borde dorado sutil
            g2d.setColor(new Color(COLOR_PRIMARIO.getRed(), COLOR_PRIMARIO.getGreen(), COLOR_PRIMARIO.getBlue(), 100));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRoundRect(x, y, w - 1, h - 1, borderRadius, borderRadius);

            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(shadowSize + 2, shadowSize + 2, shadowSize + 2, shadowSize + 2);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    static class LuxuryCardBorder implements Border {
        private int shadowSize = 6;
        private Color shadowColor = new Color(0, 0, 0, 40);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Sombra elegante
            for (int i = 0; i < shadowSize; i++) {
                g2d.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(),
                        shadowColor.getAlpha() / (i + 1)));
                g2d.drawRoundRect(x + i, y + i, w - (i * 2) - 1, h - (i * 2) - 1,
                        BORDER_RADIUS_MD, BORDER_RADIUS_MD);
            }

            // Borde dorado
            g2d.setColor(COLOR_BORDER);
            g2d.drawRoundRect(x, y, w - 1, h - 1, BORDER_RADIUS_MD, BORDER_RADIUS_MD);

            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(shadowSize + 1, shadowSize + 1, shadowSize + 1, shadowSize + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    static class LuxuryButtonBorder implements Border {
        private Color backgroundColor;
        private boolean isSelected;

        public LuxuryButtonBorder(Color backgroundColor, boolean isSelected) {
            this.backgroundColor = backgroundColor;
            this.isSelected = isSelected;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isSelected) {
                // Borde dorado de selección
                g2d.setColor(COLOR_PRIMARIO);
                g2d.fillRoundRect(x, y, 4, h, 4, 4);

                // Brillo sutil en el fondo
                g2d.setColor(new Color(COLOR_PRIMARIO.getRed(), COLOR_PRIMARIO.getGreen(), COLOR_PRIMARIO.getBlue(), 20));
                g2d.fillRoundRect(x + 4, y, w - 4, h, BORDER_RADIUS_SM, BORDER_RADIUS_SM);
            }

            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(SPACING_XS, SPACING_SM, SPACING_XS, SPACING_SM);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    static class UIFactory {
        public static JTextField createLuxuryTextField() {
            JTextField tf = new JTextField();
            tf.setFont(FONT_BODY);
            tf.setBackground(COLOR_SUPERFICIE_2);
            tf.setForeground(COLOR_TEXTO_PRIMARIO);
            tf.setCaretColor(COLOR_PRIMARIO);
            tf.setBorder(BorderFactory.createCompoundBorder(
                    new LuxuryRoundedBorder(BORDER_RADIUS_MD, COLOR_BORDER),
                    BorderFactory.createEmptyBorder(SPACING_MD, SPACING_MD, SPACING_MD, SPACING_MD)
            ));
            tf.setMaximumSize(new Dimension(380, 52));
            tf.setPreferredSize(new Dimension(380, 52));
            tf.setAlignmentX(Component.CENTER_ALIGNMENT);
            addLuxuryFocusEffect(tf);
            return tf;
        }

        public static JPasswordField createLuxuryPasswordField() {
            JPasswordField pf = new JPasswordField();
            pf.setFont(FONT_BODY);
            pf.setBackground(COLOR_SUPERFICIE_2);
            pf.setForeground(COLOR_TEXTO_PRIMARIO);
            pf.setCaretColor(COLOR_PRIMARIO);
            pf.setBorder(BorderFactory.createCompoundBorder(
                    new LuxuryRoundedBorder(BORDER_RADIUS_MD, COLOR_BORDER),
                    BorderFactory.createEmptyBorder(SPACING_MD, SPACING_MD, SPACING_MD, SPACING_MD)
            ));
            pf.setMaximumSize(new Dimension(380, 52));
            pf.setPreferredSize(new Dimension(380, 52));
            pf.setAlignmentX(Component.CENTER_ALIGNMENT);
            addLuxuryFocusEffect(pf);
            return pf;
        }

        public static void addLuxuryPlaceholder(JTextField field, String placeholder) {
            field.setText(placeholder);
            field.setForeground(COLOR_PLACEHOLDER);
            if (field instanceof JPasswordField) ((JPasswordField) field).setEchoChar((char) 0);

            field.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (field.getText().equals(placeholder)) {
                        field.setText("");
                        field.setForeground(COLOR_TEXTO_PRIMARIO);
                        if (field instanceof JPasswordField) ((JPasswordField) field).setEchoChar('●');
                    }
                }
                public void focusLost(FocusEvent e) {
                    if (field.getText().isEmpty()) {
                        field.setForeground(COLOR_PLACEHOLDER);
                        field.setText(placeholder);
                        if (field instanceof JPasswordField) ((JPasswordField) field).setEchoChar((char) 0);
                    }
                }
            });
        }

        private static void addLuxuryFocusEffect(JComponent component) {
            Border defaultBorder = new LuxuryRoundedBorder(BORDER_RADIUS_MD, COLOR_BORDER);
            Border focusedBorder = new LuxuryRoundedBorder(BORDER_RADIUS_MD, COLOR_PRIMARIO);

            component.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    component.setBorder(BorderFactory.createCompoundBorder(
                            focusedBorder,
                            BorderFactory.createEmptyBorder(SPACING_MD, SPACING_MD, SPACING_MD, SPACING_MD)
                    ));
                }
                public void focusLost(FocusEvent e) {
                    component.setBorder(BorderFactory.createCompoundBorder(
                            defaultBorder,
                            BorderFactory.createEmptyBorder(SPACING_MD, SPACING_MD, SPACING_MD, SPACING_MD)
                    ));
                }
            });
        }

        public static JButton createLuxuryPrimaryButton(String text) {
            JButton button = new JButton(text) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Degradado dorado
                    GradientPaint gp = new GradientPaint(0, 0, COLOR_PRIMARIO, 0, getHeight(), COLOR_PRIMARIO_HOVER);
                    g2d.setPaint(gp);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), BORDER_RADIUS_MD, BORDER_RADIUS_MD);

                    // Brillo superior
                    g2d.setColor(new Color(255, 255, 255, 30));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight()/3, BORDER_RADIUS_MD, BORDER_RADIUS_MD);

                    g2d.dispose();
                    super.paintComponent(g);
                }
            };

            button.setFont(FONT_BUTTON);
            button.setForeground(COLOR_FONDO_APP);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setMaximumSize(new Dimension(380, 52));
            button.setPreferredSize(new Dimension(380, 52));

            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(COLOR_PRIMARIO_HOVER);
                }
                public void mouseExited(MouseEvent e) {
                    button.setBackground(COLOR_PRIMARIO);
                }
                public void mousePressed(MouseEvent e) {
                    button.setBackground(COLOR_PRIMARIO_HOVER.darker());
                }
                public void mouseReleased(MouseEvent e) {
                    button.setBackground(COLOR_PRIMARIO_HOVER);
                }
            });

            return button;
        }
    }

    static class LuxuryRoundedBorder implements Border {
        private int radius;
        private Color color;

        public LuxuryRoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 2, 2, 2);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}