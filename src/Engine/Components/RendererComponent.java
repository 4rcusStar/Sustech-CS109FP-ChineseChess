package Engine.Components;
import javafx.scene.canvas.GraphicsContext;

public abstract class RendererComponent extends Component
{
    private int renderPriority = 0;

    public int getRenderPriority() {
        return renderPriority;
    }

    public void setRenderPriority(int priority)
    {
        this.renderPriority = priority;
    }
    /**
     * 组件的渲染逻辑
     * @param gc GraphicsContext画笔
     */
    public abstract void render(GraphicsContext gc);

}
