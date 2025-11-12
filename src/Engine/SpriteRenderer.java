package Engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SpriteRenderer extends RendererComponent
{
    private Image sprite;
    private double width=100;
    private double height=100;
    private int pivotX = 0, pivotY = 0;

    /**
     * 指定sprite宽高的创建
     * @param sprite 图像
     * @param width 宽
     * @param height 高
     */
    public SpriteRenderer(Image sprite, int width , int height )
    {
        this.sprite = sprite;
        this.width = width;
        this.height = height;
    }

    /**
     * 原图像尺寸的创建
     * @param sprite 原图
     */
    public SpriteRenderer(Image sprite)
    {
        this.sprite = sprite;
        width = sprite.getWidth();
    }

    public SpriteRenderer()
    {

    }
    /**
     * 设置图片与Transform的相对位移
     *
     * @param x x位移
     * @param y y位移
     */
    public void setPivot(int x, int y)
    {
        pivotX = x;
        pivotY = y;
    }

    /**
     * 渲染图标，若图标确实，则显示为紫色
     *
     * @param gc GraphicsContext画笔
     */
    public void render(GraphicsContext gc)
    {
        Transform t = getGameObject().getComponent(Transform.class);
        System.out.printf("rendering on t = (%f,%f)\n",t.getX(),t.getY());
        if (sprite == null)
        {
            gc.setFill(Color.color(1, 0, 1));
            gc.fillRect(t.getX()+pivotX, t.getY()+pivotY, width, height);
        } else
        {
            gc.drawImage(sprite, t.getX()+pivotX, t.getY()+pivotY, width, height);
        }
    }

}
