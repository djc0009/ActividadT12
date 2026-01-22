package ActividadT12;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class HigherOrLower extends JFrame {

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

    private static final Color PRIMARY = new Color(33, 150, 243);
    private static final Color SUCCESS = new Color(76, 175, 80);
    private static final Color DANGER = new Color(244, 67, 54);

    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font SCORE_FONT = new Font("Segoe UI", Font.BOLD, 14);

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

    public HigherOrLower() {
        setTitle("Higher or Lower - Estadios de Fútbol ⚽");
        setSize(600, 400);
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

    private void initData() {
        stadiums = new ArrayList<>();

        // Ruta relativa desde src/images/
        String imgPath = "/images/atletico.png";

        stadiums.add(new Stadium("Camp Nou - Barcelona", 99354, imgPath));
        stadiums.add(new Stadium("Santiago Bernabeu - Real Madrid", 81044, imgPath));
        stadiums.add(new Stadium("Wembley Stadium - Inglaterra", 90000, imgPath));
        stadiums.add(new Stadium("Allianz Arena - Bayern Munich", 75000, imgPath));
        stadiums.add(new Stadium("Old Trafford - Manchester United", 74879, imgPath));
        stadiums.add(new Stadium("San Siro - Milan", 80018, imgPath));
        stadiums.add(new Stadium("Metropolitano - Atletico Madrid", 68456, imgPath));
        stadiums.add(new Stadium("Sanchez Pizjuan - Sevilla", 43883, imgPath));
        stadiums.add(new Stadium("Los Carmenes - Granada", 20000, imgPath));
    }

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
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JButton higherBtn = createButton("Más asientos 🔼", SUCCESS);
        JButton lowerBtn = createButton("Menos asientos 🔽", DANGER);

        higherBtn.addActionListener(e -> checkAnswer(true));
        lowerBtn.addActionListener(e -> checkAnswer(false));

        buttonsPanel.add(higherBtn);
        buttonsPanel.add(lowerBtn);

        backgroundLabel.add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void nextRound() {
        if (current == null) current = getRandomStadium();

        next = getRandomStadium();
        while (next == current) next = getRandomStadium();

        currentLabel.setText(current.name + " tiene " + format(current.capacity) + " asientos");
        nextLabel.setText("👉 " + next.name + " tiene ¿más o menos asientos?");
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
                    "❌ Has perdido\n\n" + next.name + " tiene " + format(next.capacity) + " asientos" +
                            "\n\nPuntuación final: " + score,
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

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
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
        System.out.println("Intentando cargar: " + path);

        java.net.URL imgURL = getClass().getResource(path);
        System.out.println("URL encontrada: " + imgURL);

        if (imgURL == null) {
            System.err.println("ERROR: No se encontró la imagen en el classpath: " + path);
            backgroundLabel.setIcon(null);
            return;
        }

        ImageIcon icon = new ImageIcon(imgURL);

        int w = backgroundLabel.getWidth();
        int h = backgroundLabel.getHeight();

        System.out.println("Tamaño backgroundLabel: " + w + "x" + h);

        if (w == 0 || h == 0) {
            backgroundLabel.setIcon(icon);
            return;
        }

        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(img));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HigherOrLower().setVisible(true));
    }
}
