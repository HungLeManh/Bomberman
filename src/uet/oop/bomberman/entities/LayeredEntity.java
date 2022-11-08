package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.graphics.Sprite;

import java.util.LinkedList;

public class LayeredEntity extends Entity {
    private LinkedList<Entity> entityList = new LinkedList<>();

    public LayeredEntity(int xUnit, int yUnit, Entity ... entities) {
        x = xUnit * Sprite.SCALED_SIZE;
        y = yUnit * Sprite.SCALED_SIZE;
        for (int i = 0; i < entities.length; i++) {
            entityList.add(entities[i]);
        }
        img = Sprite.brick.getFxImage();
    }

    public Entity topEntity() {
        return entityList.getLast();
    }

    @Override
    public void update() {
        if (topEntity().removed) {
            entityList.removeLast();
            System.out.println(topEntity());
        }
        topEntity().update();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (topEntity() instanceof Brick) {
            Brick br = (Brick) topEntity();
            if (br.isDestroyed()) {
                entityList.getFirst().render(gc);
                //if (entityList.size() > 2) {
                    entityList.get(1).render(gc);
                //}
            }
            br.render(gc);
        } else {
            entityList.getFirst().render(gc);
            topEntity().render(gc);
        }
    }

    @Override
    public boolean collide(Entity e) {
        return topEntity().collide(e);
    }


}
