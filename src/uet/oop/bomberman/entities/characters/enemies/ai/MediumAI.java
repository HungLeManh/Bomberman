package uet.oop.bomberman.entities.characters.enemies.ai;

public class MediumAI extends AI {

    @Override
    public int calculateDirection() {
        // TODO: cài đặt thuật toán tìm đường đi
        return random.nextInt(4);
    }

}