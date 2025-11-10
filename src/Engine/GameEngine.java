package Engine;

import java.util.ArrayList;
import java.util.List;

public class GameEngine
{
    private static GameEngine instance;
    private List<GameObject> gameObjects = new ArrayList<>();
    private boolean isRunning = false;
    private long lastUpdateTime = 0;
    private final int targetFPS = 60;
    private final int timePerFrame = 1000/targetFPS;
    private GameEngine(){}

    /**
     * 获取单例
     * @return 单例
     */
    public static GameEngine getInstance()
    {
        if(instance == null)
        {
            instance = new GameEngine();
        }
        return instance;
    }

    /**
     * 注册游戏行为
     * @param gameObject 将要注册到引擎上的组件
     */
    public void registerGameObject(GameObject gameObject)
    {
        gameObjects.add(gameObject);
    }

    public void runEngine()
    {
        if(isRunning) return;//若已在运行，忽略启动请求
        isRunning = true;
        startGameLoop();
    }

    void startGameLoop()
    {
        //游戏循环线程
        new Thread(()->
        {
            long currentTime;
            long elapsedTime;

            while(isRunning)
            {
                //调用所有GO的awake()->调用所有start()->调用所有update()
                for (GameObject obj : gameObjects)
                {
                    obj.awake();
                }
                for(GameObject obj : gameObjects)
                {
                    obj.start();
                }
                for(GameObject obj : gameObjects)
                {
                    obj.update();
                }
                //帧率控制
                currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - lastUpdateTime;
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
                //更新上帧时间
                lastUpdateTime = System.currentTimeMillis();
            }
        }).start();
    }
    public void stopEngine()
    {
        isRunning = false;
    }
}
