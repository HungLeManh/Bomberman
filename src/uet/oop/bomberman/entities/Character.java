package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Board;
import uet.oop.bomberman.graphics.Sprite;

/**
  Bomber va Enemy
 */

public abstract class Character extends Entity {
    protected boolean alive = true;
    public Character(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean canMove(int _x, int _y) {

        int size = Sprite.SCALED_SIZE;
        switch (Board.cell[_y / size][_x / size]) {
            case Board.wall:
                return false;
            case Board.brick:
                return false;
            case Board.bomb:
                return false;
            default:
        }
        return true;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
