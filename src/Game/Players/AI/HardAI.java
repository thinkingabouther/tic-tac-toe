package tictactoe.Game.Players.AI;

import tictactoe.Game.Field.Field;
import tictactoe.Game.Field.Move;
import tictactoe.Game.Field.Status;

import static java.lang.Integer.max;
import static java.lang.Integer.min;


public class HardAI extends AI {

    private final int _drawScore = 0;
    private final int _winScore = 10;
    private final int _loseScore = -10;

    public HardAI(char Char) {
        super(Char);
        _level = "hard";
    }


    @Override
    public void PerformTurn(Field field) {
        var move = Move.EmptyMove();
        var score = Integer.MIN_VALUE;
        for (int i = 0; i < field.Size; i++) {
            for (int j = 0; j < field.Size; j++) {
                if (!field.IsOccupied(i, j)) {
                    var tempField = field.Copy();
                    tempField.PutCharAtAi(Char, i, j);
                    var currentScore = Minimax(tempField, false);
                    if (currentScore > score) {
                        score = currentScore;
                        move = new Move(i, j);
                    }
                }
            }
        }
        field.PutCharAtAi(Char, move.i, move.j);
        super.PerformTurn(field);
    }


    private int Minimax(Field field, boolean isMaximising) {
        var status = field.CheckStatus();
        if (status == Status.Draw) {
            return _drawScore;
        }
        if (status == Status.WinO && Char == 'O' || status == Status.WinX && Char == 'X') {
            return _winScore;
        }
        if (status == Status.WinO && Char == 'X' || status == Status.WinX && Char == '0') {
            return _loseScore;
        } else {
            int bestScore;
            if (isMaximising) {
                bestScore = Integer.MIN_VALUE;
                for (int i = 0; i < field.Size; i++) {
                    for (int j = 0; j < field.Size; j++) {
                        if (!field.IsOccupied(i, j)) {
                            var tempField = field.Copy();
                            tempField.PutCharAtAi(i, j);
                            var currentScore = Minimax(tempField, false);
                            bestScore = max(bestScore, currentScore);
                        }
                    }
                }
            } else {
                bestScore = Integer.MAX_VALUE;
                for (int i = 0; i < field.Size; i++) {
                    for (int j = 0; j < field.Size; j++) {
                        if (!field.IsOccupied(i, j)) {
                            var tempField = field.Copy();
                            tempField.PutCharAtAi(i, j);
                            var currentScore = Minimax(tempField, true);
                            bestScore = min(bestScore, currentScore);
                        }
                    }
                }
            }
            return bestScore;
        }
    }
}