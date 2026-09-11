package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaEscaneo extends JFrame {

    private JTextField txtIpInicio;
    private JTextField txtIpFin;
    private JButton btnEscanear;
    private JTextArea txtResultados;

    public VentanaEscaneo() {
        // Configuración de la ventana principal
        setTitle("Escáner de Red - TP Redes");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en pantalla
        setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR (Formulario de Entrada) ---
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(3, 2, 5, 5));

        panelSuperior.add(new JLabel("  IP Inicio:"));
        txtIpInicio = new JTextField("192.168.1.1");
        panelSuperior.add(txtIpInicio);

        panelSuperior.add(new JLabel("  IP Fin:"));
        txtIpFin = new JTextField("192.168.1.5");
        panelSuperior.add(txtIpFin);

        btnEscanear = new JButton("Iniciar Escaneo");
        panelSuperior.add(new JLabel("")); // Espacio vacío para alinear
        panelSuperior.add(btnEscanear);

        add(panelSuperior, BorderLayout.NORTH);

        // --- PANEL CENTRAL (Área de Resultados) ---
        txtResultados = new JTextArea();
        txtResultados.setEditable(false); // El usuario no puede escribir aquí
        txtResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(txtResultados);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Métodos para que el Controlador obtenga los datos de los campos
    public String getIpInicio() {
        return txtIpInicio.getText().trim();
    }

    public String getIpFin() {
        return txtIpFin.getText().trim();
    }

    // Método para agregar texto a la consola visual
    public void agregarResultado(String texto) {
        txtResultados.append(texto + "\n");
    }

    // Método para limpiar la consola visual
    public void limpiarResultados() {
        txtResultados.setText("");
    }

    // Método para conectar la acción del botón con el Controlador
    public void setActionListener(ActionListener listener) {
        btnEscanear.addActionListener(listener);
    }
}
