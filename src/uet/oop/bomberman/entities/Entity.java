package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    protected boolean removed = false;

    public Entity() {

    }

    /** Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas. */
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    /** Tra ve true neu co va cham(khong the di qua nhau). */
    public abstract boolean collide(Entity e);

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Image getImg() {
        return img;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public int getXUnit() {
        return (int) Math.round((double)x / Sprite.SCALED_SIZE);
    }

    public int getYUnit() {
        return (int) Math.round((double)y / Sprite.SCALED_SIZE);
    }
}
