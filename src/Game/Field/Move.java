package tictactoe.Game.Field;

public class Move {
    public int i;
    public int j;
    private static final Move emptyMove = new Move(-1, -1);

    public Move(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public static Move EmptyMove() {
        return emptyMove;
    }
}
