package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Flame extends Entity {
    private int showTime = 30;

    public Flame(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (showTime > 0) {
            showTime --;
        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    public int getShowTime() {
        return showTime;
    }

}
