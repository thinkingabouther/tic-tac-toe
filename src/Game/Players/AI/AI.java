package tictactoe.Game.Players.AI;

import tictactoe.Game.Field.Field;
import tictactoe.Game.Players.User;

import java.util.Scanner;

public abstract class AI {
    public char Char;
    protected String _level = "_";

    public AI(char Char) {
        this.Char = Char;
    }
    public void PerformTurn(Field field) {
        AnnounceMove();
    }

    public void AnnounceMove() {
        System.out.printf("Making move level \"%s\"\n", _level);
    }

    public static AI InitializePlayer(char Char, Scanner inputScanner) {
        var inputPlayer = inputScanner.next();
        AI player = null;
        if (inputPlayer.equals("easy")) {
            player = new EasyAI(Char);
        }
        else if (inputPlayer.equals("medium")) {
            player = new MediumAI(Char);
        }
        else if (inputPlayer.equals("hard")) {
            player = new HardAI(Char);
        }
        else if (inputPlayer.equals("user")) {
            player = new User(Char, inputScanner);
        }
        else System.out.println("Bad parameters!");
        return player;
    }
}
