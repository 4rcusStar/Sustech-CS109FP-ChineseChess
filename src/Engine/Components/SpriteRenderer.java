package Engine.Components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SpriteRenderer extends RendererComponent
{
    private Image sprite;
    private double width=50;
    private double height=50;
    private float pivotX = 0, pivotY = 0;
    private float scaleX = 1;
    private float scaleY = 1;

    /**
     * 指定sprite宽高的创建
     * @param sprite 图像
     * @param width 宽
     * @param height 高
     */
    public SpriteRenderer(Image sprite, float width , float height )
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
        height = sprite.getHeight();
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

    public void setSprite(Image sprite)
    {
        this.sprite = sprite;
    }

    /**
     * 缩放
     * @param scaleX
     * @param scaleY
     */
    public void Scale(float scaleX, float scaleY)
    {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public void setSize(float width, float height)
    {
        this.width = width;
        this.height = height;
    }

    /**
     * 渲染图标，若图标确实，则显示为紫色
     *
     * @param gc GraphicsContext画笔
     */
    public void render(GraphicsContext gc)
    {
        Transform t = getGameObject().getComponent(Transform.class);
        if (sprite == null)
        {
            gc.setFill(Color.color(1, 0, 1));
            gc.fillRect(t.getX()+pivotX, t.getY()+pivotY, width*scaleX, height*scaleY);
        } else
        {
            gc.drawImage(sprite, t.getX()+pivotX, t.getY()+pivotY, width*scaleX, height*scaleY);
        }
    }

}
