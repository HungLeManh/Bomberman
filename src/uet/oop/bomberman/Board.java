package uet.oop.bomberman;

import javafx.application.Platform;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.characters.Bomber;

import java.util.ArrayList;
import java.util.List;

public class Board {
    //public static char[][] cell;
    //public static final char wall = 1;
    //public static final char brick = 2;
    //public static final char grass = 0;

    public List<Entity> entities;

    public List<Entity> stillObject;

    public Board(List<Entity> entities, List<Entity> stillObject) {
        this.entities = entities;
        this.stillObject = stillObject;
    }

    public Entity getEntityAt(int xUnit, int yUnit) {
        for (int i = entities.size() - 1; i >= 0; i--) {
            Entity e = entities.get(i);
            if (e.getXUnit() == xUnit && e.getYUnit() == yUnit) {
                if (e instanceof LayeredEntity) {
                    return ((LayeredEntity) e).topEntity();
                }
                return e;
            }
        }
        for (int i = stillObject.size() - 1; i >= 0; i--) {
            Entity e = stillObject.get(i);
            if (e.getXUnit() == xUnit && e.getYUnit() == yUnit) {
                return e;
            }
        }
        return null;
    }

    public Entity getOtherEntityAt(Entity e) {
        for (int i = entities.size() - 1; i >= 0; i--) {
            Entity other = entities.get(i);
            if (other.getXUnit() == e.getXUnit() && other.getYUnit() == e.getYUnit()) {
                if (other.equals(e)) {
                    continue;
                } else if (other instanceof LayeredEntity) {
                        return ((LayeredEntity) other).topEntity();
                    }
                return other;
            }
            }

        for (int i = stillObject.size() - 1; i >= 0; i--) {
            Entity other = stillObject.get(i);
            if (other.getXUnit() == e.getXUnit() && other.getYUnit() == e.getYUnit()) {
                return other;
            }
        }
        return null;
    }

    public void nextLevel() {
        Platform.exit();
    }
}
