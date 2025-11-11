import Engine.*;

class Main
{
    static GameEngine engine = GameEngine.getInstance();
    public static void main(String[] args)
    {
        //创建,初始化GameManger
        GameObject gameManager = new GameObject("GameManger");
        gameManager.addComponent(new GameManager());
        gameManager.getComponent(GameManager.class).setScene(new Scene());
        GameObject logicManager = new GameObject("LogicManager");
        logicManager.addComponent(new LogicManager());
        GameObject uiManager = new GameObject("UIManager");
        uiManager.addComponent(new UIManager());
        gameManager.addChild(uiManager);
        gameManager.addChild(logicManager);


        //创建棋盘
        GameObject chessBoard = new GameObject("ChessBoard");
        chessBoard.addComponent(new Chessboard());

        //构建场景
        Scene gameScene = gameManager.getComponent(GameManager.class).getCurrentScene();
        GameObject sceneRoot = gameScene.getRoot();
        sceneRoot.addChild(chessBoard);

        engine.registerGameObject(gameManager);
        engine.registerGameObject(sceneRoot);
        engine.runEngine();
    }
}