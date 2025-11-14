package Engine.Core;

import Engine.GameBuilding.GameWorld;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * EngineRenderer类是游戏引擎的渲染管理中心，其将实现逻辑处理和图像绘制的解耦
 * 集中管理和渲染所有可渲染的类RendererComponent
 * 与GameEngine单节点树状管理不同，这里我们使用并列结构管理所有renderer
 */
public class RenderEngine
{
    private static RenderEngine instance;
    private Canvas canvas;//渲染所用的canvas
    private GraphicsContext gc;//canvas所持有的GraphicsContext(目前为2d)
    private Set<GameObject> rendererObjects = new LinkedHashSet<>();
    private RenderEngine(Canvas canvas)
    {
        this.canvas= canvas;
        this.gc = canvas.getGraphicsContext2D();
    }


    /**
     * 获取rendererEngine的单例
     * @param canvas 要传入的Canvas
     * @return RendererEngine.instance
     */
    public static RenderEngine getInstance(Canvas canvas)
    {
        if (instance == null)
        {
            instance = new RenderEngine(canvas);
        }
        return instance;
    }

    /**
     * 在Canvas已被设置的情况下，获取单例
     * @return RendererEngine.instance
     */
    public static RenderEngine getInstance()
    {
        if(instance == null)
            throw new IllegalStateException("EngineRenderer not initialized. Call getInstance(canvas) first.");
        return instance;
    }

    public void registerGameWorld(GameWorld gameWorld)
    {
        GameObject root = gameWorld.getRoot();
        registerRenderer(root);
    }
    /**
     * 单独地将要渲染的组件加入引擎中
     * @param rendererObject 要渲染的Object
     */
    public void registerRenderer(GameObject rendererObject)
    {
        rendererObjects.add(rendererObject);
    }

    /**启动渲染循环
     *
     */
    public void startRenderLoop()
    {
        AnimationTimer timer = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                render();
            }
        };
        timer.start();

    }
    /**
     * 每次绘制的核心逻辑
     */
    private void render()
    {
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(GameObject rendererObject : rendererObjects)
        {
            rendererObject.render(gc);
        }
    }
}
