package ChineseChess;

import Engine.GameObject;
import Engine.GameWorldConstructor;

public class ChessWorldConstructor extends GameWorldConstructor
{
    @Override
    public void construct(GameObject root)
    {
        ChessBoard chessBoard = new ChessBoard("ChessBoard");
        root.addChild(chessBoard);
        ChessPiece blackKing = new ChessPiece("King","Black",0,0);
        chessBoard.addChild(blackKing);
    }
}
