package modelo;

import java.util.ArrayList;
import java.util.List;

// Logica principal del escaneo por rangos de IP
public class EscanerRed {

    private ComandoService comandoService;

    public EscanerRed() {
        this.comandoService = new ComandoService();
    }

    // Revisa que la IP tenga el formato correcto x.x.x.x
    public boolean esIpValida(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(regex);
    }

    // Recorre las IPs desde la de inicio hasta la de fin
    public List<Dispositivo> escanearRango(String ipInicio, String ipFin, int timeoutMs) {
        List<Dispositivo> resultados = new ArrayList<>();

        try {
            String[] partesInicio = ipInicio.split("\\.");
            String[] partesFin = ipFin.split("\\.");

            String prefijoRed = partesInicio[0] + "." + partesInicio[1] + "." + partesInicio[2] + ".";
            int hostInicio = Integer.parseInt(partesInicio[3]);
            int hostFin = Integer.parseInt(partesFin[3]);

            if (hostFin < hostInicio) {
                return resultados;
            }

            for (int i = hostInicio; i <= hostFin; i++) {
                String ipActual = prefijoRed + i;
                Dispositivo dispositivo = comandoService.escanearIP(ipActual, timeoutMs);
                resultados.add(dispositivo);
            }

        } catch (Exception e) {
            System.out.println("Error al procesar el rango de IPs.");
        }

        return resultados;
    }
}