import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public class PhysicEngine implements Engine {

    private ArrayList<DynamicSprite> movingSpriteList;
    //private ArrayList<Object> objectList;
    private GameEngine gameEngine;
    protected static ArrayList<SolidSprite> environment;
    final protected DynamicSprite hero;
    final protected Object bombe;
    private RenderEngine renderEngine;
    private Explosion explosionImage;


    public PhysicEngine(GameEngine gameEngine, RenderEngine renderEngine, DynamicSprite hero, Object bombe, Explosion explosionImage) {
        movingSpriteList = new ArrayList<>();
        this.renderEngine = renderEngine;
        environment = new ArrayList<>();
        this.gameEngine = gameEngine;
        this.hero = hero;
        this.bombe = bombe;
        this.explosionImage = explosionImage;
    }


    public void addMovingSpriteList(DynamicSprite element) {
        movingSpriteList.add(element);
    }

    //public void addobjectlist(Object object) {
    //    objectList.add(object);
    //}

    public void addEnvironment(SolidSprite element) {
        environment.add(element);
    }

    @Override
    public void update() {
        for (DynamicSprite element : movingSpriteList) {
            element.moveIfPossible(environment);
        }

        // Vérifie si on doit poser une bombe, grâce à ce fameux flag
        if (gameEngine.placement_bombe()) {
            // Initialise les coordonnées pout la nouvelle bombe, en fonction des coordonnées de notre hero
            double xBombe = bombe.getx_pose(hero.getX());  // Position en X basée sur la direction du héros
            double yBombe = bombe.gety_pose(hero.getY());  // Position en Y basée sur la direction du héros

            // Créer une nouvelle bombe en initialisant directement la position
            Object newBombe = new Object(bombe.image, xBombe, yBombe, bombe.getWidth(), bombe.getHeight());

            // Créer la hitbox de la bombe
            Rectangle2D.Double hitboxBombe = newBombe.creation_hitbox(newBombe.getX(), newBombe.getY(), newBombe.getWidth(), newBombe.getHeight());

            // Vérifier s'il y a un malheureux contact avec d'autres SolidSprite
            boolean canPlaceBombe = true; //c'est le flag qui va dire si oui ou non on peut placer la bombe
            for (Sprite s : PhysicEngine.environment) {
                if (s instanceof SolidSprite) {
                    // Créer la hitbox de l'objet solide
                    Rectangle2D.Double hitboxSprite = new Rectangle2D.Double(s.getX(), s.getY(), s.getWidth(), s.getHeight());

                    // Utiliser enough_space pour vérifier s'il y a assez de place pour poser la bombe
                    if (!newBombe.enough_space(hitboxBombe, hitboxSprite)) {
                        canPlaceBombe = false;  // Si la bombe entre en collision avec un objet solide, pas de bombe !
                        break;
                    }
                }
            }

            // Si la place est valide, on initialise le timer pour décompter le temps durant lequel la bombe va attendre avant d'exploser
            if (canPlaceBombe) {
                newBombe.setTimePlaced(System.currentTimeMillis());  // Initialisation de timePlaced à chaque fois qu'une bombe est posée

                // Ajoute la nouvelle bombe à l'environnement et à la renderList
                addEnvironment(newBombe);
                renderEngine.addToRenderList(newBombe);
            }

            // Réinitialisation du flag pour la prochaine bombe
            gameEngine.placement_bombe = false;
        }

        // Liste temporaire qui va spécifier les éléments à rétirer de l'environnement, et de renderlist
        ArrayList<Sprite> toRemove = new ArrayList<>();
        for (Sprite sprite : PhysicEngine.environment) {
            if (sprite instanceof Object) {
                Object bombe = (Object) sprite;
                if (bombe.isExploded()) {
                    bombe.explode();  // Gère l'explosion
                    toRemove.add(bombe);  // ajoute bombe comme élément à supprier

                    // Créer une nouvelle instance de l'explosion à la position de la bombe
                    Explosion explosion = new Explosion(explosionImage.image, bombe.getX(), bombe.getY(), 60, 61);
                    renderEngine.addToRenderList(explosion);  // Ajouter l'explosion à la liste des objets à afficher
                }
            }
        }

        // Supprime les bombes de la liste temporaire après l'explosion
        for (Sprite bombe : toRemove) {
            renderEngine.getRenderList().remove(bombe);  // Retirer de la renderList
            PhysicEngine.environment.remove(bombe);  // Retirer de l'environnement physique
        }

        // comme pour la bombe, liste temporaire spécifiant la suppression en temps voulu de l'explosion
        ArrayList<Explosion> toRemove_ex = new ArrayList<>();
        for (Displayable displayable : renderEngine.getRenderList()) {
            if (displayable instanceof Explosion) {
                Explosion explosion = (Explosion) displayable;
                if (explosion.isFinished()) { //on utilise isFinished présent dans la classe Explosion
                    toRemove_ex.add(explosion);  // Ajouter l'explosion terminée à la liste de suppression
                }
            }
        }

        // Supprimer les explosions terminées de render list
        for (Explosion explosion : toRemove_ex) {
            renderEngine.getRenderList().remove(explosion);
        }
    }

}
