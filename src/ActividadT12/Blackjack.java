package ActividadT12;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class Blackjack extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private int puntosJugador = 0;
	private int puntosBanca = 0;

	private JLabel lblPuntosJugador;
	private JLabel lblPuntosBanca;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Blackjack frame = new Blackjack();
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Blackjack() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		// ================= TITULO =================
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.BLACK);
		contentPane.add(panel);

		JLabel lblNewLabel = new JLabel("BLACKJACK", JLabel.CENTER);
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("Century Schoolbook", Font.BOLD, 70));
		lblNewLabel.setPreferredSize(new Dimension(400, 80));
		panel.add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel(new GridLayout(3, 0));
		panel_1.setBackground(Color.BLACK);
		panel.add(panel_1, BorderLayout.CENTER);

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

		panel_1.add(contenedorBanca);

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

		panel_1.add(contenedorJugador);

		// ================= BOTONES =================
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(Color.BLACK);
		panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		panel_1.add(panelBotones);

		JButton btnPedir = new JButton("PEDIR");
		btnPedir.setFont(new Font("Verdana", Font.BOLD, 30));
		btnPedir.setPreferredSize(new Dimension(400, 80));
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
			}
		});
		panelBotones.add(btnPedir);

		JButton btnPlantarse = new JButton("PLANTARSE");
		btnPlantarse.setFont(new Font("Verdana", Font.BOLD, 30));
		btnPlantarse.setPreferredSize(new Dimension(400, 80));
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
				resultado = "GANAS 🟢";
			} else if (puntosJugador == puntosBanca) {
				resultado = "EMPATE ⚖";
			} else {
				resultado = "PIERDES 🔴";
			}
			JOptionPane.showMessageDialog(this, resultado);
		});
		panelBotones.add(btnPlantarse);

		JButton btnIniciar = new JButton("INICIAR");
		btnIniciar.setFont(new Font("Verdana", Font.BOLD, 30));
		btnIniciar.setPreferredSize(new Dimension(400, 80));
		btnIniciar.addActionListener(e -> {
			panelTusCartas.removeAll();
			panelCartasBanca.removeAll();
			panelTusCartas.repaint();
			panelCartasBanca.repaint();

			puntosJugador = 0;
			puntosBanca = 0;
			lblPuntosJugador.setText("Jugador: 0");
			lblPuntosBanca.setText("Banca: 0");

			btnPedir.setEnabled(true);
		});
		panelBotones.add(btnIniciar);
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
