package paramedic_assistant_version_3_1;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

//Home page Controller class
public class HomePageController {

    @FXML
    private Button logoutButton;

    @FXML
    private Button startNewCaseButton;


    @FXML
    void logoutButtonOnAction(ActionEvent event) throws IOException {
        switchScene_Logout();
    }

    @FXML
    void startNewCaseButtonOnAction(ActionEvent event) throws IOException {
        switchScene_ParamedicLocation();
    }


    @FXML // a method that switches between the app pages
    private void switchScene_ParamedicLocation() throws IOException {
        App.setRoot("ParamedicLocation");
    }

    @FXML
    private void switchScene_Logout() throws IOException {
        App.setRoot("login");
    }


}

