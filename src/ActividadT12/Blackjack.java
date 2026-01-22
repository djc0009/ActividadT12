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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Blackjack extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Blackjack frame = new Blackjack();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Blackjack() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("BLACKJACK\r\n");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setFont(new Font("Century Schoolbook", Font.BOLD, 70));
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel.setPreferredSize(new Dimension(400, 80));
		panel.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(3, 0, 0, 0));
		
		JPanel panelCartasBanca = new JPanel();
		panelCartasBanca.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		panelCartasBanca.setBackground(Color.BLACK);
		panel_1.add(panelCartasBanca);
		
		JPanel panelTusCartas = new JPanel();
		panelTusCartas.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		panelTusCartas.setBackground(Color.BLACK);
		panel_1.add(panelTusCartas);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0));
		panel_2.setBackground(Color.BLACK);
		panel_1.add(panel_2);
		
		JButton btnNewButton = new JButton("PEDIR");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 30));
		btnNewButton.addActionListener(e -> {
		    int carta = (int)(Math.random() * 10) + 1; // carta aleatoria 1-10
		    puntosJugador += carta;

		    panelTusCartas.add(crearCarta(carta));
		    panelTusCartas.revalidate();
		    panelTusCartas.repaint();

		    if (puntosJugador > 21) {
		        JOptionPane.showMessageDialog(this, "PIERDES Te has pasado con " + puntosJugador + " puntos");
		        
		    }
		});

		btnNewButton.setPreferredSize(new Dimension(400, 80));
		panel_2.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("PLANTARSE");
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setFont(new Font("Verdana", Font.BOLD, 30));
		btnNewButton_1.addActionListener(e -> {
		    // La banca juega sola
		    while (puntosBanca < 17) {
		        int cartaBanca = (int) (Math.random() * 10) + 1;
		        puntosBanca += cartaBanca;

		        // Crea un JLabel NUEVO cada vez dentro del bucle
		        JLabel nuevaCarta = new JLabel(String.valueOf(cartaBanca), JLabel.CENTER);
		        nuevaCarta.setForeground(Color.RED);
		        nuevaCarta.setPreferredSize(new Dimension(150, 190));
		        nuevaCarta.setFont(new Font("Arial", Font.BOLD, 50));
		        nuevaCarta.setOpaque(true);
		        nuevaCarta.setBackground(Color.WHITE);
		        nuevaCarta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		        panelCartasBanca.add(nuevaCarta);
		        panelCartasBanca.revalidate();
		        panelCartasBanca.repaint();
		    }

		    // Comparar resultados
		    String mensaje;
		    if (puntosBanca > 21) {
		        mensaje = "GANAS La banca se paso (" + puntosBanca + ") ";
		    } else if (puntosJugador > puntosBanca) {
		        mensaje = "GANAS Jugador: " + puntosJugador + ", Banca: " + puntosBanca;
		    } else if (puntosJugador == puntosBanca) {
		        mensaje = "Empate. Jugador: " + puntosJugador + ", Banca: " + puntosBanca;
		    } else {
		        mensaje = "PIERDES. Jugador: " + puntosJugador + ", Banca: " + puntosBanca;
		    }

		    JOptionPane.showMessageDialog(this, mensaje);
		    
		});


		btnNewButton_1.setPreferredSize(new Dimension(400, 80));
		panel_2.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("INICIAR");
		btnNewButton_2.setBackground(Color.LIGHT_GRAY);
		btnNewButton_2.setFont(new Font("Verdana", Font.BOLD, 30));
		btnNewButton_2.addActionListener(e -> {
		    panelTusCartas.removeAll();
		    panelCartasBanca.removeAll();
		    panelTusCartas.repaint();
		    panelCartasBanca.repaint();

		    puntosJugador = 0;
		    puntosBanca = 0;

		    btnNewButton.setEnabled(true);
		    btnNewButton_1.setEnabled(true);
		});

		btnNewButton_2.setPreferredSize(new Dimension(400, 80));
		panel_2.add(btnNewButton_2);

		
		
		
	}

	private int puntosJugador = 0;
	private int puntosBanca = 0;

	private JLabel crearCarta(int valor) {
	    JLabel carta = new JLabel(String.valueOf(valor), JLabel.CENTER);
	    carta.setForeground(Color.GREEN);
	    carta.setPreferredSize(new Dimension(150, 190)); // tamaño de la carta
	    carta.setFont(new Font("Arial", Font.BOLD, 50));
	    carta.setOpaque(true);
	    carta.setBackground(Color.WHITE);
	    carta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	    return carta;
	}
	

}
