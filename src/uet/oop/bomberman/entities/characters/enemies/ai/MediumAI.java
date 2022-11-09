package uet.oop.bomberman.entities.characters.enemies.ai;

import uet.oop.bomberman.entities.characters.Bomber;
import uet.oop.bomberman.entities.characters.enemies.Enemy;

public class MediumAI extends AI {

    private Bomber bomber;
    private Enemy enemy;

    public MediumAI(Bomber b, Enemy e) {
        bomber = b;
        enemy = e;
    }

    @Override
    public int calculateDirection() {
        // TODO: cài đặt thuật toán tìm đường đi
        return random.nextInt(4);
    }

}