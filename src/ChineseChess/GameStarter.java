package ChineseChess;

import Engine.*;

public class GameStarter
{

    public static void main(String[] args)
    {
        GameEngine engine = GameEngine.getInstance();
        GameManager gameManager = GameManager.getInstance();
        gameManager.init();

        //获取gameManager创建的默认场景
        Scene scene = gameManager.getCurrentScene();
        GameObject root = scene.getRoot();

        //创建棋盘
        ChessBoard chessBoard = new ChessBoard("ChessBoard");
        root.addChild(chessBoard);

        engine.runEngine();
    }
}
