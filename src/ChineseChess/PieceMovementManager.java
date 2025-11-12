package ChineseChess;
import Engine.Component;
import Engine.SpriteRenderer;

public class PieceMovementManager extends Component
{
    float frame=0;
    SpriteRenderer spriteRenderer;
    public void awake()
    {
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
    }
    @Override
    public void update()
    {
        frame=frame>=60?0:frame+1;
        if(frame==30)
        {
            spriteRenderer.Scale(0.5f,0.5f);
        }
        if(frame==60)
        {
            spriteRenderer.Scale(1f,1f);
        }
    }
}
