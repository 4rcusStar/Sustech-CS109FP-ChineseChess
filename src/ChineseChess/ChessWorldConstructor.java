package ChineseChess;

import ChineseChess.ChessBoard.ChessBoard;
import ChineseChess.ChessPiece.ChessPiece;
import ChineseChess.ChessPiece.PieceType;
import ChineseChess.ChessPiece.Side;
import Engine.Core.GameObject;
import Engine.GameBuilding.GameWorldConstructor;

public class ChessWorldConstructor extends GameWorldConstructor
{
    @Override
    public void construct(GameObject root)
    {
        ChessBoard chessBoard = new ChessBoard("ChessBoard");
        root.addChild(chessBoard);

        ChessPiece redGeneral = new ChessPiece(Side.RED, PieceType.GENERAL,0);
        chessBoard.addChild(redGeneral);

        ChessPiece blackGeneral = new ChessPiece(Side.BLACK, PieceType.GENERAL,0);
        chessBoard.addChild(blackGeneral);

        ChessPiece blackRook_1 = new ChessPiece(Side.BLACK, PieceType.ROOK,1);
        chessBoard.addChild(blackRook_1);
        ChessPiece blackRook_2 =  new ChessPiece(Side.BLACK, PieceType.ROOK,2);
        chessBoard.addChild(blackRook_2);
        //TODO:完成棋子对象的创建
    }
}
