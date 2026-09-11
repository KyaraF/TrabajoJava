package modelo;

public class Dispositivo {

    private String ip;
    private String nombre;
    private boolean conectado;
    private String tiempoRespuesta;

    public Dispositivo() {
    }

    public Dispositivo(String ip, String nombre, boolean conectado, String tiempoRespuesta) {
        this.ip = ip;
        this.nombre = nombre;
        this.conectado = conectado;
        this.tiempoRespuesta = tiempoRespuesta;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public String getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public void setTiempoRespuesta(String tiempoRespuesta) {
        this.tiempoRespuesta = tiempoRespuesta;
    }

    @Override
    public String toString() {
        String estado = conectado ? "CONECTADO" : "NO CONECTADO";
        return "IP: " + ip + " | Nombre: " + nombre + " | Estado: " + estado + " | Tiempo: " + tiempoRespuesta;
    }
}
