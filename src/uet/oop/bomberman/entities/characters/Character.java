package uet.oop.bomberman.entities.characters;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Flame;
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

    public void move() {

    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {

    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Flame) {
            this.kill();
            return false;
        }
        return e.collide(this);
    }
}
