package tictactoe.Game;

import tictactoe.Game.Field.Field;
import tictactoe.Game.Players.AI.AI;

public class Game {

    private final Field _field;

    private final AI[] players = new AI[2];

    public Game(AI player1, AI player2) {
        _field = new Field();
        _field.Init();
        _field.Show();
        players[0] = player1;
        players[1] = player2;
    }

    public void Loop() {
        var isFinished = false;
        while (!isFinished) {
            for (AI player : players) {
                player.PerformTurn(_field);
                _field.Show();
                if (IsFinished()) {
                    isFinished = true;
                    break;
                }
            }
        }
    }

    private boolean IsFinished(){
        var status = _field.CheckStatus();
        switch (status) {
            case Impossible:
                System.out.println("Impossible");
                break;
            case NotFinished:
                return false;
            case Draw:
                System.out.println("Draw");
                break;
            case WinX:
                System.out.println("X wins");
                break;
            case WinO:
                System.out.println("O wins");
                break;
        }
        return true;
    }

}
