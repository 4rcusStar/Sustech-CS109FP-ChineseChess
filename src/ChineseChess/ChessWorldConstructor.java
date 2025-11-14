package ChineseChess;

import Engine.GameObject;
import Engine.GameWorldConstructor;

public class ChessWorldConstructor extends GameWorldConstructor
{
    @Override
    public void construct(GameObject root)
    {
        ChessPiece redKing = new ChessPiece("King","Red",100,100);
        root.addChild(redKing);
        ChessPiece blackKing = new ChessPiece("King","Black",300,300);
        redKing.addChild(blackKing);
    }
}
