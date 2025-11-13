package Engine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameStarter extends Application
{
    private static float _canvasWidth;
    private static float _canvasHeight;
    private static String _title;
    private static GameEngine engine = GameEngine.getInstance();

    private static GameWorldConstructor _gameWorldConstructor;


    public static void setStartSetting(float canvasWidth, float canvasHeight, String title, GameWorldConstructor gameWorldConstructor)
    {
        _canvasHeight = canvasHeight;
        _canvasWidth = canvasWidth;
        _title = title;
        _gameWorldConstructor = gameWorldConstructor;
    }

    public static void launchGame(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        GameWorld gameWorld = new GameWorld(_gameWorldConstructor);
        GameObject root = gameWorld.getRoot();
        engine.registerGameObject(root);

        //初始化渲染器
        Canvas canvas = new Canvas(_canvasWidth, _canvasHeight);
        EngineRenderer renderEngine = EngineRenderer.getInstance(canvas);
        Input.bindCanvas(canvas);

        renderEngine.startRenderLoop();
        renderEngine.registerGameWorld(gameWorld);
        engine.runEngine();

        //JavaFX 场景
        Scene fxScene = new Scene(new StackPane(canvas));
        stage.setScene(fxScene);
        //关闭窗口时，引擎也关闭
        stage.setOnCloseRequest(event ->
        {
            GameEngine.getInstance().stopEngine();
        });
        stage.setTitle(_title);
        stage.show();
    }

}
