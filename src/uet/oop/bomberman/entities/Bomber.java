package uet.oop.bomberman.entities;


import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.graphics.Board;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Bomber extends Character {
    private List<Bomb> bombList = new ArrayList<Bomb>();
    private List<Item> itemList = new ArrayList<Item>();
    private KeyEvent direction = null;
    private int speed = 8;
    private int animate = 0;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (!alive) {

        }

        if (direction != null) {
            move();
            direction = null;
        }
        if (Board.cell[y/32][x/32] == '0' || Board.cell[y/32][x/32] == 'f') {
            alive = false;
        }

    }

    public void setDirection(KeyEvent direction) {
        this.direction = direction;
    }


    public void move() {
        if (animate == 99999) {
            animate = 0;
        }
        switch (direction.getCode().toString()) {
            //2*Sprite.player_right_2.get_realWidth() = 24
            case "RIGHT":
                if (y % Sprite.SCALED_SIZE != 0) {
                    y = getYUnit() * Sprite.SCALED_SIZE;
                }
                if (canMove(x + speed + 24, y)) {
                    x += speed;
                }
                img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2,
                        animate++, 3).getFxImage();
                break;
            case "LEFT":
                if (y % Sprite.SCALED_SIZE != 0) {
                    y = getYUnit() * Sprite.SCALED_SIZE;
                }
                if (canMove(x - speed, y)) {
                    x -= speed;
                }
                img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2,
                        animate++, 3).getFxImage();
                break;
            case "UP":
                if (x % Sprite.SCALED_SIZE != 0) {
                    x = getXUnit() * Sprite.SCALED_SIZE;
                }
                if (canMove(x, y - speed)) {
                    y -= speed;
                }
                img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2,
                        animate++, 3).getFxImage();
                break;
            case "DOWN":
                if (x % Sprite.SCALED_SIZE != 0) {
                    x = getXUnit() * Sprite.SCALED_SIZE;
                }
                if (canMove(x, y + speed + 32)) {
                    y += speed;
                }
                img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2,
                        animate++, 3).getFxImage();
                break;
            default:
                System.out.println(direction.getCode().toString());
        }
    }


    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
    }
    public void die(int control) {
        if (control == 0) {
            img = Sprite.player_dead1.getFxImage();
            die(1);
        } else if (control == 1) {
            img = Sprite.player_dead2.getFxImage();
            die(2);
        } else {
            img = Sprite.player_dead3.getFxImage();
        }
    }

    public List<Bomb> getBombList() {
        return bombList;
    }

}
