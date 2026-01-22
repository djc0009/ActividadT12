package ActividadT12;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SimonsSays extends JFrame {

    private JButton[] botones = new JButton[9];
    private Color[] colores = {
            Color.blue,
            Color.cyan,
            Color.green,
            Color.magenta,
            Color.orange,
            Color.pink,
            Color.red,
            Color.gray,
            Color.yellow
    };
    private ArrayList<Integer> secuencia = new ArrayList<>();
    private ArrayList<Integer> jugador = new ArrayList<>();
    private JButton btnReiniciar;
    private JLabel lblRonda;
    private int rondaActual = 0;

    public SimonsSays() {
        setTitle("Simon Says");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // --- Fondo general ---
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(25, 25, 25));
        contentPane.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setContentPane(contentPane);

        // --- Título ---
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(25, 25, 25));
        JLabel title = new JLabel("SIMON SAYS", JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 48));
        title.setForeground(Color.WHITE);

        lblRonda = new JLabel("Ronda: 0", JLabel.CENTER);
        lblRonda.setFont(new Font("Arial", Font.BOLD, 28));
        lblRonda.setForeground(Color.YELLOW);

        panelTitulo.add(title, BorderLayout.NORTH);
        panelTitulo.add(lblRonda, BorderLayout.SOUTH);
        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // --- Panel de botones ---
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(25, 25, 25));
        contentPane.add(panel, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(3, 3, 25, 25));
        panelBotones.setBackground(new Color(25, 25, 25));
        panel.add(panelBotones);

        // Crear botones y asignar ActionListener con la lógica directamente
        for (int i = 0; i < 9; i++) {
            final int botonActual = i;
            botones[i] = new JButton();
            botones[i].setBackground(Color.WHITE);
            botones[i].setOpaque(true);
            botones[i].setBorderPainted(false);
            botones[i].setPreferredSize(new Dimension(200, 200));
            panelBotones.add(botones[i]);

            // Lógica directamente en el ActionListener
            botones[i].addActionListener(e -> {
                jugador.add(botonActual);
                int pos = jugador.size() - 1;

                // Verificar fallo del jugador
                if (!jugador.get(pos).equals(secuencia.get(pos))) {
                    lblRonda.setText("¡Perdiste! Llegaste a la ronda: " + rondaActual);
                    return;
                }

                // Si completa la secuencia correctamente
                if (jugador.size() == secuencia.size()) {
                    siguienteRonda();
                }
            });
        }

        // Botón de reinicio
        btnReiniciar = new JButton("Iniciar Partida");
        btnReiniciar.setFont(new Font("Arial", Font.BOLD, 28));
        btnReiniciar.setBackground(Color.yellow);
        btnReiniciar.setForeground(Color.black);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.setPreferredSize(new Dimension(70, 70));
        btnReiniciar.setOpaque(true);
        btnReiniciar.addActionListener(e -> iniciarJuego());
        contentPane.add(btnReiniciar, BorderLayout.SOUTH);

        setVisible(true);
    }

    // --- FUNCIONES DEL JUEGO ---
    private void iniciarJuego() {
        secuencia.clear();
        jugador.clear();
        rondaActual = 0;
        lblRonda.setText("Ronda: " + rondaActual);
        siguienteRonda();
    }

    private void siguienteRonda() {
        jugador.clear();
        rondaActual++;
        lblRonda.setText("Ronda: " + rondaActual);
        secuencia.add(new Random().nextInt(9));
        mostrarSecuencia();
    }

    private void mostrarSecuencia() {
        new Thread(() -> {
            try {
                for (int botonActual : secuencia) {
                    // iluminar botón
                    SwingUtilities.invokeLater(() -> botones[botonActual].setBackground(colores[botonActual]));
                    Thread.sleep(600);
                    // volver a blanco
                    SwingUtilities.invokeLater(() -> botones[botonActual].setBackground(Color.WHITE));
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimonsSays::new);
    }
}
