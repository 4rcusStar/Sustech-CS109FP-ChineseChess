import Engine.*;
import javax.swing.*;

public class LogicManager extends Component
{
    private Scene scene;
    GameObject gameObject;
    GameManager gameManager;
    public void awake()
    {
        gameObject = getGameObject();
        gameManager = gameObject.getParent().getComponent(GameManager.class);
        scene = gameManager.getCurrentScene();
    }

    public void start()
    {
        System.out.println("LogicManager Found:"+scene.find("ChessBoard").getName() );
    }
    public void update()
    {
        System.out.println(getName()+" Update");
    }
}
