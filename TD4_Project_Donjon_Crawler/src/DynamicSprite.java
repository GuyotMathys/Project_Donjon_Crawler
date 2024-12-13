import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{

    boolean isWalking = true;
    double speed = 5;
    final int spriteSheetNumberOfColumn = 5;
    int timeBetweenFrame = 200;
    Direction direction = Direction.EAST;

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public DynamicSprite(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }


    @Override
    public void draw(Graphics g) {
        long index = 0;
        index = System.currentTimeMillis() / timeBetweenFrame;
        index %= 10;

        int attitude;
        attitude = direction.getFrameLineNumber();

        double dx1 = x;
        double dy1 = y;
        double dx2 = x+width;
        double dy2 = y+height;
        double sx1 = index*width;
        double sy1 = attitude*height;
        double sx2 = index*width +48;
        double sy2 = attitude*height +51;

        g.drawImage(image,(int) dx1,(int) dy1,(int) dx2,(int) dy2,(int) sx1,(int) sy1,(int) sx2,(int)sy2,null);
    }

    public void move() {
        switch (direction) {
            case NORTH:
                this.y -= speed;
                break;
            case SOUTH:
                this.y += speed;
                break;
            case EAST:
                this.x += speed;
                break;
            case WEST:
                this.x -= speed;
                break;
        }
    }

    public boolean isMovingPossible(ArrayList<SolidSprite> environment){
        Rectangle2D.Double hitbox = new Rectangle2D.Double(); //hitbox du héros
        Rectangle2D.Double hitboxsrpite; //hitbox des sprites solides

        //assignation même si c'est pas la meilleure écriture je crois bien des hitbox futurs du héros
        switch (direction) {
            case NORTH:
                hitbox = new Rectangle2D.Double(this.x, this.y - speed, this.width, this.height);
                break;
            case SOUTH:
                hitbox = new Rectangle2D.Double(this.x, this.y + speed, this.width, this.height);
                break;
            case EAST:
                hitbox = new Rectangle2D.Double(this.x + speed, this.y, this.width, this.height);
                break;
            case WEST:
                hitbox = new Rectangle2D.Double(this.x - speed, this.y, this.width, this.height);
                break;
        }
        //on boucle sur les éléments de l'environnement
        for (Sprite s : environment) {
            //hitbox des sprites en accédant à leurs valeurs de position et taille grâce à de supers setter qui n'étaient pas là avant
            hitboxsrpite = new Rectangle2D.Double(s.getX(), s.getY(), s.getWidth(), s.getHeight());
            if (s instanceof SolidSprite){ //vérifiaction que l'élément de décor n'est pas ce bon vieux héros
                if (hitbox.intersects(hitboxsrpite)){ //vérification de leur intersection
                    return false;
                }
            }
        }
    return true;
    }

    public void moveIfPossible(ArrayList<SolidSprite> environment){
        if (isMovingPossible(environment) == true){
            move();
        }
    }


    public Direction getDirection() {
        return direction;
    }
}
