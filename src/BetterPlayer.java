//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.LinkedList;
import java.util.ListIterator;

public class BetterPlayer implements OthelloPlayer {
    static final float RECURSION_WEIGHT = 0.85F;
    private OthelloSide side;
    private OthelloSide opponentSide;
    private OthelloBoard board = new OthelloBoard();

    public BetterPlayer() {
    }

    private int RecLevel() {
        return 3;
    }

    public void init(OthelloSide var1) {
        this.side = var1;
        if(var1 == OthelloSide.BLACK) {
            this.opponentSide = OthelloSide.WHITE;
        } else {
            this.opponentSide = OthelloSide.BLACK;
        }

    }

    public Move doMove(Move var1, long var2) {
        if(var1 != null) {
            this.board.move(var1, this.opponentSide);
        }

        LinkedList var4 = this.getMoveList(this.board, this.side);
        if(var4.size() != 0) {
            ListIterator var6 = var4.listIterator();
            int var7 = -1000000;
            Move var9 = null;

            while(var6.hasNext()) {
                Move var10 = (Move)var6.next();
                OthelloBoard var5 = this.board.copy();
                var5.move(var10, this.side);
                int var8 = this.evaluateBoard(var5, this.side);
                var8 = (int)((float)var8 - 0.85F * (float)this.recurse(this.RecLevel(), var5, this.side.opposite()));
                if(var8 > var7) {
                    var9 = var10;
                    var7 = var8;
                }
            }

            System.out.println("done iters, picked: " + var9);
            this.board.move(var9, this.side);
            return var9;
        } else {
            System.out.println("PASS");
            return null;
        }
    }

    private int recurse(int var1, OthelloBoard var2, OthelloSide var3) {
        if(var1 == 0) {
            return this.evaluateBoard(var2, var3);
        } else {
            LinkedList var4 = this.getMoveList(var2, var3);
            if(var4.size() != 0) {
                ListIterator var6 = var4.listIterator(0);
                int var7 = -1000000;
                Object var8 = null;

                while(var6.hasNext()) {
                    Move var9 = (Move)var6.next();
                    OthelloBoard var5 = var2.copy();
                    var5.move(var9, var3);
                    int var10 = this.evaluateBoard(var5, var3);
                    var10 = (int)((float)var10 - 0.85F * (float)this.recurse(var1 - 1, var5, var3.opposite()));
                    if(var10 > var7) {
                        var7 = var10;
                    }
                }

                return var7;
            } else {
                return -35;
            }
        }
    }

    private int evaluateBoard(OthelloBoard var1, OthelloSide var2) {
        int var3 = 0;

        for(int var4 = 0; var4 <= 7; ++var4) {
            for(int var5 = 0; var5 <= 7; ++var5) {
                if(var1.get(var2, var4, var5)) {
                    var3 += this.evaluatePiece(var1, var2, var4, var5);
                } else if(var1.get(this.opponentSide, var4, var5)) {
                    var3 -= this.evaluatePiece(var1, this.opponentSide, var4, var5);
                }
            }
        }

        return var3;
    }

    private int evaluatePiece(OthelloBoard var1, OthelloSide var2, int var3, int var4) {
        int var5 = 1;
        if((var3 == 0 || var3 == 7) && (var4 == 0 || var4 == 7)) {
            var5 += 40;
        }

        if(var3 == 0 || var3 == 7 || var4 == 0 || var4 == 7) {
            var5 += 10;
        }

        if(var3 == 1 || var3 == 6 || var4 == 1 || var4 == 6) {
            --var5;
            if(var3 == 1 && var4 == 1 && !var1.get(var2, 0, 0)) {
                var5 -= 5;
            }

            if(var3 == 1 && var4 == 6 && !var1.get(var2, 0, 7)) {
                var5 -= 5;
            }

            if(var3 == 6 && var4 == 1 && !var1.get(var2, 7, 0)) {
                var5 -= 5;
            }

            if(var3 == 6 && var4 == 6 && !var1.get(var2, 7, 7)) {
                var5 -= 5;
            }
        }

        return var5;
    }

    private LinkedList getMoveList(OthelloBoard var1, OthelloSide var2) {
        LinkedList var4 = new LinkedList();

        for(int var5 = 0; var5 <= 7; ++var5) {
            for(int var6 = 0; var6 <= 7; ++var6) {
                Move var3 = new Move(var5, var6);
                if(var1.checkMove(var3, var2)) {
                    var4.add(var3);
                }
            }
        }

        return var4;
    }
}
