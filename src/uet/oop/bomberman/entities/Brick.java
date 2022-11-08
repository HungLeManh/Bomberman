package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity implements Tile {
    private int time = 45;
    private boolean destroyed = false;

    private Board board;
    private Sprite[] desBrick;
    public Brick(int x, int y, Image img, Board board) {
        super(x, y, img);
        this.board = board;
        desBrick = new Sprite[]{Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2};
    }

    @Override
    public void update() {
        if (!destroyed) {
            board.stillObject.add(new Grass(getXUnit(), getYUnit(), Sprite.grass.getFxImage()));
            collide(board.getEntityAt(getXUnit(), getYUnit()));
        } else if (time > 0) {
            destroy();
        } else {
            removed = true;
        }
    }

    public void destroy() {
        switch (time) {
            case 45:
                img = desBrick[0].getFxImage();
                time--;
                break;
            case 30:
                img = desBrick[1].getFxImage();
                time--;
                break;
            case 15:
                img = desBrick[2].getFxImage();
                time--;
                break;
            case 0:
                return;
            default:
                time--;
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Flame) {
            destroy();
            destroyed = true;
        }
        return false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
