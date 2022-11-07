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
    }

    @Override
    public void calculateMove() {
        if (animate % 50 == 0) {
            direction = ThreadLocalRandom.current().nextInt(0, 3);
            speed = ThreadLocalRandom.current().nextInt(4, 28);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Enemy) {
            return true;
        }
        return super.collide(e);
    }
}
