import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class RenderEngine extends JPanel implements Engine{

    static ArrayList<Displayable> renderList = new ArrayList<>();

    public RenderEngine(){
        RenderEngine.renderList = renderList;
    }

    public static ArrayList<Displayable> getRenderList() {
        return renderList;
    }

    public void setRenderList(ArrayList<Displayable> renderList) {
        RenderEngine.renderList = renderList;
    }

    public void addToRenderList(Displayable displayable){
        renderList.add(displayable);
    }

    @Override
    public void update(){
        //System.out.println("RenderEngine");
        repaint();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);             //appel de paint de la superclasse car on surcharge paint et paintcomponent
        for (Displayable displayable : renderList){
            displayable.draw(g);
        }
    }

}
