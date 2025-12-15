package ChineseChess.ChessPiece;

import Engine.Components.SpriteRenderer;
import Engine.Components.Transform;
import Engine.Core.GameObject;
import javafx.scene.image.Image;

public class PieceGhost extends GameObject
{
    public PieceGhost(Image sprite, float x, float y, float width, float height)
    {
        super("PieceGhost");
        
        Transform transform = getComponent(Transform.class);
        if (transform != null)
        {
            transform.setPosition(x, y);
        }
        else
        {
            transform = new Transform(x, y);
            addComponent(transform);
        }
        
        SpriteRenderer spriteRenderer = new SpriteRenderer(sprite, width, height);
        spriteRenderer.setOpacity(0.4);
        spriteRenderer.setRenderPriority(-1);
        addComponent(spriteRenderer);
        
        System.out.println("PieceGhost: Created with sprite at (" + x + ", " + y + "), opacity=0.2, priority=-1");
    }
}

