package uet.oop.bomberman.entities.characters;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tile;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Sprite;

/**
  Bomber va Enemy
 */

public abstract class Character extends AnimatedEntity {
    protected final int up = 0;
    protected final int down = 1;
    protected final int left = 2;
    protected final int right = 3;
    protected boolean alive = true;
    protected int speed;
    protected int direction;

    protected int killTime;

    protected Board board;

    protected Sprite spriteMove[];

    protected Sprite spriteDead[];
    public Character(int x, int y, Image img, Board board) {
        super(x, y, img);
        this.board = board;
    }

    public abstract void calculateMove();

    public boolean canMove(int xUnit, int yUnit) {
        Entity e = board.getEntityAt(xUnit, yUnit);
        if (e == null) {
            return true;
        } else if (e instanceof Tile) {
            return false;
        } else if (e instanceof Character) {
            return true;
        } else if (e instanceof Bomb) {
            return false;
        }
        return true;
    }

    public void move() {
    }

    public void kill() {
        alive = false;
    }

    public void afterKill() {}

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Character) {
            return false;
        }
        if (e instanceof Flame) {
            this.kill();
            return false;
        }
        if (e == null) {
            return true;
        }
        return e.collide(this);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
