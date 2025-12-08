package ChineseChess.ChessPiece;

import Engine.Core.GameObject;
import Engine.Components.PointerDetector;
import Engine.Components.SpriteRenderer;


public class ChessPiece extends GameObject
{
    public ChessPiece(Side side, PieceType type, int pieceIndex)
    {
        super(side+"_"+type+"_"+pieceIndex);
        addComponent(new PointerDetector(80,80,1));
        addComponent(new SpriteRenderer());
        addComponent(new PerspectiveRenderer());
        addComponent(new ChessPieceManager(side,type,pieceIndex));
        addComponent(new PieceMovementManager());
        addComponent(new GlowRenderer());
    }
}
