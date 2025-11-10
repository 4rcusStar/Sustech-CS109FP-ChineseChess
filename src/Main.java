import Engine.GameEngine;

class Main
{
    static GameManager manager= GameManager.getInstance();
    static GameEngine engine = GameEngine.getInstance();
    public static void main(String[] args)
    {
        manager = GameManager.getInstance();
        engine.runEngine();
    }
}