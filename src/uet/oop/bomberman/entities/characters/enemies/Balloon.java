package uet.oop.bomberman.entities.characters.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.characters.enemies.ai.LowAI;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    public Balloon(int x, int y, Image img, Board board) {
        super(x, y, img, board);
        spriteMove = new Sprite[]{
                Sprite.balloon_left1, Sprite.balloon_left2, Sprite.balloon_left3,
                Sprite.balloon_right1, Sprite.balloon_right2, Sprite.balloon_right3,
                Sprite.balloon_left1, Sprite.balloon_left2, Sprite.balloon_left3,
                Sprite.balloon_right1, Sprite.balloon_right2, Sprite.balloon_right3};
        spriteDead = new Sprite[]{Sprite.balloon_dead};
        speed = 1;
    }

    @Override
    public void update() {

        LowAI lowAI = new LowAI();
        direction = lowAI.calculateDirection();
        int d = direction;
        if(canMove(getXUnit(), getYUnit())){
            for(int i=0; i< 10; i++){
                move(32 - speed, 32);
            }
        }
    }
}
