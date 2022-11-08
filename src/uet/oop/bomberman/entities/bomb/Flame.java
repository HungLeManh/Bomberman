package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tile;
import uet.oop.bomberman.entities.characters.Bomber;
import uet.oop.bomberman.entities.characters.Character;
import uet.oop.bomberman.entities.characters.enemies.Enemy;

public class Flame extends Entity {
    private int showTime = 10;

    private Board board;

    public Flame(int x, int y, Image img, Board board) {
        super(x, y, img);
        this.board = board;
    }

    @Override
    public void update() {
        if (showTime > 0) {
            showTime --;
            collide(board.getEntityAt(getXUnit(), getYUnit()));
        } else {
            removed = true;
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Tile) {
            return true;
        }
        if (e instanceof Bomber) {
            ((Bomber)e).kill();
        }
        if (e instanceof Enemy) {
            ((Enemy)e).kill();

        }
        //if (e instanceof Brick) {
        //    ((Brick)e).destroy();
        //}
        return false;
    }

    public int getShowTime() {
        return showTime;
    }

}
