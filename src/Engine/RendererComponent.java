package Engine;
import javafx.scene.canvas.GraphicsContext;

public abstract class RendererComponent extends Component
{
    /**
     * 组件的渲染逻辑
     * @param gc GraphicsContext画笔
     */
    public abstract void render(GraphicsContext gc);
}
