public class TestChessPiece extends ChessPiece
{
    public TestChessPiece(ChessPieceSide chessPieceSide)
    {
        side=chessPieceSide;
    }

    boolean isValidMove(int desX, int desY)
    {
        return true;
    }

    public void awake()
    {
        //System.out.println(getName() + " Awake");
    }

    public void start()
    {
        //System.out.println(getName() + " Start");
    }

    public void update()
    {
        //System.out.println(getName() + " Update");
    }
}
