package model;


import database.DatabaseConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientDatabaseDAO {


    public ClientDatabaseDAO() {
    }


    public List<Client> leerClientesDesdeDB() {
        List<Client> activos = new ArrayList<>();
        String sql = "SELECT * FROM Cliente WHERE estado = TRUE";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                activos.add(mapRowToClient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activos;
    }


    public List<Client> leerTodosLosClientesDesdeDB() {
        List<Client> todos = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                todos.add(mapRowToClient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }


    public void registrarCliente(Client client) {
        String sql = "INSERT INTO Cliente (nombre, apellido, numeroTelefono, correo, direccion, fechaNacimiento, sexo, tipoDocumento, numeroDocumento, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();


             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, client.getNombre());
            statement.setString(2, client.getApellido());
            statement.setString(3, client.getTelefono());
            statement.setString(4, client.getCorreo());
            statement.setString(5, client.getDireccion());
            if (client.getFecha() != null) {
                statement.setDate(6, new java.sql.Date(client.getFecha().getTime()));
            } else {
                statement.setNull(6, Types.DATE);
            }
            statement.setString(7, client.getSexo());
            statement.setString(8, client.getTipoDocumento());
            statement.setString(9, client.getNumeroDocumento());
            statement.setBoolean(10, client.isEstado());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void actualizarCliente(Client client) {
        String sql = "UPDATE Cliente SET nombre = ?, apellido = ?, numeroTelefono = ?, correo = ?, direccion = ?, fechaNacimiento = ?, sexo = ?, tipoDocumento = ?, numeroDocumento = ?, estado = ? WHERE idCliente = ?";
        try (Connection connection = DatabaseConnection.getConnection();


             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, client.getNombre());
            statement.setString(2, client.getApellido());
            statement.setString(3, client.getTelefono());
            statement.setString(4, client.getCorreo());
            statement.setString(5, client.getDireccion());
            if (client.getFecha() != null) {
                statement.setDate(6, new java.sql.Date(client.getFecha().getTime()));
            } else {
                statement.setNull(6, Types.DATE);
            }
            statement.setString(7, client.getSexo());
            statement.setString(8, client.getTipoDocumento());
            statement.setString(9, client.getNumeroDocumento());
            statement.setBoolean(10, client.isEstado());
            statement.setInt(11, client.getIdCliente());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void eliminarClienteLogico(int idClient) {
        String sql = "UPDATE Cliente SET estado = FALSE WHERE idCliente = ?";
        try(Connection connection = DatabaseConnection.getConnection();


            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idClient);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void activarClienteLogico(int idClient) {
        String sql = "UPDATE Cliente SET estado = TRUE WHERE idCliente = ?";
        try ( Connection connection = DatabaseConnection.getConnection();


              PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idClient);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Client buscarClientePorId(int idClient) {
        String sql = "SELECT * FROM Cliente WHERE idCliente = ?";
        try( Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idClient);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return mapRowToClient(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Client mapRowToClient(ResultSet rs) throws SQLException {
        int idCliente = rs.getInt("idCliente");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String numeroTelefono = rs.getString("numeroTelefono");
        String correo = rs.getString("correo");
        String direccion = rs.getString("direccion");
        Date fechaNacimiento = rs.getDate("fechaNacimiento");
        String sexo = rs.getString("sexo");
        String tipoDocumento = rs.getString("tipoDocumento");
        String numeroDocumento = rs.getString("numeroDocumento");
        boolean estado = rs.getBoolean("estado");
        return new Client(idCliente, nombre, apellido, numeroTelefono, correo, direccion, fechaNacimiento, sexo, tipoDocumento, numeroDocumento, estado);
    }
}
