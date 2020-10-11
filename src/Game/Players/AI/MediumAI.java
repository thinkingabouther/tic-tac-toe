package tictactoe.Game.Players.AI;

import tictactoe.Game.Field.Field;
import tictactoe.Game.Field.Move;

import java.util.Random;

public class MediumAI extends EasyAI {

    private static final Random _random = new Random();
    public MediumAI(char Char) {
        super(Char);
        _level = "medium";
    }

    @Override
    public void PerformTurn(Field field) {
        var move = CheckMove(field);
        if (move != Move.EmptyMove()) field.PutCharAtAi(Char, move.i, move.j);
        else super.PerformTurn(field);
    }

    private Move CheckMove(Field field) {
        var move = Move.EmptyMove();
        for (int i = 0; i < field.Size; i++) {
            move = CheckRow(field, i);
            if (move != Move.EmptyMove()) return move;
            move = CheckColumn(field, i);
            if (move != Move.EmptyMove()) return move;
        }

        move = CheckPrimaryDiagonal(field);
        if (move != Move.EmptyMove()) return move;

        move = CheckSecondaryDiagonal(field);
        if (move != Move.EmptyMove()) return move;

        return move;
    }


    private boolean CheckSumOneMoveWin(int sum) { return sum == 271 || sum == 191; }

    private Move CheckRow(Field field, int rowIndex) {
        var sum = 0;
        int placeIndex = 0;
        for (int i = 0; i < field.Size; i++) {
            var currentChar = field.GetCharAt(rowIndex, i);
            sum += currentChar;
            if (currentChar == '_') placeIndex = i;
        }
        if (CheckSumOneMoveWin(sum)) return new Move(rowIndex, placeIndex);
        return Move.EmptyMove();
    }

    private Move CheckColumn(Field field, int columnIndex) {
        var sum = 0;
        int placeIndex = 0;
        for (int i = 0; i < field.Size; i++) {
            var currentChar = field.GetCharAt(i, columnIndex);
            sum += currentChar;
            if (currentChar == '_') placeIndex = i;
        }
        if (CheckSumOneMoveWin(sum)) return new Move(placeIndex, columnIndex);
        return Move.EmptyMove();
    }

    private Move CheckPrimaryDiagonal(Field field) {
        var sum = 0;
        int placeIndex = 1;
        for (int i = 0; i < field.Size; i++) {
            var currentChar = field.GetCharAt(i, i);
            sum += currentChar;
            if (currentChar == '_') placeIndex = i;
        }
        if (CheckSumOneMoveWin(sum)) return new Move(placeIndex, placeIndex);
        return Move.EmptyMove();
    }

    private Move CheckSecondaryDiagonal(Field field) {
        var sum = 0;
        int placeIndex = 1;
        for (int i = 0; i < field.Size; i++) {
            var currentChar = field.GetCharAt(field.Size - i - 1, i);
            sum += currentChar;
            if (currentChar == '_') placeIndex = i;
        }
        if (CheckSumOneMoveWin(sum)) return new Move(field.Size - placeIndex, placeIndex);
        return Move.EmptyMove();
    }
}

