package modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ComandoService {

    public Dispositivo escanearIP(String ip) {
        Dispositivo disp = new Dispositivo();
        disp.setIp(ip);

        try {
            // Ejecutamos el comando ping enviando 1 solo paquete (-n 1)
            ProcessBuilder pb = new ProcessBuilder("ping", "-n", "1", ip);
            Process proceso = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            boolean responde = false;
            String tiempo = "N/A";

            while ((linea = reader.readLine()) != null) {
                String lineaLower = linea.toLowerCase();
                // Verificamos si hubo respuesta del equipo
                if (lineaLower.contains("respuesta desde") || lineaLower.contains("reply from")) {
                    responde = true;
                    
                    // Buscamos extraer el tiempo si aparece en la línea
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