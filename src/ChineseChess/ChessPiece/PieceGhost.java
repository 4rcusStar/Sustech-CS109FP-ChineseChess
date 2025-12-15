package ChineseChess.ChessPiece;

import Engine.Components.SpriteRenderer;
import Engine.Components.Transform;
import Engine.Core.GameObject;
import javafx.scene.image.Image;

/**
 * 棋子残影GameObject
 * 用于显示上一步移动的棋子位置
 */
public class PieceGhost extends GameObject
{
    /**
     * 创建残影
     * @param sprite 棋子的sprite图片
     * @param x 世界坐标X
     * @param y 世界坐标Y
     * @param width sprite宽度
     * @param height sprite高度
     */
    public PieceGhost(Image sprite, float x, float y, float width, float height)
    {
        super("PieceGhost");
        
        // GameObject构造函数已经创建了一个Transform，直接使用它
        Transform transform = getComponent(Transform.class);
        if (transform != null)
        {
            transform.setPosition(x, y);
        }
        else
        {
            // 如果获取不到，创建一个新的
            transform = new Transform(x, y);
            addComponent(transform);
        }
        
        // 创建SpriteRenderer，设置透明度
        SpriteRenderer spriteRenderer = new SpriteRenderer(sprite, width, height);
        spriteRenderer.setOpacity(0.4); // 20%透明度
        spriteRenderer.setRenderPriority(-1); // 渲染优先级设为-1，在棋子(0)下方，但在棋盘背景(-999)上方
        addComponent(spriteRenderer);
        
        System.out.println("PieceGhost: Created with sprite at (" + x + ", " + y + "), opacity=0.2, priority=-1");
    }
}

