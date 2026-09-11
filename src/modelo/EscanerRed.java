package modelo;

import java.util.ArrayList;
import java.util.List;

public class EscanerRed {

    private ComandoService comandoService;

    public EscanerRed() {
        this.comandoService = new ComandoService();
    }

    public List<Dispositivo> escanearRango(String ipInicio, String ipFin) {
        List<Dispositivo> resultados = new ArrayList<>();

        try {
            String[] partesInicio = ipInicio.split("\\.");
            String[] partesFin = ipFin.split("\\.");

            String prefijoRed = partesInicio[0] + "." + partesInicio[1] + "." + partesInicio[2] + ".";

            int hostInicio = Integer.parseInt(partesInicio[3]);
            int hostFin = Integer.parseInt(partesFin[3]);

            for (int i = hostInicio; i <= hostFin; i++) {
                String ipActual = prefijoRed + i;
                Dispositivo dispositivo = comandoService.escanearIP(ipActual);
                resultados.add(dispositivo);
            }

        } catch (Exception e) {
            System.out.println("Error al procesar las direcciones IP.");
        }

        return resultados;
    }
}