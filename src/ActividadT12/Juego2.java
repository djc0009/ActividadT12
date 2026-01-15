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

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
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
		
		int numImagenes = 5;
		ImageIcon[] ArrayImagenes = new ImageIcon[numImagenes];
		String [] rutas = {"elefante.png", "hippo.png", "mono.png", "oso.png", "panda.png"};
		

		JButton btnNewButton = new JButton("New button");
		panel.add(btnNewButton);
		mostrarImagenAlClic(btnNewButton, "src/ActividadT12/foto1.png");

		JButton btnNewButton_1 = new JButton("New button");
		panel.add(btnNewButton_1);
		mostrarImagenAlClic(btnNewButton_1, "src/ActividadT12/foto2.png");

		JButton btnNewButton_2 = new JButton("New button");
		panel.add(btnNewButton_2);
		mostrarImagenAlClic(btnNewButton_2, "src/ActividadT12/foto3.png");

		JButton btnNewButton_3 = new JButton("New button");
		panel.add(btnNewButton_3);
		mostrarImagenAlClic(btnNewButton_3, "src/ActividadT12/foto4.png");

		JButton btnNewButton_4 = new JButton("New button");
		panel.add(btnNewButton_4);
		mostrarImagenAlClic(btnNewButton_4, "src/ActividadT12/foto5.png");

		JButton btnNewButton_5 = new JButton("New button");
		panel.add(btnNewButton_5);
		mostrarImagenAlClic(btnNewButton_5, "src/ActividadT12/foto6.png");

		JButton btnNewButton_6 = new JButton("New button");
		panel.add(btnNewButton_6);
		mostrarImagenAlClic(btnNewButton_6, "src/ActividadT12/foto7.png");

		JButton btnNewButton_7 = new JButton("New button");
		panel.add(btnNewButton_7);
		mostrarImagenAlClic(btnNewButton_7, "src/ActividadT12/foto8.png");

		JButton btnNewButton_8 = new JButton("New button");
		panel.add(btnNewButton_8);
		mostrarImagenAlClic(btnNewButton_8, "src/ActividadT12/foto9.png");

		JButton btnNewButton_9 = new JButton("New button");
		panel.add(btnNewButton_9);
		mostrarImagenAlClic(btnNewButton_9, "src/ActividadT12/foto10.png");
	}

	private void mostrarImagenAlClic(JButton boton, String rutaImagen) {
		boton.addActionListener(e -> {
			ImageIcon icono = new ImageIcon(rutaImagen);
			boton.setIcon(icono);
			boton.setText("");
		});
	}
}
