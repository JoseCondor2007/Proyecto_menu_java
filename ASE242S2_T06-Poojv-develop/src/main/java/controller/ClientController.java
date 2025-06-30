package controller;

import model.Client;
import service.ClientService;

import java.util.Date;
import java.util.List;

public class ClientController {
    private final ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    // Solo clientes activos
    public List<Client> obtenerClientes() {
        return clientService.obtenerTodosLosClientes();
    }

    // Todos los clientes (activos e inactivos)
    public List<Client> obtenerTodosLosClientesConEstado() {
        return clientService.obtenerTodosLosClientesConEstado();
    }

    public void registrarNuevoCliente(String nombre, String apellido, String telefono, String correo,
                                      String direccion, Date fechaNacimiento, String sexo, String tipoDocumento,
                                      String numeroDocumento, boolean recibirNotificaciones) {
        clientService.registrarNuevoCliente(
                nombre, apellido, telefono, correo, direccion,
                fechaNacimiento, sexo, tipoDocumento, numeroDocumento,
                true, recibirNotificaciones
        );
    }

    public void actualizarClienteExistente(int idClient, String nombre, String apellido, String telefono,
                                           String correo, String direccion, String fechaNacimiento, String sexo,
                                           String tipoDocumento, String numeroDocumento, boolean recibirNotificaciones) {
        clientService.actualizarClienteExistente(
                idClient, nombre, apellido, telefono, correo,
                direccion, fechaNacimiento, sexo, tipoDocumento,
                numeroDocumento, true, recibirNotificaciones
        );
    }

    public void eliminarClienteLogicoPorId(int id) {
        clientService.eliminarClienteLogicoPorId(id);
    }

    public Client activarClienteLogicoPorId(int id) {
        return clientService.activarClienteLogicoPorId(id);
    }

    public Client buscarClientePorId(int id) {
        return clientService.buscarClientePorId(id);
    }
}
