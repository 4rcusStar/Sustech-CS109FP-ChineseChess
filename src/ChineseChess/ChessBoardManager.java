package ChineseChess;

import Engine.*;
import javafx.scene.image.Image;

public class ChessBoardManager extends Component
{
    Transform transform;
    SpriteRenderer spriteRenderer;
    PointerDetector pointerDetector;
    Image sprite = new Image("file:/C:/Users/ASUS/IdeaProjects/FP-ChineseChess/src/ChessBoard.png");
    @Override
    public void onAwake()
    {
        transform = getGameObject().getComponent(Transform.class);
        spriteRenderer = getGameObject().getComponent(SpriteRenderer.class);
        pointerDetector = getGameObject().getComponent(PointerDetector.class);
    }

    public void onStart()
    {
        spriteRenderer.setSprite(sprite);
        spriteRenderer.setSize(800,800);
    }

    public static float[] coordMapToTransformPos(int coordX, int coordY)
    {
        float[] coordMap = new float[2];
        coordMap[0] = 82+coordX*80.6f;
        coordMap[1] = 52+coordY*78.f;
        return coordMap;
    }

    public void update()
    {

        //System.out.printf("(%.0f,%.0f)\n",Input.getMouseY(),Input.getMouseX());
    }

}
