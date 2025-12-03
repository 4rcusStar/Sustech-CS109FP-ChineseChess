package Engine.GameBuilding;

import Engine.Core.GameEngine;
import Engine.Core.RenderEngine;
import Engine.Input;
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
    private static StackPane rootPane;
    private static Runnable onEngineReadyCallback;

    public static void setStartSetting(float canvasWidth, float canvasHeight, String title, GameWorldConstructor gameWorldConstructor)
    {
        _canvasHeight = canvasHeight;
        _canvasWidth = canvasWidth;
        _title = title;
        _gameWorldConstructor = gameWorldConstructor;
    }

    /**
     * 引擎准备完后的回调
     * @param callback 
     */
    public static void setOnEngineReady(Runnable callback)
    {
        onEngineReadyCallback = callback;
    }

    public static void launchGame(String[] args)
    {
        Application.launch(args);
    }

    public static void setResizable(boolean resizable)
    {
    }

    @Override
    public void start(Stage stage)
    {
        Canvas canvas = new Canvas(_canvasWidth, _canvasHeight);
        
        //初始化渲染器
        RenderEngine renderEngine = RenderEngine.getInstance(canvas);
        Input.bindCanvas(canvas);

        renderEngine.startRenderLoop();
        engine.runEngine();

        GameWorldManager worldManager = GameWorldManager.getInstance();
        
        //JavaFX StackPane场景（用于UI）
        rootPane = new StackPane(canvas);
        Scene fxScene = new Scene(rootPane);
        stage.setScene(fxScene);
        
        //初始化引擎
        worldManager.initialize(engine, renderEngine);
        
        //执行回调，让游戏代码控制初始场景切换
        if(onEngineReadyCallback != null)
        {
            onEngineReadyCallback.run();
        }
        //关闭窗口时，引擎也关闭
        stage.setOnCloseRequest(event ->
        {
            GameEngine.getInstance().stopEngine();
        });
        stage.setTitle(_title);
        stage.setResizable(false);
        stage.show();
    }

    public static StackPane getRootPane()
    {
        return rootPane;
    }

}
