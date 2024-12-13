import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Main {

    JFrame displayZoneFrame;
    RenderEngine renderEngine = new RenderEngine();
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    boolean validation;


    public Main() throws Exception{
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(1500,800);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DynamicSprite hero = new DynamicSprite(ImageIO.read(new File("./img/heroTileSheetLowRes.png")),200,300,48,50);
        Object bombe = new Object(ImageIO.read(new File("./img/bomb_icon_40x40-removebg-preview (1).png")),0,0,40,40);
        Explosion explosionImage = new Explosion(ImageIO.read(new File("./img/explosion.png")),0,0,60,61 );

        renderEngine = new RenderEngine();
        gameEngine = new GameEngine(hero,bombe);
        physicEngine = new PhysicEngine(gameEngine,renderEngine,hero,bombe,explosionImage);

        Timer renderTimer = new Timer(50,(time)-> renderEngine.update());
        Timer gameTimer = new Timer(50,(time)-> gameEngine.update());
        Timer physicTimer = new Timer(50,(time)-> physicEngine.update());

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);
        displayZoneFrame.addKeyListener(gameEngine);


        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        PlayGround niveau = new PlayGround("./data/milieu.txt");
        for (Sprite solidSprite : niveau.getSolidSpriteList()){
            physicEngine.addEnvironment((SolidSprite) solidSprite);
        }
        physicEngine.addMovingSpriteList(hero);

        for (Displayable sprite : niveau.getSpriteList()){
            renderEngine.addToRenderList(sprite);
        }
        renderEngine.addToRenderList(hero);





    }

    public static void main (String[] args) throws Exception {
        Main main = new Main();
    }

}