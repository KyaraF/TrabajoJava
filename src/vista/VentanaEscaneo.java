package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.Dispositivo;

// Interfaz grafica con Swing
public class VentanaEscaneo extends JFrame {

    private JTextField txtIpInicio;
    private JTextField txtIpFin;
    private JTextField txtTimeout;
    private JComboBox<String> comboFiltro;
    private JButton btnEscanear;
    private JButton btnLimpiar;
    private JButton btnGuardar;
    private JProgressBar progressBar;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JLabel lblResumen;

    public VentanaEscaneo() {
        setTitle("Escáner de Red - TP Redes");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior para cargar datos
        JPanel panelSuperior = new JPanel(new GridLayout(6, 2, 5, 5));

        panelSuperior.add(new JLabel("  IP Inicio:"));
        txtIpInicio = new JTextField("192.168.1.1");
        panelSuperior.add(txtIpInicio);

        panelSuperior.add(new JLabel("  IP Fin:"));
        txtIpFin = new JTextField("192.168.1.5");
        panelSuperior.add(txtIpFin);

        panelSuperior.add(new JLabel("  Timeout (ms):"));
        txtTimeout = new JTextField("1000");
        panelSuperior.add(txtTimeout);

        panelSuperior.add(new JLabel("  Mostrar:"));
        comboFiltro = new JComboBox<>(new String[]{"Todos", "Solo Conectados"});
        panelSuperior.add(comboFiltro);

        btnEscanear = new JButton("Iniciar Escaneo");
        btnLimpiar = new JButton("Limpiar Pantalla");
        panelSuperior.add(btnEscanear);
        panelSuperior.add(btnLimpiar);

        btnGuardar = new JButton("Guardar Resultados");
        btnGuardar.setEnabled(false);
        panelSuperior.add(btnGuardar);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla al centro
        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));

        String[] columnas = {"IP", "Nombre", "Estado", "Tiempo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquea edicion de celdas
            }
        };

        tablaResultados = new JTable(modeloTabla);
        // Permite ordenar la tabla haciendo clic en los encabezados
        tablaResultados.setRowSorter(new TableRowSorter<>(modeloTabla));

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        // Barra de progreso y resumen al pie
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        panelInferior.add(progressBar, BorderLayout.NORTH);

        lblResumen = new JLabel("Resumen: 0 equipos escaneados.", SwingConstants.CENTER);
        lblResumen.setFont(new Font("SansSerif", Font.BOLD, 12));
        panelInferior.add(lblResumen, BorderLayout.SOUTH);

        panelCentro.add(panelInferior, BorderLayout.SOUTH);

        add(panelCentro, BorderLayout.CENTER);
    }

    public String getIpInicio() { return txtIpInicio.getText().trim(); }
    public String getIpFin() { return txtIpFin.getText().trim(); }
    public String getTimeout() { return txtTimeout.getText().trim(); }
    public String getFiltroSeleccionado() { return (String) comboFiltro.getSelectedItem(); }

    // Muestra la lista de dispositivos en la tabla aplicando el filtro
    public void mostrarEnTabla(List<Dispositivo> lista) {
        modeloTabla.setRowCount(0); // Limpia filas viejas
        String filtro = getFiltroSeleccionado();

        for (Dispositivo disp : lista) {
            String estado = disp.isConectado() ? "CONECTADO" : "NO CONECTADO";
            
            if ("Solo Conectados".equals(filtro) && !disp.isConectado()) {
                continue; // Salta los no conectados si el filtro esta activo
            }

            Object[] fila = {disp.getIp(), disp.getNombre(), estado, disp.getTiempoRespuesta()};
            modeloTabla.addRow(fila);
        }
    }

    public void limpiarResultados() {
        modeloTabla.setRowCount(0);
        progressBar.setValue(0);
        lblResumen.setText("Resumen: 0 equipos escaneados.");
        btnGuardar.setEnabled(false);
    }

    public void actualizarResumen(int activos, int total) {
        lblResumen.setText("Resumen: " + activos + " de " + total + " equipos respondieron.");
    }

    public void setProgreso(int porcentaje) { progressBar.setValue(porcentaje); }
    public void habilitarBotonEscanear(boolean estado) { btnEscanear.setEnabled(estado); }
    public void habilitarBotonGuardar(boolean estado) { btnGuardar.setEnabled(estado); }

    public void setActionListenerEscanear(ActionListener l) { btnEscanear.addActionListener(l); }
    public void setActionListenerLimpiar(ActionListener l) { btnLimpiar.addActionListener(l); }
    public void setActionListenerGuardar(ActionListener l) { btnGuardar.addActionListener(l); }
    public void setActionListenerFiltro(ActionListener l) { comboFiltro.addActionListener(l); }
}