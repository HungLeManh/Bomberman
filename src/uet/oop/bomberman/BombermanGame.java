package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemies.Balloon;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.enemies.Oneal;
import uet.oop.bomberman.entities.items.*;

import uet.oop.bomberman.graphics.Board;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BombermanGame extends Application {
    
    public static int WIDTH;
    public static int HEIGHT;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    public static char[][] matrix;
    public Board board = null;

    public static void main(String[] args) {

        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        //Doc file cau hinh
        createMatrix();
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

        Enemy o = new Oneal(3, 1, Sprite.oneal_left1.getFxImage());
        entities.add(o);
        board.cell[1][3] = Board.enemy;

        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (Objects.equals(event.getCode().toString(), "SPACE")) {
                    Bomb bomb = new Bomb(bomberman.getXUnit(), bomberman.getYUnit(), Sprite.bomb.getFxImage());
                    entities.add(bomb);
                    bomberman.addBomb(bomb);
                } else {
                    bomberman.setDirection(event);
                }

            }
        });

        if (!bomberman.isAlive()) {
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).equals(bomberman)) {
                    entities.remove(i);
                }
            }
            Platform.exit();
        }

    }

    public void createMap() {
        board = new Board(WIDTH, HEIGHT);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                Image grassImg = Sprite.grass.getFxImage();
                Image brickImg = Sprite.brick.getFxImage();

                switch (matrix[i][j]) {
                    case '#':
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        board.cell[i][j] = board.wall;
                        break;
                    case '*':
                        object = new Brick(j, i, brickImg);
                        stillObjects.add(object);
                        board.cell[i][j] = board.brick;
                        break;
                    case 'x':
                        //stillObjects.add(new Grass(j, i, grassImg));
                        object = new Portal(j, i, Sprite.portal.getFxImage());
                        stillObjects.add(object);
                        stillObjects.add(new Brick(j, i, brickImg));
                        board.cell[i][j] = board.brick;
                        break;
                    case '1':
                        stillObjects.add(new Grass(j, i, grassImg));
                        object = new Balloon(j, i, Sprite.balloon_left1.getFxImage());
                        entities.add(object);
                        board.cell[i][j] = board.enemy;
                        break;
                    case '2':
                        stillObjects.add(new Grass(j, i, grassImg));
                        object = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                        entities.add(object);
                        board.cell[i][j] = board.enemy;
                        break;
                    case 'b':
                        //stillObjects.add(new Grass(j, i, grassImg));
                        object = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                        stillObjects.add(object);
                        stillObjects.add(new Brick(j, i, brickImg));
                        board.cell[i][j] = board.brick;
                        break;
                    case 'f':
                        //stillObjects.add(new Grass(j, i, grassImg));
                        object = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        stillObjects.add(object);
                        stillObjects.add(new Brick(j, i, brickImg));
                        board.cell[i][j] = board.brick;
                        break;
                    case 's':
                        //stillObjects.add(new Grass(j, i, grassImg));
                        object = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        stillObjects.add(object);
                        stillObjects.add(new Brick(j, i, brickImg));
                        board.cell[i][j] = board.brick;
                        break;
                    /*case 'p':
                        object = new Bomber(j, i, Sprite.player_right.getFxImage());
                        entities.add(object);
                        break;*/
                    default:
                        object = new Grass(j, i, grassImg);
                        stillObjects.add(object);
                        board.cell[i][j] = board.grass;
                }
            }
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

    public static void createMatrix() {
        BufferedReader buffered = null;
        try {
            Reader in = new FileReader("res/levels/Level1.txt");
            buffered = new BufferedReader(in);

            String[] token = buffered.readLine().split(" ");
            int row = Integer.parseInt(token[1]);
            int col = Integer.parseInt(token[2]);
            matrix = new char[row][col];
            WIDTH = col;
            HEIGHT = row;

            String line;
            for (int i = 0; i < row; i++) {
                line = buffered.readLine();
                for (int j = 0; j < col; j++) {
                    matrix[i][j] = line.charAt(j);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
