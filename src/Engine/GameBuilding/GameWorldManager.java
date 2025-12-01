package Engine.GameBuilding;

import Engine.Core.GameEngine;
import Engine.Core.RenderEngine;
import java.util.HashMap;
import java.util.Map;
public class GameWorldManager
{
    private static GameWorldManager instance;
    private Map<String, GameWorld> gameWorlds;
    private GameWorld currentGameWorld;
    private String currentWorldName;
    private GameEngine gameEngine;
    private RenderEngine renderEngine;

    private GameWorldManager()
    {
        gameWorlds = new HashMap<>();
    }

    public static GameWorldManager getInstance()
    {
        if(instance == null)
            instance = new GameWorldManager();
        return instance;
    }
/**
 * 初始化游戏世界管理器
 * @param gameEngine 游戏引擎
 * @param renderEngine 渲染引擎
 */
    public void initialize(GameEngine gameEngine, RenderEngine renderEngine)
    {   
        this.gameEngine = gameEngine;
        this.renderEngine = renderEngine;
    }

    /**
     * 注册场景
     * @param worldName 场景名字
     * @param gameWorld 场景
     */
    public void registerGameWorld(String worldName, GameWorld gameWorld)
    {
        gameWorlds.put(worldName, gameWorld);
    }

    public void switchGameWorldTo(String worldName)
    {
        //退出当前
        if(currentGameWorld != null)
        {
            currentGameWorld.onExit();
        }
        //切换
        GameWorld newGameWorld = gameWorlds.get(worldName);
        if(newGameWorld != null)
        {
            currentGameWorld = newGameWorld;
            currentWorldName = worldName;
            updateEngineRegistration();

            currentGameWorld.onEnter();
        }
    }

    /**
     * 更新渲染引擎，逻辑引擎的根注册
     */
    private void updateEngineRegistration() 
    {
        if(currentGameWorld != null&&renderEngine != null&&gameEngine != null)
        {
            gameEngine.unregisterGameWorld();
            renderEngine.unregisterGameWorld();
            gameEngine.registerGameWorld(currentGameWorld);
            renderEngine.registerGameWorld(currentGameWorld);
        }
    }

    public GameWorld getCurrentGameWorld()
    {
        return currentGameWorld;
    }
    public String getCurrentWorldName()
    {
        return currentWorldName;
    }
}
