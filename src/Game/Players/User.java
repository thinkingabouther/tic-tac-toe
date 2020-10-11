package tictactoe.Game.Players;

import tictactoe.Game.Field.Field;
import tictactoe.Game.Players.AI.AI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class User extends AI {

    private final Scanner _scanner;

    public User(char Char, Scanner scanner) {
        super(Char);
        _scanner = scanner;
    }

    @Override
    public void PerformTurn(Field field) {
        var isInputValid = false;
        while (!isInputValid) {
            System.out.print("Enter the coordinates: ");
            int i = 0, j = 0;
            try {
                i = _scanner.nextInt();
                j = _scanner.nextInt();
            }
            catch (InputMismatchException exception) {
                System.out.println("You should enter numbers!");
                _scanner.nextLine();
                continue;
            }
            if (i < 1 || i > field.Size || j < 1 || j > field.Size) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (field.IsOccupiedUser(i, j)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            field.PutCharAtUser(Char, i, j);
            isInputValid = true;
        }
    }
}

