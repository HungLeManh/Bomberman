package uet.oop.bomberman.graphics;

public class Board {
    public static char[][] cell;
    public static final char wall = '#';
    public static final char brick = '*';
    public static final char enemy = '0';

    public static final char bomb = 'b';

    public static final char flame = 'f';
    public static final char grass = 0;

    public Board(int width, int height) {
        cell = new char[height][width];
    }

    /*public void checkCollide() {

    }*/
}
