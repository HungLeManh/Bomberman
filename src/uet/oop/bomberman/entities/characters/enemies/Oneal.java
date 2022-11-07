package uet.oop.bomberman.entities.characters.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Image img, Board board) {
        super( x, y, img, board);
        spriteMove = new Sprite[]{Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3,
                                    Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3,
                                    Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3,
                                    Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3};
        spriteDead = new Sprite[]{Sprite.oneal_dead};
    }

    @Override
    public void update() {

    }
}
