//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.LinkedList;
import java.util.Random;

public class SimplePlayer implements OthelloPlayer
{
    private OthelloSide side;
    private OthelloSide opponentSide;
    private Random gen = new Random();
    private OthelloBoard board = new OthelloBoard();

    public SimplePlayer()
    {
    }

    public void init(OthelloSide var1)
    {
        this.side = var1;
        if (var1 == OthelloSide.BLACK)
        {
            this.opponentSide = OthelloSide.WHITE;
        }
        else
        {
            this.opponentSide = OthelloSide.BLACK;
        }

    }

    public Move doMove(Move var1, long var2)
    {
        if (var1 != null)
        {
            this.board.move(var1, this.opponentSide);
        }

        LinkedList var5 = this.getMoveList(this.board, this.side);
        if (var5.size() != 0)
        {
            Move var4 = (Move) var5.get(this.gen.nextInt(var5.size()));
            this.board.move(var4, this.side);
            return var4;
        }
        else
        {
            System.out.println("PASS");
            return null;
        }
    }

    private LinkedList getMoveList(OthelloBoard var1, OthelloSide var2)
    {
        LinkedList var4 = new LinkedList();

        for (int var5 = 0; var5 < 8; ++var5)
        {
            for (int var6 = 0; var6 < 8; ++var6)
            {
                Move var3 = new Move(var5, var6);
                if (var1.checkMove(var3, var2))
                {
                    var4.add(var3);
                }
            }
        }

        return var4;
    }
}
