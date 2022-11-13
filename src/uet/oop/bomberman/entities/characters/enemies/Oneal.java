package uet.oop.bomberman.entities.characters.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.characters.enemies.ai.LowAI;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {

    public int k = 0;
    public Oneal(int x, int y, Image img, Board board) {
        super( x, y, img, board);
        spriteMove = new Sprite[]{Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3,
                                    Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3,
                                    Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3,
                                    Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3};
        spriteDead = new Sprite[]{Sprite.oneal_dead};
        speed = 1;
        LowAI lowAI = new LowAI();
        direction = lowAI.calculateDirection();
        killTime = 20;
    }

    public void update() {
        if (alive) {
            collide(board.getEntityAt(getXUnit(), getYUnit()));

            if(k == 65){
                k = 0;
            }
            if(direction == 0){
                direction = 2;
            }
            if(direction == 1){
                direction = 3;
            }

            while (!check()){
                LowAI lowAI = new LowAI();
                direction = lowAI.calculateDirection();
                if(direction == 0){
                    direction = 2;
                }
                if(direction == 1){
                    direction = 3;
                }
            }

            int d = direction;

            LowAI lowAI = new LowAI();
            direction = lowAI.calculateDirection();
            if(direction != d && k < 64){
                direction = d;
            }

            move();

            k++;


        } else {
            afterKill();
        }

    }

    public boolean check(){
        int ss =  Sprite.SCALED_SIZE;
        boolean k =true;
        if(direction == 2 && !canMove((x - speed) / ss, getYUnit())){
            k = false;
        }
        if(direction == 3 && !canMove((x + ss) / ss, getYUnit())){
            k = false;
        }
        return k;
    }

}
