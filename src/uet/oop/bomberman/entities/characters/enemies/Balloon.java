package uet.oop.bomberman.entities.characters.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.characters.enemies.ai.LowAI;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.graphics.Sprite.movingSprite;

public class Balloon extends Enemy {

    public int k = 0;
    public Balloon(int x, int y, Image img, Board board) {
        super(x, y, img, board);
        spriteMove = new Sprite[]{
                Sprite.balloon_left1, Sprite.balloon_left2, Sprite.balloon_left3,
                Sprite.balloon_right1, Sprite.balloon_right2, Sprite.balloon_right3,
                Sprite.balloon_left1, Sprite.balloon_left2, Sprite.balloon_left3,
                Sprite.balloon_right1, Sprite.balloon_right2, Sprite.balloon_right3};
        spriteDead = new Sprite[]{Sprite.balloon_dead};
        speed = 1;
        LowAI lowAI = new LowAI();
        direction = lowAI.calculateDirection();
        killTime = 20;
    }

    /*@Override
    public void update() {
<<<<<<< HEAD

        /*
        LowAI lowAI = new LowAI();
        direction = lowAI.calculateDirection();
        int d = direction;
        if(canMove(getXUnit(), getYUnit())){
            for(int i=0; i< 10; i++){
                move();
            }
        }
    }*/

    public void calculateMove() {
        /*if (alive) {
            collide(board.getEntityAt(getXUnit(), getYUnit()));
        */
            if(k == 17){
                k = 0;
            }
            int d = direction;

            LowAI lowAI = new LowAI();
            direction = lowAI.calculateDirection();
            while (!check()){
                direction = lowAI.calculateDirection();
            }
            if(direction != d && k < 16){
                direction = d;
            }

            move();

            k++;



    }

    public boolean check(){
        int ss =  Sprite.SCALED_SIZE;
        boolean k =true;
        if(direction == 0 && !canMove(getXUnit(), (y - speed) / ss)){
            k = false;
        }
        if(direction == 1 && !canMove(getXUnit(), (y + ss) / ss)){
            k = false;
        }
        if(direction == 2 && !canMove((x - speed) / ss, getYUnit())){
            k = false;
        }
        if(direction == 3 && !canMove((x + ss) / ss, getYUnit())){
            k = false;
        }
        return k;
    }


}

