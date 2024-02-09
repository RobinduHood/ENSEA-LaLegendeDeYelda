import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreen extends JFrame {

    public TitleScreen() {
        setTitle("Écran titre");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du bouton "Commencer le jeu"
        JButton startButton = new JButton("Commencer le jeu");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fermer l'écran titre
                MainInterface mainInterface = new MainInterface();
            }
        });

        // Ajout du bouton au centre de l'écran titre
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(startButton);
        add(panel);
    }

    public void display() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TitleScreen titleScreen = new TitleScreen();
                titleScreen.display();
            }
        });
    }
}
