package ActividadT12;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class Juego2 extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JPanel panelCartas;
    private JPanel panelSur;

    private int parejasEncontradas = 0;
    private JButton primeraCarta = null;
    private JButton segundaCarta = null;
    private boolean bloqueo = false;

    private ArrayList<String> cartas;
    private ArrayList<JButton> botones;

    private ImageIcon reverso;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Juego2 frame = new Juego2();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Juego2() {
        configurarVentana();
        cargarRecursos();
        prepararCartas();
        crearTitulo();
        crearPanelCartas();
        crearPanelSur();
        crearBotonesCartas();

        // Tamaño fijo y centrado
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Redimensionar cartas después de que la ventana sea visible
        EventQueue.invokeLater(() -> redimensionarCartas());
    }

    private void configurarVentana() {
        setTitle("Memory Davante - Modo Fácil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 700));

        contentPane = new JPanel(new BorderLayout(15, 15));
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setBackground(new Color(25, 25, 25));
        setContentPane(contentPane);
    }

    private void cargarRecursos() {
        reverso = new ImageIcon("src/images/reverso.png");
    }

    private void prepararCartas() {
        cartas = new ArrayList<>();
        botones = new ArrayList<>();

        String[] imagenes = {
            "elefante.png", "hippo.png", "mono.png", "oso.png", "panda.png"
        };

        for (String img : imagenes) {
            cartas.add(img);
            cartas.add(img);
        }

        Collections.shuffle(cartas);
    }

    private void crearTitulo() {
        JLabel lblTitulo = new JLabel("Memory Davante");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(255, 255, 0));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(10, 0, 20, 0));
        contentPane.add(lblTitulo, BorderLayout.NORTH);
    }

    private void crearPanelCartas() {
        panelCartas = new JPanel();
        panelCartas.setLayout(new GridLayout(2, 5, 12, 12));
        panelCartas.setBackground(new Color(25, 25, 25));
        contentPane.add(panelCartas, BorderLayout.CENTER);

        panelCartas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensionarCartas();
            }
        });
    }

    private void crearPanelSur() {
        panelSur = new JPanel();
        panelSur.setLayout(new GridLayout(1, 3, 20, 0));
        panelSur.setBackground(new Color(25, 25, 25));

        JButton btnModoDificil = crearBotonInferior("Modo Difícil", new Color(60, 140, 255));
        btnModoDificil.addActionListener(e -> {
            dispose();
            new Juego2_2().setVisible(true); // Tu modo difícil
        });

        JButton btnReiniciar = crearBotonInferior("Reiniciar", new Color(60, 140, 255));
        btnReiniciar.addActionListener(e -> reiniciarJuego());

        JButton btnSalir = crearBotonInferior("Salir", new Color(200, 60, 60));
        btnSalir.addActionListener(e -> {
        	dispose();
            new PnatallaInicio().setVisible(true);
        });
        

        panelSur.add(btnModoDificil);
        panelSur.add(btnReiniciar);
        panelSur.add(btnSalir);

        contentPane.add(panelSur, BorderLayout.SOUTH);
    }

    private JButton crearBotonInferior(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ⚡ Esto fuerza que el LookAndFeel no cambie el color
        btn.setUI(new BasicButtonUI());

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });

        return btn;
    }

    private void crearBotonesCartas() {
        panelCartas.removeAll();
        botones.clear();

        for (int i = 0; i < cartas.size(); i++) {
            JButton boton = new JButton();
            boton.setFocusPainted(false);
            boton.setBackground(new Color(45, 45, 45));
            boton.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 3, true));
            boton.putClientProperty("imagen", cartas.get(i));
            boton.putClientProperty("descubierta", false);

            boton.setIcon(escalarImagen("src/images/reverso.png", 120, 120));
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Evita que el LookAndFeel cambie el botón
            boton.setUI(new BasicButtonUI());

            boton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!(boolean) boton.getClientProperty("descubierta"))
                        boton.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 3, true));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    if (!(boolean) boton.getClientProperty("descubierta"))
                        boton.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 3, true));
                }
            });

            boton.addActionListener(e -> manejarClick(boton));

            botones.add(boton);
            panelCartas.add(boton);
        }

        panelCartas.revalidate();
        panelCartas.repaint();
    }

    private void manejarClick(JButton boton) {
        if (bloqueo) return;
        if (boton == primeraCarta) return;
        if ((boolean) boton.getClientProperty("descubierta")) return;

        mostrarImagen(boton);

        if (primeraCarta == null) {
            primeraCarta = boton;
        } else {
            segundaCarta = boton;
            comprobarPareja();
        }
    }

    private void mostrarImagen(JButton boton) {
        String img = (String) boton.getClientProperty("imagen");
        ImageIcon icon = escalarImagen("src/images/" + img, boton.getWidth(), boton.getHeight());
        boton.setIcon(icon);
    }

    private void ocultarImagen(JButton boton) {
        ImageIcon reversoEscalado = escalarImagen("src/images/reverso.png", boton.getWidth(), boton.getHeight());
        boton.setIcon(reversoEscalado);
    }

    private void comprobarPareja() {
        bloqueo = true;

        String img1 = (String) primeraCarta.getClientProperty("imagen");
        String img2 = (String) segundaCarta.getClientProperty("imagen");

        if (img1.equals(img2)) {
            primeraCarta.putClientProperty("descubierta", true);
            segundaCarta.putClientProperty("descubierta", true);

            primeraCarta.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 100), 4, true));
            segundaCarta.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 100), 4, true));

            primeraCarta = null;
            segundaCarta = null;
            bloqueo = false;

            parejasEncontradas++;

            if (parejasEncontradas == cartas.size() / 2) {
                mostrarMensajeFinal();
            }
        } else {
            Timer timer = new Timer(700, e -> {
                ocultarImagen(primeraCarta);
                ocultarImagen(segundaCarta);
                primeraCarta.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 3, true));
                segundaCarta.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 3, true));
                primeraCarta = null;
                segundaCarta = null;
                bloqueo = false;
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void mostrarMensajeFinal() {
        JOptionPane.showMessageDialog(
            this,
            "¡Enhorabuena, has terminado el juego!",
            "Juego completado",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void reiniciarJuego() {
        parejasEncontradas = 0;
        primeraCarta = null;
        segundaCarta = null;
        bloqueo = false;
        prepararCartas();
        crearBotonesCartas();
    }

    private ImageIcon escalarImagen(String ruta, int ancho, int alto) {
        if (ancho <= 0 || alto <= 0) {
            ancho = 120;
            alto = 120;
        }

        try {
            ImageIcon icon = new ImageIcon(ruta);
            Image img = icon.getImage().getScaledInstance(ancho - 10, alto - 10, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    private void redimensionarCartas() {
        for (JButton boton : botones) {
            if ((boolean) boton.getClientProperty("descubierta")) {
                String img = (String) boton.getClientProperty("imagen");
                boton.setIcon(escalarImagen("src/images/" + img, boton.getWidth(), boton.getHeight()));
            } else {
                boton.setIcon(escalarImagen("src/images/reverso.png", boton.getWidth(), boton.getHeight()));
            }
        }
    }
}
