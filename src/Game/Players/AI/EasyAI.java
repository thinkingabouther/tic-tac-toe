package tictactoe.Game.Players.AI;

import tictactoe.Game.Field.Field;

import java.util.Random;

public class EasyAI extends AI {
    private static final Random _random = new Random();
    public EasyAI(char Char) {
        super(Char);
        _level = "easy";
    }

    @Override
    public void PerformTurn(Field field) {
        boolean inputValid = false;
        while (!inputValid) {
            var i = _random.nextInt(3) + 1;
            var j = _random.nextInt(3) + 1;
            if (!field.IsOccupiedUser(i, j)) {
                field.PutCharAtUser(Char, i, j);
                inputValid = true;
            }
        }
        super.PerformTurn(field);
    }
}

