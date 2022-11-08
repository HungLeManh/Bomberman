package uet.oop.bomberman.entities.characters;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tile;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Sprite;

/**
  Bomber va Enemy
 */

public abstract class Character extends Entity {
    protected final int up = 0;
    protected final int down = 1;
    protected final int left = 2;
    protected final int right = 3;
    protected boolean alive = true;
    protected int speed;
    protected int animate = 0;
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
        if (animate == 99999) {
            animate = 0;
        }
        int ss = Sprite.SCALED_SIZE;
        switch (direction) {
            case up:
                if (x % ss != 0) {
                    x = getXUnit() * ss;
                }
                if (canMove(getXUnit(), (y - speed) / ss)) {
                    y -= speed;
                }
                img = Sprite.movingSprite(spriteMove[0], spriteMove[1], spriteMove[2],
                        animate++, 60).getFxImage();
                break;
            case down:
                if (x % ss != 0) {
                    x = getXUnit() * ss;
                }
                if (canMove(getXUnit(), (y + ss) / ss)) {
                    y += speed;
                }
                img = Sprite.movingSprite(spriteMove[3], spriteMove[4], spriteMove[5],
                        animate++, 60).getFxImage();
                break;
            case left:
                if (y % ss != 0) {
                    y = getYUnit() * ss;
                }
                if (canMove((x - speed) / ss, getYUnit())) {
                    x -= speed;
                }
                img = Sprite.movingSprite(spriteMove[6], spriteMove[7], spriteMove[8],
                        animate++, 60).getFxImage();
                break;
            case right:
                if (y % ss != 0) {
                    y = getYUnit() * ss;
                }
                if (canMove((x + ss) / ss, getYUnit())) {
                    x += speed;
                }
                img = Sprite.movingSprite(spriteMove[9], spriteMove[10], spriteMove[11],
                        animate++, 60).getFxImage();
                break;
            default:
        }
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
