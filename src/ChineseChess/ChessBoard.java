package ChineseChess;

import Engine.*;

public class ChessBoard extends GameObject
{
    public ChessBoard(String name)
    {
        super(name);
        initPieces();
    }

    /**
     * 创建并添加棋子
     */
    public void initPieces()
    {
        ChessPiece redRook = new ChessPiece("Rook","Red",0,0);
        ChessPiece blackRook = new ChessPiece("Rook","Black",0,0);
        addChild(redRook);
        addChild(blackRook);
    }
}
