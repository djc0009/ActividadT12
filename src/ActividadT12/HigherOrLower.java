package ActividadT12;



import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;

import java.util.Random;



public class HigherOrLower extends JFrame {



	//Creamos la clase de estadio con sus variables

    static class Stadium {

        String name;

        int capacity;

        String imagePath;



        Stadium(String name, int capacity, String imagePath) {

            this.name = name;

            this.capacity = capacity;

            this.imagePath = imagePath;

        }

    }

    

    //Clase ShadowLabel que extiende de JLabel, esta clase hace que el texto de la imagen sea visible

    static class ShadowLabel extends JLabel {



        private final Color shadowColor = new Color(0, 0, 0, 180);

        private final int shadowOffset = 4;



        public ShadowLabel(String text, int align) {

            super(text, align);

            setOpaque(false);

        }

        // Funcion Paint para dibujar el texto con sombra

        @Override

        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setFont(getFont());



            FontMetrics fm = g2.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(getText())) / 2;

            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();



            g2.setColor(shadowColor);

            g2.drawString(getText(), x + shadowOffset, y + shadowOffset);



            g2.setColor(getForeground());

            g2.drawString(getText(), x, y);



            g2.dispose();

        }

    }

    // Variables finales para definir colores y el fuente de las letras

    private static final Color PRIMARY = new Color(255, 215, 0);

    private static final Color SUCCESS = new Color(76, 175, 80);

    private static final Color DANGER = new Color(244, 67, 54);



    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 70);

    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 65);

    private static final Font SCORE_FONT = new Font("Segoe UI", Font.BOLD, 58);

    // Creamos las variables principales

    private ArrayList<Stadium> stadiums;

    // Lista con los dos estadios a comparar, current el que esta actualmente, next el que se esta comparando

    private Stadium current;

    private Stadium next;

    

    private JLabel currentLabel;

    private JLabel nextLabel;

    private JLabel scoreLabel;

    private JLabel resultLabel;



    private JLabel backgroundLabel;

    private JPanel mainPanel;

    // Creamos la variable que llevara la cuenta de los puntos que consigue el usuario

    private int score = 0;

    private final Random random = new Random();

    // Constructor, donde se crea el titulo, el tamaño de la ventana, el abrir y cerrar la ventana

    public HigherOrLower() {

        setTitle("Higher or Lower - Estadios ⚽;

        setSize(1920, 1080);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);



        initData();

        initUI();



        addComponentListener(new java.awt.event.ComponentAdapter() {

            public void componentResized(java.awt.event.ComponentEvent evt) {

                if (current != null) {

                    updateBackground(current.imagePath);

                }

            }

        });



        SwingUtilities.invokeLater(this::nextRound);

    }

    // Aqui creamos el contenido de la lista de estadios con todos los estadios que vamos a añadirm junto con su capacidad y su foto

    private void initData() {

        stadiums = new ArrayList<>();



        stadiums.add(new Stadium("Camp Nou - Barcelona", 99354, "/images/camp_nou.png));

        stadiums.add(new Stadium("Bernabeu - Real Madrid", 81044, "/images/bernabeu.png"));

        stadiums.add(new Stadium("Wembley Stadium - Inglaterra", 90000, "/images/wembley.png"));

        stadiums.add(new Stadium("Allianz Arena - Bayern Munich", 75000, "/images/allianz.png"));

        stadiums.add(new Stadium("Old Trafford - M.United", 74879, "/images/old_trafford.png"));

        stadiums.add(new Stadium("San Siro - Milan", 80018, "/images/san_siro.png"));

        stadiums.add(new Stadium("Metropolitano-Atletico Madrid", 68456, "/images/metropolitano.png"));

        stadiums.add(new Stadium("Sanchez Pizjuan - Sevilla", 43883, "/images/pizjuan.png"));

        stadiums.add(new Stadium("Los Carmenes - Granada", 20000, "/images/carmenes.png"));

    }



    // Inicializamos la interfaz de usuario

    private void initUI() {



        backgroundLabel = new JLabel();

        backgroundLabel.setLayout(new BorderLayout());



        mainPanel = new JPanel(new BorderLayout());

        mainPanel.setOpaque(false);



        setContentPane(backgroundLabel);

        backgroundLabel.add(mainPanel);



        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        centerPanel.setOpaque(false);

        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));



        currentLabel = createLabel("", TITLE_FONT);

        nextLabel = createLabel("", TEXT_FONT);

        scoreLabel = createLabel("Puntuación: 0", SCORE_FONT);

        resultLabel = createLabel("", TEXT_FONT);



        centerPanel.add(currentLabel);

        centerPanel.add(nextLabel);

        centerPanel.add(scoreLabel);

        centerPanel.add(resultLabel);



        mainPanel.add(centerPanel, BorderLayout.CENTER);



        JPanel buttonsPanel = new JPanel();

        buttonsPanel.setOpaque(false);

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));



        JButton higherBtn = createButton("Más asientos", SUCCESS);

        JButton lowerBtn = createButton("Menos asientos", DANGER);



        higherBtn.addActionListener(e -> checkAnswer(true));

        lowerBtn.addActionListener(e -> checkAnswer(false));



        buttonsPanel.add(higherBtn);

        buttonsPanel.add(lowerBtn);



        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);



        JButton exitButton = createButton("Salir", Color.RED);

        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 28));

    
        
        
        exitButton.addActionListener(e -> {
            dispose();
            EventQueue.invokeLater(() -> new PnatallaInicio().setVisible(true));
        });
        		
        



        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        exitPanel.setOpaque(false);

        exitPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 20));

        exitPanel.add(exitButton);



        mainPanel.add(exitPanel, BorderLayout.EAST);

    }

    // En caso de que no haya estadio actual crea un aleatorio y luego elige otro, tambien actualiza los textos y la imagen

    private void nextRound() {

        if (current == null) current = getRandomStadium();



        next = getRandomStadium();

        while (next == current) next = getRandomStadium();



        currentLabel.setText(current.name + " tiene " + format(current.capacity) + " asientos");

        nextLabel.setText(next.name + " tiene ¿más o menos asientos?");

        resultLabel.setText("");



        updateBackground(current.imagePath);

    }

    // Esta funcion comprueba si el jugador ha acertado

    private void checkAnswer(boolean higher) {

        boolean correct = higher

                ? next.capacity >= current.capacity

                : next.capacity <= current.capacity;



        if (correct) {

            score++;

            resultLabel.setForeground(SUCCESS);

            resultLabel.setText("✔ ¡Correcto! (" + format(next.capacity) + " asientos)");

            scoreLabel.setText("Puntuación: " + score);

            current = next;

            SwingUtilities.invokeLater(this::nextRound);

        } else {

            JOptionPane.showMessageDialog(

                    this,

                    "❌ Has perdido\n\n" + next.name + " tiene " + format(next.capacity) +

                            " asientos\n\nPuntuación final: " + score,

                    "Fin del juego",

                    JOptionPane.INFORMATION_MESSAGE

            );

            resetGame();

        }

    }

    // Hace un reset completo del juego, es decir, la puntuacion empieza de nuevo ademas de crear dos nuevos estadios llamando a next round e igualando la variable current a null

    private void resetGame() {

        score = 0;

        current = null;

        scoreLabel.setText("Puntuación: 0");

        nextRound();

    }



    private JLabel createLabel(String text, Font font) {

        ShadowLabel label = new ShadowLabel(text, SwingConstants.CENTER);

        label.setFont(font);

        label.setForeground(PRIMARY);

        return label;

    }

    // Funcion que crea los botones que recoge el texto del boton y el color

    private JButton createButton(String text, Color bg) {

        JButton btn = new JButton(text);

        btn.setFont(TEXT_FONT);

        btn.setFocusPainted(false);

        btn.setBackground(bg);

        btn.setForeground(Color.WHITE);

        btn.setOpaque(true);

        btn.setContentAreaFilled(true);

        btn.setBorderPainted(false);

        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        return btn;

    }

    //Getter de los estadios aleatorios

    private Stadium getRandomStadium() {

        return stadiums.get(random.nextInt(stadiums.size()));

    }



    private String format(int number) {

        return String.format("%,d", number).replace(',', '.');

    }

    // Funcion que cambia el fondo de la aplicacion con el estadio actual (current)

    private void updateBackground(String path) {

        java.net.URL imgURL = getClass().getResource(path);

        if (imgURL == null) return;



        Image img = new ImageIcon(imgURL).getImage().getScaledInstance(

                getWidth(),

                getHeight(),

                Image.SCALE_SMOOTH

        );

        backgroundLabel.setIcon(new ImageIcon(img));

    }



    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new HigherOrLower().setVisible(true));

    }

}