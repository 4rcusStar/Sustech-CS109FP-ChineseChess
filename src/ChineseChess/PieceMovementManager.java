package ChineseChess;
import Engine.Component;
import Engine.Transform;

public class PieceMovementManager extends Component
{
    static float frame = 0;
    @Override
    public void update()
    {
        Transform t = getGameObject().getComponent(Transform.class);
        t.setPosition(frame+=0.1f,100);
    }
}
