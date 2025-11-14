package Engine;

import java.util.ArrayList;
import java.util.List;

public class GameEngine
{
    private static GameEngine instance;
    private List<GameObject> gameObjects = new ArrayList<>();
    private boolean isRunning = false;
    private long lastUpdateTime;
    private final int targetFPS = 60;
    private final int timePerFrame = 1000 / targetFPS;
    private long deltaTime;

    private GameEngine() {}

    public static GameEngine getInstance()
    {
        if (instance == null)
        {
            instance = new GameEngine();
        }
        return instance;
    }

    public void registerGameObject(GameObject gameObject)
    {
        gameObjects.add(gameObject);
    }

    /**
     * @return 两帧之间的毫秒间隔
     */
    public long getDeltaTime()
    {
        return deltaTime;
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
                for(GameObject obj : gameObjects)//更新父子关系
                {
                    obj.applyPendingRelation();
                }
                for (GameObject obj : gameObjects)//调用未调用的awake()
                {
                    obj.awake();
                }
                for (GameObject obj : gameObjects)//调用未调用的awake()
                {
                    obj.start();
                }
                for (GameObject obj : gameObjects)//调用所有GameObj的update()
                {
                    obj.update();
                }

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
            }
        }).start();
    }

    public void stopEngine()
    {
        isRunning = false;
    }
}
