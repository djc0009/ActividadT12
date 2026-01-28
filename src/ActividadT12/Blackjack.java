package ActividadT12;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Blackjack extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private int puntosJugador = 0;
    private int puntosBanca = 0;
    private int dinero = 1000;

    private JLabel lblPuntosJugador;
    private JLabel lblPuntosBanca;
    private JLabel lblDinero;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Blackjack frame = new Blackjack();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // pantalla completa
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Blackjack() {
        setTitle("BLACKJACK");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(0, 1));

        // ================= TÍTULO =================
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(Color.BLACK);

        JLabel lblTitulo = new JLabel("BLACKJACK", JLabel.CENTER);
        lblTitulo.setForeground(Color.YELLOW);
        lblTitulo.setFont(new Font("Century Schoolbook", Font.BOLD, 70));
        lblTitulo.setPreferredSize(new Dimension(400, 80));

        panelTitulo.add(lblTitulo, BorderLayout.NORTH);
        contentPane.add(panelTitulo);

        JPanel panelCentral = new JPanel(new GridLayout(4, 1)); // 4 filas: banca, jugador, dinero, botones
        panelCentral.setBackground(Color.BLACK);
        panelTitulo.add(panelCentral, BorderLayout.CENTER);

        // ================= BANCA =================
        JPanel contenedorBanca = new JPanel(new BorderLayout());
        contenedorBanca.setOpaque(false);
        contenedorBanca.setBorder(BorderFactory.createEmptyBorder(20, 250, 0, 250));

        lblPuntosBanca = new JLabel("Banca: 0", JLabel.CENTER);
        lblPuntosBanca.setForeground(Color.WHITE);
        lblPuntosBanca.setFont(new Font("Verdana", Font.BOLD, 25));
        contenedorBanca.add(lblPuntosBanca, BorderLayout.NORTH);

        JPanel panelCartasBanca = new JPanel();
        panelCartasBanca.setBackground(new Color(0, 102, 0));
        panelCartasBanca.setPreferredSize(new Dimension(600, 150));
        panelCartasBanca.setBorder(BorderFactory.createLineBorder(new Color(212, 175, 55), 3));

        contenedorBanca.add(panelCartasBanca, BorderLayout.CENTER);
        panelCentral.add(contenedorBanca);

        // ================= JUGADOR =================
        JPanel contenedorJugador = new JPanel(new BorderLayout());
        contenedorJugador.setOpaque(false);
        contenedorJugador.setBorder(BorderFactory.createEmptyBorder(0, 250, 0, 250));

        lblPuntosJugador = new JLabel("Jugador: 0", JLabel.CENTER);
        lblPuntosJugador.setForeground(Color.WHITE);
        lblPuntosJugador.setFont(new Font("Verdana", Font.BOLD, 25));
        contenedorJugador.add(lblPuntosJugador, BorderLayout.NORTH);

        JPanel panelTusCartas = new JPanel();
        panelTusCartas.setBackground(new Color(0, 102, 0));
        panelTusCartas.setPreferredSize(new Dimension(600, 300));
        panelTusCartas.setBorder(BorderFactory.createLineBorder(new Color(212, 175, 55), 3));

        contenedorJugador.add(panelTusCartas, BorderLayout.CENTER);
        panelCentral.add(contenedorJugador);

        // ================= DINERO =================
        lblDinero = new JLabel("Dinero: " + dinero + " €", JLabel.CENTER);
        lblDinero.setForeground(Color.ORANGE);
        lblDinero.setFont(new Font("Verdana", Font.BOLD, 25));
        panelCentral.add(lblDinero);

        // ================= BOTONES =================
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        panelCentral.add(panelBotones);

        JButton btnPedir = crearBoton("PEDIR");
        JButton btnPlantarse = crearBoton("PLANTARSE");
        JButton btnIniciar = crearBoton("INICIAR");
        JButton btnVolver = crearBoton("VOLVER");

        // ----- ACCIONES -----
        btnPedir.addActionListener(e -> {
            int carta = (int) (Math.random() * 10) + 1;
            puntosJugador += carta;
            lblPuntosJugador.setText("Jugador: " + puntosJugador);

            panelTusCartas.add(crearCarta(carta));
            panelTusCartas.revalidate();
            panelTusCartas.repaint();

            if (puntosJugador > 21) {
                JOptionPane.showMessageDialog(this, "PIERDES. Te has pasado con " + puntosJugador);
                btnPedir.setEnabled(false);
                btnPlantarse.setEnabled(false);
            }
        });

        btnPlantarse.addActionListener(e -> {
            while (puntosBanca < 17) {
                int carta = (int) (Math.random() * 10) + 1;
                puntosBanca += carta;
                lblPuntosBanca.setText("Banca: " + puntosBanca);

                panelCartasBanca.add(crearCartaBanca(carta));
            }
            panelCartasBanca.repaint();

            String resultado;
            if (puntosBanca > 21 || puntosJugador > puntosBanca) {
                resultado = "GANAS 100€";
                dinero += 200; // ganancia
            } else if (puntosJugador == puntosBanca) {
                resultado = "EMPATE ⚖";
                dinero += 100; // devuelve apuesta
            } else {
                resultado = "PIERDES 100€";
                // apuesta ya descontada al iniciar
            }

            lblDinero.setText("Dinero: " + dinero + " €");
            JOptionPane.showMessageDialog(this, resultado);

            btnPedir.setEnabled(false);
            btnPlantarse.setEnabled(false);
        });

        btnIniciar.addActionListener(e -> {
            if (dinero < 100) {
                JOptionPane.showMessageDialog(this, "No tienes suficiente dinero para apostar 100€", "Dinero insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }

            dinero -= 100; // descontamos apuesta
            lblDinero.setText("Dinero: " + dinero + " €");

            panelTusCartas.removeAll();
            panelCartasBanca.removeAll();
            panelTusCartas.repaint();
            panelCartasBanca.repaint();

            puntosJugador = 0;
            puntosBanca = 0;
            lblPuntosJugador.setText("Jugador: 0");
            lblPuntosBanca.setText("Banca: 0");

            btnPedir.setEnabled(true);
            btnPlantarse.setEnabled(true);
        });

        // Botón VOLVER rojo con efecto
        btnVolver.setBackground(Color.RED);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnVolver.setBackground(new Color(200, 0, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnVolver.setBackground(Color.RED);
            }
        });
        btnVolver.addActionListener(e -> {
            dispose();
            EventQueue.invokeLater(() -> new PnatallaInicio().setVisible(true));
        });

        panelBotones.add(btnPedir);
        panelBotones.add(btnPlantarse);
        panelBotones.add(btnIniciar);
        panelBotones.add(btnVolver);
    }

    // ================= MÉTODOS AUXILIARES =================
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Verdana", Font.BOLD, 30));
        btn.setPreferredSize(new Dimension(400, 80));
        btn.setBackground(Color.LIGHT_GRAY);
        return btn;
    }

    private JLabel crearCarta(int valor) {
        JLabel carta = new JLabel(String.valueOf(valor), JLabel.CENTER);
        carta.setPreferredSize(new Dimension(120, 180));
        carta.setFont(new Font("Arial", Font.BOLD, 40));
        carta.setOpaque(true);
        carta.setBackground(Color.WHITE);
        carta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return carta;
    }

    private JLabel crearCartaBanca(int valor) {
        JLabel carta = crearCarta(valor);
        carta.setForeground(Color.RED);
        return carta;
    }
}
