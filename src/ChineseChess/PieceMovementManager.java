package ChineseChess;
import Engine.Component;
import Engine.Transform;

public class PieceMovementManager extends Component
{
    @Override
    public void update()
    {
        Transform t = getGameObject().getComponent(Transform.class);
        System.out.println(t.getX() + " " + t.getY());
    }
}
