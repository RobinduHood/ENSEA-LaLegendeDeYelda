public class Trap extends SolidThings {
    private static final int DAMAGE = 1; // Dégâts infligés par le piège

    public Trap(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    // Méthode pour vérifier la collision avec le héros et lui infliger des dégâts
    public boolean checkCollision(Hero hero) {
        HitBox trapHitBox = getHitBox(); // Utilisation de la hitbox du piège
        HitBox heroHitBox = hero.getHitBox(); // Utilisation de la hitbox du héros

        if (trapHitBox.intersect(heroHitBox)) {
            // Collision détectée, infliger des dégâts au héros
            hero.reduceHealth(DAMAGE);
            return true;
        }
        return false;
    }
}
