package ChineseChess.ChessPiece;

import Engine.Core.GameObject;
import Engine.Components.PointerDetector;
import Engine.Components.SpriteRenderer;

public class ChessPiece extends GameObject
{
    private String type;
    private String side;
    /*public ChessPiece(String type, String side,float x,float y)
    {
        super(side+"_"+type);
        //添加移动逻辑组件
        addComponent(new PieceMovementManager());
        addComponent(new SpriteRenderer());
        addComponent(new PointerDetector(50,50,1));
    }*/

    public ChessPiece(String type, String side, int x, int y)
    {
        super(side+"_"+type);
        addComponent(new PointerDetector(60,60,1));
        addComponent(new SpriteRenderer());
        addComponent(new ChessPieceManager());
        addComponent(new PieceMovementManager());
    }

}
