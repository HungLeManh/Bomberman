package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    private int timeToExplode = 90;
    private boolean exploded = false;
    private List<Flame> flameList = new ArrayList<Flame>();

    // Pham vi cua bom no 4 huong tren, duoi, trai, phai
    private int[] scope = {1, 1, 1, 1};
    private int radius = 1;

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

    public void calculatedScope() {
        int x, y;
        //Tren
        for (int i = 0; i < radius; i++) {
            x = getXUnit();
            y = getYUnit() - i - 1;
            if (Board.cell[y][x] == Board.brick || Board.cell[y][x] == Board.wall) {
                scope[0] = i;
                break;
            }
        }
        //Duoi
        for (int i = 0; i < radius; i++) {
            x = getXUnit();
            y = getYUnit() + i + 1;
            if (Board.cell[y][x] == Board.brick || Board.cell[y][x] == Board.wall) {
                scope[1] = i;
                break;
            }
        }
        // trai
        for (int i = 0; i < radius; i++) {
            x = getXUnit() - i - 1;
            y = getYUnit();
            if (Board.cell[y][x] == Board.brick || Board.cell[y][x] == Board.wall) {
                scope[2] = i;
                break;
            }
        }
        //Phai
        for (int i = 0; i < radius; i++) {
            x = getXUnit() + i + 1;
            y = getYUnit();
            if (Board.cell[y][x] == Board.brick || Board.cell[y][x] == Board.wall) {
                scope[3] = i;
                break;
            }
        }
    }

    public void explode() {
        calculatedScope();
        Flame center = new Flame(getXUnit(), getYUnit(), Sprite.bomb_exploded.getFxImage());
        List<Flame> top = new ArrayList<>();
        List<Flame> down = new ArrayList<>();
        List<Flame> left = new ArrayList<>();
        List<Flame> right = new ArrayList<>();

        if (scope[0] > 0) {
            top.add(new Flame(getXUnit(), getYUnit() - scope[0], Sprite.explosion_vertical_top_last.getFxImage()));
            if (scope[0] > 1) {
                for (int i = 1; i < scope[0]; i++) {
                    top.add(new Flame(getXUnit(), getYUnit() - i, Sprite.explosion_vertical.getFxImage()));
                }
            }
        }
        if (scope[1] > 0) {
            down.add(new Flame(getXUnit(), getYUnit() + scope[1], Sprite.explosion_vertical_down_last.getFxImage()));
            if (scope[1] > 1) {
                for (int i = 1; i < scope[1]; i++) {
                    down.add(new Flame(getXUnit(), getYUnit() + i, Sprite.explosion_vertical.getFxImage()));
                }
            }
        }
        if (scope[2] > 0) {
            left.add(new Flame(getXUnit() - scope[2], getYUnit(), Sprite.explosion_horizontal_left_last.getFxImage()));
            if (scope[2] > 1) {
                for (int i = 1; i < scope[2]; i++) {
                    left.add(new Flame(getXUnit() - i, getYUnit(), Sprite.explosion_horizontal.getFxImage()));
                }
            }
        }
        if (scope[3] > 0) {
            right.add(new Flame(getXUnit() + scope[3], getYUnit(), Sprite.explosion_horizontal_right_last.getFxImage()));
            if (scope[3] > 1) {
                for (int i = 1; i < scope[3]; i++) {
                    right.add(new Flame(getXUnit() + i, getYUnit(), Sprite.explosion_horizontal.getFxImage()));
                }
            }
        }

        flameList.add(center);
        flameList.addAll(top);
        flameList.addAll(down);
        flameList.addAll(left);
        flameList.addAll(right);

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
