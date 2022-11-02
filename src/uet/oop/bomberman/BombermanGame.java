package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();
    }


    public void createMap() {
        BufferedReader bufferedReader = null;
        try {
            Reader reader = new FileReader("res/levels/Level1.txt");
            bufferedReader = new BufferedReader(reader);
            String firstLine = bufferedReader.readLine();
            int level = 0;
            int row = 0;
            int column = 0;
            String[] tokens = firstLine.split(" ");
            level = Integer.parseInt(tokens[0]);
            row = Integer.parseInt(tokens[1]);
            column = Integer.parseInt(tokens[2]);
            char[][] mapMatrix = new char[row][column];
            for (int i = 0; i < row; i++) {
                String line = bufferedReader.readLine();
                for (int j = 0; j < column; j++) {
                    char character = line.charAt(j);
                    mapMatrix[i][j] = character;
                }
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    Entity object;
                    switch (mapMatrix[i][j]) {
                        case '#':
                            object = new Wall(j, i, Sprite.wall.getFxImage());
                            stillObjects.add(object);
                            break;
                        case '*':
                            object = new Brick(j, i, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                            break;
                        case 'x':
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object);
                            object = new Portal(j, i, Sprite.portal.getFxImage());
                            stillObjects.add(object);
                            break;
                        case 'p':
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object);
                            object = new Bomber(j, i, Sprite.player_right.getFxImage());
                            entities.add(object);
                            break;
                        case '1':
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object);
                            object = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                            entities.add(object);
                            break;
                        case '2':
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object);
                            object = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                            entities.add(object);
                            break;
                        case ' ':
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object);
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
