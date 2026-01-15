package ActividadT12;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Blackjack extends JFrame {

    // ===== MODELOS =====
    static class Carta {
        String palo, valor;
        int puntos;

        public Carta(String palo, String valor, int puntos) {
            this.palo = palo;
            this.valor = valor;
            this.puntos = puntos;
        }
    }

    static class Baraja {
        private Stack<Carta> cartas = new Stack<>();

        public Baraja() {
            String[] palos = {"♥", "♦", "♠", "♣"};
            String[] valores = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
            for (String palo : palos) {
                for (String valor : valores) {
                    int puntos;
                    if (valor.equals("A")) puntos = 11;
                    else if (valor.equals("J") || valor.equals("Q") || valor.equals("K")) puntos = 10;
                    else puntos = Integer.parseInt(valor);
                    cartas.add(new Carta(palo, valor, puntos));
                }
            }
            Collections.shuffle(cartas);
        }

        public Carta robar() { return cartas.pop(); }
    }

    static class Jugador {
        ArrayList<Carta> mano = new ArrayList<>();

        public void recibirCarta(Carta c) { mano.add(c); }

        public int getPuntaje() {
            int total = 0, ases = 0;
            for (Carta c : mano) {
                total += c.puntos;
                if (c.valor.equals("A")) ases++;
            }
            while (total > 21 && ases > 0) { total -= 10; ases--; }
            return total;
        }

        public void limpiar() { mano.clear(); }
    }

    // ===== VARIABLES =====
    private Baraja baraja;
    private Jugador jugador, dealer;
    private int dinero = 1000, apuesta = 100;

    private JPanel panelJugador, panelDealer;
    private JLabel lblDinero, lblEstado, lblPuntajeJugador, lblPuntajeDealer;
    private JButton btnPedir, btnPlantarse, btnNueva;

    // ===== CONSTRUCTOR =====
    public Blackjack() {
        setTitle("🎴 Blackjack Visual 🎴");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // pantalla completa
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(34,139,34));
        setLayout(new BorderLayout(20,20));

        jugador = new Jugador();
        dealer = new Jugador();

        // PANEL SUPERIOR: dinero y estado
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(new Color(0,100,0));
        lblDinero = new JLabel("💰 Dinero: $" + dinero);
        lblDinero.setForeground(Color.WHITE);
        lblDinero.setFont(new Font("Arial",Font.BOLD,50));

        lblEstado = new JLabel("Haz tu jugada");
        lblEstado.setForeground(Color.YELLOW);
        lblEstado.setFont(new Font("Arial",Font.BOLD,50));

        panelInfo.add(lblDinero);
        panelInfo.add(Box.createHorizontalStrut(50));
        panelInfo.add(lblEstado);

        add(panelInfo,BorderLayout.NORTH);

        // PANEL CENTRAL: cartas
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new GridLayout(2,1,10,50));
        panelCentro.setBackground(new Color(34,139,34));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        // PANEL DEALER
        panelDealer = new JPanel();
        panelDealer.setOpaque(false);
        panelDealer.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        lblPuntajeDealer = new JLabel("Puntos: 0");
        lblPuntajeDealer.setFont(new Font("Arial",Font.BOLD,40));
        lblPuntajeDealer.setForeground(Color.WHITE);
        panelDealer.add(lblPuntajeDealer);

        // PANEL JUGADOR
        panelJugador = new JPanel();
        panelJugador.setOpaque(false);
        panelJugador.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        lblPuntajeJugador = new JLabel("Puntos: 0");
        lblPuntajeJugador.setFont(new Font("Arial",Font.BOLD,40));
        lblPuntajeJugador.setForeground(Color.WHITE);
        panelJugador.add(lblPuntajeJugador);

        panelCentro.add(panelDealer);
        panelCentro.add(panelJugador);

        add(panelCentro,BorderLayout.CENTER);

        // BOTONES
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(0,100,0));
        btnPedir = new JButton("Pedir carta");
        btnPlantarse = new JButton("Plantarse");
        btnNueva = new JButton("Nueva ronda");
        styleButton(btnPedir);
        styleButton(btnPlantarse);
        styleButton(btnNueva);
        panelBotones.add(btnPedir);
        panelBotones.add(Box.createHorizontalStrut(50));
        panelBotones.add(btnPlantarse);
        panelBotones.add(Box.createHorizontalStrut(50));
        panelBotones.add(btnNueva);
        add(panelBotones,BorderLayout.SOUTH);

        iniciarRonda();
        acciones();

        setVisible(true);
    }

    private void styleButton(JButton b){
        b.setFont(new Font("Arial",Font.BOLD,40));
        b.setBackground(Color.ORANGE);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
    }

    private JPanel crearCarta(Carta c){
        JPanel carta = new JPanel();
        carta.setPreferredSize(new Dimension(150,220));
        carta.setBackground(Color.WHITE);
        carta.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        carta.setLayout(new BorderLayout());

        JLabel lblValor = new JLabel(c.valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial",Font.BOLD,50));
        lblValor.setForeground(c.palo.equals("♥")||c.palo.equals("♦")?Color.RED:Color.BLACK);

        JLabel lblPalo = new JLabel(c.palo, SwingConstants.CENTER);
        lblPalo.setFont(new Font("Arial",Font.BOLD,80));
        lblPalo.setForeground(c.palo.equals("♥")||c.palo.equals("♦")?Color.RED:Color.BLACK);

        carta.add(lblValor,BorderLayout.NORTH);
        carta.add(lblPalo,BorderLayout.CENTER);

        return carta;
    }

    private void actualizarPantalla(){
        panelJugador.removeAll();
        panelDealer.removeAll();

        panelDealer.add(lblPuntajeDealer);
        for(Carta c: dealer.mano) panelDealer.add(crearCarta(c));

        panelJugador.add(lblPuntajeJugador);
        for(Carta c: jugador.mano) panelJugador.add(crearCarta(c));

        // Actualizar puntajes
        lblPuntajeJugador.setText("Puntos: " + jugador.getPuntaje());
        lblPuntajeDealer.setText("Puntos: " + dealer.getPuntaje());

        lblDinero.setText("💰 Dinero: $" + dinero);

        panelJugador.revalidate();
        panelJugador.repaint();
        panelDealer.revalidate();
        panelDealer.repaint();
    }

    private void iniciarRonda(){
        baraja = new Baraja();
        jugador.limpiar();
        dealer.limpiar();

        jugador.recibirCarta(baraja.robar());
        jugador.recibirCarta(baraja.robar());
        dealer.recibirCarta(baraja.robar());

        lblEstado.setText("Tu turno");
        actualizarPantalla();
    }

    private void turnoDealer(){
        while(dealer.getPuntaje()<17) dealer.recibirCarta(baraja.robar());
        decidirGanador();
    }

    private void decidirGanador(){
        int pj = jugador.getPuntaje();
        int pd = dealer.getPuntaje();
        if(pj>21){ lblEstado.setText("😵 Te pasaste! Perdiste"); dinero-=apuesta; }
        else if(pd>21||pj>pd){ lblEstado.setText("🎉 ¡Ganaste!"); dinero+=apuesta; }
        else if(pj<pd){ lblEstado.setText("😢 Perdiste"); dinero-=apuesta; }
        else lblEstado.setText("🤝 Empate");
        actualizarPantalla();
    }

    private void acciones(){
        btnPedir.addActionListener(e->{
            jugador.recibirCarta(baraja.robar());
            actualizarPantalla();
            if(jugador.getPuntaje()>21){
                lblEstado.setText("😵 Te pasaste!");
                dinero-=apuesta;
                actualizarPantalla();
            }
        });
        btnPlantarse.addActionListener(e->turnoDealer());
        btnNueva.addActionListener(e->iniciarRonda());
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(()->new Blackjack());
    }
}
