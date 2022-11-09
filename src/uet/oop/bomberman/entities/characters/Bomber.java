package uet.oop.bomberman.entities.characters;


import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.Portal;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.characters.enemies.Enemy;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.items.FlameItem;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.items.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Bomber extends Character {
    private List<Bomb> bombList = new LinkedList<Bomb>();
    private List<Item> itemList = new ArrayList<Item>();
    private KeyEvent inputDir = null;
    private boolean portalPass = false;

    public Bomber(int x, int y, Image img, Board board) {
        super( x, y, img, board);
        spriteMove = new Sprite[]{Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2,
                Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2,
                Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2,
                Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2};
        spriteDead = new Sprite[]{Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3};
        speed = 16;
        killTime = 60;
    }

    @Override
    public void update() {
        animate();
        if (alive) {
            collide(board.getOtherEntityAt(this));
            if (inputDir != null) {
                calculateMove();
                move();
                inputDir = null;
            }
            clearBomb();
        } else if (killTime > 0) {
            afterKill();
        } else {
            removed = true;
        }
    }

    public void calculateMove() {
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
            case "X":
                System.out.println(board.getOtherEntityAt(this));
                break;
            default:
        }
    }

    public boolean canMove(int xUnit, int yUnit) {
        return super.canMove(xUnit, yUnit);
    }

    public void move() {
        int ss = Sprite.SCALED_SIZE;
        int time = 60;
        switch (direction) {
            case up:
                if (x % ss != 0) {
                    x = getXUnit() * ss;
                }
                if (canMove(getXUnit(), (y - speed) / ss)) {
                    y -= speed;
                }
                img = Sprite.movingSprite(spriteMove[0], spriteMove[1], spriteMove[2],
                        animate, time).getFxImage();
                break;
            case down:
                if (x % ss != 0) {
                    x = getXUnit() * ss;
                }
                if (canMove(getXUnit(), (y + ss) / ss)) {
                    y += speed;
                }
                img = Sprite.movingSprite(spriteMove[3], spriteMove[4], spriteMove[5],
                        animate, time).getFxImage();
                break;
            case left:
                if (y % ss != 0) {
                    y = getYUnit() * ss;
                }
                if (canMove((x - speed) / ss, getYUnit())) {
                    x -= speed;
                }
                img = Sprite.movingSprite(spriteMove[6], spriteMove[7], spriteMove[8],
                        animate, time).getFxImage();
                break;
            case right:
                if (y % ss != 0) {
                    y = getYUnit() * ss;
                }
                if (canMove((x + ss) / ss, getYUnit())) {
                    x += speed;
                }
                img = Sprite.movingSprite(spriteMove[9], spriteMove[10], spriteMove[11],
                        animate, time).getFxImage();
                break;
            default:
        }
    }

    public void placeBomb() {
        Bomb bomb = new Bomb(getXUnit(), getYUnit(), Sprite.bomb.getFxImage(), board);
        bombList.add(bomb);
        Sound.play("BOM_SET");
    }

    public void clearBomb() {
        if (!bombList.isEmpty()) {
            Bomb b = bombList.get(0);
            if (b.isRemoved()) {
                bombList.remove(0);
                board.entities.remove(b);
                BombermanGame.addBombRate();
            }
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            return true;
        }
        if (e instanceof Enemy) {
            kill();
            Sound.play("AA126_11");
            return false;
        }
        if (e instanceof Portal) {
            if (portalPass) {
                board.nextLevel();
                Sound.play("CRYST_UP");
                return false;
            }
        }

        if (e instanceof SpeedItem) {
            e.setRemoved(true);
            Sound.play("Item");
            speedUp();
            itemList.add((Item) e);
        } else if (e instanceof BombItem) {
            e.setRemoved(true);
            Sound.play("Item");
            BombermanGame.addBombRate();
            itemList.add((Item) e);
        } else if (e instanceof FlameItem) {
            e.setRemoved(true);
            Sound.play("Item");
            Bomb.addFlameRadius();
            itemList.add((Item) e);
        }
        return super.collide(e);
    }

    @Override
    public void kill() {
        alive = false;
        Sound.play("endgame3");
    }

    public void afterKill() {
        switch (killTime) {
            case 30:
                img = spriteDead[0].getFxImage();
                killTime--;
                break;
            case 20:
                img = spriteDead[1].getFxImage();
                killTime--;
                break;
            case 10:
                img = spriteDead[2].getFxImage();
                killTime--;
                break;
            case 0:
                break;
            default:
                killTime--;
        }
    }

    public void speedUp() {
        speed += 16;
    }

    public void setInputDir(KeyEvent inputDir) {
        this.inputDir = inputDir;
    }

    public List<Bomb> getBombList() {
        return bombList;
    }

    public void setPortalPass(boolean portalPass) {
        this.portalPass = portalPass;
    }
}
