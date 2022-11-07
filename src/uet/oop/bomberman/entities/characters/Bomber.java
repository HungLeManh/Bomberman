package uet.oop.bomberman.entities.characters;


import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.bomb.Bomb;
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

    private int killTime = 75;
    private boolean bombPass = false;

    public Bomber(int x, int y, Image img, Board board) {
        super( x, y, img, board);
        spriteMove = new Sprite[]{Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2,
                Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2,
                Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2,
                Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2};
        spriteDead = new Sprite[]{Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3};
        speed = 8;
    }

    @Override
    public void update() {
        if (alive) {
            if (inputDir != null) {
                calculateMove();
                move(24, 32);
                inputDir = null;
            }
            collide(board.getEntityAt(getXUnit(), getYUnit()));
        } else if (killTime > 0) {
            afterKill();
        } else {
            removed = true;
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

    public boolean canMove(int xUnit, int yUnit) {
        if (bombPass) {
            bombPass = false;
            return true;
        }
        return super.canMove(xUnit, yUnit);
    }


    public void placeBomb() {
        Bomb bomb = new Bomb(getXUnit(), getYUnit(), Sprite.bomb.getFxImage(), board);
        bombList.add(bomb);
        bombPass = true;
        //Board.cell[getYUnit()][getXUnit()] = 'b';
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
        if (e instanceof Bomber) {
            return true;
        }
        return super.collide(e);
    }

    @Override
    public void kill() {
        alive = false;
    }

    public void afterKill() {
        switch (killTime) {
            case 75:
                img = spriteDead[0].getFxImage();
                killTime--;
                break;
            case 50:
                img = spriteDead[1].getFxImage();
                killTime--;
                break;
            case 25:
                img = spriteDead[2].getFxImage();
                killTime--;
                break;
            case 0:
                break;
            default:
                killTime--;
        }
    }

    public void setInputDir(KeyEvent inputDir) {
        this.inputDir = inputDir;
    }
}
