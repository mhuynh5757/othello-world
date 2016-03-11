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

    @Override
    public String toString()
    {
        return "Move: " + getMove().toString() + " | Score: " + getScore();
    }
}

public class TheRealSlimShady implements OthelloPlayer
{
    private OthelloGame game;

    private OthelloSide mySide;

    private OthelloSide oppSide;

    private int[][] heuristicScore = {
            {40, 5, 10, 10, 10, 10, 5, 40},
            {5, 0, 10, 10, 10, 10, 0, 5},
            {10, 10, 10, 10, 10, 10, 10, 10},
            {10, 10, 10, 10, 10, 10, 10, 10},
            {10, 10, 10, 10, 10, 10, 10, 10},
            {10, 10, 10, 10, 10, 10, 10, 10},
            {5, 0, 10, 10, 10, 10, 0, 5},
            {40, 5, 10, 10, 10, 10, 5, 40},
    };

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
                else if (b.get(oppSide, j, i))
                {
                    score -= heuristicScore[i][j];
                }
            }
        }
        return score;
    }

    private ArrayList<Move> findPossibleMoves(OthelloBoard b, OthelloSide s)
    {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                Move m = new Move(j, i);
                if (b.checkMove(m, s))
                {
                    moves.add(m);
                }
            }
        }
        return moves;
    }

    @Override
    public void init(OthelloSide side)
    {
        mySide = side;
        oppSide = mySide.opposite();
    }

    @Override
    public Move doMove(Move opponentsMove, long millisLeft)
    {
        OthelloBoard curr_board = game.getBoard();

        ArrayList<Move> possible_moves = findPossibleMoves(curr_board, mySide);

        if (possible_moves.size() > 0)
        {
            Move best_move = possible_moves.get(0);

            OthelloBoard new_board = curr_board.copy();
            new_board.move(best_move, mySide);
            int best_score = minimax(new_board, oppSide, 3);

            for (int i = 1; i < possible_moves.size(); i++)
            {
                new_board = curr_board.copy();
                new_board.move(possible_moves.get(i), mySide);
                int temp_s = minimax(new_board, oppSide, 3);
                if (temp_s > best_score)
                {
                    best_score = temp_s;
                    best_move = possible_moves.get(i);
                }
            }

            return best_move;
        }
        else
        {
            return null;
        }
    }

    private int minimax(OthelloBoard b, OthelloSide s, int depth)
    {
        if (depth > 0)
        {
            ArrayList<Move> possible_moves = findPossibleMoves(b, s);
            ArrayList<Integer> scores = new ArrayList<>();
            for (Move mv : possible_moves)
            {
                OthelloBoard new_board = b.copy();
                new_board.move(mv, s);
                scores.add(minimax(new_board, s.opposite(), depth - 1));
            }
            if (scores.size() > 0)
            {
                int min_score = scores.get(0);
                for (Integer i : scores)
                {
                    if (min_score > i)
                    {
                        min_score = i;
                    }
                }
                return min_score;
            }
        }
        return scoreBoard(b);
    }

    public void setGame(OthelloGame g)
    {
        game = g;
    }
}