package tictactoe.Game.Field;

public class Field {
    private char[][] field;
    public int Size = 3;
    public int TurnsCount = 0;

    public Field() {
        field = new char[Size][Size];
    }

    public Status CheckStatus() {
        if (IsImpossible()) return Status.Impossible;
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
                if (IsFinished()) return Status.Draw;
                else return Status.NotFinished;
            }
            else return winner == 'X' ? Status.WinX : Status.WinO;
        }
    }

    public boolean IsOccupiedUser(int i, int j){
        var coordinateI = Size - j;
        var coordinateJ = i - 1;
        return IsOccupied(coordinateI, coordinateJ);
    }

    public boolean IsOccupied(int i, int j) {
        return field[i][j] != '_';
    }

    public void PutCharAtUser(char ch, int i, int j) {
        var coordinateI = Size - j;
        var coordinateJ = i - 1;
        PutCharAtAi(ch, coordinateI, coordinateJ);
    }

    public void PutCharAtAi(char ch, int i, int j) {
        field[i][j] = ch;
        TurnsCount++;
    }

    public void PutCharAtAi(int i, int j) {
        var currentChar = TurnsCount % 2 == 0 ? 'X' : 'O';
        PutCharAtAi(currentChar, i, j);
    }

    public char GetCharAt(int i, int j) {
        return field[i][j];
    }

    public void Init(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                field[i][j] = '_';
            }
        }
    }
    private boolean IsFinished(){
        for (char[] row : field) {
            for (char el : row) {
                if (el == '_') return false;
            }
        }
        return true;
    }
    private boolean IsImpossible(){
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

    private char CheckRow(char[] row) {
        int sum = 0;
        for (char el : row) {
            sum += el;
        }
        return CheckSumInstantWin(sum);
    }

    private char CheckSumInstantWin(int sum){
        return sum == 264 ? 'X' : sum == 237 ? 'O' : '_';
    }


    /**
     * Returns 'O' or 'X' in case there is a winner in any row
     * Returns '_' if no one wins after row analysis
     * Returns 'E' in case there is more than one winner among rows
     */
    private char CheckRows(char[][] field){
        var answer = '_';
        for (char[] row : field) {
            var currentRowWinner = CheckRow(row);
            answer = GetAnswer(currentRowWinner, answer);
            if (answer == 'E') return answer;
        }
        return answer;
    }


    private  char GetAnswer(char currentFigureWinner, char currentAnswer){
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
    private char CheckColumns(char[][] field) {
        var answer = '_';
        for (int j = 0; j < field.length; j++) {
            int sum = 0;
            for (char[] chars : field) {
                sum += chars[j];
            }
            var currentColumnWinner = CheckSumInstantWin(sum);
            answer = GetAnswer(currentColumnWinner, answer);
            if (answer == 'E') return answer;
        }
        return answer;
    }

    public void Show() {
        System.out.println("---------");
        for (char[] chars : field) {
            System.out.print("| ");
            for (int j = 0; j < field.length; j++) {
                System.out.print(chars[j] == '_' ? "  " : chars[j] + " ");
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
    private char CheckDiagonals(char[][] field) {
        int diagonal1 = 0, diagonal2 = 0;
        for (int i = 0; i < field.length; i++){
            diagonal1 += field[i][i];
            diagonal2 += field[i][field.length - 1 - i];
        }
        var diag1Answer = CheckSumInstantWin(diagonal1);
        var diag2Answer = CheckSumInstantWin(diagonal2);
        if (diag1Answer != '_' && diag2Answer != '_') return 'E';
        return diag1Answer != '_' ? diag1Answer : diag2Answer;
    }

    public Field Copy() {
        var field = new Field();
        field.field = new char[Size][Size];
        for (int i = 0; i < Size; i ++) {
            System.arraycopy(this.field[i], 0, field.field[i], 0, Size);
        }
        field.TurnsCount = this.TurnsCount;
        return field;
    }
}

