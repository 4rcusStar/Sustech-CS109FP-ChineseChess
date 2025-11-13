package ChineseChess;
import Engine.*;
import javafx.scene.shape.MoveTo;

public class PieceMovementManager extends Component
{
    float frame = 0;
    SpriteRenderer spriteRenderer;
    Transform transform;
    PointerDetector pointerDetector;

    public void awake()
    {
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
        transform = getGameObject().getComponent(Transform.class);
        pointerDetector = getGameObject().getComponent(PointerDetector.class);
    }

    public void start()
    {

    }

    @Override
    public void update()
    {
        if(pointerDetector.isPointed())
        {
            if(Input.isMousePressed())
            {
                transform.moveTo(30,30,100);
            }

        }
    }
}
