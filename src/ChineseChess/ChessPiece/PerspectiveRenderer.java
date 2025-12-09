package ChineseChess.ChessPiece;

import ChineseChess.ChessBoard.ChessBoardManager;
import Engine.Components.RendererComponent;
import Engine.Components.SpriteRenderer;
import Engine.Components.Transform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

/**
 * 透视渲染器 浮起并向后倾斜效果
 */
public class PerspectiveRenderer extends RendererComponent
{
    private ChessBoardManager board;
    private Transform transform;
    private SpriteRenderer spriteRenderer;
    private boolean isSelected = false;
    
    private static final float LIFT_AMOUNT = -12f; // 浮起高度
    // 透视参数：顶部不要太小，避免“尖顶”感；底部略大制造厚重感
    private static final float TOP_SCALE = 0.9f;          // 顶部缩放
    private static final float BOTTOM_SCALE = 1.18f;      // 底部缩放
    private static final int LAYER_COUNT = 48;            // 分层数量
    
    @Override
    public void onAwake()
    {
        board = getGameObject().getParent().getComponent(ChessBoardManager.class);
        transform = getGameObject().getComponent(Transform.class);
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
        
        // 在 SpriteRenderer 之后渲染，覆盖原渲染（SpriteRenderer 的优先级是 0）
        setRenderPriority(1);
    }
    
    @Override
    public void update()
    {
        // 检查是否被选中
        if (board != null)
        {
            ChessPiece currentPiece = (ChessPiece) getGameObject();
            boolean wasSelected = isSelected;
            isSelected = (board.getSelectedChessPiece() == currentPiece);
            
            // 如果被选中，隐藏 SpriteRenderer 的渲染
            if (spriteRenderer != null)
            {
                if (isSelected && !wasSelected)
                {
                    // 被选中：隐藏 SpriteRendere
                    spriteRenderer.setOpacity(0.0);
                }
                else if (!isSelected && wasSelected)
                {
                    // 取消选中
                    spriteRenderer.setOpacity(1.0);
                }
            }
        }
    }
    
    @Override
    public void render(GraphicsContext gc)
    {
        // 只在被选中时渲染透视效果，并隐藏 SpriteRenderer 的渲染
        if (!isSelected || spriteRenderer == null || transform == null)
        {
            return;
        }
        
        Image sprite = spriteRenderer.getSprite();
        if (sprite == null)
        {
            return; // 如果没有 sprite，不渲染
        }
        
        float width = (float)spriteRenderer.getWidth();
        float height = (float)spriteRenderer.getHeight();
        float x = transform.getX();
        float y = transform.getY();
        
        double opacity = 1.0;
        
        double originalGlobalAlpha = gc.getGlobalAlpha();
        gc.setGlobalAlpha(opacity);
        
        renderWithPerspective(gc, sprite, x, y, width, height);
        
        gc.setGlobalAlpha(originalGlobalAlpha);
    }
    
    /**
     * 使用透视变换渲染（浮起并向后倾斜的效果）
     */
    private void renderWithPerspective(GraphicsContext gc, Image sprite, float x, float y, float w, float h)
    {
        // 保存当前变换状态
        Affine originalTransform = gc.getTransform();
        
        // 1. 向上移动
        float liftY = LIFT_AMOUNT;

        if (sprite != null)
        {
            float imageHeight = (float)sprite.getHeight();
            float imageWidth = (float)sprite.getWidth();

            int layerCount = LAYER_COUNT;
            float layerHeight = imageHeight / layerCount;
            float targetLayerHeight = h / layerCount;

            float currentSourceY = 0;
            float currentTargetY = y + liftY;

            for (int i = 0; i < layerCount; i++)
            {
                // 位置比例：0 顶部，1 底部
                float t = (float)i / (layerCount - 1);
                float eased = t * t * (2f - t);

                float scale = TOP_SCALE + (BOTTOM_SCALE - TOP_SCALE) * eased;

                float layerWidth = w * scale;
                float layerX = x + (w - layerWidth) / 2f;  // 居中

                float targetLayerHeightScaled = targetLayerHeight * scale;

                gc.drawImage(
                    sprite,
                    0, currentSourceY, imageWidth, layerHeight,
                    layerX, currentTargetY, layerWidth, targetLayerHeightScaled
                );

                currentSourceY += layerHeight;
                currentTargetY += targetLayerHeightScaled;
            }
        }

        // 恢复原始变换
        gc.setTransform(originalTransform);
    }
    
}

