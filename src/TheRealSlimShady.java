public class TheRealSlimShady implements OthelloPlayer
{
    private OthelloGame game;

    private OthelloSide mySide;

    private int[][] heuristicScore = {
            {100, -25, 5, 5, 5, 5, -25, 100},
            {-25, -50, 5, 5, 5, 5, -50, -25},
            {5, 5, 5, 5, 5, 5, 5, 5},
            {5, 5, 5, 5, 5, 5, 5, 5},
            {5, 5, 5, 5, 5, 5, 5, 5},
            {5, 5, 5, 5, 5, 5, 5, 5},
            {-25, -50, 5, 5, 5, 5, -50, -25},
            {100, -25, 5, 5, 5, 5, -25, 100}
    };

    @Override
    public void init(OthelloSide side)
    {
        mySide = side;
    }

    @Override
    public Move doMove(Move opponentsMove, long millisLeft)
    {
        if (!game.getBoard()
                .hasMoves(mySide))
        {
            return null;
        }

        OthelloBoard b = game.getBoard();
        int x = 0, y = 0, min_score = -100;
        Move result = new Move(x, y);

        while (!b.checkMove(result, mySide))
        {
            if (heuristicScore[y][x] > min_score)
            {
                result.set(x, y);
            }
            if (++x > 7)
            {
                x = 0;
                ++y;
            }
        }

        return result;
    }

    public void setGame(OthelloGame g)
    {
        game = g;
    }
}