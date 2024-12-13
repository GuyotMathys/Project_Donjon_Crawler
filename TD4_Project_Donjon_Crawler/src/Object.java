import java.awt.*;
import java.awt.geom.Rectangle2D;


public class Object extends SolidSprite{

    Direction direction = Direction.EAST;
    private long timePlaced;  // Temps en millisecondes au moment où la bombe est posée
    private static final long EXPLOSION_DELAY = 3000; //les trois secondes avant le grand boum !renderEngine;
    private boolean exploded = false;  // Drapeau pour savoir si la bombe a explosé

    public Object(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
        this.timePlaced = System.currentTimeMillis();
    }



    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public double getx_pose(double x) {
        double x_pose = 0;

        switch(direction){
            case EAST:
                x_pose = x + 50;
                break;
            case WEST:
                x_pose = x - 50;
                break;
            case NORTH:
                x_pose = x;
                break;
            case SOUTH:
                x_pose = x;
        }
        return x_pose;
    }

    public double gety_pose(double y) {
        double y_pose = 0;
        switch(direction){
            case EAST:
                y_pose = y;
                break;
            case WEST:
                y_pose = y;
                break;
            case NORTH:
                y_pose = y - 50;
                break;
            case SOUTH:
                y_pose = y + 50;
        }
        return y_pose;
    }

    public Rectangle2D.Double creation_hitbox(double x, double y, double width, double height) {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public boolean enough_space(Rectangle2D.Double hitbox_bombe,Rectangle2D.Double hitbox_sprite) {
        if (hitbox_bombe.intersects(hitbox_sprite)) {
            return false;
        }
        return true;
    }

    public void setX(double xBombe) {
        this.x = xBombe;
    }

    public void setY(double yBombe) {
        this.y = yBombe;
    }

    public long getTimePlaced() {
        return timePlaced;
    }

    public void setTimePlaced(long timePlaced) {
        this.timePlaced = timePlaced;
    }

    public boolean isExploded() {
        // Vérifie si 3 secondes se sont écoulées et que la bombe n'a pas déjà explosé
        return !exploded && (System.currentTimeMillis() - timePlaced >= EXPLOSION_DELAY);
    }


    public void explode() {
        if (!exploded) {
            exploded = true;  // Marque la bombe comme explosée
            System.out.println("BOOM! La bombe a explosé !");
            // Tu peux ajouter ici des effets visuels ou d'autres comportements (par exemple, dégâts)
        }
    }
}
