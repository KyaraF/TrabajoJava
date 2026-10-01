package controlador;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

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
        ultimosResultados.clear();

        // SwingWorker para realizar el escaneo en segundo plano y actualizar la vista uno a uno
        SwingWorker<Void, Dispositivo> worker = new SwingWorker<Void, Dispositivo>() {
            private int activos = 0;
            private int totalIps = 0;

            @Override
            protected Void doInBackground() throws Exception {
                String[] partesInicio = ipInicio.split("\\.");
                String[] partesFin = ipFin.split("\\.");

                String prefijoRed = partesInicio[0] + "." + partesInicio[1] + "." + partesInicio[2] + ".";
                int hostInicio = Integer.parseInt(partesInicio[3]);
                int hostFin = Integer.parseInt(partesFin[3]);

                if (hostFin < hostInicio) {
                    return null;
                }

                totalIps = (hostFin - hostInicio) + 1;
                int procesados = 0;

                for (int i = hostInicio; i <= hostFin; i++) {
                    String ipActual = prefijoRed + i;
                    
                    // Escanear IP individual a traves del servicio de comandos
                    Dispositivo disp = escaner.getComandoService().escanearIP(ipActual, timeoutMs);
                    
                    procesados++;
                    ultimosResultados.add(disp);
                    
                    if (disp.isConectado()) {
                        activos++;
                    }

                    // Notificar avance a la GUI
                    int porcentaje = (int) (((double) procesados / totalIps) * 100);
                    setProgress(porcentaje);
                    
                    // Publicar dispositivo individual para que la vista lo agregue a la tabla
                    publish(disp);
                }

                return null;
            }

            @Override
            protected void process(List<Dispositivo> chunks) {
                // Se ejecuta en el EDT (hilo de interfaz)
                for (Dispositivo disp : chunks) {
                    vista.agregarDispositivoTabla(disp);
                }
                vista.setProgreso(getProgress());
                vista.actualizarResumen(activos, ultimosResultados.size());
            }

            @Override
            protected void done() {
                // Finalizacion del escaneo
                vista.habilitarBotonEscanear(true);

                if (ultimosResultados.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "La IP final debe ser mayor a la IP inicial.", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    vista.habilitarBotonGuardar(true);
                }
            }
        };

        worker.execute();
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