package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tile;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends AnimatedEntity {
    private int timeToExplode = 50;

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
        animate();
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60).getFxImage();
        collide(board.getOtherEntityAt(this));
        if (timeToExplode == 0) {
            explode();
        } else {
            timeToExplode--;
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Flame) {
            timeToExplode = 0;
            return false;
        }
        return true;
    }

    public void calculatedScope() {
        int x, y;
        //Tren
        for (int i = 0; i < radius; i++) {
            x = getXUnit();
            y = getYUnit() - i - 1;
            if (board.getEntityAt(x, y) instanceof Tile) {
                scope[0] = i;
                top.add(new Flame(x, y, board, Sprite.transparent()));
                break;
            }
        }
        //Duoi
        for (int i = 0; i < radius; i++) {
            x = getXUnit();
            y = getYUnit() + i + 1;
            if (board.getEntityAt(x, y) instanceof Tile) {
                scope[1] = i;
                down.add(new Flame(x, y, board, Sprite.transparent()));
                break;
            }
        }
        // trai
        for (int i = 0; i < radius; i++) {
            x = getXUnit() - i - 1;
            y = getYUnit();
            if (board.getEntityAt(x, y) instanceof Tile) {
                scope[2] = i;
                left.add(new Flame(x, y, board, Sprite.transparent()));
                break;
            }
        }
        //Phai
        for (int i = 0; i < radius; i++) {
            x = getXUnit() + i + 1;
            y = getYUnit();
            if (board.getEntityAt(x, y) instanceof Tile) {
                scope[3] = i;
                right.add(new Flame(x, y, board, Sprite.transparent()));
                break;
            }
        }
    }

    public void explode() {
        calculatedScope();

        Sprite[] exp_center = {Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2};

        Sprite[] exp_vertical = {Sprite.explosion_vertical, Sprite.explosion_vertical1,Sprite.explosion_vertical2};
        Sprite[] exp_horizontal = {Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2};

        Sprite[] exp_vertical_top_last = {Sprite.explosion_vertical_top_last,
                            Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2};
        Sprite[] exp_vertical_down_last = {Sprite.explosion_vertical_down_last,
                            Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2};

        Sprite[] exp_horizontal_left_last = {Sprite.explosion_horizontal_left_last,
                            Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2};
        Sprite[] exp_horizontal_right_last = {Sprite.explosion_horizontal_right_last,
                            Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2};

        Flame center = new Flame(getXUnit(), getYUnit(),  board, exp_center);
        if (scope[0] > 0) {
            top.add(new Flame(getXUnit(), getYUnit() - scope[0], board, exp_vertical_top_last));
            if (scope[0] > 1) {
                for (int i = 1; i < scope[0]; i++) {
                    top.add(new Flame(getXUnit(), getYUnit() - i, board, exp_vertical));
                }
            }
        }
        if (scope[1] > 0) {
            down.add(new Flame(getXUnit(), getYUnit() + scope[1], board, exp_vertical_down_last));
            if (scope[1] > 1) {
                for (int i = 1; i < scope[1]; i++) {
                    down.add(new Flame(getXUnit(), getYUnit() + i, board, exp_vertical));
                }
            }
        }
        if (scope[2] > 0) {
            left.add(new Flame(getXUnit() - scope[2], getYUnit(), board, exp_horizontal_left_last));
            if (scope[2] > 1) {
                for (int i = 1; i < scope[2]; i++) {
                    left.add(new Flame(getXUnit() - i, getYUnit(), board, exp_horizontal));
                }
            }
        }
        if (scope[3] > 0) {
            right.add(new Flame(getXUnit() + scope[3], getYUnit(), board, exp_horizontal_right_last));
            if (scope[3] > 1) {
                for (int i = 1; i < scope[3]; i++) {
                    right.add(new Flame(getXUnit() + i, getYUnit(), board, exp_horizontal));
                }
            }
        }

        flameList.add(center);
        flameList.addAll(top);
        flameList.addAll(down);
        flameList.addAll(left);
        flameList.addAll(right);
        board.entities.addAll(flameList);

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
