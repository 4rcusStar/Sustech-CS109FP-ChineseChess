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

        // 红方棋子
        ChessPiece redGeneral = new ChessPiece(Side.RED, PieceType.GENERAL, 0);
        chessBoard.addChild(redGeneral);

        ChessPiece redAdvisor_1 = new ChessPiece(Side.RED, PieceType.ADVISOR, 1);
        chessBoard.addChild(redAdvisor_1);
        ChessPiece redAdvisor_2 = new ChessPiece(Side.RED, PieceType.ADVISOR, 2);
        chessBoard.addChild(redAdvisor_2);

        ChessPiece redElephant_1 = new ChessPiece(Side.RED, PieceType.ELEPHANT, 1);
        chessBoard.addChild(redElephant_1);
        ChessPiece redElephant_2 = new ChessPiece(Side.RED, PieceType.ELEPHANT, 2);
        chessBoard.addChild(redElephant_2);

        ChessPiece redHorse_1 = new ChessPiece(Side.RED, PieceType.HORSE, 1);
        chessBoard.addChild(redHorse_1);
        ChessPiece redHorse_2 = new ChessPiece(Side.RED, PieceType.HORSE, 2);
        chessBoard.addChild(redHorse_2);

        ChessPiece redRook_1 = new ChessPiece(Side.RED, PieceType.ROOK, 1);
        chessBoard.addChild(redRook_1);
        ChessPiece redRook_2 = new ChessPiece(Side.RED, PieceType.ROOK, 2);
        chessBoard.addChild(redRook_2);

        ChessPiece redCannon_1 = new ChessPiece(Side.RED, PieceType.CANNON, 1);
        chessBoard.addChild(redCannon_1);
        ChessPiece redCannon_2 = new ChessPiece(Side.RED, PieceType.CANNON, 2);
        chessBoard.addChild(redCannon_2);

        ChessPiece redSoldier_1 = new ChessPiece(Side.RED, PieceType.SOLDIER, 1);
        chessBoard.addChild(redSoldier_1);
        ChessPiece redSoldier_2 = new ChessPiece(Side.RED, PieceType.SOLDIER, 2);
        chessBoard.addChild(redSoldier_2);
        ChessPiece redSoldier_3 = new ChessPiece(Side.RED, PieceType.SOLDIER, 3);
        chessBoard.addChild(redSoldier_3);
        ChessPiece redSoldier_4 = new ChessPiece(Side.RED, PieceType.SOLDIER, 4);
        chessBoard.addChild(redSoldier_4);
        ChessPiece redSoldier_5 = new ChessPiece(Side.RED, PieceType.SOLDIER, 5);
        chessBoard.addChild(redSoldier_5);

        // 黑方棋子
        ChessPiece blackGeneral = new ChessPiece(Side.BLACK, PieceType.GENERAL, 0);
        chessBoard.addChild(blackGeneral);

        ChessPiece blackAdvisor_1 = new ChessPiece(Side.BLACK, PieceType.ADVISOR, 1);
        chessBoard.addChild(blackAdvisor_1);
        ChessPiece blackAdvisor_2 = new ChessPiece(Side.BLACK, PieceType.ADVISOR, 2);
        chessBoard.addChild(blackAdvisor_2);

        ChessPiece blackElephant_1 = new ChessPiece(Side.BLACK, PieceType.ELEPHANT, 1);
        chessBoard.addChild(blackElephant_1);
        ChessPiece blackElephant_2 = new ChessPiece(Side.BLACK, PieceType.ELEPHANT, 2);
        chessBoard.addChild(blackElephant_2);

        ChessPiece blackHorse_1 = new ChessPiece(Side.BLACK, PieceType.HORSE, 1);
        chessBoard.addChild(blackHorse_1);
        ChessPiece blackHorse_2 = new ChessPiece(Side.BLACK, PieceType.HORSE, 2);
        chessBoard.addChild(blackHorse_2);

        ChessPiece blackRook_1 = new ChessPiece(Side.BLACK, PieceType.ROOK, 1);
        chessBoard.addChild(blackRook_1);
        ChessPiece blackRook_2 = new ChessPiece(Side.BLACK, PieceType.ROOK, 2);
        chessBoard.addChild(blackRook_2);

        ChessPiece blackCannon_1 = new ChessPiece(Side.BLACK, PieceType.CANNON, 1);
        chessBoard.addChild(blackCannon_1);
        ChessPiece blackCannon_2 = new ChessPiece(Side.BLACK, PieceType.CANNON, 2);
        chessBoard.addChild(blackCannon_2);

        ChessPiece blackSoldier_1 = new ChessPiece(Side.BLACK, PieceType.SOLDIER, 1);
        chessBoard.addChild(blackSoldier_1);
        ChessPiece blackSoldier_2 = new ChessPiece(Side.BLACK, PieceType.SOLDIER, 2);
        chessBoard.addChild(blackSoldier_2);
        ChessPiece blackSoldier_3 = new ChessPiece(Side.BLACK, PieceType.SOLDIER, 3);
        chessBoard.addChild(blackSoldier_3);
        ChessPiece blackSoldier_4 = new ChessPiece(Side.BLACK, PieceType.SOLDIER, 4);
        chessBoard.addChild(blackSoldier_4);
        ChessPiece blackSoldier_5 = new ChessPiece(Side.BLACK, PieceType.SOLDIER, 5);
        chessBoard.addChild(blackSoldier_5);
        //
    }
}
