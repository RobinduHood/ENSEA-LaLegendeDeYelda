import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameRender extends JPanel {
    private Dungeon dungeon;
    private Hero hero;
    private HealthBar healthBar;
    private boolean gameOver = false; // Variable pour indiquer si le jeu est terminé
    private JButton restartButton; // Bouton "Recommencer"

    // Position initiale du héros
    private int initialHeroX;
    private int initialHeroY;

    // Constructeur prenant Dungeon, Hero et HealthBar
    public GameRender(Dungeon dungeon, Hero hero, HealthBar healthBar) {
        this.dungeon = dungeon;
        this.hero = hero;
        this.healthBar = healthBar;

        // Enregistrer la position initiale du héros
        this.initialHeroX = hero.getX();
        this.initialHeroY = hero.getY();

        // Lancer le timer pour infliger des dégâts périodiquement
        Timer trapTimer = new Timer(5000, e -> {
            if (!gameOver) { // Vérifier si le jeu n'est pas terminé
                for (Trap trap : dungeon.getTraps()) {
                    // Vérifier la collision entre le héros et chaque piège
                    if (trap.getHitBox().intersect(hero.getHitBox())) {
                        // Réduire la santé du héros de 20%
                        hero.reduceHealth(20);
                        // Mettre à jour la barre de santé
                        healthBar.setHealthPercentage(hero.getHealth());
                        // Mettre le héros en invincibilité pendant 1 seconde pour éviter les dégâts répétés
                        hero.setInvincibleForTime(1000);
                        // Vérifier si le héros est mort
                        if (hero.getHealth() <= 0) {
                            gameOver(); // Indiquer que le jeu est terminé
                        }
                    }
                }
            }
        });
        trapTimer.start();
    }

    // Méthode pour vérifier les collisions avec les pièges
    void checkCollisions() {
        if (!gameOver) { // Vérifier si le jeu n'est pas terminé
            for (Trap trap : dungeon.getTraps()) {
                // Vérifier la collision entre le héros et chaque piège
                if (trap.getHitBox().intersect(hero.getHitBox())) {
                    // Réduire la santé du héros de 20%
                    hero.reduceHealth(20);
                    // Mettre à jour la barre de santé
                    healthBar.setHealthPercentage(hero.getHealth());
                    // Mettre le héros en invincibilité pendant 1 seconde pour éviter les dégâts répétés
                    hero.setInvincibleForTime(1000);
                    // Vérifier si le héros est mort
                    if (hero.getHealth() <= 0) {
                        gameOver(); // Indiquer que le jeu est terminé
                    }
                }
            }
        }
    }

    // Méthode pour indiquer que le jeu est terminé
    private void gameOver() {
        gameOver = true; // Indiquer que le jeu est terminé
        hero.resetHealth(); // Réinitialiser la santé du héros
        hero.setX(initialHeroX); // Remettre le héros à sa position initiale en X
        hero.setY(initialHeroY); // Remettre le héros à sa position initiale en Y
        repaint(); // Redessiner le panneau pour afficher le message "Game Over" et le bouton "Recommencer"
        addRestartButton(); // Ajouter le bouton "Recommencer"
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!gameOver) { // Si le jeu n'est pas terminé
            // Dessiner tous les éléments de la salle du donjon
            for (Things t : dungeon.getRenderList()) {
                t.draw(g);
            }

            // Vérifier les collisions avec les pièges
            checkCollisions();

            // Dessiner le héros
            hero.draw(g);
        } else { // Si le jeu est terminé
            // Dessiner un rectangle noir couvrant toute la fenêtre
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Dessiner le message "Game Over" au centre de l'écran
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String gameOverText = "Game Over";
            int textWidth = g.getFontMetrics().stringWidth(gameOverText);
            int textHeight = g.getFontMetrics().getHeight();
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - textHeight) / 2;
            g.drawString(gameOverText, x, y);

            // Dessiner le bouton "Recommencer"
            restartButton.setBounds(x, y + textHeight + 20, 200, 50); // Définir les dimensions et la position du bouton
        }
    }

    // Méthode pour dessiner le bouton "Recommencer"
    private void addRestartButton() {
        restartButton = new JButton("Recommencer");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Réinitialiser le jeu ici
                gameOver = false; // Réinitialiser le jeu à son état initial
                remove(restartButton); // Supprimer le bouton "Recommencer"
                repaint(); // Redessiner le panneau pour recommencer le jeu
            }
        });
        restartButton.setBounds(0, 0, 200, 50); // Définir les dimensions et la position du bouton
        add(restartButton); // Ajouter le bouton au panneau
        restartButton.setVisible(true); // Rendre le bouton visible

        // Activer les écouteurs d'événements de souris pour le héros
        enableHeroMouseEvents(true);
    }

    // Méthode pour activer ou désactiver les écouteurs d'événements de souris pour le héros
    private void enableHeroMouseEvents(boolean enabled) {
        if (enabled) {
            // Ajouter les écouteurs d'événements de souris au héros
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Récupérer les coordonnées du clic de souris
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    // Déplacer le héros vers les coordonnées du clic
                    hero.setX(mouseX);
                    hero.setY(mouseY);
                    // Redessiner le panneau pour mettre à jour l'affichage
                    repaint();
                }
            });
        } else {
            // Supprimer les écouteurs d'événements de souris du héros
            removeMouseListener(getMouseListeners()[0]);
        }
    }
}
