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
     * @param worldName 场景名字
     * @param factory 
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
     * 强制重新创建指定的游戏世界（清除缓存并重新创建）
     * @param worldName 世界名称
     */
    public void recreateGameWorld(String worldName)
    {
        System.out.println("recreateGameWorld: " + worldName);
        //退出当前
        if(currentGameWorld != null)
        {
            currentGameWorld.onExit();
            // 把旧世界从引擎里卸下来
            gameEngine.unregisterGameWorld();
            renderEngine.unregisterGameWorld();
        }
        
        // 缓存清掉
        cachedWorlds.remove(worldName);
        
        // 重新创建一个新的世界
        Supplier<GameWorld> factory = worldFactories.get(worldName);
        if(factory == null)
        {
            System.err.println("Warning: GameWorld factory for '" + worldName + "' not found!");
            return;
        }
        
        GameWorld newGameWorld = factory.get();
        cachedWorlds.put(worldName, newGameWorld);
        
        currentGameWorld = newGameWorld;
        currentWorldName = worldName;
        
        // 直接注册到引擎
        if(renderEngine != null && gameEngine != null)
        {
            gameEngine.registerGameWorld(currentGameWorld);
            renderEngine.registerGameWorld(currentGameWorld);
        }
        
        // 调用 onEnter 把场景搭起来
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
