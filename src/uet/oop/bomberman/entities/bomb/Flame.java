package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tile;
import uet.oop.bomberman.entities.characters.Bomber;
import uet.oop.bomberman.entities.characters.Character;
import uet.oop.bomberman.entities.characters.enemies.Enemy;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends AnimatedEntity {
    private int showTime = 10;

    private Sprite[] exp = {};

    private Board board;

    public Flame(int x, int y, Board board, Sprite ... sprites) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.board = board;
        this.exp = sprites;
        this.img = sprites[0].getFxImage();
    }

    @Override
    public void update() {
        animate();
        if (exp.length == 3) {
           img = Sprite.movingSprite(exp[0], exp[1], exp[2], animate, 60).getFxImage();
        }
        if (showTime > 0) {
            showTime --;
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
