package laboflieven.boardmoveinstructions;

public class Board
{
    public Board(int width, int height) {
        values = new int[width][height];
    }
    private int[][] values;

    public int[][] getValues() {
        return values;
    }

    public void setValues(int[][] values) {
        this.values = values;
    }
}
