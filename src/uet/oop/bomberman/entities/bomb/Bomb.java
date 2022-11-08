package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tile;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    private int timeToExplode = 90;
    private boolean exploded = false;
    private List<Flame> flameList = new ArrayList<Flame>();

    // Pham vi cua bom no 4 huong tren, duoi, trai, phai
    private int[] scope;
    private static int radius = 1;

    private List<Flame> top = new ArrayList<>();
    private List<Flame> down = new ArrayList<>();
    private List<Flame> left = new ArrayList<>();
    private List<Flame> right = new ArrayList<>();
    private Board board;

    public Bomb(int x, int y, Image img, Board board) {
        super( x, y, img);
        this.board = board;
        scope = new int[]{radius, radius, radius, radius};
    }

    @Override
    public void update() {
        if (timeToExplode == 0) {
            explode();
        } else {
            timeToExplode--;
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
            if (board.getEntityAt(x, y) instanceof Tile) {
                scope[0] = i;
                top.add(new Flame(x, y, Sprite.transparent().getFxImage(), board));
                break;
            }
        }
        //Duoi
        for (int i = 0; i < radius; i++) {
            x = getXUnit();
            y = getYUnit() + i + 1;
            if (board.getEntityAt(x, y) instanceof Tile) {
                scope[1] = i;
                down.add(new Flame(x, y, Sprite.transparent().getFxImage(), board));
                break;
            }
        }
        // trai
        for (int i = 0; i < radius; i++) {
            x = getXUnit() - i - 1;
            y = getYUnit();
            if (board.getEntityAt(x, y) instanceof Tile) {
                scope[2] = i;
                left.add(new Flame(x, y, Sprite.transparent().getFxImage(), board));
                break;
            }
        }
        //Phai
        for (int i = 0; i < radius; i++) {
            x = getXUnit() + i + 1;
            y = getYUnit();
            if (board.getEntityAt(x, y) instanceof Tile) {
                scope[3] = i;
                right.add(new Flame(x, y, Sprite.transparent().getFxImage(), board));
                break;
            }
        }
    }

    public void explode() {
        calculatedScope();
        Flame center = new Flame(getXUnit(), getYUnit(), Sprite.bomb_exploded.getFxImage(), board);

        if (scope[0] > 0) {
            top.add(new Flame(getXUnit(), getYUnit() - scope[0], Sprite.explosion_vertical_top_last.getFxImage(), board));
            if (scope[0] > 1) {
                for (int i = 1; i < scope[0]; i++) {
                    top.add(new Flame(getXUnit(), getYUnit() - i, Sprite.explosion_vertical.getFxImage(), board));
                }
            }
        }
        if (scope[1] > 0) {
            down.add(new Flame(getXUnit(), getYUnit() + scope[1], Sprite.explosion_vertical_down_last.getFxImage(), board));
            if (scope[1] > 1) {
                for (int i = 1; i < scope[1]; i++) {
                    down.add(new Flame(getXUnit(), getYUnit() + i, Sprite.explosion_vertical.getFxImage(), board));
                }
            }
        }
        if (scope[2] > 0) {
            left.add(new Flame(getXUnit() - scope[2], getYUnit(), Sprite.explosion_horizontal_left_last.getFxImage(), board));
            if (scope[2] > 1) {
                for (int i = 1; i < scope[2]; i++) {
                    left.add(new Flame(getXUnit() - i, getYUnit(), Sprite.explosion_horizontal.getFxImage(), board));
                }
            }
        }
        if (scope[3] > 0) {
            right.add(new Flame(getXUnit() + scope[3], getYUnit(), Sprite.explosion_horizontal_right_last.getFxImage(), board));
            if (scope[3] > 1) {
                for (int i = 1; i < scope[3]; i++) {
                    right.add(new Flame(getXUnit() + i, getYUnit(), Sprite.explosion_horizontal.getFxImage(), board));
                }
            }
        }

        flameList.add(center);
        flameList.addAll(top);
        flameList.addAll(down);
        flameList.addAll(left);
        flameList.addAll(right);

        exploded = true;
        removed = true;
        Sound.play("BOM_11_M");
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

    public static void addFlameRadius() {
        radius++;
    }
}
