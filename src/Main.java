import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static int fieldSize = 3;
    private enum Status {
        Impossible,
        NotFinished,
        Draw,
        WinX,
        WinO
    }
    public static void main(String[] args) {
        var field = new char[fieldSize][fieldSize];
        InitField(field);
        ShowField(field);
        GameLoop(field);
    }

    private static void GameLoop(char[][] field)
    {
        var scanner = new Scanner(System.in);
        var isFinished = false;
        int turnsCount = 0;
        while (!isFinished) {
            PerformTurn(field, scanner, turnsCount);
            turnsCount++;
            ShowField(field);
            var status = CheckStatus(field);
            switch (status) {
                case Impossible:
                    System.out.println("Impossible");
                    break;
                case NotFinished:
                    continue;
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
            isFinished = true;
        }
    }
    private static void PerformTurn(char[][] field, Scanner scanner, int turnsCount) {
        System.out.print("Enter the coordinates: ");
        var isInputValid = false;
        while (!isInputValid) {
            int i = 0, j = 0;
            try {
                i = scanner.nextInt();
                j = scanner.nextInt();
            }
            catch (InputMismatchException exception) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (i < 1 || i > fieldSize || j < 1 || j > fieldSize){
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            var coordinateI = fieldSize - j;
            var coordinateJ = i - 1;
            if (field[coordinateI][coordinateJ] != '_') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            field[coordinateI][coordinateJ] = turnsCount % 2 == 0 ? 'X' : 'O';
            isInputValid = true;
        }
    }

    private static Status CheckStatus(char[][] field) {
        if (IsImpossible(field)) return Status.Impossible;
        else {
            char[] answers = { CheckRows(field), CheckColumns(field), CheckDiagonals(field)};
            int winnersCount = 0;
            char winner = '_';
            for (char answer : answers){
                if (answer == 'E') {
                    return Status.Impossible;
                }
                if (answer != '_') {
                    winnersCount++;
                    winner = answer;
                }
            }
            if (winnersCount > 1) return Status.Impossible;
            else if (winner == '_') {
                if (IsFinished(field)) return Status.Draw;
                else return Status.NotFinished;
            }
            else return winner == 'X' ? Status.WinX : Status.WinO;
        }
    }

    private static void InitField(char[][] field){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                field[i][j] = '_';
            }
        }
    }
    private static boolean IsFinished(char[][] field){
        for (char[] row : field) {
            for (char el : row) {
                if (el == '_') return false;
            }
        }
        return true;
    }
    private static boolean IsImpossible(char[][] field){
        int countX = 0;
        int countO = 0;
        for (char[] row : field) {
            for (char el : row) {
                if (el == 'O') countO++;
                if (el == 'X') countX++;
            }
        }
        return Math.abs(countO - countX) > 1;
    }

    private static char CheckRow(char[] row) {
        int sum = 0;
        for (char el : row) {
            sum += el;
        }
        return CheckSum(sum);
    }

    private static char CheckSum(int sum){
        return sum == 264 ? 'X' : sum == 237 ? 'O' : '_';
    }

    /**
     * Returns 'O' or 'X' in case there is a winner in any row
     * Returns '_' if no one wins after row analysis
     * Returns 'E' in case there is more than one winner among rows
     */
    private static char CheckRows(char[][] field){
        var answer = '_';
        for (char[] row : field) {
            var currentRowWinner = CheckRow(row);
            answer = GetAnswer(currentRowWinner, answer);
            if (answer == 'E') return answer;
        }
        return answer;
    }


    private static char GetAnswer(char currentFigureWinner, char currentAnswer){
        if (currentFigureWinner != '_') {
            if (currentAnswer != '_') {
                return 'E';
            }
            return currentFigureWinner;
        }
        return currentAnswer;
    }

    /**
     * Returns 'O' or 'X' in case there is a winner in any column
     * Returns '_' if no one wins after columns analysis
     * Returns 'E' in case there is more than one winner among columns
     */
    private static char CheckColumns(char[][] field) {
        var answer = '_';
        for (int j = 0; j < field.length; j++) {
            int sum = 0;
            for (int i = 0; i < field.length; i++){
                sum += field[i][j];
            }
            var currentColumnWinner = CheckSum(sum);
            answer = GetAnswer(currentColumnWinner, answer);
            if (answer == 'E') return answer;
        }
        return answer;
    }

    private static void ShowField(char[][] field) {
        System.out.println("---------");
        for (int i = 0; i < field.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < field.length; j++) {
                System.out.print(field[i][j] == '_' ? "  " : field[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    /**
     * Returns 'O' or 'X' in case there is a winner in any diagonal
     * Returns '_' if no one wins after diagonal analysis
     * Returns 'E' in case there is more than one winner among diagonal
     */
    private static char CheckDiagonals(char[][] field) {
        int diagonal1 = 0, diagonal2 = 0;
        for (int i = 0; i < field.length; i++){
            diagonal1 += field[i][i];
            diagonal2 += field[i][field.length - 1 - i];
        }
        var diag1Answer = CheckSum(diagonal1);
        var diag2Answer = CheckSum(diagonal2);
        if (diag1Answer != '_' && diag2Answer != '_') return 'E';
        return diag1Answer != '_' ? diag1Answer : diag2Answer;
    }
}
