import Engine.*;

public class GameManager extends Component
{
    Scene currentScene;//GameManager持有的场景
    LogicManager logicManager;
    UIManager uiManager;
    GameObject gameObject;
    static GameManager instance;

    public static GameManager getGameManager()
    {
        if(instance == null)
            instance = new GameManager();
        return instance;
    }

    public void awake()
    {
        gameObject = getGameObject();
        logicManager = gameObject.getChild("LogicManager").getComponent(LogicManager.class);
        uiManager = gameObject.getChild("UIManager").getComponent(UIManager.class);
    }

    /**
     * 设置场景,若之下已经有场景，则更换至指定场景
     * @param scene :要设置的场景
     */
    public void setScene(Scene scene)
    {
        currentScene = scene;
    }
    /**
     *
     * @return GameManager下当前的场景
     */
    public Scene getCurrentScene()
    {
        return currentScene;
    }

    /**
     * 当前场景下所有角色游戏循环:
     */
    public void update()
    {
        //currentScene.update();
    }

    /**
     * 设置游戏的UIManager
     * @param uiManager UIManager
     */
    public void setUIManager(UIManager uiManager)
    {
        this.uiManager = uiManager;
    }

    /**
     * 设置游戏的LogicManager
     * @param logicManager LogicManger
     */
    public void setLogicManager(LogicManager logicManager)
    {
        this.logicManager = logicManager;
    }

    public LogicManager getLogicManager()
    {
        return logicManager;
    }

    public UIManager getUIManager()
    {
        return uiManager;
    }

}
