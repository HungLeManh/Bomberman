package uet.oop.bomberman.entities.characters.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.characters.Character;
import uet.oop.bomberman.graphics.Sprite;

import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Character {

    public Enemy(int x, int y, Image img, Board board) {
        super( x, y, img, board);
        killTime = 60;
    }

    public void update() {
        animate();
        if (alive) {
            collide(board.getEntityAt(getXUnit(), getYUnit()));
            calculateMove();
            move();
        } else {
            afterKill();
        }
    }

    @Override
    public void calculateMove() {
        /*if (animate % 50 == 0) {
            direction = ThreadLocalRandom.current().nextInt(0, 3);
            speed = ThreadLocalRandom.current().nextInt(4, 28);
        }*/
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Enemy) {
            return true;
        }
        return super.collide(e);
    }

    public void afterKill() {
        if (killTime == 0) {
            removed = true;
        }
        img = spriteDead[0].getFxImage();
        killTime--;
    }
}
