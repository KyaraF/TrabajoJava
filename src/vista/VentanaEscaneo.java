package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaEscaneo extends JFrame {

    private JTextField txtIpInicio;
    private JTextField txtIpFin;
    private JButton btnEscanear;
    private JButton btnLimpiar;
    private JButton btnGuardar;
    private JProgressBar progressBar;
    private JTextArea txtResultados;

    public VentanaEscaneo() {
        setTitle("Escáner de Red - TP Redes");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR (Formulario y Botones) ---
        JPanel panelSuperior = new JPanel(new GridLayout(4, 2, 5, 5));

        panelSuperior.add(new JLabel("  IP Inicio:"));
        txtIpInicio = new JTextField("192.168.1.1");
        panelSuperior.add(txtIpInicio);

        panelSuperior.add(new JLabel("  IP Fin:"));
        txtIpFin = new JTextField("192.168.1.5");
        panelSuperior.add(txtIpFin);

        btnEscanear = new JButton("Iniciar Escaneo");
        btnLimpiar = new JButton("Limpiar Pantalla");
        panelSuperior.add(btnEscanear);
        panelSuperior.add(btnLimpiar);

        btnGuardar = new JButton("Guardar Resultados");
        btnGuardar.setEnabled(false); // Desactivado hasta que haya resultados
        panelSuperior.add(btnGuardar);

        add(panelSuperior, BorderLayout.NORTH);

        // --- PANEL CENTRAL (Consola Visual y Barra de Progreso) ---
        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));

        txtResultados = new JTextArea();
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(txtResultados);
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        panelCentro.add(progressBar, BorderLayout.SOUTH);

        add(panelCentro, BorderLayout.CENTER);
    }

    // Getters para los datos ingresados
    public String getIpInicio() { return txtIpInicio.getText().trim(); }
    public String getIpFin() { return txtIpFin.getText().trim(); }

    // Métodos para actualizar la pantalla
    public void agregarResultado(String texto) { txtResultados.append(texto + "\n"); }
    public void limpiarResultados() { 
        txtResultados.setText(""); 
        progressBar.setValue(0);
        btnGuardar.setEnabled(false);
    }

    public void setProgreso(int porcentaje) { progressBar.setValue(porcentaje); }
    public void habilitarBotonEscanear(boolean estado) { btnEscanear.setEnabled(estado); }
    public void habilitarBotonGuardar(boolean estado) { btnGuardar.setEnabled(estado); }

    // Escuchadores de eventos
    public void setActionListenerEscanear(ActionListener l) { btnEscanear.addActionListener(l); }
    public void setActionListenerLimpiar(ActionListener l) { btnLimpiar.addActionListener(l); }
    public void setActionListenerGuardar(ActionListener l) { btnGuardar.addActionListener(l); }
}
