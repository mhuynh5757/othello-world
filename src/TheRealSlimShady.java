public class TheRealSlimShady implements OthelloPlayer
{

    private OthelloGame game;
    private OthelloSide mySide;

    @Override
    public void init(OthelloSide side)
    {
        mySide = side;
    }

    @Override
    public Move doMove(Move opponentsMove, long millisLeft)
    {
        int x = 0, y = 0;
        Move result = new Move(x, y);

        while (!game.getBoard()
                .checkMove(new Move(x, y), mySide))
        {
            if (++x > 7)
            {
                x = 0;
                ++y;
            }
            if (y > 7)
            {
                return null;
            }
            result.set(x, y);
        }

        return result;
    }

    public void setGame(OthelloGame g)
    {
        game = g;
    }
}
