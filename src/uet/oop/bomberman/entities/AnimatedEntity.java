package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.awt.*;

public abstract class AnimatedEntity extends Entity {
    protected int animate = 0;
    protected int MAX_ANIMATE = 99999;

    public AnimatedEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    public AnimatedEntity() {
    }

    public void animate() {
        if (animate == MAX_ANIMATE) {
            animate = 0;
        } else {
            animate++;
        }
    }
}
