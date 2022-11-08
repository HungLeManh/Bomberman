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
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.characters.Bomber;
import uet.oop.bomberman.entities.characters.enemies.Balloon;
import uet.oop.bomberman.entities.characters.enemies.Enemy;
import uet.oop.bomberman.entities.characters.enemies.Oneal;
import uet.oop.bomberman.entities.items.*;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BombermanGame extends Application {
    
    public static int WIDTH = 7; ///cho nhanh
    public static int HEIGHT = 7; //cho nhanh
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();

    private Bomber bomberman = null;

    public static char[][] matrix;/* = {
            {'#', '#', '#', '#', '#', '#', '#'},
            {'#', 'p', ' ', '1', ' ', 's', '#'},
            {'#', ' ', '*', ' ', '#', '*', '#'},
            {'#', '2', '#', ' ', ' ', '*', '#'},
            {'#', 'x', ' ', '*', 'b', ' ', '#'},
            {'#', ' ', 'f', ' ', ' ', '2', '#'},
            {'#', '#', '#', '#', '#', '#', '#'} };*/
    public Board board = null;

    private static int bombRate = 1;
    //private static int flameRadius = 1;

    public static void main(String[] args) {
        Sound.play("soundtrack");
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
        stage.setTitle("Bomberman Game");
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

        entities.add(bomberman);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (Objects.equals(event.getCode().toString(), "SPACE")) {
                    if (bombRate > 0) {
                        bomberman.placeBomb();
                        entities.add(((LinkedList<Bomb>)bomberman.getBombList()).getLast());
                        bombRate --;
                    }
                } else {
                    bomberman.setInputDir(event);
                }

            }
        });

    }

    public void createMap() {
        board = new Board(entities, stillObjects);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                Image grassImg = Sprite.grass.getFxImage();
                Image brickImg = Sprite.brick.getFxImage();

                switch (matrix[i][j]) {
                    case '#':
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    case '*':
                        object = new Brick(j, i, brickImg, board);
                        entities.add(object);
                        break;
                    case 'x':
                        //object = new Portal(j, i, Sprite.portal.getFxImage());
                        object = new LayeredEntity (j, i, new Grass(j, i, grassImg),
                                new Portal(j, i, Sprite.portal.getFxImage()), new Brick(j, i, brickImg, board));
                        entities.add(object);
                        break;
                    case '1':
                        stillObjects.add(new Grass(j, i, grassImg));
                        object = new Balloon(j, i, Sprite.balloon_left1.getFxImage(), board);
                        entities.add(object);
                        enemies.add((Enemy) object);
                        break;
                    case '2':
                        stillObjects.add(new Grass(j, i, grassImg));
                        object = new Oneal(j, i, Sprite.oneal_left1.getFxImage(), board);
                        entities.add(object);
                        enemies.add((Enemy) object);
                        break;
                    case 'b':
                        object = new LayeredEntity (j, i, new Grass(j, i, grassImg),
                                new BombItem(j, i, Sprite.powerup_bombs.getFxImage()), new Brick(j, i, brickImg, board));
                        entities.add(object);
                        break;
                    case 'f':
                        object = new LayeredEntity (j, i, new Grass(j, i, grassImg),
                                new FlameItem(j, i, Sprite.powerup_flames.getFxImage()), new Brick(j, i, brickImg, board));
                        entities.add(object);
                        break;
                    case 's':
                        object = new LayeredEntity (j, i, new Grass(j, i, grassImg),
                                new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()), new Brick(j, i, brickImg, board));
                        entities.add(object);
                        break;
                    case 'p':
                        bomberman = new Bomber(j, i, Sprite.player_right.getFxImage(), board);
                        stillObjects.add(new Grass(j, i, grassImg));
                        break;
                    default:
                        object = new Grass(j, i, grassImg);
                        stillObjects.add(object);
                }
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);

        if (!bomberman.getBombList().isEmpty()) {
            Bomb bomb = bomberman.getBombList().get(0);
            if (bomb.getTimeToExplode() == 0) {
                entities.addAll(bomb.getFlameList());
            }

            if (bomb.isExploded()) {
                entities.remove(bomb);
                bomberman.getBombList().remove(bomb);
                bombRate ++;
            }
        }

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).isRemoved()) {
                entities.remove(i);
            }

        }
        /*if (!bomberman.isAlive()) {

        }*/
        if (enemies.isEmpty() && bomberman.isAlive()) {
            bomberman.setPortalPass(true);
        } else for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isRemoved()) {
                enemies.remove(i);
            }
        }
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

    public int getBombRate() {
        return bombRate;
    }

    public static void addBombRate() {
        BombermanGame.bombRate++;
    }

    /*public int getFlameRadius() {
        return flameRadius;
    }

    public static void addFlameRadius() {
        BombermanGame.flameRadius++;
    }*/
}
