import java.util.ArrayList;

class scorePair
{
    int score = 0;
    Move move;

    scorePair(int s, Move m)
    {
        score = s;
        move = m;
    }

    int getScore()
    {
        return score;
    }

    Move getMove()
    {
        return move;
    }
}

public class TheRealSlimShady implements OthelloPlayer
{
    private OthelloGame game;

    private OthelloSide mySide;

    private OthelloSide oppSide;

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
        oppSide = mySide.opposite();
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

        scorePair res = minimax(b);

        return res.getMove();
    }

    private int scoreBoard(OthelloBoard b)
    {
        int score = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (b.get(mySide, j, i))
                {
                    score += heuristicScore[i][j];
                }
                else
                {
                    score -= heuristicScore[i][j];
                }
            }
        }
        return score;
    }

    private scorePair minimax(OthelloBoard b)
    {
        ArrayList<scorePair> scores = new ArrayList<>();

        if (b.hasMoves(mySide))
        {
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    Move myMove = new Move(j, i);
                    if (b.checkMove(myMove, mySide))
                    {
                        OthelloBoard my_new_board = b.copy();
                        my_new_board.move(myMove, mySide);
                        if (my_new_board.hasMoves(oppSide))
                        {
                            for (int k = 0; k < 8; k++)
                            {
                                for (int l = 0; l < 8; l++)
                                {
                                    Move theirMove = new Move(l, k);
                                    if (my_new_board.checkMove(theirMove, oppSide))
                                    {
                                        OthelloBoard their_new_board = my_new_board.copy();
                                        their_new_board.move(theirMove, oppSide);
                                        scores.add(new scorePair(scoreBoard(their_new_board), myMove));
                                    }
                                }
                            }
                        }
                        else
                        {
                            scores.add(new scorePair(scoreBoard(my_new_board), myMove));
                        }
                    }
                }
            }
        }
        if (scores.size() > 0)
        {
            int best_score = scores.get(0).getScore();
            int best_sp = 0;
            for (int i = 0; i < scores.size(); i++)
            {
                if (best_score < scores.get(i).getScore())
                {
                    best_score = scores.get(i).getScore();
                    best_sp = i;
                }
            }
            return scores.get(best_sp);
        }
        else
        {
            return null;
        }
    }

    public void setGame(OthelloGame g)
    {
        game = g;
    }
}