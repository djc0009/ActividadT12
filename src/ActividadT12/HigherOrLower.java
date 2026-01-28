package ActividadT12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class HigherOrLower extends JFrame {
    /* =======================
    CLASE STADIUM
 ======================== */
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
 /* =======================
    LABEL CON SOMBRA
 ======================== */
 static class ShadowLabel extends JLabel {
     private final Color shadowColor = new Color(0, 0, 0, 180);
     private final int shadowOffset = 4;
     public ShadowLabel(String text, int align) {
         super(text, align);
         setOpaque(false);
     }

     @Override
     protected void paintComponent(Graphics g) {
         Graphics2D g2 = (Graphics2D) g.create();
         g2.setFont(getFont());
         FontMetrics fm = g2.getFontMetrics();
         int x = (getWidth() - fm.stringWidth(getText())) / 2;
         int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
         // Sombra
         g2.setColor(shadowColor);
         g2.drawString(getText(), x + shadowOffset, y + shadowOffset);
         // Texto principal
         g2.setColor(getForeground());
         g2.drawString(getText(), x, y);
         g2.dispose();
     }
 }
 /* =======================
    CONSTANTES VISUALES
 ======================== */
 private static final Color PRIMARY = new Color(255, 215, 0); // Oro
 private static final Color SUCCESS = new Color(76, 175, 80);
 private static final Color DANGER = new Color(244, 67, 54);
 private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 70);
 private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 65);
 private static final Font SCORE_FONT = new Font("Segoe UI", Font.BOLD, 58);
 /* =======================
    VARIABLES
 ======================== */
 private ArrayList<Stadium> stadiums;
 private Stadium current;

 private Stadium next;



 private JLabel currentLabel;

 private JLabel nextLabel;

 private JLabel scoreLabel;

 private JLabel resultLabel;

 private JLabel backgroundLabel;



 private int score = 0;

 private final Random random = new Random();



 /* =======================

    CONSTRUCTOR

 ======================== */

 public HigherOrLower() {

     setTitle("Higher or Lower - Estadios de Fútbol ⚽");

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



 /* =======================

    DATOS

 ======================== */

 private void initData() {

     stadiums = new ArrayList<>();



     stadiums.add(new Stadium("Camp Nou - Barcelona", 99354, "/images/camp_nou.png"));

     stadiums.add(new Stadium("Santiago Bernabeu - Real Madrid", 81044, "/images/bernabeu.png"));

     stadiums.add(new Stadium("Wembley Stadium - Inglaterra", 90000, "/images/wembley.png"));

     stadiums.add(new Stadium("Allianz Arena - Bayern Munich", 75000, "/images/allianz.png"));

     stadiums.add(new Stadium("Old Trafford - Manchester United", 74879, "/images/old_trafford.png"));

     stadiums.add(new Stadium("San Siro - Milan", 80018, "/images/san_siro.png"));

     stadiums.add(new Stadium("Metropolitano - Atletico Madrid", 68456, "/images/metropolitano.png"));

     stadiums.add(new Stadium("Sanchez Pizjuan - Sevilla", 43883, "/images/pizjuan.png"));

     stadiums.add(new Stadium("Los Carmenes - Granada", 20000, "/images/carmenes.png"));

 }



 /* =======================

    UI

 ======================== */

 private void initUI() {

     backgroundLabel = new JLabel();

     backgroundLabel.setLayout(new BorderLayout());

     setContentPane(backgroundLabel);



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



     backgroundLabel.add(centerPanel, BorderLayout.CENTER);



     JPanel buttonsPanel = new JPanel();

     buttonsPanel.setOpaque(false);

     buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));



     JButton higherBtn = createButton("Más asientos", SUCCESS);

     JButton lowerBtn = createButton("Menos asientos", DANGER);



     higherBtn.addActionListener(e -> checkAnswer(true));

     lowerBtn.addActionListener(e -> checkAnswer(false));



     buttonsPanel.add(higherBtn);

     buttonsPanel.add(lowerBtn);



     backgroundLabel.add(buttonsPanel, BorderLayout.SOUTH);

  

     JButton exitButton = new JButton("Salir");

     exitButton.addActionListener(new ActionListener() {

     	public void actionPerformed(ActionEvent e) {
     		dispose();
            new PnatallaInicio().setVisible(true);
     	}

     });

     exitButton.setFont(new Font("Segoe UI", Font.BOLD, 28));

     exitButton.setFocusPainted(false);

     exitButton.setBackground(new Color(255, 0, 0));

     exitButton.setForeground(Color.WHITE);

     exitButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));



     JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

     exitPanel.setOpaque(false);

     exitPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 20));

     exitPanel.add(exitButton);



     backgroundLabel.add(exitPanel, BorderLayout.EAST);



 }



 /* =======================

    LÓGICA DEL JUEGO

 ======================== */

 private void nextRound() {

     if (current == null) current = getRandomStadium();



     next = getRandomStadium();

     while (next == current) next = getRandomStadium();



     currentLabel.setText(current.name + " tiene " + format(current.capacity) + " asientos");

     nextLabel.setText(next.name + " tiene ¿más o menos asientos?");

     resultLabel.setText("");



     updateBackground(current.imagePath);

 }



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



 private void resetGame() {

     score = 0;

     current = null;

     scoreLabel.setText("Puntuación: 0");

     nextRound();

 }



 /* =======================

    UTILIDADES

 ======================== */

 private JLabel createLabel(String text, Font font) {

     ShadowLabel label = new ShadowLabel(text, SwingConstants.CENTER);

     label.setFont(font);

     label.setForeground(PRIMARY);

     return label;

 }



 private JButton createButton(String text, Color bg) {

     JButton btn = new JButton(text);

     btn.setFont(TEXT_FONT);

     btn.setFocusPainted(false);

     btn.setBackground(bg);

     btn.setForeground(Color.WHITE);

     btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

     return btn;

 }



 private Stadium getRandomStadium() {

     return stadiums.get(random.nextInt(stadiums.size()));

 }



 private String format(int number) {

     return String.format("%,d", number).replace(',', '.');

 }



 private void updateBackground(String path) {

     java.net.URL imgURL = getClass().getResource(path);

     if (imgURL == null) return;



     ImageIcon icon = new ImageIcon(imgURL);

     Image img = icon.getImage().getScaledInstance(

             backgroundLabel.getWidth(),

             backgroundLabel.getHeight(),

             Image.SCALE_SMOOTH

     );

     backgroundLabel.setIcon(new ImageIcon(img));

 }



 /* =======================

    MAIN

 ======================== */

 public static void main(String[] args) {

     SwingUtilities.invokeLater(() -> new HigherOrLower().setVisible(true));

 }
}
