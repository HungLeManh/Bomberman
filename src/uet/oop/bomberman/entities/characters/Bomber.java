package uet.oop.bomberman.entities.characters;


import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.characters.enemies.Enemy;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Bomber extends Character {
    private List<Bomb> bombList = new ArrayList<Bomb>();
    private List<Item> itemList = new ArrayList<Item>();
    private KeyEvent inputDir = null;
    private int animate = 0;

    private int killTime = 90;
    private boolean bombPass = false;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        spriteMove = new Sprite[]{Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2,
                                    Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2,
                                    Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2,
                                    Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2};
        speed = 8;
    }

    @Override
    public void update() {
        if (!alive) {
        }

        if (inputDir != null) {
            calculateMove();
            move(24, 32, spriteMove);
            inputDir = null;
        }
        if (Board.cell[getYUnit()][getXUnit()] == '0' || Board.cell[getYUnit()][getXUnit()] == 'f') {
            kill();
        }

    }

    public void calculateMove() {
        if (animate == 99999) {
            animate = 0;
        }
        switch (inputDir.getCode().toString()) {
            case "RIGHT":
                direction = right;
                break;
            case "LEFT":
                direction = left;
                break;
            case "UP":
                direction = up;
                break;
            case "DOWN":
                direction = down;
                break;
            default:
        }
    }

    /*public boolean canMove(int xUnit, int yUnit) {
        if (bombPass) {
            bombPass = false;
            return true;
        }
        return super.canMove(xUnit, yUnit);
    }*/

    public void move() {
        if (animate == 99999) {
            animate = 0;
        }
        int ss = Sprite.SCALED_SIZE;
        switch (direction) {
            case up:
                if (x % ss != 0) {
                    x = getXUnit() * ss;
                }
                if (canMove(getXUnit(), (y - speed) / ss)) {
                    y -= speed;
                }
                img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2,
                        animate++, 3).getFxImage();
                break;
            case down:
                if (x % ss != 0) {
                    x = getXUnit() * ss;
                }
                if (canMove(getXUnit(), (y + 32) / ss)) {
                    y += speed;
                }
                img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2,
                        animate++, 3).getFxImage();
                break;
            case left:
                if (y % ss != 0) {
                    y = getYUnit() * ss;
                }
                if (canMove((x - speed) / ss, getYUnit())) {
                    x -= speed;
                }
                img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2,
                        animate++, 3).getFxImage();
                break;
            case right:
                if (y % ss != 0) {
                    y = getYUnit() * ss;
                }
                if (canMove((x + 24 + speed) / ss, getYUnit())) {
                    x += speed;
                }
                img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2,
                        animate++, 3).getFxImage();
                break;
            default:
        }
    }


    public void placeBomb() {
        Bomb bomb = new Bomb(getXUnit(), getYUnit(), Sprite.bomb.getFxImage());
        bombList.add(bomb);
        bombPass = true;
        Board.cell[getYUnit()][getXUnit()] = 'b';
    }

    public List<Bomb> getBombList() {
        return bombList;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Enemy) {
            kill();
            return false;
        }
        return super.collide(e);
    }

    @Override
    public void kill() {
        switch (killTime) {
            case 90:
                img = Sprite.brick_exploded.getFxImage();
                killTime--;
                break;
            case 60:
                img = Sprite.brick_exploded1.getFxImage();
                killTime--;
                break;
            case 30:
                img = Sprite.brick_exploded2.getFxImage();
                killTime--;
                break;
            case 0:
                return;
            default:
                killTime--;
        }
    }

    public void setInputDir(KeyEvent inputDir) {
        this.inputDir = inputDir;
    }
}
