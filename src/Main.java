import Engine.GameEngine;
import Engine.GameObject;

class Main
{
    static GameEngine engine = GameEngine.getInstance();
    public static void main(String[] args)
    {
        GameObject gameManager = new GameObject("GameManager");
        gameManager.addComponent(new LogicManager());
        engine.registerGameObject(gameManager);
        engine.runEngine();
    }
}