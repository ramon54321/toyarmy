package org.toyarmy;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.lwjgl.Version;
import org.toyarmy.debug.Controller;
import org.toyarmy.engine.Entity;
import org.toyarmy.engine.EntityManager;
import org.toyarmy.game.behaviours.GameManager;
import org.toyarmy.graphics.Display;
import org.toyarmy.graphics.rendering.Camera;
import org.toyarmy.graphics.rendering.RenderManager;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class Main extends Application{

    public static Main instance;

    private Display display;
    private Camera camera;

    private EntityManager entityManager;
    private RenderManager renderManager;

    public Display getDisplay() {
        return display;
    }

    public Camera getCamera() {
        return camera;
    }

    public static boolean isRunning = true;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("debug.fxml"));
        primaryStage.setTitle("Toy Army Debugger");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }

    public void run() {
        instance = this;

        display = new Display(1024,1024,"Toy Army!");
        camera = new Camera();

        entityManager = EntityManager.getInstance();
        renderManager = RenderManager.getInstance(display, camera, entityManager);

        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        initGame();
        gameLoop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(display.getWindowId());
        glfwDestroyWindow(display.getWindowId());

        // Terminate GLFW and free the error callback
        glfwTerminate();
    }

    private void initGame() {

        Entity entity = entityManager.createNewEntity(0);
        entity.addComponent(new GameManager(entity));

    }

    private void gameLoop(){

        long lastTime = System.nanoTime();
        long lastUpdate = lastTime;
        double delta = 0.0;
        double ns = 1000000000.0 / 120.0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while(!glfwWindowShouldClose(display.getWindowId()) && isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1.0) {
                double deltaTime = ((System.nanoTime() - lastUpdate) / 1000000000.0);
                lastUpdate = System.nanoTime();
                update((float)deltaTime);
                updates++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println(updates + " ups, " + frames + " fps");
                Controller.setFps(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
            glfwPollEvents();
        }

    }

    private void update(float deltaTime){
        entityManager.update(deltaTime);
    }

    private void render(){

        renderManager.render();

    }

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                new Main().run();
            }
        }.start();
        launch(args);
    }

}