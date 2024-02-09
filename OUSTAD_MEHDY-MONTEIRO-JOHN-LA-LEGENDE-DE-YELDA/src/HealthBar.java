import javax.swing.*;
import java.awt.*;

class HealthBar extends JPanel {
    private double healthPercentage;

    public HealthBar() {
        setPreferredSize(new Dimension(400, 30));
        healthPercentage = 100.0; // Santé initiale à 100%
    }

    public void setHealthPercentage(double percentage) {
        healthPercentage = percentage;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth() - 10; // Largeur totale de la barre de vie
        int height = getHeight() - 10; // Hauteur de la barre de vie

        // Calcul de la largeur de la barre de vie en fonction du pourcentage de santé
        int healthWidth = (int) (width * healthPercentage / 100.0);

        // Dessin du contour de la barre de vie
        g.setColor(Color.BLACK);
        g.drawRoundRect(5, 5, width, height, 15, 15);

        // Dessin de la partie remplie de la barre de vie (vert foncé)
        g.setColor(new Color(0, 128, 0));
        g.fillRoundRect(5, 5, healthWidth, height, 15, 15);

        // Dessin de la partie vide de la barre de vie (gris foncé)
        g.setColor(new Color(64, 64, 64));
        g.fillRoundRect(5 + healthWidth, 5, width - healthWidth, height, 15, 15);

        // Dessin du texte du pourcentage
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        String percentageText = String.format("%.0f%%", healthPercentage);
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(percentageText);
        int textX = (getWidth() - textWidth) / 2;
        int textY = getHeight() / 2 + fontMetrics.getAscent() / 2;
        g.drawString(percentageText, textX, textY);
    }
}
