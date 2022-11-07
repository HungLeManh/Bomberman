package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Brick extends Entity implements Tile {
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    public void destroy() {

    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Flame) {
            destroy();
        }
        return false;
    }
}
