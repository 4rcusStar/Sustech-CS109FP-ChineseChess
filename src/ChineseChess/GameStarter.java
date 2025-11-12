package ChineseChess;

import Engine.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GameStarter extends Application
{
    public void start(Stage stage) throws Exception
    {
        //初始化游戏世界
        GameEngine engine = GameEngine.getInstance();
        GameManager gameManager = GameManager.getInstance();
        gameManager.init();
        //初始化渲染器
        Canvas canvas  = new Canvas(800,800);
        EngineRenderer renderEngine = EngineRenderer.getInstance(canvas);

        //获取gameManager创建的默认场景
        GameWorld gameWorld = gameManager.getCurrentScene();
        GameObject root = gameWorld.getRoot();

        /*//创建棋盘
        ChessBoard chessBoard = new ChessBoard("ChessBoard");
        root.addChild(chessBoard);*/
        //临时创建一个棋子
        ChessPiece piece = new ChessPiece("King","Red",200,300);
        piece.addComponent(new SpriteRenderer());

        renderEngine.registerRenderer(piece.getComponent(SpriteRenderer.class));
        renderEngine.startRenderLoop();
        root.addChild(piece);
        engine.runEngine();

        //JavaFX 场景
        Scene fxScene = new Scene(new StackPane(canvas));
        stage.setScene(fxScene);
        stage.setTitle("Chinese Chess");
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
