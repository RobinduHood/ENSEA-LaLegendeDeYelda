import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {
    private JButton restartButton;

    public GameOverScreen() {
        setLayout(null); // Définit un layout nul pour positionner les composants manuellement

        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameOverLabel.setBounds(100, 50, 200, 30);
        add(gameOverLabel);

        restartButton = new JButton("Recommencer");
        restartButton.setBounds(100, 100, 200, 50);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Réinitialiser le jeu lorsque le bouton "Recommencer" est cliqué
                resetGame();
            }
        });
        add(restartButton);
    }

    private void resetGame() {
        // Réinitialiser le jeu ici
        // Par exemple, réinitialisez les variables du jeu, redémarrez le jeu, etc.
    }
}
