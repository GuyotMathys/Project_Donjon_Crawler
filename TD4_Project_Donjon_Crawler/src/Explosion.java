import java.awt.*;
import java.awt.image.ImageObserver;

public class Explosion extends Sprite {
    private final int explosionFrames = 5;  // Nombre de frames dans la sprite sheet (5 images d'explosion)
    private int currentFrame = 0;           // L'index de la frame courante, comme pour le hero avec la variable index
    private long lastUpdateTime = 0;        // Dernier temps de mise à jour pour l'animation
    private final int timeBetweenFrames = 100;  // Temps entre chaque frame (100ms)

    public Explosion(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        long currentTime = System.currentTimeMillis();

        // Mettre à jour l'animation si le temps est écoulé
        if (currentTime - lastUpdateTime >= timeBetweenFrames) {
            currentFrame++;
            if (currentFrame >= explosionFrames) {
                currentFrame = explosionFrames - 1;  // Fin de l'animation, on reste sur la dernière frame
            }
            lastUpdateTime = currentTime;
        }

        // Dessiner l'image correspondant à la frame actuelle
        int sx1 = currentFrame * 60;  // La largeur d'une image est de 60px, donc on décale l'image dans la sprite sheet
        int sy1 = 0;  // L'explosion est sur une seule ligne dans la sprite sheet
        int sx2 = sx1 + 60;
        int sy2 = 61;  // Hauteur de l'image dans la sprite sheet

        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                sx1, sy1, sx2, sy2, (ImageObserver) null);
    }

    // Méthode pour vérifier si l'animation est terminée
    public boolean isFinished() {
        return currentFrame == explosionFrames - 1;
    }
}

