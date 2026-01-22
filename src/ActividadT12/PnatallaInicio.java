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
        setUndecorated(true);
        setLocationRelativeTo(null);

        // ===== PANEL PRINCIPAL =====
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);
        setContentPane(mainPanel);

        // ===== TÍTULO =====
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.DARK_GRAY);

        JLabel titulo = new JLabel("TRES x MESSI");
        titulo.setFont(new Font("Arial", Font.BOLD, 96));
        titulo.setForeground(Color.BLUE);

        titlePanel.add(titulo);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // ===== BOTONES =====
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

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
        contenedor.add(Box.createVerticalStrut(10));
        contenedor.add(btnInstrucciones);
        contenedor.add(Box.createVerticalStrut(10));
        contenedor.add(btnOpciones);
        contenedor.add(Box.createVerticalStrut(10));
        contenedor.add(btnSalir);
        contenedor.add(Box.createVerticalGlue()); // espacio abajo

        // Centrar horizontalmente
        btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInstrucciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOpciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(contenedor, BorderLayout.CENTER);

        // ===== FOOTER =====
        JLabel footer = new JLabel("© Actividad T12");
        footer.setForeground(Color.WHITE);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(footer, BorderLayout.SOUTH);

        // ===== ACCIONES =====
        btnJugar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "¡Vamos a jugar!")
        );

        btnInstrucciones.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Instrucciones:\n- Cada jugador roba cartas\n- Gana quien se quede sin cartas")
        );

        btnOpciones.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Opciones no disponibles")
        );

        btnSalir.addActionListener(e -> System.exit(0));
    }

    // ===== BOTÓN BÁSICO (ESTRECHO) =====
    private JButton crearBotonBasico(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(Color.YELLOW);
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setMaximumSize(new Dimension(120, 35)); // menos ancho
        boton.setPreferredSize(new Dimension(120, 35));
        return boton;
    }
}
