package ChineseChess;
import Engine.*;

public class PieceMovementManager extends Component
{
    float frame = 0;
    SpriteRenderer spriteRenderer;
    Transform transform;
    PointerDetector pointerDetector;

    @Override
    public void onAwake()
    {
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
        transform = getGameObject().getComponent(Transform.class);
        pointerDetector = getGameObject().getComponent(PointerDetector.class);
    }
    @Override
    public void onStart()
    {

    }
    boolean moveStarted = false;
    @Override
    public void update()
    {

        if(pointerDetector.isPointed())
        {
            System.out.println(getGameObject().getName()+"isPointed,parent:"+getGameObject().getParent().getName());
            if(Input.isMouseClicked())
            {
                System.out.println(getGameObject().getChildren().size());
                transform.moveTo(30,30,100);
                moveStarted = true;
            }
        }

        if(moveStarted)
        {
            if(!transform.isMoving())
                getGameObject().destroy();
        }
    }
}
