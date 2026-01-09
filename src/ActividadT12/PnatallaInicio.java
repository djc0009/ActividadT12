package ActividadT12;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class PnatallaInicio extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel mainPanel;
    private java.util.List<Carta> cartas;
    private java.util.List<Particula> particulas;
    private java.util.List<Particula> particulasLateral;
    private JLabel titulo;
    private Random rand = new Random();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PnatallaInicio frame = new PnatallaInicio();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PnatallaInicio() {
        setTitle("UNO - Pantalla de Inicio Profesional");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);

        cartas = new ArrayList<>();
        particulas = new ArrayList<>();
        particulasLateral = new ArrayList<>();

        // ===== PANEL PRINCIPAL CON FONDO ANIMADO =====
        mainPanel = new JPanel() {
            private float hue = 0f;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                // Fondo degradado dinámico
                Color color1 = Color.getHSBColor(hue, 0.8f, 1f);
                Color color2 = Color.getHSBColor((hue + 0.25f) % 1f, 0.8f, 1f);
                Color color3 = Color.getHSBColor((hue + 0.5f) % 1f, 0.8f, 1f);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                hue += 0.001f;
                if(hue > 1f) hue = 0f;

                // Cartas cayendo con rotación
                for (Carta c : cartas) c.dibujar(g2, getWidth(), getHeight());

                // Partículas centrales
                for (Particula p : particulas) p.dibujar(g2);

                // Partículas laterales
                for (Particula p : particulasLateral) p.dibujar(g2);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // ===== TÍTULO ANIMADO =====
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        titulo = new JLabel("UNO");
        titulo.setFont(new Font("Impact", Font.BOLD, 96));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("¡El clásico juego de cartas!");
        subtitle.setFont(new Font("Arial", Font.BOLD, 24));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(Box.createVerticalStrut(50));
        titlePanel.add(titulo);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitle);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // ===== BOTONES PRINCIPALES =====
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JButton btnJugar = crearBotonEpico("JUGAR", new Color(0, 200, 0));
        JButton btnInstrucciones = crearBotonEpico("INSTRUCCIONES", new Color(0, 150, 200));
        JButton btnOpciones = crearBotonEpico("OPCIONES", new Color(200, 180, 0));
        JButton btnSalir = crearBotonEpico("SALIR", new Color(200, 0, 0));

        gbc.gridy = 0; buttonPanel.add(btnJugar, gbc);
        gbc.gridy = 1; buttonPanel.add(btnInstrucciones, gbc);
        gbc.gridy = 2; buttonPanel.add(btnOpciones, gbc);
        gbc.gridy = 3; buttonPanel.add(btnSalir, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // ===== SIDEBARS LATERALES =====
        JPanel leftPanel = new JPanel() { public void paintComponent(Graphics g){ super.paintComponent(g);} };
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(120,0));
        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel() { public void paintComponent(Graphics g){ super.paintComponent(g);} };
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(120,0));
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // ===== FOOTER =====
        JLabel footer = new JLabel("© Proyecto UNO - Actividad T12 - Versión Profesional");
        footer.setForeground(Color.WHITE);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(footer, BorderLayout.SOUTH);

        // ===== ACCIONES DE BOTONES =====
        btnSalir.addActionListener(e -> System.exit(0));
        btnJugar.addActionListener(e -> JOptionPane.showMessageDialog(this, "¡Vamos a jugar!"));
        btnInstrucciones.addActionListener(e -> JOptionPane.showMessageDialog(this, "Instrucciones:\n- Cada jugador roba una carta...\n- ¡El objetivo es quedarse sin cartas!"));
        btnOpciones.addActionListener(e -> JOptionPane.showMessageDialog(this, "Opciones del juego: volumen, tema, modos..."));

        // ===== TIMER DE ANIMACIÓN =====
        Timer timer = new Timer(16, e -> {
            generarCartas();
            actualizarCartas();
            actualizarParticulas();
            efectoTitulo();
            mainPanel.repaint();
        });
        timer.start();
    }

    // ===== EFECTO DEL TÍTULO Y PARTICULAS =====
    private void efectoTitulo() {
        float brillo = (float)(0.5 + 0.5*Math.sin(System.currentTimeMillis()*0.005));
        titulo.setForeground(Color.getHSBColor(0.1f, 1f, brillo));

        if(rand.nextInt(5) == 0) particulasLateral.add(new Particula(rand.nextInt(120), 100+rand.nextInt(50)));
        if(rand.nextInt(5) == 0) particulasLateral.add(new Particula(1000-120+rand.nextInt(120),100+rand.nextInt(50)));
    }

    private void generarCartas() { if(rand.nextInt(10)<2) cartas.add(new Carta(rand.nextInt(mainPanel.getWidth()),-50)); }
    private void actualizarCartas() { cartas.removeIf(c->{c.y+=c.vel; return c.y>mainPanel.getHeight();}); }
    private void actualizarParticulas() {
        particulas.removeIf(p->{p.y+=p.velY; p.alpha-=0.02f; return p.alpha<=0;});
        particulasLateral.removeIf(p->{p.y+=p.velY; p.alpha-=0.02f; return p.alpha<=0;});
    }

    // ===== BOTONES ESTILO JUEGO =====
    private JButton crearBotonEpico(String texto, Color base){
        JButton boton=new JButton(texto){
            @Override protected void paintComponent(Graphics g){
                Graphics2D g2=(Graphics2D) g.create();
                if(getModel().isPressed()) g2.setColor(base.darker());
                else if(getModel().isRollover()) g2.setColor(base.brighter());
                else g2.setColor(base);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),30,30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        boton.setFocusPainted(false);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial Black", Font.BOLD,24));
        boton.setContentAreaFilled(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    // ===== CLASES INTERNAS =====
    private class Carta{
        int x,y,vel;
        double rot;
        Color color;
        Carta(int x,int y){ this.x=x; this.y=y; this.vel=3+rand.nextInt(3); rot=Math.toRadians(rand.nextInt(360)); 
            Color[] colores={Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE}; color=colores[rand.nextInt(colores.length)];}
        void dibujar(Graphics2D g2,int w,int h){
            AffineTransform old=g2.getTransform();
            g2.rotate(rot,x+20,y+30);
            g2.setColor(color);
            g2.fillRoundRect(x,y,40,60,10,10);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial",Font.BOLD,18));
            g2.drawString("UNO", x+5, y+35);
            g2.setTransform(old);
        }
    }

    private class Particula{
        int x,y,velY; float alpha;
        Particula(int x,int y){ this.x=x; this.y=y; this.alpha=1f; this.velY=1+rand.nextInt(2);}
        void dibujar(Graphics2D g2){ g2.setColor(new Color(1f,1f,1f,alpha)); g2.fillOval(x,y,6,6);}
    }
}
