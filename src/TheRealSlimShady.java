import java.util.ArrayList;

public class TheRealSlimShady implements OthelloPlayer
{
    private OthelloGame game;

    private OthelloSide mySide;

    private OthelloSide oppSide;

    private int[][] heuristicScore = {
            { 20, -3, 11,  8,  8, 11, -3, 20},
            { -3, -7, -4,  1,  1, -4, -7, -3},
            { 11, -4,  2,  2,  2,  2, -4, 11},
            {  8,  1,  2, -3, -3,  2,  1,  8},
            {  8,  1,  2, -3, -3,  2,  1,  8},
            { 11, -4,  2,  2,  2,  2, -4, 11},
            { -3, -7, -4,  1,  1, -4, -7, -3},
            { 20, -3, 11,  8,  8, 11, -3, 20},
    };

    private int scoreBoard(OthelloBoard b, OthelloSide s)
    {
        int score = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(b.get(s, j, i))
                {
                    score += heuristicScore[j][i];
                }
                else if (b.get(s.opposite(), j, i))
                {
                    score -= heuristicScore[j][i];
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
        OthelloBoard b = game.getBoard().copy();

        if (!b.hasMoves(mySide))
        {
            return null;
        }

        ArrayList<Move> possible_moves = findPossibleMoves(b, mySide);

        int max_score = Integer.MIN_VALUE;
        Move best_move = possible_moves.get(0);
        for (Move m : possible_moves)
        {
            OthelloBoard new_board = b.copy();
            new_board.move(m, mySide);
            int score = -negascout(new_board, oppSide, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
            if (max_score < score)
            {
                max_score = score;
                best_move = m;
                System.out.println("Possible move: " + m.toString() + " Score: " + score);
            }
        }

        System.out.println("Best move: " + best_move.toString() + " Score: " + max_score);

        return best_move;
    }

    private int negascout(OthelloBoard board, OthelloSide side, int alpha, int beta, int depth)
    {
        if (!board.hasMoves(side) || depth <= 0)
        {
            return scoreBoard(board, side);
        }
        ArrayList<Move> possible_moves = findPossibleMoves(board, side);
        for (int i = 0; i < possible_moves.size(); i++)
        {
            OthelloBoard new_board = board.copy();
            new_board.move(possible_moves.get(i), side);
            int score;
            if (i > 0)
            {
                score = -negascout(new_board, side.opposite(), -alpha - 1, -alpha, depth - 1);
                if (alpha < score && score < beta)
                {
                    score = -negascout(new_board, side.opposite(), -beta, -score, depth - 1);
                }
            }
            else
            {
                score = -negascout(new_board, side.opposite(), -beta, -alpha, depth - 1);
            }
            alpha = Math.max(alpha, score);
            if (alpha >= beta)
            {
                return alpha;
            }
        }
        return alpha;
    }

    public void setGame(OthelloGame g)
    {
        game = g;
    }
}