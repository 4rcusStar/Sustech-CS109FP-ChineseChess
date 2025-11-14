package ChineseChess;

import ChineseChess.ChessBoard.ChessBoard;
import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.ChessPiece.ChessPiece;
import Engine.Components.SpriteRenderer;
import Engine.Core.GameObject;
import Engine.GameBuilding.GameWorldConstructor;
import javafx.scene.image.Image;

public class ChessWorldConstructor extends GameWorldConstructor
{
    @Override
    public void construct(GameObject root)
    {
        ChessBoard chessBoard = new ChessBoard("ChessBoard");
        root.addChild(chessBoard);
        ChessPiece blackKing = new ChessPiece("King","Black",0,0);
        chessBoard.addChild(blackKing);
        blackKing.getComponent(SpriteRenderer.class).setSprite(new Image("file:/C:/Users/ASUS/IdeaProjects/FP-ChineseChess/src/black-jiang.png"));
    }
}
