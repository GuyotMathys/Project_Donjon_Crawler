import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameEngine implements Engine, KeyListener {

    final protected DynamicSprite heros;
    final protected Object bombe;
    protected boolean placement_bombe;
    Direction direction = Direction.EAST;

    public GameEngine(DynamicSprite heros, Object bombe) {
        this.heros = heros;
        this.bombe = bombe;
    }


    public boolean placement_bombe(){
        return placement_bombe;
    }
    

    @Override
    public void update() {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                heros.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                heros.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                heros.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                heros.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_B:
                bombe.setDirection(heros.getDirection());

                //placement_bombe c'est le flag qui va autoriser le placement de la bombe, c'est super pratique
                placement_bombe = true;
                System.out.println(bombe.direction);
                break;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
    }
}
