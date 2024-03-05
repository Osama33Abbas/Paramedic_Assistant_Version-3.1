package paramedic_assistant_version_3_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;



/**
 * JavaFX App / Paramedic Assistant Main Class
 */
public class App extends Application {

    private static Scene scene;
   
    @Override
    public void start(Stage stage) throws IOException {

        //set the app logo
        Image logoImage = new Image(getClass().getResourceAsStream("logo2_2E.png"));
       
        scene = new Scene(loadFXML("login"));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Paramedic Assistant app");
        stage.getIcons().add(logoImage);
        stage.setScene(scene);
        stage.show();
    }


    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    
    public static void main(String[] args) {
        launch();
    }

}

