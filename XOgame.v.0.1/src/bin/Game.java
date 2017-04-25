package bin;

import java.util.Random;

/**
 * Created on 04.03.2017.
 */

public class Game {

    public enum PlayerSide {X, O, DRAW, NO_ONE_YET}

    private static PlayerSide playerSide = null;
    private static PlayerSide aiSide = null;
    private static GameField gameField = new GameField();
    private static int[] playerTurn = new int[2];
    private static int[] aiTurn = new int[2];
    private static int gameTurn = 0;

    public static void reset() {
        gameField = new GameField();
        playerSide = null;
        aiSide = null;
        gameTurn = 0;
    }

    public static int getGameTurn() {
        return gameTurn;
    }

    public static void setFirstGameTurn() {
        if (gameTurn == 0) {
            gameTurn = 1;
        }
    }

    public static void setPlayerSide(PlayerSide playerSide) {
        Game.playerSide = playerSide;
        setAiSide();
    }

    public static int[] getAiTurn() {
        return aiTurn;
    }

    private static void setAiTurn(int i, int j) {
        aiTurn[0] = i;
        aiTurn[1] = j;
    }

    private static void setAiTurn(int[] ij) {
        aiTurn[0] = ij[0];
        aiTurn[1] = ij[1];
    }

    public static PlayerSide getPlayerSide() {
        return playerSide;
    }

    public static PlayerSide getAiSide() {
        return aiSide;
    }

    public static boolean playerTurn(int i, int j) {
        playerTurn[0] = i;
        playerTurn[1] = j;
        GameField.Cell cell = null;
        switch (getPlayerSide()) {
            case X:
                cell = GameField.Cell.X;
                break;
            case O:
                cell = GameField.Cell.O;
                break;
        }
        gameTurn++;
        return setCell(gameField, i, j, cell);
    }

    public static boolean aiTurn() {
        if (gameTurn == 1) {
            if (playerSide == PlayerSide.X) {
                cornerTurn(playerTurn[0], playerTurn[1]);
                return true;
            } else {
                centerTurn(aiSide);
                return true;
            }
        }
        if (gameField.isFullFilled()) {
            return false;
        }
        int[] pMove = checkPossibleTurns(playerSide);
        int[] aiMove = checkPossibleTurns(aiSide);
        if (pMove != null) {
            gameField.setState(pMove[0], pMove[1], playerSideToCell(aiSide));
            setAiTurn(pMove);
            return true;
        }
        if (aiMove != null) {
            gameField.setState(aiMove[0], aiMove[1], playerSideToCell(aiSide));
            setAiTurn(aiMove);
            return true;
        }
        // true - если сделан ход в случайную клетку
        // false - если ходить некуда
        return randomTurn(aiSide);
    }

    public static PlayerSide checkForWinner() {
        GameField.Cell[] xWins = {GameField.Cell.X, GameField.Cell.X, GameField.Cell.X};
        GameField.Cell[] oWins = {GameField.Cell.O, GameField.Cell.O, GameField.Cell.O};
        for (int i = 0; i < 3; i++) {
            if (compare(gameField.getColumn(i), xWins)) {
                return PlayerSide.X;
            }
            if (compare(gameField.getRow(i), xWins)) {
                return PlayerSide.X;
            }
            if (compare(gameField.getColumn(i), oWins)) {
                return PlayerSide.O;
            }
            if (compare(gameField.getRow(i), oWins)) {
                return PlayerSide.O;
            }
        }
        if (compare(gameField.getDiagonal(GameField.Diagonal.LeftToRight), xWins)) {
            return PlayerSide.X;
        }
        if (compare(gameField.getDiagonal(GameField.Diagonal.LeftToRight), oWins)) {
            return PlayerSide.O;
        }
        if (compare(gameField.getDiagonal(GameField.Diagonal.RightToLeft), xWins)) {
            return PlayerSide.X;
        }
        if (compare(gameField.getDiagonal(GameField.Diagonal.RightToLeft), oWins)) {
            return PlayerSide.O;
        }
        if (gameField.isFullFilled()) {
            return PlayerSide.DRAW;
        }
        return PlayerSide.NO_ONE_YET;
    }

    public static PlayerSide checkForPossibleWinner(GameField field) {
        GameField.Cell[] xWins = {GameField.Cell.X, GameField.Cell.X, GameField.Cell.X};
        GameField.Cell[] oWins = {GameField.Cell.O, GameField.Cell.O, GameField.Cell.O};
        for (int i = 0; i < 3; i++) {
            if (compare(field.getColumn(i), xWins)) {
                return PlayerSide.X;
            }
            if (compare(field.getRow(i), xWins)) {
                return PlayerSide.X;
            }
            if (compare(field.getColumn(i), oWins)) {
                return PlayerSide.O;
            }
            if (compare(field.getRow(i), oWins)) {
                return PlayerSide.O;
            }
        }
        if (compare(field.getDiagonal(GameField.Diagonal.LeftToRight), xWins)) {
            return PlayerSide.X;
        }
        if (compare(field.getDiagonal(GameField.Diagonal.LeftToRight), oWins)) {
            return PlayerSide.O;
        }
        if (compare(field.getDiagonal(GameField.Diagonal.RightToLeft), xWins)) {
            return PlayerSide.X;
        }
        if (compare(field.getDiagonal(GameField.Diagonal.RightToLeft), oWins)) {
            return PlayerSide.O;
        }
        if (field.isFullFilled()) {
            return PlayerSide.DRAW;
        }
        return PlayerSide.NO_ONE_YET;
    }

    private static void setAiSide() {
        if (playerSide != null) {
            aiSide = playerSide == PlayerSide.X ? PlayerSide.O : PlayerSide.X;
        }
    }

    private static boolean compare(GameField.Cell[] combinationForCheck, GameField.Cell[] winingCombination) {
        for (int i = 0; i < 3; i++) {
            if (combinationForCheck[i] != winingCombination[i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean setCell(GameField field, int i, int j, GameField.Cell sellState) {
        if (field.getState(i, j).equals(GameField.Cell.E)) {
            field.setState(i, j, sellState);
            return true;
        } else {
            return false;
        }
    }

    //выяснить, есть ли потенциально выигрышные ходы, null - если таких нет
    private static int[] checkPossibleTurns(PlayerSide side) {
        int[] putHere = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (check(gameField, i, j, side)) {
                    putHere[0] = i;
                    putHere[1] = j;
                    return putHere;
                }
            }
        }
        return null;
    }

    // возможна ли победа, если походить в клетку i , j
    private static boolean check(GameField gameField, int i, int j, PlayerSide newState) {
        GameField.Cell newCellState = playerSideToCell(newState);
        GameField tempField = new GameField(gameField);
        setCell(tempField, i, j, newCellState);
        return newState == checkForPossibleWinner(tempField);
    }

    private static GameField.Cell playerSideToCell(PlayerSide playerSide) {
        return playerSide == PlayerSide.O ? GameField.Cell.O : GameField.Cell.X;
    }

    private static boolean randomTurn(PlayerSide aiSide) {
        Random rnd = new Random();
        int rndI;
        int rndJ;
        boolean foundEmpty;
        do {
            rndI = rnd.nextInt(3);
            rndJ = rnd.nextInt(3);
            foundEmpty = setCell(gameField, rndI, rndJ, playerSideToCell(aiSide));
        }
        while (!foundEmpty);
        setAiTurn(rndI, rndJ);
        return foundEmpty;
    }

    private static boolean cornerTurn(int i, int j) {
        switch (i) {
            case 0:
                if (j == 0) {
                    setAiTurn(2, 2);
                } else {
                    setAiTurn(2, 0);
                }
                return true;
            case 2:
                if (j == 0) {
                    setAiTurn(0, 2);
                } else {
                    setAiTurn(0, 0);
                }
                return true;
        }
        return false;
    }

    private static boolean centerTurn(PlayerSide aiSide) {
        return setCell(gameField, 1, 1, playerSideToCell(aiSide));
    }

    public static void parseField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("[" + Game.gameField.getState(i, j) + "]");
            }
            System.out.println();
        }
    }
}
