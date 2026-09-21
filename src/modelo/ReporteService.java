package modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

// Guarda los datos en un archivo de texto .txt
public class ReporteService {

    public boolean guardarReporte(File archivo, List<Dispositivo> dispositivos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("        REPORTE DE ESCANEO DE RED        ");
            writer.println("=========================================");
            writer.println();

            int activos = 0;
            for (Dispositivo disp : dispositivos) {
                writer.println(disp.toString());
                if (disp.isConectado()) {
                    activos++;
                }
            }

            writer.println();
            writer.println("Resumen: " + activos + " de " + dispositivos.size() + " equipos respondieron.");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
