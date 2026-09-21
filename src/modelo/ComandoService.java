package modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// Clase para comunicarme con la consola de comandos de Windows (CMD)
public class ComandoService {

    // Ejecuta el comando ping en la consola
    public Dispositivo escanearIP(String ip, int timeoutMs) {
        Dispositivo disp = new Dispositivo();
        disp.setIp(ip);

        try {
            // Ejecuto 'ping -n 1 -w timeout ip'
            ProcessBuilder pb = new ProcessBuilder("ping", "-n", "1", "-w", String.valueOf(timeoutMs), ip);
            Process proceso = pb.start();

            // Leo lo que me responde la consola
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            boolean responde = false;
            String tiempo = "N/A";

            while ((linea = reader.readLine()) != null) {
                String lineaLower = linea.toLowerCase();
                // Si la respuesta dice esto, el equipo esta activo
                if (lineaLower.contains("respuesta desde") || lineaLower.contains("reply from")) {
                    responde = true;
                    if (lineaLower.contains("tiempo=") || lineaLower.contains("time=")) {
                        tiempo = linea;
                    } else {
                        tiempo = "<1ms";
                    }
                }
            }

            proceso.waitFor();

            if (responde) {
                disp.setConectado(true);
                disp.setTiempoRespuesta(tiempo);
                disp.setNombre(obtenerNombreDNS(ip));
            } else {
                disp.setConectado(false);
                disp.setTiempoRespuesta("Sin respuesta");
                disp.setNombre("Desconocido");
            }

        } catch (Exception e) {
            disp.setConectado(false);
            disp.setTiempoRespuesta("Error");
            disp.setNombre("Error");
        }

        return disp;
    }

    // Usa nslookup en CMD para buscar el nombre del equipo
    private String obtenerNombreDNS(String ip) {
        try {
            ProcessBuilder pb = new ProcessBuilder("nslookup", ip);
            Process proceso = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;

            while ((linea = reader.readLine()) != null) {
                if (linea.contains("Nombre:") || linea.contains("Name:")) {
                    String[] partes = linea.split(":");
                    if (partes.length > 1) {
                        return partes[1].trim();
                    }
                }
            }
        } catch (Exception e) {
            return "Sin Nombre";
        }
        return "Sin Nombre";
    }
}