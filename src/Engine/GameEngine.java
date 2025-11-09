package Engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameEngine
{
    private static GameEngine instance;
    private List<Component> componentsList = new ArrayList<>();
    private List<Component> componentsToStart = new ArrayList<>();
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
     * @param component 将要注册到引擎上的组件
     */
    public void registerComponent(Component component)
    {
        componentsList.add(component);
        componentsToStart.add(component);
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
                //每帧检查是否有新的组件需要调用start()并调用
                if(!componentsToStart.isEmpty())
                {
                    //使用iterator遍历组件，执行start()并删除
                    Iterator<Component> iterator = componentsToStart.iterator();
                    while(iterator.hasNext())
                    {
                        Component component = iterator.next();
                        //只对激活的组件生效
                        if (component.isEnabled())
                        {
                            component.start();
                            component.markStarted();
                        }
                        iterator.remove();
                    }
                }
                //每帧检查是否有组件被destroy()并调用onDestroy()
                //对已经start()过并enable的组件调用update()
                for(Component component:componentsList)
                {
                    if(component.isEnabled()&& component.isStarted())
                    {
                        try
                        {
                            component.update();
                        } catch (Exception e)
                        {
                            System.err.println(component.getName()+"FailedToUpdate:"+e.getMessage());
                        }
                    }
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
