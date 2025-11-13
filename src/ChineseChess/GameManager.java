/*
package ChineseChess;

import Engine.*;

public class GameManager
{
    private static GameManager instance;
    private GameManager() { }

    public static GameManager getInstance()
    {
        if (instance == null)
            instance = new GameManager();
        return instance;
    }
    public void saveGame()
    {
    }
    public void LoadGame()
    {
    }//todo:存档系统完善

    // 当前活动场景
    private GameWorld currentGameWorld;

    // 全局逻辑系统，UI系统
    private UIManager uiManager;
    private LogicManager logicManager;

    */
/**
     * 初始化,会默认加载一个场景
     *//*

    public void init()
    {
        this.uiManager = new UIManager();
        this.logicManager = new LogicManager();

        loadScene(new GameWorld()); // 默认场景
    }

    */
/**
     * 加载场景
     * @param gameWorld 要加载的场景
     *//*

    public void loadScene(GameWorld gameWorld)
    {
        this.currentGameWorld = gameWorld;
        GameEngine.getInstance().registerGameObject(gameWorld.getRoot());
    }

    */
/**
     *
     * @return 当前场景
     *//*

    public GameWorld getCurrentScene()
    {
        return currentGameWorld;
    }

    */
/**
     *
     * @return GameManager下的UIManager
     *//*

    public UIManager getUIManager() { return uiManager; }

    */
/**
     *
     * @return GameManager下的LogicManager
     *//*

    public LogicManager getLogicManager() { return logicManager; }
}*/
