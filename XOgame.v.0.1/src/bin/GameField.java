package bin;

/**
 * Created on 04.03.2017.
 */

public class GameField {

    public enum Cell {X, O, E}

    public enum Diagonal {LeftToRight, RightToLeft}

    private Cell[][] field = new Cell[3][3];

    public GameField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.field[i][j] = Cell.E;
            }
        }
    }

    public GameField(GameField originalField) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.field[i][j] = originalField.getState(i, j);
            }
        }
    }

    /**
     * @param i     - 0-2
     * @param j     - 0-2
     * @param state X - crosses, O - zeros, E
     */
    public void setState(int i, int j, Cell state) {
        field[i][j] = state;
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = Cell.E;
            }
        }
    }

    public Cell getState(int i, int j) {
        return field[i][j];
    }

    public Cell[] getRow(int i) {
        return field[i];
    }

    public Cell[] getColumn(int j) {
        Cell[] c = new Cell[3];
        for (int i = 0; i < 3; i++) {
            c[i] = field[i][j];
        }
        return c;
    }

    public Cell[] getDiagonal(Diagonal d) {
        Cell[] diag = new Cell[3];
        for (int i = 0; i < 3; i++) {
            diag[i] = d == Diagonal.LeftToRight ? field[i][i] : field[2 - i][i];
        }
        return diag;
    }

    public boolean isFullFilled() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == Cell.E) {
                    return false;
                }
            }
        }
        return true;
    }

}

