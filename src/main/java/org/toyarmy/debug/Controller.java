package org.toyarmy.debug;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import org.toyarmy.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static Controller controller = null;

    @FXML private Text entityid;
    @FXML private Text primaryweapon;
    @FXML private Text magazine;
    @FXML private Text ammunition;
    @FXML private ProgressBar bloodPercentage;
    @FXML private Text healthsystem;
    @FXML private Text rotation;
    @FXML private Text position;
    @FXML private Text fps;

    public static void setEntityid(String text) {
        if(controller != null) {
            Platform.runLater(() -> controller.entityid.setText(text));
        } else {
            System.out.println("Controller null");
        }
    }

    public static void setPrimaryweapon(String text) {
        if(controller != null) {
            Platform.runLater(() -> controller.primaryweapon.setText(text));
        } else {
            System.out.println("Controller null");
        }
    }

    public static void setMagazine(String text) {
        if(controller != null) {
            Platform.runLater(() -> controller.magazine.setText(text));
        } else {
            System.out.println("Controller null");
        }
    }

    public static void setAmmunition(String text) {
        if(controller != null) {
            Platform.runLater(() -> controller.ammunition.setText(text));
        } else {
            System.out.println("Controller null");
        }
    }

    public static void setBloodPercentage(float percentage) {
        if(controller != null) {
            Platform.runLater(() -> controller.bloodPercentage.setProgress((double) percentage / 100));
        } else {
            System.out.println("Controller null");
        }
    }

    public static void setHealthSystem(String text) {
        if(controller != null) {
            Platform.runLater(() -> controller.healthsystem.setText(text));
        } else {
            System.out.println("Controller null");
        }
    }

    public static void setRotation(String text) {
        if(controller != null) {
            Platform.runLater(() -> controller.rotation.setText(text));
        } else {
            System.out.println("Controller null");
        }
    }

    public static void setPosition(String text) {
        if(controller != null) {
            Platform.runLater(() -> controller.position.setText(text));
        } else {
            System.out.println("Controller null");
        }
    }

    public static void setFps(String text) {
        if(controller != null) {
            Platform.runLater(() -> controller.fps.setText(text));
        } else {
            System.out.println("Controller null");
        }
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        controller = this;
    }


    // Menu
    @FXML private void closeMethod() {
        System.out.println("Closing");
        Main.isRunning = false;
        Platform.exit();
    }
}
