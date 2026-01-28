package ActividadT12;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.io.File;
import javax.imageio.ImageIO;

public class Juego2 extends JFrame {

    private static final long serialVersionUID = 1L;

    // Panel principal y paneles secundarios para las cartas y los botones inferiores
    private JPanel contentPane;
    private JPanel panelCartas;
    private JPanel panelSur;

    private int parejasEncontradas = 0; // lleva la cuenta de cuántas parejas se han descubierto
    private JButton primeraCarta = null; // referencia de la primera carta seleccionada
    private JButton segundaCarta = null; // referencia de la segunda carta seleccionada
    private boolean bloqueo = false;      // sirve para bloquear clicks mientras se muestran cartas

    // Listas para guardar las cartas y los botones correspondientes
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
        configurarVentana();   // definimos la ventana principal
        cargarRecursos();      // cargamos imagenes (reverso)
        prepararCartas();      // generamos las cartas y las mezclamos
        crearTitulo();         // título arriba
        crearPanelCartas();    // panel central con las cartas
        crearPanelSur();       // panel inferior con botones

        // Abrir ventana en grande
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Creamos los botones para las cartas
        crearBotonesCartas();

        // Después de que la ventana esté visible, ajustamos tamaño de las cartas
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                SwingUtilities.invokeLater(() -> redimensionarCartas());
            }
        });
    }

    // Configura ventana principal: color de fondo, layout y bordes
    private void configurarVentana() {
        setTitle("Memory Davante - Modo Fácil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel(new BorderLayout(15, 15));
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setBackground(new Color(25, 25, 25));
        setContentPane(contentPane);
    }

    // Cargamos recursos gráficos: en este caso la imagen de reverso de las cartas
    private void cargarRecursos() {
        try {
            reverso = new ImageIcon(getClass().getResource("/images/reverso.png"));
        } catch (Exception e) {
            System.err.println("No se pudo cargar reverso.png, usando icono vacío");
            reverso = new ImageIcon();
        }
    }

    // Preparamos la lista de cartas: cada imagen aparece dos veces y luego mezclamos
    private void prepararCartas() {
        cartas = new ArrayList<>();
        botones = new ArrayList<>();

        String[] imagenes = {"elefante.png", "hippo.png", "mono.png", "oso.png", "panda.png"};

        for (String img : imagenes) {
            cartas.add(img);
            cartas.add(img); // duplicamos cada imagen para formar pareja
        }

        Collections.shuffle(cartas); // mezclamos las cartas
    }

    // Creamos un título arriba de la ventana
    private void crearTitulo() {
        JLabel lblTitulo = new JLabel("Memory Davante");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(255, 255, 0));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(10, 0, 20, 0));
        contentPane.add(lblTitulo, BorderLayout.NORTH);
    }

    // Panel central donde estarán las cartas en una cuadrícula
    private void crearPanelCartas() {
        panelCartas = new JPanel();
        panelCartas.setLayout(new GridLayout(2, 5, 12, 12)); // 2 filas x 5 columnas
        panelCartas.setBackground(new Color(25, 25, 25));
        contentPane.add(panelCartas, BorderLayout.CENTER);

        // Cada vez que cambiemos tamaño, redimensionamos las cartas
        panelCartas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> redimensionarCartas());
            }
        });
    }

    // Panel inferior con los botones 
    private void crearPanelSur() {
        panelSur = new JPanel();
        panelSur.setLayout(new GridLayout(1, 3, 20, 0));
        panelSur.setBackground(new Color(25, 25, 25));

        JButton btnModoDificil = crearBotonInferior("Modo Difícil", new Color(60, 140, 255));
        btnModoDificil.addActionListener(e -> {
            dispose();
            new Juego2_2().setVisible(true); // abrimos modo difícil
        });

        JButton btnReiniciar = crearBotonInferior("Reiniciar", new Color(60, 140, 255));
        btnReiniciar.addActionListener(e -> reiniciarJuego()); // reiniciamos el juego

        JButton btnSalir = crearBotonInferior("Salir", new Color(200, 60, 60));
        btnSalir.addActionListener(e -> {
            dispose();
            new PnatallaInicio().setVisible(true); // volvemos a la pantalla de inicio
        });

        panelSur.add(btnModoDificil);
        panelSur.add(btnReiniciar);
        panelSur.add(btnSalir);

        contentPane.add(panelSur, BorderLayout.SOUTH);
    }

    // Método genérico para crear los botones del panel inferior con efectos de hover
    private JButton crearBotonInferior(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setUI(new BasicButtonUI());

        // efecto visual al pasar el mouse
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

    // Creamos los botones que representan las cartas
    private void crearBotonesCartas() {
        panelCartas.removeAll(); // limpiamos panel
        botones.clear();

        for (String carta : cartas) {
            JButton boton = new JButton();
            boton.setFocusPainted(false);
            boton.setBackground(new Color(45, 45, 45));
            boton.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 3, true));
            boton.putClientProperty("imagen", carta);     // guardamos qué imagen tiene
            boton.putClientProperty("descubierta", false); // si ya fue descubierta
            boton.setIcon(reverso);                        // se muestra el reverso
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            boton.setUI(new BasicButtonUI());

            // efecto hover solo si la carta no está descubierta
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

            // acción al hacer click
            boton.addActionListener(e -> manejarClick(boton));

            botones.add(boton);
            panelCartas.add(boton);
        }

        panelCartas.revalidate();
        panelCartas.repaint();

        // ajustamos tamaño de las imágenes
        SwingUtilities.invokeLater(this::redimensionarCartas);
    }

    // Qué pasara cuando se hace click en una carta
    private void manejarClick(JButton boton) {
        if (bloqueo) return; // no hacer nada si estamos bloqueando
        if (boton == primeraCarta) return; // no seleccionar la misma carta dos veces
        if ((boolean) boton.getClientProperty("descubierta")) return; // carta ya descubierta

        mostrarImagen(boton); // revelamos la carta

        if (primeraCarta == null) {
            primeraCarta = boton; // si es la primera carta seleccionada
        } else {
            segundaCarta = boton; // si es la segunda, comprobamos pareja
            comprobarPareja();
        }
    }

    // Mostramos la imagen de la carta escalándola al tamaño del botón
    private void mostrarImagen(JButton boton) {
        String img = (String) boton.getClientProperty("imagen");
        int w = boton.getWidth();
        int h = boton.getHeight();
        if (w > 20 && h > 20) { // evitamos botones demasiado pequeños
            boton.setIcon(escalarImagen(img, w, h));
        }
    }

    // Ocultamos la imagen de una carta (volvemos al reverso)
    private void ocultarImagen(JButton boton) {
        boton.setIcon(reverso);
    }

    // Comprobamos si las dos cartas seleccionadas forman una pareja
    private void comprobarPareja() {
        bloqueo = true; // bloqueamos mientras mostramos las cartas

        String img1 = (String) primeraCarta.getClientProperty("imagen");
        String img2 = (String) segundaCarta.getClientProperty("imagen");

        if (img1.equals(img2)) { // si coinciden
            primeraCarta.putClientProperty("descubierta", true);
            segundaCarta.putClientProperty("descubierta", true);

            // ponemos borde verde
            primeraCarta.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 100), 4, true));
            segundaCarta.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 100), 4, true));

            primeraCarta = null;
            segundaCarta = null;
            bloqueo = false;

            parejasEncontradas++; // sumamos pareja encontrada

            // si ya encontramos todas las parejas
            if (parejasEncontradas == cartas.size() / 2) {
                JOptionPane.showMessageDialog(this, "¡Enhorabuena, has terminado el juego!", "Juego completado", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // si no coinciden, las ocultamos tras 0.7 segundos
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

    // Reiniciamos el juego: mezclamos cartas y reseteamos variables
    private void reiniciarJuego() {
        parejasEncontradas = 0;
        primeraCarta = null;
        segundaCarta = null;
        bloqueo = false;
        prepararCartas();
        crearBotonesCartas();
    }

    // Escalamos la imagen al tamaño del botón
    private ImageIcon escalarImagen(String nombreArchivo, int ancho, int alto) {
        try {
            File archivo = new File("src/images/" + nombreArchivo);
            if (!archivo.exists()) {
                return reverso;
            }

            BufferedImage imgOriginal = ImageIO.read(archivo);
            BufferedImage buffered = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buffered.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.drawImage(imgOriginal, 0, 0, ancho, alto, null);
            g2.dispose();

            return new ImageIcon(buffered);
        } catch (Exception e) {
            return reverso; // si falla, mostramos reverso(lo utilizo para que las cartas nunca se vean en blanco ya que me pasaba eso)
        }
    }

    // Redimensiona las cartas cuando cambia el tamaño de la ventana
    private void redimensionarCartas() {
        for (JButton boton : botones) {
            int w = boton.getWidth();
            int h = boton.getHeight();
            if ((boolean) boton.getClientProperty("descubierta")) {
                boton.setIcon(escalarImagen((String) boton.getClientProperty("imagen"), w, h));
            } else {
                boton.setIcon(reverso);
            }
        }
        panelCartas.revalidate();
        panelCartas.repaint();
    }
}
