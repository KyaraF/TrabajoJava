package controlador;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import modelo.Dispositivo;
import modelo.EscanerRed;
import modelo.ReporteService;
import vista.VentanaEscaneo;

// Conecta la vista con el modelo
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

        // Asigno las acciones a los botones y combo box
        this.vista.setActionListenerEscanear(e -> iniciarEscaneo());
        this.vista.setActionListenerLimpiar(e -> {
            ultimosResultados.clear();
            vista.limpiarResultados();
        });
        this.vista.setActionListenerGuardar(e -> guardarResultadosEnArchivo());
        this.vista.setActionListenerFiltro(e -> vista.mostrarEnTabla(ultimosResultados));
    }

    private void iniciarEscaneo() {
        String ipInicio = vista.getIpInicio();
        String ipFin = vista.getIpFin();
        String timeoutStr = vista.getTimeout();

        // Valido datos antes de empezar
        if (!escaner.esIpValida(ipInicio) || !escaner.esIpValida(ipFin)) {
            JOptionPane.showMessageDialog(vista, "Formato de IP inválido. Ejemplo: 192.168.1.1", "Error de IP", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int timeoutMs;
        try {
            timeoutMs = Integer.parseInt(timeoutStr);
            if (timeoutMs <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El timeout debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        vista.limpiarResultados();
        vista.habilitarBotonEscanear(false);

        // Hilo secundario para que la pantalla no se congele durante el escaneo
        new Thread(() -> {
            vista.setProgreso(10);
            ultimosResultados = escaner.escanearRango(ipInicio, ipFin, timeoutMs);
            vista.setProgreso(100);

            if (ultimosResultados.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "La IP final debe ser mayor a la IP inicial.", "Error", JOptionPane.WARNING_MESSAGE);
            }

            int activos = 0;
            for (Dispositivo d : ultimosResultados) {
                if (d.isConectado()) activos++;
            }

            // Actualizo los componentes en pantalla
            vista.actualizarResumen(activos, ultimosResultados.size());
            vista.mostrarEnTabla(ultimosResultados);
            vista.habilitarBotonEscanear(true);

            if (!ultimosResultados.isEmpty()) {
                vista.habilitarBotonGuardar(true);
            }
        }).start();
    }

    private void guardarResultadosEnArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte");

        int seleccion = fileChooser.showSaveDialog(vista);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            if (!archivo.getName().endsWith(".txt")) {
                archivo = new File(archivo.getAbsolutePath() + ".txt");
            }

            boolean exito = reporteService.guardarReporte(archivo, ultimosResultados);
            if (exito) {
                JOptionPane.showMessageDialog(vista, "Archivo guardado correctamente.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}