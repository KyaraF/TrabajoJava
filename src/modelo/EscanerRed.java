package modelo;

import java.util.ArrayList;
import java.util.List;

public class EscanerRed {

    private ComandoService comandoService;

    // Interfaz para notificar avances a la vista a través del controlador
    public interface ProgresoCallback {
        void onProgresoActualizado(int porcentaje, Dispositivo dispositivoEscaneado);
    }

    public EscanerRed() {
        this.comandoService = new ComandoService();
    }

    public List<Dispositivo> escanearRangoConProgreso(String ipInicio, String ipFin, ProgresoCallback callback) {
        List<Dispositivo> resultados = new ArrayList<>();

        try {
            String[] partesInicio = ipInicio.split("\\.");
            String[] partesFin = ipFin.split("\\.");

            String prefijoRed = partesInicio[0] + "." + partesInicio[1] + "." + partesInicio[2] + ".";
            int hostInicio = Integer.parseInt(partesInicio[3]);
            int hostFin = Integer.parseInt(partesFin[3]);

            int totalIPs = (hostFin - hostInicio) + 1;
            int procesadas = 0;

            for (int i = hostInicio; i <= hostFin; i++) {
                String ipActual = prefijoRed + i;
                Dispositivo dispositivo = comandoService.escanearIP(ipActual);
                resultados.add(dispositivo);

                procesadas++;
                int porcentaje = (int) (((double) procesadas / totalIPs) * 100);

                // Notificamos el avance
                if (callback != null) {
                    callback.onProgresoActualizado(porcentaje, dispositivo);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al procesar las direcciones IP.");
        }

        return resultados;
    }
}