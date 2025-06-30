package service;

import model.Client;
import model.ClientDatabaseDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClientService {
    private final ClientDatabaseDAO clientDatabaseDAO;
    private final SimpleDateFormat dateFormatter;

    public ClientService() {
        this.clientDatabaseDAO = new ClientDatabaseDAO();
        this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    public List<Client> obtenerTodosLosClientes() {
        return clientDatabaseDAO.leerClientesDesdeDB(); // solo activos
    }

    public List<Client> obtenerTodosLosClientesConEstado() {
        return clientDatabaseDAO.leerTodosLosClientesDesdeDB(); // todos
    }

    public void registrarNuevoCliente(String nombre, String apellido, String telefono, String correo,
                                      String direccion, Date fechaNacimiento, String sexo, String tipoDocumento,
                                      String numeroDocumento, boolean estado, boolean recibirNotificaciones) {
        Client client = new Client(0, nombre, apellido, telefono, correo, direccion,
                fechaNacimiento, sexo, tipoDocumento, numeroDocumento, estado);
        client.setRecibirNotificaciones(recibirNotificaciones);
        clientDatabaseDAO.registrarCliente(client);
    }

    public void actualizarClienteExistente(int idClient, String nombre, String apellido, String telefono,
                                           String correo, String direccion, String fechaNacimiento, String sexo,
                                           String tipoDocumento, String numeroDocumento, boolean estado,
                                           boolean recibirNotificaciones) {
        try {
            Date fecha = fechaNacimiento != null ? dateFormatter.parse(fechaNacimiento) : null;
            Client client = new Client(idClient, nombre, apellido, telefono, correo,
                    direccion, fecha, sexo, tipoDocumento, numeroDocumento, estado);
            client.setRecibirNotificaciones(recibirNotificaciones);
            clientDatabaseDAO.actualizarCliente(client);
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear fecha: " + e.getMessage());
        }
    }

    public void eliminarClienteLogicoPorId(int idClient) {
        clientDatabaseDAO.eliminarClienteLogico(idClient);
    }

    public Client activarClienteLogicoPorId(int idClient) {
        clientDatabaseDAO.activarClienteLogico(idClient);
        return buscarClientePorId(idClient);
    }

    public Client buscarClientePorId(int idClient) {
        return clientDatabaseDAO.buscarClientePorId(idClient);
    }
}
