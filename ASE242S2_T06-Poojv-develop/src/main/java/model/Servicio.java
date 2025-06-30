package model;


public class Servicio {


    private String fecha;
    private String tipoServicio;
    private String comentario;
    private String urgencia;
    private double precio;
    private boolean estadoVehiculo; // ✅ Campo obligatorio


    // Constructor completo usado al listar servicios
    public Servicio(String fecha, String tipoServicio, String comentario, String urgencia, double precio, boolean estadoVehiculo) {
        this.fecha = fecha;
        this.tipoServicio = tipoServicio;
        this.comentario = comentario;
        this.urgencia = urgencia;
        this.precio = precio;
        this.estadoVehiculo = estadoVehiculo;
    }


    // Constructor básico usado para insertar (si aún no necesitas id u otros campos)
    public Servicio(String fecha, String tipoServicio, String comentario, String urgencia, double precio) {
        this.fecha = fecha;
        this.tipoServicio = tipoServicio;
        this.comentario = comentario;
        this.urgencia = urgencia;
        this.precio = precio;
        // El campo estadoVehiculo debe establecerse posteriormente con su setter
    }


    // Getters y Setters


    public String getFecha() {
        return fecha;
    }


    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    public String getTipoServicio() {
        return tipoServicio;
    }


    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }


    public String getComentario() {
        return comentario;
    }


    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    public String getUrgencia() {
        return urgencia;
    }


    public void setUrgencia(String urgencia) {
        this.urgencia = urgencia;
    }


    public double getPrecio() {
        return precio;
    }


    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public boolean isEstadoVehiculo() {
        return estadoVehiculo;
    }


    public void setEstadoVehiculo(boolean estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }


}
