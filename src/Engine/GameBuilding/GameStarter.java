package Engine.GameBuilding;

import Engine.Core.GameEngine;
import Engine.Core.RenderEngine;
import Engine.Core.GameObject;
import Engine.Input;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.UI.GameTimer;
import ChineseChess.UsersAndSavingSystem.SaveService;

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
        //关闭窗口时，保存游戏并关闭引擎
        stage.setOnCloseRequest(event ->
        {
            // 调用当前场景的onExit()
            GameWorld currentWorld = worldManager.getCurrentGameWorld();
            if (currentWorld != null)
            {
                currentWorld.onExit();
            }
            
            // 如果当前场景是游戏场景，保存游戏
            saveGameIfNeeded(worldManager);
            
            // 停止引擎
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

    /**
     * 如果当前场景是游戏场景，保存游戏
     */
    private static void saveGameIfNeeded(GameWorldManager worldManager)
    {
        GameWorld currentWorld = worldManager.getCurrentGameWorld();
        if (currentWorld == null)
        {
            return;
        }

        // 检查是否是游戏场景（ChessGameWorld）
        String worldName = worldManager.getCurrentWorldName();
        if (!"Game".equals(worldName))
        {
            return; // 不是游戏场景，不需要保存
        }

        // 获取场景根节点
        GameObject root = currentWorld.getRoot();
        if (root == null)
        {
            return;
        }

        // 查找ChessBoard
        GameObject chessBoardObj = root.getChild("ChessBoard");
        if (chessBoardObj == null)
        {
            return;
        }

        ChessBoardManager board = chessBoardObj.getComponent(ChessBoardManager.class);
        if (board == null)
        {
            return;
        }

        // 如果游戏已结束，不保存
        if (board.isGameOver())
        {
            return;
        }

        // 获取GameTimer的时间
        GameObject sideBarUI = root.getChild("SideBarUI");
        long[] times = new long[]{0, 0, 0};
        if (sideBarUI != null)
        {
            GameTimer timer = sideBarUI.getComponent(GameTimer.class);
            if (timer != null)
            {
                times = timer.getTimes();
            }
        }

        // 保存游戏
        SaveService.save(board, times[0], times[1], times[2]);
    }

}
