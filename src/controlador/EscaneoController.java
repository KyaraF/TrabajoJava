package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import modelo.Dispositivo;
import modelo.EscanerRed;
import modelo.ReporteService;
import vista.VentanaEscaneo;

public class EscaneoController {

    private VentanaEscaneo vista;
    private EscanerRed escaner;
    private ReporteService reporteService;
    private List<Dispositivo> ultimosResultados;

    public EscaneoController(VentanaEscaneo vista, EscanerRed escaner) {
        this.vista = vista;
        this.escaner = escaner;
        this.reporteService = new ReporteService();
        this.ultimosResultados = new ArrayList<>();

        // Asignamos la lógica a cada botón
        this.vista.setActionListenerEscanear(e -> iniciarEscaneoMultihilo());
        this.vista.setActionListenerLimpiar(e -> vista.limpiarResultados());
        this.vista.setActionListenerGuardar(e -> guardarResultadosEnArchivo());
    }

    private void iniciarEscaneoMultihilo() {
        String ipInicio = vista.getIpInicio();
        String ipFin = vista.getIpFin();

        vista.limpiarResultados();
        vista.agregarResultado("--- INICIANDO ESCANEO DE RED ---");
        vista.agregarResultado("Rango: " + ipInicio + " -> " + ipFin + "\n");
        vista.habilitarBotonEscanear(false);

        // Creamos un hilo secundario para que la interfaz gráfica no se congele
        new Thread(() -> {
            ultimosResultados = escaner.escanearRangoConProgreso(ipInicio, ipFin, (progreso, disp) -> {
                // Se ejecuta cada vez que termina de escanear 1 IP
                vista.setProgreso(progreso);
                vista.agregarResultado(disp.toString());
            });

            // Al finalizar el bucle:
            int activos = 0;
            for (Dispositivo d : ultimosResultados) {
                if (d.isConectado()) activos++;
            }

            vista.agregarResultado("\nResumen: " + activos + " de " + ultimosResultados.size() + " equipos respondieron.");
            vista.habilitarBotonEscanear(true);
            vista.habilitarBotonGuardar(true);
        }).start();
    }

    private void guardarResultadosEnArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Escaneo");
        
        int seleccion = fileChooser.showSaveDialog(vista);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            
            // Agregamos extensión .txt si no la puso el usuario
            if (!archivo.getName().endsWith(".txt")) {
                archivo = new File(archivo.getAbsolutePath() + ".txt");
            }

            boolean exito = reporteService.guardarReporte(archivo, ultimosResultados);
            if (exito) {
                JOptionPane.showMessageDialog(vista, "Reporte guardado correctamente en:\n" + archivo.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}