package ChineseChess;

import ChineseChess.ChessBoard.ChessBoard;
import ChineseChess.ChessPiece.ChessPiece;
import Engine.Core.GameObject;
import Engine.GameBuilding.GameWorldConstructor;

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
