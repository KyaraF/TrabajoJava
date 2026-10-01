package modelo;

import java.util.ArrayList;
import java.util.List;

// Logica principal del escaneo por rangos de IP
public class EscanerRed {

    private ComandoService comandoService;

    public EscanerRed() {
        this.comandoService = new ComandoService();
    }

    public ComandoService getComandoService() {
        return comandoService;
    }

    // Revisa que la IP tenga el formato correcto x.x.x.x
    public boolean esIpValida(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(regex);
    }

    // Convierte una cadena IP "10.120.0.63" a un entero numérico continuo
    public static long ipATotalNum(String ip) {
        String[] partes = ip.split("\\.");
        long resultado = 0;
        for (int i = 0; i < 4; i++) {
            resultado = (resultado << 8) + Integer.parseInt(partes[i]);
        }
        return resultado;
    }

    // Convierte un entero numérico de vuelta a cadena IP "10.120.0.63"
    public static String totalNumAIp(long num) {
        return ((num >> 24) & 0xFF) + "." +
               ((num >> 16) & 0xFF) + "." +
               ((num >> 8) & 0xFF) + "." +
               (num & 0xFF);
    }

    // Recorre las IPs desde la de inicio hasta la de fin
    public List<Dispositivo> escanearRango(String ipInicio, String ipFin, int timeoutMs) {
        List<Dispositivo> resultados = new ArrayList<>();

        try {
            long inicio = ipATotalNum(ipInicio);
            long fin = ipATotalNum(ipFin);

            if (fin < inicio) {
                return resultados;
            }

            for (long i = inicio; i <= fin; i++) {
                String ipActual = totalNumAIp(i);
                Dispositivo dispositivo = comandoService.escanearIP(ipActual, timeoutMs);
                resultados.add(dispositivo);
            }

        } catch (Exception e) {
            System.out.println("Error al procesar el rango de IPs.");
        }

        return resultados;
    }
}