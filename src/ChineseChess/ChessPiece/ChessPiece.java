package ChineseChess.ChessPiece;

import Engine.Core.GameObject;
import Engine.Components.PointerDetector;
import Engine.Components.SpriteRenderer;


public class ChessPiece extends GameObject
{
    /*public ChessPiece(String type, String side,float x,float y)
    {
        super(side+"_"+type);
        //添加移动逻辑组件
        addComponent(new PieceMovementManager());
        addComponent(new SpriteRenderer());
        addComponent(new PointerDetector(50,50,1));
    }*/
    public ChessPiece(Side side, PieceType type, int pieceIndex)
    {
        super(side+"_"+type+"_"+pieceIndex);
        addComponent(new PointerDetector(60,60,1));
        addComponent(new SpriteRenderer());
        addComponent(new ChessPieceManager(side,type,pieceIndex));
        addComponent(new PieceMovementManager());
    }

}
