package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.UIControl.MainWindow;

public class Main extends Application {

   MainWindow controller;
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            URL xmlUrl=getClass().getResource("fxml/sample.fxml");
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(xmlUrl);
            Parent root=loader.load();
            primaryStage.setTitle("Музыкальная библиотека");
            primaryStage.setScene(new Scene(root, 800, 585));
            primaryStage.show();
            controller=loader.getController();
            controller.start(primaryStage);
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    if(1==controller.showConfirmation())
                        we.consume();
                }
            });
        } catch (LoadException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка запуска");
            alert.showAndWait();
        }
        catch (IOException e){e.printStackTrace();}

    }


    public static void main(String[] args) {
        launch(args);
    }
}