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
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Juego2_2 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int contador = 0;
    private JButton primeraCarta = null;
    private JButton segundaCarta = null;
    private boolean bloqueo = false;

    private ArrayList<String> cartas;

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

    public Juego2_2() {
        configurarVentana();
        crearTitulo();
        prepararCartas();
        crearBotones();
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
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

    private void prepararCartas() {
        cartas = new ArrayList<>();
        //Almacen de imagenes
        String[] imagenes = {"hippo.png", "oso.png", "elefante.png", "panda.png", "mono.png"};

        for (String img : imagenes) {
            cartas.add(img);
            cartas.add(img);
        }
        //Barajar cartas
        Collections.shuffle(cartas);
    }

    private void crearBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 5, 5, 5));
        contentPane.add(panel, BorderLayout.CENTER);
        {
        	JPanel panel_1 = new JPanel();
        	contentPane.add(panel_1, BorderLayout.SOUTH);
        	{
        		JButton btnNewButton_1 = new JButton("Volver");
        		btnNewButton_1.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				dispose();
        			}
        		});
        		panel_1.add(btnNewButton_1);
        	}
        	{
        		JButton btnNewButton = new JButton("Modo Dificil");
        		btnNewButton.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        			}
        		});
        		panel_1.add(btnNewButton);
        	}
        }
        //Bucle que crea 10 botones y le selecciona una imagen
        for (int i = 0; i < 10; i++) {
            JButton boton = new JButton("Carta");
            boton.putClientProperty("imagen", cartas.get(i));
            panel.add(boton);

            boton.addActionListener(e -> manejarClick(boton));
        }
    }

    private void manejarClick(JButton boton) {
        if (bloqueo) return; 
        mostrarImagen(boton);

        if (primeraCarta == null) {
            primeraCarta = boton;
        } else {
            segundaCarta = boton;
            comprobarPareja();
        }
    }

    private void mostrarImagen(JButton boton) {
        String ruta = "src/images/" + boton.getClientProperty("imagen");
        boton.setIcon(new ImageIcon(ruta));
        boton.setText("");
    }

    private void ocultarImagen(JButton boton) {
        boton.setIcon(null);
        boton.setText("Carta");
    }

    private void comprobarPareja() {
        bloqueo = true;

        String img1 = (String) primeraCarta.getClientProperty("imagen");
        String img2 = (String) segundaCarta.getClientProperty("imagen");

        if (img1.equals(img2)) {
            primeraCarta = null;
            segundaCarta = null;
            bloqueo = false;
            contador = contador + 1;
            if (contador == 5) {
            	mostrarMensajeFinal();
            }
            
        } else {
            Timer timer = new Timer(1000, e -> {
                ocultarImagen(primeraCarta);
                ocultarImagen(segundaCarta);
                primeraCarta = null;
                segundaCarta = null;
                bloqueo = false;
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

	private void mostrarMensajeFinal() {
		javax.swing.JOptionPane.showMessageDialog( this, "¡Enhorabuena, has terminado el juego!", "Juego completado", javax.swing.JOptionPane.INFORMATION_MESSAGE );
		
	}
}
