public class TheRealSlimShady implements OthelloPlayer
{

    private OthelloGame myGame;
    private OthelloSide mySide;

    @Override
    public void init(OthelloSide side)
    {
        mySide = side;
    }

    @Override
    public Move doMove(Move opponentsMove, long millisLeft)
    {
        long start_time = System.currentTimeMillis();
        int x = 0, y = 0;
        Move result = new Move(x, y);

        while (!myGame.getBoard().checkMove(new Move(x, y), mySide))
        {
            result.setX(++x);
            result.setY(++y);
        }



        return result;
    }

    public void setGame(OthelloGame g)
    {
        myGame = g;
    }
}
