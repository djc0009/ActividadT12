package ActividadT12;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.ImageIcon;

public class Juego2 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

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
        crearTitulo();
        crearBotones();
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

    private void crearTitulo() {
        JLabel lblTitulo = new JLabel("Bienvenido a Memory Davante");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblTitulo, BorderLayout.NORTH);
    }

    private void crearBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 5, 0, 0));
        contentPane.add(panel, BorderLayout.CENTER);

        // Crear 10 botones con imágenes
        for (int i = 1; i <= 10; i++) {
            JButton boton = new JButton("Carta " + i);
            panel.add(boton);
            mostrarImagenAlClic(boton, "src/ActividadT12/foto" + i + ".png");
        }
    }

    private void mostrarImagenAlClic(JButton boton, String rutaImagen) {
        boton.addActionListener(e -> {
            ImageIcon icono = new ImageIcon(rutaImagen);
            boton.setIcon(icono);
            boton.setText("");
        });
    }
}
