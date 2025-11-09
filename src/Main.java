import Engine.GameEngine;

class Main
{
    static GameEngine engine = GameEngine.getInstance();
    static GameManager manager = GameManager.getInstance();
    public static void main(String[] args)
    {
        engine.runEngine();
    }
}