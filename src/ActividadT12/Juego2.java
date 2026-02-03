package ActividadT12;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.imageio.ImageIO;

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

    // Imagen del reverso de la carta
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
        // Configuración general de la ventana
        configurarVentana();

        // Carga del reverso de las cartas
        cargarRecursos();

        // Prepara la lista de cartas y las baraja
        prepararCartas();

        // Título superior
        crearTitulo();

        // Panel central con las cartas
        crearPanelCartas();

        // Panel inferior con botones
        crearPanelSur();

        // Ventana a pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Creación de los botones de las cartas
        crearBotonesCartas();

        // Ajusta el tamaño de las cartas al abrir la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                SwingUtilities.invokeLater(() -> redimensionarCartas());
            }
        });
    }

    /**
     * Configuración básica de la ventana
     */
    private void configurarVentana() {
        setTitle("Memory Davante - Modo Fácil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel(new BorderLayout(15, 15));
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setBackground(new Color(25, 25, 25));
        setContentPane(contentPane);
    }

    /**
     * Carga los recursos gráficos necesarios
     */
    private void cargarRecursos() {
        // Imagen común para todas las cartas boca abajo
        reverso = cargarIconoEscalado("reverso.png", 200, 200);
    }

    /**
     * Prepara las cartas del juego (pares + barajado)
     */
    private void prepararCartas() {
        cartas = new ArrayList<>();
        botones = new ArrayList<>();

        // Imágenes disponibles (5 parejas)
        String[] imagenes = {
            "elefante.png",
            "hippo.png",
            "mono.png",
            "oso.png",
            "panda.png"
        };

        // Añadimos cada imagen dos veces (pareja)
        for (String img : imagenes) {
            cartas.add(img);
            cartas.add(img);
        }

        // Mezclamos las cartas
        Collections.shuffle(cartas);
    }

    /**
     * Crea el título superior del juego
     */
    private void crearTitulo() {
        JLabel lblTitulo = new JLabel("Memory Davante");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitulo.setForeground(Color.YELLOW);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(10, 0, 20, 0));
        contentPane.add(lblTitulo, BorderLayout.NORTH);
    }

    /**
     * Panel central donde van las cartas
     */
    private void crearPanelCartas() {
        panelCartas = new JPanel(new GridLayout(2, 5, 12, 12));
        panelCartas.setBackground(new Color(25, 25, 25));
        contentPane.add(panelCartas, BorderLayout.CENTER);

        // Reescala las cartas cuando se redimensiona la ventana
        panelCartas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> redimensionarCartas());
            }
        });
    }

    /**
     * Panel inferior co   	º
     *1#.@
     *n los botones del juego
     */
    private void crearPanelSur() {
        panelSur = new JPanel(new GridLayout(1, 3, 20, 0));
        panelSur.setBackground(new Color(25, 25, 25));

        // Botón para pasar al modo difícil
        JButton btnModoDificil = crearBotonInferior("Modo Difícil", new Color(60, 140, 255));
        btnModoDificil.addActionListener(e -> {
            dispose();
            Juego2_2 juego = null;
            juego.setVisible(true); 
        });

        // Reinicia la partida
        JButton btnReiniciar = crearBotonInferior("Reiniciar", new Color(60, 140, 255));
        btnReiniciar.addActionListener(e -> reiniciarJuego());

        // Sale al menú principal
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

    /**
     * Crea botones inferiores con estilo común
     */
    private JButton crearBotonInferior(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setUI(new BasicButtonUI());
        return btn;
    }

    /**
     * Crea los botones de las cartas
     */
    private void crearBotonesCartas() {
        panelCartas.removeAll();
        botones.clear();

        for (String carta : cartas) {
            JButton boton = new JButton();

            // Guardamos la imagen asociada al botón
            boton.putClientProperty("imagen", carta);

            // Indica si la carta ya está descubierta
            boton.putClientProperty("descubierta", false);

            // Empieza mostrando el reverso
            boton.setIcon(reverso);
            boton.setBackground(new Color(45, 45, 45));
            boton.setFocusPainted(false);
            boton.setUI(new BasicButtonUI());

            boton.addActionListener(e -> manejarClick(boton));

            botones.add(boton);
            panelCartas.add(boton);
        }

        panelCartas.revalidate();
        panelCartas.repaint();
    }

    /**
     * Controla el clic sobre una carta
     */
    private void manejarClick(JButton boton) {
        if (bloqueo) return;
        if ((boolean) boton.getClientProperty("descubierta")) return;
        if (boton == primeraCarta) return;

        mostrarImagen(boton);

        if (primeraCarta == null) {
            primeraCarta = boton;
        } else {
            segundaCarta = boton;
            comprobarPareja();
        }
    }

    /**
     * Muestra la imagen real de una carta
     */
    private void mostrarImagen(JButton boton) {
        String img = (String) boton.getClientProperty("imagen");
        boton.setIcon(cargarIconoEscalado(img, boton.getWidth(), boton.getHeight()));
    }

    /**
     * Vuelve a ocultar una carta
     */
    private void ocultarImagen(JButton boton) {
        boton.setIcon(reverso);
    }

    /**
     * Comprueba si las dos cartas seleccionadas forman pareja
     */
    private void comprobarPareja() {
        bloqueo = true;

        String img1 = (String) primeraCarta.getClientProperty("imagen");
        String img2 = (String) segundaCarta.getClientProperty("imagen");

        if (img1.equals(img2)) {
            // Pareja correcta
            primeraCarta.putClientProperty("descubierta", true);
            segundaCarta.putClientProperty("descubierta", true);

            primeraCarta = null;
            segundaCarta = null;
            bloqueo = false;

            parejasEncontradas++;

            // Si se encuentran todas las parejas
            if (parejasEncontradas == cartas.size() / 2) {
                JOptionPane.showMessageDialog(this, "¡Juego completado!");
            }
        } else {
            // Pareja incorrecta → se vuelven a ocultar
            Timer t = new Timer(700, e -> {
                ocultarImagen(primeraCarta);
                ocultarImagen(segundaCarta);
                primeraCarta = null;
                segundaCarta = null;
                bloqueo = false;
            });
            t.setRepeats(false);
            t.start();
        }
    }

    /**
     * Reinicia el juego desde cero
     */
    private void reiniciarJuego() {
        parejasEncontradas = 0;
        primeraCarta = null;
        segundaCarta = null;
        bloqueo = false;
        prepararCartas();
        crearBotonesCartas();
    }

    /**
     * Carga una imagen desde resources y la escala
     */
    private ImageIcon cargarIconoEscalado(String nombre, int w, int h) {
        try {
            var is = getClass().getResourceAsStream("/images/" + nombre);
            if (is == null) return new ImageIcon();

            BufferedImage original = ImageIO.read(is);
            Image escalada = original.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(escalada);
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    /**
     * Ajusta el tamaño de las cartas al cambiar la ventana
     */
    private void redimensionarCartas() {
        for (JButton b : botones) {
            if ((boolean) b.getClientProperty("descubierta")) {
                mostrarImagen(b);
            } else {
                b.setIcon(reverso);
            }
        }
    }
}
