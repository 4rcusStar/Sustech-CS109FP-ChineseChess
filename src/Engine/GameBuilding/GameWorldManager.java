package Engine.GameBuilding;

import Engine.Core.GameEngine;
import Engine.Core.RenderEngine;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GameWorldManager
{
    private static GameWorldManager instance;
    private Map<String, Supplier<GameWorld>> worldFactories;
    private Map<String, GameWorld> cachedWorlds;
    private GameWorld currentGameWorld;
    private String currentWorldName;
    private GameEngine gameEngine;
    private RenderEngine renderEngine;

    private GameWorldManager()
    {
        worldFactories = new HashMap<>();
        cachedWorlds = new HashMap<>();
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
     * 注册场景工厂
     * @param worldName 场景名字
     * @param factory 场景工厂方法
     */
    public void registerGameWorld(String worldName, Supplier<GameWorld> factory)
    {
        worldFactories.put(worldName, factory);
    }

    public void switchGameWorldTo(String worldName)
    {
        System.out.println("switchGameWorldTo: " + worldName);
        //退出当前
        if(currentGameWorld != null)
        {
            currentGameWorld.onExit();
        }
        //切换：使用工厂方法创建或获取缓存的实例
        GameWorld newGameWorld = cachedWorlds.get(worldName);
        if(newGameWorld == null)
        {
            Supplier<GameWorld> factory = worldFactories.get(worldName);
            if(factory == null)
            {
                System.err.println("Warning: GameWorld factory for '" + worldName + "' not found!");
                return;
            }
            newGameWorld = factory.get();
            cachedWorlds.put(worldName, newGameWorld);
        }
        
        currentGameWorld = newGameWorld;
        currentWorldName = worldName;
        updateEngineRegistration();
        currentGameWorld.onEnter();
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
