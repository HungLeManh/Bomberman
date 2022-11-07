package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    private int timeToExplode = 90;
    private boolean exploded = false;
    private List<Flame> flameList = new ArrayList<Flame>();
    public Bomb(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (timeToExplode == 0) {
            explode();
        } else {
            timeToExplode --;
        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    public void explode() {

        Flame center = new Flame(getXUnit(), getYUnit(), Sprite.bomb_exploded.getFxImage());
        Flame left = new Flame(getXUnit() - 1, getYUnit(), Sprite.explosion_horizontal_left_last.getFxImage());
        Flame right = new Flame(getXUnit() + 1, getYUnit(), Sprite.explosion_horizontal_right_last.getFxImage());
        Flame top = new Flame(getXUnit(), getYUnit() - 1, Sprite.explosion_vertical_top_last.getFxImage());
        Flame down = new Flame(getXUnit(), getYUnit() + 1, Sprite.explosion_vertical_down_last.getFxImage());

        flameList.add(center);
        flameList.add(left);
        flameList.add(right);
        flameList.add(top);
        flameList.add(down);

        exploded = true;
    }

    public int getTimeToExplode() {
        return timeToExplode;
    }

    public List<Flame> getFlameList() {
        return flameList;
    }

    public boolean isExploded() {
        return exploded;
    }
}
