package assignment5;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	static Stage graphicStage = new Stage();
    static Stage controllerStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ControllerScene.fxml"));
        controllerStage.setTitle("Controller Component");
        controllerStage.setScene(new Scene(root));
        controllerStage.show();
    }

    public static void main(String[] args) {
          launch(args);
    }

}

