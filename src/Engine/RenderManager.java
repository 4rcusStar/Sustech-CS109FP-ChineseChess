package Engine;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderManager extends JPanel
{
    private List<GameObject> gameObjectsToRender = new ArrayList<>();

    public RenderManager(Scene sceneToRender)
    {
        setPreferredSize(new Dimension(640,720));
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g)
    {

    }
}
