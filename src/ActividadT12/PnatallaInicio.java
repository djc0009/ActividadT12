package ActividadT12;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PnatallaInicio extends JFrame {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PnatallaInicio().setVisible(true);
        });
    }

    public PnatallaInicio() {
        setTitle("🎰 CASINO TRIPLE - 3 Juegos en 1 🎰");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setContentPane(crearPantallaJuegos());
    }

    // ================= PANTALLA DE JUEGOS =================
    private JPanel crearPantallaJuegos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 15, 35));

        // ===== TÍTULO =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(100, 0, 100));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 0), 4),
                BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));

        JLabel titulo = new JLabel("🎮 SELECCIONA TU JUEGO 🎮");
        titulo.setFont(new Font("Serif", Font.BOLD, 64));
        titulo.setForeground(new Color(255, 215, 0));
        headerPanel.add(titulo);
        panel.add(headerPanel, BorderLayout.NORTH);

        // ===== PANEL DE JUEGOS =====
        JPanel juegosPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        juegosPanel.setBackground(new Color(15, 15, 35));
        juegosPanel.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        // Blackjack
        JPanel blackjackCard = crearCartaJuego(
                "BLACKJACK",
                "♠ BLACKJACK ♥",
                "El clásico 21",
                "Vence a la banca sin pasarte de 21 puntos",
                new Color(139, 0, 0),
                "🃏"
        );
        

        // Memory
        JPanel memoryCard = crearCartaJuego(
                "MEMORY",
                "🧠 MEMORY",
                "Pon a prueba tu memoria",
                "Encuentra todas las parejas de cartas",
                new Color(0, 100, 150),
                "🎴"
        );

        // Mayor o Menor
        JPanel higherCard = crearCartaJuego(
                "HIGHER",
                "📊 MAYOR o MENOR",
                "Adivina la siguiente",
                "¿La próxima carta será mayor o menor?",
                new Color(150, 100, 0),
                "🎯"
        );

        juegosPanel.add(blackjackCard);
        juegosPanel.add(memoryCard);
        juegosPanel.add(higherCard);

        panel.add(juegosPanel, BorderLayout.CENTER);

        // ===== BOTÓN INFERIOR =====
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        JButton btnSalir = crearBotonSecundario("❌ SALIR");
        btnSalir.addActionListener(e -> System.exit(0));
        bottomPanel.add(btnSalir);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ================= MÉTODOS AUXILIARES =================
    private JPanel crearCartaJuego(
            String tipoJuego,
            String titulo,
            String subtitulo,
            String descripcion,
            Color color,
            String emoji
    ) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 0), 5),
                BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblEmoji = new JLabel(emoji);
        lblEmoji.setFont(new Font("Serif", Font.PLAIN, 80));
        lblEmoji.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 38));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel(subtitulo);
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 22));
        lblSubtitulo.setForeground(new Color(255, 255, 200));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea txtDescripcion = new JTextArea(descripcion);
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 18));
        txtDescripcion.setForeground(Color.WHITE);
        txtDescripcion.setBackground(color);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setFocusable(false);
        txtDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnJugar = new JButton("JUGAR");
        btnJugar.setFont(new Font("Verdana", Font.BOLD, 24));
        btnJugar.setBackground(new Color(255, 215, 0));
        btnJugar.setForeground(Color.BLACK);
        btnJugar.setFocusPainted(false);
        btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnJugar.setMaximumSize(new Dimension(200, 50));
        btnJugar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ================= Acción JUGAR según el juego =================
        btnJugar.addActionListener(e -> {
            dispose(); // cerrar menú

            switch (tipoJuego) {
                case "MEMORY":
                    EventQueue.invokeLater(() -> new Juego2().setVisible(true));
                    break;
                case "BLACKJACK":
                    EventQueue.invokeLater(() -> new Blackjack().setVisible(true));
                    break;
                case "HIGHER":
                    EventQueue.invokeLater(() -> new HigherOrLower().setVisible(true));
                    break;
                default:
                    JOptionPane.showMessageDialog(this,
                            "Juego en desarrollo: " + titulo,
                            "Atención",
                            JOptionPane.INFORMATION_MESSAGE);
            }
        });

        card.add(lblEmoji);
        card.add(Box.createVerticalStrut(15));
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(10));
        card.add(lblSubtitulo);
        card.add(Box.createVerticalStrut(20));
        card.add(txtDescripcion);
        card.add(Box.createVerticalStrut(25));
        card.add(btnJugar);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(color.brighter());
                txtDescripcion.setBackground(color.brighter());
            }

            public void mouseExited(MouseEvent e) {
                card.setBackground(color);
                txtDescripcion.setBackground(color);
            }
        });

        return card;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 24));
        btn.setBackground(new Color(70, 70, 90));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
        btn.setPreferredSize(new Dimension(280, 60));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(100, 100, 120));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(70, 70, 90));
            }
        });

        return btn;
    }
}
