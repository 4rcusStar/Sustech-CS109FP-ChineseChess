import Engine.GameEngine;
import Engine.GameObject;

class Main
{
    static GameEngine engine = GameEngine.getInstance();
    public static void main(String[] args)
    {
        GameObject gameManager = new GameObject("GameManager");
        gameManager.addComponent(new LogicManager());
        GameObject testChildObject = new GameObject("TestChildObject");
        gameManager.addChild(testChildObject);
        System.out.println(gameManager.getChildren().getFirst().getName());
        System.out.println(gameManager.getChildren().getFirst().getParent().getName());
        engine.registerGameObject(gameManager);
        engine.runEngine();
    }
}