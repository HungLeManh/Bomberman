package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    public Bomb(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {

    }

    public void explode() {
        Flame center = new Flame(x, y, Sprite.bomb_exploded.getFxImage());
        Flame left = new Flame(x, y, Sprite.explosion_horizontal_left_last.getFxImage());
        Flame right = new Flame(x, y, Sprite.explosion_horizontal_right_last.getFxImage());
    }
}
