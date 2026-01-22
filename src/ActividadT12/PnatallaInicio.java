package ActividadT12;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PnatallaInicio extends JFrame {

    private JLabel personajeAnimado;
    private Timer animacion;
    private BufferedImage[] frames;

    public PnatallaInicio() {
        setTitle("TRES x UNO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // PANEL PRINCIPAL
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(20, 20, 20),
                        0, getHeight(), new Color(50, 50, 50)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(255, 255, 0, 30));
                for (int i = -getHeight(); i < getWidth(); i += 90) {
                    g2.fillRect(i, 0, 10, getHeight());
                }
            }
        };

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        setContentPane(mainPanel);

        /* =================== TÍTULO =================== */
        JLabel titulo = new JLabel("TRES x UNO", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setFont(getFont());

                g2.setColor(new Color(255, 255, 0, 80));
                g2.drawString(getText(), 14, 100);
                g2.drawString(getText(), 18, 105);

                g2.setColor(Color.YELLOW);
                g2.drawString(getText(), 16, 102);
            }
        };
        titulo.setFont(new Font("Consolas", Font.BOLD, 110));
        titulo.setPreferredSize(new Dimension(1200, 140));

        JPanel lineaDecorativa = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.YELLOW);
                g.fillRoundRect(0, 12, getWidth(), 6, 20, 20);
            }
        };
        lineaDecorativa.setOpaque(false);
        lineaDecorativa.setPreferredSize(new Dimension(400, 30));

        JPanel tituloPanel = new JPanel();
        tituloPanel.setOpaque(false);
        tituloPanel.setLayout(new BoxLayout(tituloPanel, BoxLayout.Y_AXIS));
        tituloPanel.add(titulo);
        tituloPanel.add(Box.createVerticalStrut(10));
        tituloPanel.add(lineaDecorativa);

        mainPanel.add(tituloPanel, BorderLayout.NORTH);

        /* ================= PERSONAJE ANIMADO ================= */
        personajeAnimado = new JLabel();
        personajeAnimado.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(personajeAnimado, BorderLayout.WEST);

        cargarSprites();
        iniciarAnimacion();

        /* ================= BOTONES ================= */
        JButton btnJugar = crearBoton("JUGAR");
        JButton btnInstrucciones = crearBoton("INSTRUCCIONES");
        JButton btnOpciones = crearBoton("OPCIONES");
        JButton btnSalir = crearBoton("SALIR");

        btnSalir.addActionListener(e -> System.exit(0));

        JPanel botonesPanel = new JPanel(new GridLayout(4, 1, 0, 25));
        botonesPanel.setOpaque(false);
        botonesPanel.add(btnJugar);
        botonesPanel.add(btnInstrucciones);
        botonesPanel.add(btnOpciones);
        botonesPanel.add(btnSalir);

        JPanel marco = new JPanel(new BorderLayout());
        marco.setOpaque(false);
        marco.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 4),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));
        marco.add(botonesPanel, BorderLayout.CENTER);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        centro.add(marco);

        mainPanel.add(centro, BorderLayout.CENTER);
    }

    /* =============== CARGAR SPRITES =============== */
    private void cargarSprites() {
        try {
            frames = new BufferedImage[] {
                ImageIO.read(new File("src/images/sprite1.png")),
                ImageIO.read(new File("src/images/sprite2.png")),
                ImageIO.read(new File("src/images/sprite3.png"))
            };
        } catch (IOException e) {
            System.out.println("Error cargando sprites");
        }
    }

    /* =============== ANIMACIÓN SIMPLE =============== */
    private void iniciarAnimacion() {
        animacion = new Timer(200, new ActionListener() {
            int frame = 0;
            public void actionPerformed(ActionEvent e) {
                Image img = frames[frame].getScaledInstance(350, 500, Image.SCALE_SMOOTH);
                personajeAnimado.setIcon(new ImageIcon(img));
                frame = (frame + 1) % frames.length;
            }
        });
        animacion.start();
    }

    /* =============== BOTÓN ARCADE =============== */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 0, 0, 90));
                g2.fillRoundRect(6, 6, getWidth() - 6, getHeight() - 6, 40, 40);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 6, getHeight() - 6, 40, 40);

                super.paintComponent(g);
            }
        };

        boton.setFont(new Font("Consolas", Font.BOLD, 34));
        boton.setForeground(Color.BLACK);
        boton.setBackground(Color.YELLOW);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(500, 80));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(255, 220, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Color.YELLOW);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boton.setBackground(new Color(255, 200, 0));
            }
        });

        return boton;
    }

    public static void main(String[] args) {
        new PnatallaInicio().setVisible(true);
    }
}
