

import view.MainMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Ejecutar en el hilo de despacho de eventos de Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Configurar el look and feel del sistema
                setSystemLookAndFeel();

                // Crear y mostrar la ventana principal
                MainMenu mainMenu = new MainMenu();
                mainMenu.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                showErrorDialog("Error al iniciar la aplicación");
            }
        });
    }

    private static void setSystemLookAndFeel() {
        try {
            // Usar el look and feel del sistema operativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Configuraciones adicionales para mejorar la apariencia
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");

        } catch (Exception e) {
            System.err.println("No se pudo configurar el Look and Feel del sistema");
        }
    }

    private static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}