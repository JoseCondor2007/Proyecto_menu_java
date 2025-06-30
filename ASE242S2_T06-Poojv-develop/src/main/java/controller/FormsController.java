package controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import org.jdatepicker.impl.JDatePickerImpl;
import model.Servicio;
import model.ServicioDAO; // Importa ServicioDAO
import view.ServiceForm;

import java.util.ArrayList;
public class FormsController implements ActionListener, ListSelectionListener {

    private ServiceForm view;
    private List<Servicio> listaServicios;
    private ServicioDAO servicioDAO; // Instancia de ServicioDAO

    public FormsController(ServiceForm view) {
        this.view = view;
        this.listaServicios = new ArrayList<>();
        this.servicioDAO = new ServicioDAO(); // Inicializa ServicioDAO
        this.view.setController(this);
        cargarServiciosDesdeBD(); // Cargar datos al inicio
    }

    private void cargarServiciosDesdeBD() {
        try {
            listaServicios.clear();
            listaServicios.addAll(servicioDAO.listarRegistros());
            actualizarTabla();
        } catch (SQLException e) {
            System.err.println("Error al cargar servicios: " + e.getMessage());
            JOptionPane.showMessageDialog(view, "Error al cargar los servicios desde la base de datos.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnRegistrar()) {
            registrarServicio();
        } else if (e.getSource() == view.getBtnModificar()) {
            modificarServicio();
        } else if (e.getSource() == view.getBtnEliminar()) {
            eliminarServicio();
        }
    }

    private void registrarServicio() {
        JDatePickerImpl datePicker = view.getDatePicker();
        JComboBox<String> comboServicio = view.getComboServicio();
        JTextField txtComentario = view.getTxtComentario();
        JRadioButton leve = view.getLeve();
        JRadioButton medio = view.getMedio();
        JRadioButton grave = view.getGrave();
        JTextField txtPrecio = view.getTxtPrecio();

        // Obtener la fecha seleccionada del datePicker
        java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(view, "Por favor, seleccione una fecha válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Convertir a formato yyyy-MM-dd
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(selectedDate);

        String servicio = (String) comboServicio.getSelectedItem();
        String comentario = txtComentario.getText();
        String urgencia = obtenerUrgenciaSeleccionada(leve, medio, grave);
        String precioStr = txtPrecio.getText();

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Por favor, ingrese un precio válido (numérico).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fecha.isEmpty() || servicio.isEmpty() || comentario.isEmpty() || urgencia.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Servicio nuevoServicio = new Servicio(fecha, servicio, comentario, urgencia, precio);
        try {
            servicioDAO.insertar(nuevoServicio);
            cargarServiciosDesdeBD();
            limpiarFormulario();
            JOptionPane.showMessageDialog(view, "Servicio registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.err.println("Error al insertar servicio: " + e.getMessage());
            JOptionPane.showMessageDialog(view, "Error al registrar el servicio.");
        }
    }

    private void modificarServicio() {
        int fila = view.getFilaSeleccionada();
        if (fila == -1) {
            JOptionPane.showMessageDialog(view, "Seleccione un servicio para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Servicio servicioOriginal = listaServicios.get(fila); // Registro original (fecha usada como ID)
        String fechaOriginal = servicioOriginal.getFecha();

        // Obtener los nuevos datos del formulario
        String nuevaFecha = view.getDatePicker().getJFormattedTextField().getText();
        String tipoServicio = (String) view.getComboServicio().getSelectedItem();
        String comentario = view.getTxtComentario().getText();
        String urgencia = obtenerUrgenciaSeleccionada(view.getLeve(), view.getMedio(), view.getGrave());
        String precioStr = view.getTxtPrecio().getText();

        if (nuevaFecha.isEmpty() || tipoServicio.isEmpty() || comentario.isEmpty() || urgencia.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Precio inválido. Ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Servicio servicioModificado = new Servicio(nuevaFecha, tipoServicio, comentario, urgencia, precio);

        try {
            servicioDAO.modificar(fechaOriginal, servicioModificado);
            cargarServiciosDesdeBD();
            limpiarFormulario();
            JOptionPane.showMessageDialog(view, "Servicio modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.err.println("Error al modificar servicio: " + e.getMessage());
            JOptionPane.showMessageDialog(view, "Error al modificar el servicio.");
        }
    }

    private void eliminarServicio() {
        int fila = view.getFilaSeleccionada();
        if (fila == -1) {
            JOptionPane.showMessageDialog(view, "Seleccione un servicio para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Servicio servicio = listaServicios.get(fila);
        String fecha = servicio.getFecha();

        int opcion = JOptionPane.showConfirmDialog(view, "¿Está seguro de que desea eliminar este servicio?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                servicioDAO.eliminar(fecha); // eliminación lógica
                cargarServiciosDesdeBD();
                limpiarFormulario();
                JOptionPane.showMessageDialog(view, "Servicio eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                System.err.println("Error al eliminar servicio: " + e.getMessage());
                JOptionPane.showMessageDialog(view, "Error al eliminar el servicio.");
            }
        }
    }

    private void actualizarTabla() {
        DefaultTableModel modeloTabla = view.getModeloTabla();
        modeloTabla.setRowCount(0);
        for (Servicio servicio : listaServicios) {
            Object[] fila = {
                    servicio.getFecha(),
                    servicio.getTipoServicio(),
                    servicio.getComentario(),
                    servicio.getUrgencia(),
                    servicio.getPrecio()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarFormulario() {
        view.getDatePicker().getJFormattedTextField().setText("");
        view.getComboServicio().setSelectedIndex(0);
        view.getTxtComentario().setText("");
        ButtonGroup urgenciaGroup = new ButtonGroup();
        urgenciaGroup.add(view.getLeve());
        urgenciaGroup.add(view.getMedio());
        urgenciaGroup.add(view.getGrave());
        urgenciaGroup.clearSelection();
        view.getTxtPrecio().setText("");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int filaSeleccionada = view.getTablaDatos().getSelectedRow();
            view.setFilaSeleccionada(filaSeleccionada);
            if (filaSeleccionada != -1) {
                Servicio servicio = listaServicios.get(filaSeleccionada);
                view.getDatePicker().getJFormattedTextField().setText(servicio.getFecha());
                view.getComboServicio().setSelectedItem(servicio.getTipoServicio());
                view.getTxtComentario().setText(servicio.getComentario());
                String urgencia = servicio.getUrgencia();
                view.getLeve().setSelected(urgencia.equals("Leve"));
                view.getMedio().setSelected(urgencia.equals("Medio"));
                view.getGrave().setSelected(urgencia.equals("Grave"));
                view.getTxtPrecio().setText(String.valueOf(servicio.getPrecio()));
                view.getBtnModificar().setEnabled(true);
                view.getBtnEliminar().setEnabled(true);
            } else {
                view.getBtnModificar().setEnabled(false);
                view.getBtnEliminar().setEnabled(false);
            }
        }
    }

    private String obtenerUrgenciaSeleccionada(JRadioButton leve, JRadioButton medio, JRadioButton grave) {
        if (leve.isSelected()) {
            return "Leve";
        } else if (medio.isSelected()) {
            return "Medio";
        } else if (grave.isSelected()) {
            return "Grave";
        }
        return "";
    }
}