package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Importar las clases de otros paquetes
import vista.VentanaEscaneo;
import modelo.EscanerRed;
import modelo.Dispositivo;

public class EscaneoController implements ActionListener {

    private VentanaEscaneo vista;
    private EscanerRed escaner;

    public EscaneoController(VentanaEscaneo vista, EscanerRed escaner) {
        this.vista = vista;
        this.escaner = escaner;

        // Registramos el controlador como el escuchador del botón
        this.vista.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Se ejecuta cuando el usuario presiona "Iniciar Escaneo"
        String ipInicio = vista.getIpInicio();
        String ipFin = vista.getIpFin();

        vista.limpiarResultados();
        vista.agregarResultado("--- INICIANDO ESCANEO DE RED ---");
        vista.agregarResultado("Rango: " + ipInicio + " -> " + ipFin);
        vista.agregarResultado("Por favor espere...\n");

        // Ejecutamos el escaneo mediante el modelo
        List<Dispositivo> resultados = escaner.escanearRango(ipInicio, ipFin);

        // Mostramos los resultados devueltos por el modelo
        vista.agregarResultado("--- RESULTADOS DEL ESCANEO ---");
        int activos = 0;

        for (Dispositivo disp : resultados) {
            vista.agregarResultado(disp.toString());
            if (disp.isConectado()) {
                activos++;
            }
        }

        vista.agregarResultado("\nResumen: " + activos + " de " + resultados.size() + " equipos respondieron.");
    }
}