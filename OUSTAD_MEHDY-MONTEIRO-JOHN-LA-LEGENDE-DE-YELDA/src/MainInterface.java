import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MainInterface extends JFrame {

    public MainInterface() {
        setTitle("Bienvenue dans La Légende de Yelda");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du bouton "Commencer le jeu"
        JButton startButton = new JButton("Bienvenue dans l'aventure !");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(); // Démarrer le jeu lorsque le bouton est cliqué
            }
        });

        // Ajout du bouton au centre de l'interface principale
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(startButton);
        add(panel);
    }

    private void startGame() {
        // Lancer le jeu en créant une instance de MainInterfaceWithGame
        dispose(); // Fermer l'écran d'accueil
        SwingUtilities.invokeLater(() -> {
            new MainInterfaceWithGame();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainInterface mainInterface = new MainInterface();
            mainInterface.setVisible(true);
        });
    }
}

class MainInterfaceWithGame extends JFrame implements KeyListener {
    private TileManager tileManager;
    private Dungeon dungeon;
    private Hero hero;
    private GameRender panel;
    private HealthBar healthBar;

    public MainInterfaceWithGame() {
        super();
        // Initialisation des instances
        tileManager = new TileManager(48, 48, "./image/tileSet.png");
        dungeon = new Dungeon("./gameData/level1.txt", tileManager);
        hero = Hero.getInstance();
        healthBar = new HealthBar();
        panel = new GameRender(dungeon, hero, healthBar);

        setTitle("La légende de Yelda");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(healthBar, BorderLayout.SOUTH);
        setVisible(true);
        setSize(new Dimension(dungeon.getWidth() * tileManager.getWidth(), dungeon.getHeight() * tileManager.getHeigth()));
        addKeyListener(this);

        // Jouer de la musique au lancement du jeu
        playMusic("./Musique/Title-Theme-The-Legend-of-Zelda-Ocarina-of-Time.wav");

        // Timer pour gérer les animations et les mouvements du personnage
        ActionListener animationTimer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                final double speed = 10;
                if (hero.isWalking()) {
                    switch (hero.getOrientation()) {
                        case LEFT:
                            hero.moveIfPossible(-speed, 0, dungeon);
                            break;
                        case RIGHT:
                            hero.moveIfPossible(speed, 0, dungeon);
                            break;
                        case UP:
                            hero.moveIfPossible(0, -speed, dungeon);
                            break;
                        case DOWN:
                            hero.moveIfPossible(0, speed, dungeon);
                            break;
                    }
                    panel.checkCollisions(); // Vérifier les collisions avec les pièges
                    healthBar.setHealthPercentage(hero.getHealth()); // Mettre à jour la barre de santé
                }
            }
        };
        Timer timer = new Timer(50, animationTimer);
        timer.start();
    }

    // Méthode pour jouer de la musique
    public void playMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            if (musicFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Fichier audio non trouvé : " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthodes de l'interface KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                hero.setOrientation(Orientation.LEFT);
                hero.setWalking(true);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setOrientation(Orientation.RIGHT);
                hero.setWalking(true);
                break;
            case KeyEvent.VK_UP:
                hero.setOrientation(Orientation.UP);
                hero.setWalking(true);
                break;
            case KeyEvent.VK_DOWN:
                hero.setOrientation(Orientation.DOWN);
                hero.setWalking(true);
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        hero.setWalking(false);
    }
}
