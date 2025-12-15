package Engine.Core;
import Engine.Input;
import Engine.GameBuilding.GameWorld;

import java.util.ArrayList;
import java.util.List;

public class GameEngine
{
    private static GameEngine instance;
    private List<GameObject> gameObjects = new ArrayList<>();
    private GameWorld gameWorld;
    private boolean isRunning = false;
    private long lastUpdateTime;
    private final int targetFPS = 60;
    private final int timePerFrame = 1000 / targetFPS;
    private static long deltaTime;
    private static long totalFrame=0;

    private GameEngine() {}

    public static GameEngine getInstance()
    {
        if (instance == null)
        {
            instance = new GameEngine();
        }
        return instance;
    }

    public void registerGameWorld(GameWorld gameWorld)
    {
        // 先清理旧的场景（如果存在）
        if(this.gameWorld != null)
        {
            unregisterGameWorld();
        }
        this.gameWorld = gameWorld;
        registerGameObject(gameWorld.getRoot());
    }
    public void unregisterGameWorld()
    {
        if(this.gameWorld !=null)
        {
            unregisterGameObject(gameWorld.getRoot());
            gameWorld = null;
        }
    }
    public void registerGameObject(GameObject gameObject)
    {
        if(gameObject !=null&&!gameObjects.contains(gameObject))
            gameObjects.add(gameObject);
    }

    public void unregisterGameObject(GameObject gameObject)
    {
        if(gameObject !=null&&gameObjects.contains(gameObject))
            gameObjects.remove(gameObject);
    }

    /**
     * @return 两帧之间的毫秒间隔
     */
    public static long getDeltaTime()
    {
        return deltaTime;
    }

    public static long getTotalFrame()
    {
        return totalFrame;
    }

    public void runEngine()
    {
        if (isRunning) return;
        isRunning = true;
        startGameLoop();
    }

    void startGameLoop()
    {
        new Thread(() ->
        {
            lastUpdateTime = System.currentTimeMillis();

            while (isRunning)
            {
                long currentTime = System.currentTimeMillis();
                deltaTime = currentTime - lastUpdateTime;
                lastUpdateTime = currentTime;

                // ---- 游戏逻辑更新 ----
                for(GameObject obj : new ArrayList<>(gameObjects))//更新父子关系
                {
                    obj.applyPendingRelation();
                }
                for (GameObject obj : new ArrayList<>(gameObjects))//调用未调用的awake()
                {
                    obj.awake();
                }
                for (GameObject obj : new ArrayList<>(gameObjects))//调用未调用的awake()
                {
                    obj.start();
                }
                for (GameObject obj : new ArrayList<>(gameObjects))//调用所有GameObj的update()
                {
                    obj.update();
                }

                Input.resetMouseClicked();
                //帧率控制
                long elapsedTime = System.currentTimeMillis() - currentTime;
                if (elapsedTime < timePerFrame)
                {
                    try
                    {
                        Thread.sleep(timePerFrame - elapsedTime);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                ++totalFrame;
            }
        }).start();
    }

    public void stopEngine()
    {
        // 程序退出时调用当前场景的 onExit()
        if(gameWorld != null)
        {
            gameWorld.onExit();
        }
        isRunning = false;
    }
}
