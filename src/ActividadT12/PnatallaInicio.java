package ActividadT12;

import java.awt.*;
import javax.swing.*;

public class PnatallaInicio extends JFrame {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
        	
            new PnatallaInicio().setVisible(true);
        });
    }

    public PnatallaInicio() {
        setTitle("TRES");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== PANTALLA COMPLETA =====
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setLocationRelativeTo(null);

        // ===== PANEL PRINCIPAL =====
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);
        setContentPane(mainPanel);

        // ===== TÍTULO =====
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.DARK_GRAY);

        JLabel titulo = new JLabel("TRES x UNO");
        titulo.setFont(new Font("Constantia", Font.BOLD, 96));
        titulo.setForeground(Color.BLUE);

        // Agregar espacio arriba
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0)); 
        // arriba=50, izquierda=0, abajo=0, derecha=0


        titlePanel.add(titulo);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // ===== BOTONES =====
        JButton btnJugar = crearBotonBasico("JUGAR");
        JButton btnInstrucciones = crearBotonBasico("INSTRUCCIONES");
        JButton btnOpciones = crearBotonBasico("OPCIONES");
        JButton btnSalir = crearBotonBasico("SALIR");

        // Centrar los botones usando un panel contenedor
        JPanel contenedor = new JPanel();
        contenedor.setBackground(Color.DARK_GRAY);
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));

        contenedor.add(Box.createVerticalGlue()); // espacio arriba
        contenedor.add(btnJugar);
        contenedor.add(Box.createVerticalStrut(20));
        contenedor.add(btnInstrucciones);
        contenedor.add(Box.createVerticalStrut(20));
        contenedor.add(btnOpciones);
        contenedor.add(Box.createVerticalStrut(20));
        contenedor.add(btnSalir);
        contenedor.add(Box.createVerticalGlue()); // espacio abajo

        // Centrar horizontalmente
        btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInstrucciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOpciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(contenedor, BorderLayout.CENTER);
    }

    // ===== BOTÓN BÁSICO (MÁS ANCHO Y MENOS ALTO) =====
    private JButton crearBotonBasico(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(Color.YELLOW);
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 24)); // fuente más grande
        boton.setFocusPainted(false);

        // Ajustamos tamaño: MÁS ANCHO, MENOS ALTO
        boton.setMaximumSize(new Dimension(600, 80)); // ancho máximo grande, altura reducida
        boton.setPreferredSize(new Dimension(600, 80));
        return boton;
    }
}
