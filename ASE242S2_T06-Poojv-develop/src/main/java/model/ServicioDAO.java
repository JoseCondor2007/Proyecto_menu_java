package model;


import database.DatabaseConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServicioDAO {


    public List<Servicio> listarRegistros() throws SQLException {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT fecha, tipoServicio, comentario, urgencia, precio, estado_vehiculo FROM servicio WHERE activo = TRUE";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {


            while (rs.next()) {
                Servicio servicio = new Servicio(
                        rs.getString("fecha"),
                        rs.getString("tipoServicio"),
                        rs.getString("comentario"),
                        rs.getString("urgencia"),
                        rs.getDouble("precio"),
                        rs.getBoolean("estado_vehiculo")
                );
                servicios.add(servicio);
            }
        }


        return servicios;
    }


    public void insertar(Servicio servicio) throws SQLException {
        String sql = "INSERT INTO servicio (fecha, tipoServicio, comentario, urgencia, precio, estado_vehiculo, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?, TRUE)";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, servicio.getFecha());
            pstmt.setString(2, servicio.getTipoServicio());
            pstmt.setString(3, servicio.getComentario());
            pstmt.setString(4, servicio.getUrgencia());
            pstmt.setDouble(5, servicio.getPrecio());
            pstmt.setBoolean(6, servicio.isEstadoVehiculo()); // ✅ Campo obligatorio


            pstmt.executeUpdate();
        }
    }


    public void modificar(String fechaOriginal, Servicio servicio) throws SQLException {
        String sql = "UPDATE servicio SET fecha = ?, tipoServicio = ?, comentario = ?, urgencia = ?, precio = ?, estado_vehiculo = ? " +
                "WHERE fecha = ? AND activo = TRUE";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, servicio.getFecha());
            pstmt.setString(2, servicio.getTipoServicio());
            pstmt.setString(3, servicio.getComentario());
            pstmt.setString(4, servicio.getUrgencia());
            pstmt.setDouble(5, servicio.getPrecio());
            pstmt.setBoolean(6, servicio.isEstadoVehiculo());
            pstmt.setString(7, fechaOriginal);


            pstmt.executeUpdate();
        }
    }


    public void eliminar(String fecha) throws SQLException {
        String sql = "UPDATE servicio SET activo = FALSE WHERE fecha = ? AND activo = TRUE";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, fecha);
            pstmt.executeUpdate();
        }
    }
}
