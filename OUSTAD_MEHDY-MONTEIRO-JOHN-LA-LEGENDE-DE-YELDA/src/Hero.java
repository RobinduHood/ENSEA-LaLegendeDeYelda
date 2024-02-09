import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public final class Hero extends DynamicThings {
    private static volatile Hero instance = null;
    private Orientation orientation = Orientation.RIGHT;
    private boolean isWalking = false;
    private int health;
    private boolean invincible = false; // Nouvelle variable pour suivre l'invincibilité
    private long invincibleDuration = 5000; // Durée d'invincibilité en millisecondes
    private long lastDamageTime = 0; // Temps du dernier dégât pris
    private int initialHealth; // Stocke la santé initiale du héros

    private Hero() {
        super(120, 120, 48, 52);
        try {
            this.image = ImageIO.read(new File("image/heroTileSheet.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.health = 100; // Santé initiale à 100%
        this.initialHealth = 100; // Initialise la santé initiale
    }

    public final static Hero getInstance() {
        if (Hero.instance == null) {
            Hero.instance = new Hero();
        }
        return Hero.instance;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void moveIfPossible(double dx, double dy, Dungeon dungeon) {
        boolean movePossible = true;

        this.getHitBox().move(dx, dy);
        for (Things things : dungeon.getRenderList()) {
            if (things instanceof SolidThings) {
                if (((SolidThings) things).getHitBox().intersect(this.getHitBox())) {
                    movePossible = false;
                    break;
                }
            }
        }
        if (movePossible) {
            this.x = x + dx;
            this.y = y + dy;
        } else {
            this.getHitBox().move(-dx, -dy);
        }
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public void reduceHealth(int amount) {
        // Vérifie si le héros est invincible et si le temps écoulé depuis le dernier dégât est supérieur ou égal à la durée d'invincibilité
        if (!invincible || (System.currentTimeMillis() - lastDamageTime >= invincibleDuration)) {
            // Réduit la santé uniquement si la quantité de dégâts est inférieure ou égale à la santé actuelle
            if (amount <= health) {
                health -= amount;
            } else {
                // Si la quantité de dégâts est supérieure à la santé actuelle, réduit la santé à 0
                health = 0;
            }
            // Mettre à jour le temps du dernier dégât pris
            lastDamageTime = System.currentTimeMillis();
            // Si le héros n'était pas déjà invincible, activer l'invincibilité pour une durée spécifiée
            if (!invincible) {
                setInvincibleForTime(invincibleDuration);
            }
        }
    }

    // Méthode pour réinitialiser la santé du héros
    public void resetHealth() {
        health = initialHealth;
    }

    // Méthodes pour définir les coordonnées x et y du héros
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void draw(Graphics g) {
        int attitude = orientation.getI();
        int index = (int) ((System.currentTimeMillis() / 125) % 10);
        index = isWalking ? index : 0;
        g.drawImage(image, (int) x, (int) y, (int) x + 48, (int) y + 52, index * 96, 100 * attitude, (index + 1) * 96, 100 * (attitude + 1), null, null);
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Nouvelle méthode pour vérifier si le héros est invincible
    public boolean isInvincible() {
        return invincible;
    }

    // Méthode pour rendre le héros invincible pendant un certain temps (en millisecondes)
    public void setInvincibleForTime(long timeInMillis) {
        invincible = true;
        // Démarrer un nouveau thread pour désactiver l'invincibilité après un certain délai
        new Thread(() -> {
            try {
                Thread.sleep(timeInMillis);
                invincible = false; // Après le délai, désactiver l'invincibilité
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Méthode pour mettre à jour la durée d'invincibilité
    public void updateInvincibleDuration(long duration) {
        invincibleDuration = duration;
    }

    // Méthode pour récupérer le temps du dernier dégât pris
    public long getLastDamageTime() {
        return lastDamageTime;
    }
}
