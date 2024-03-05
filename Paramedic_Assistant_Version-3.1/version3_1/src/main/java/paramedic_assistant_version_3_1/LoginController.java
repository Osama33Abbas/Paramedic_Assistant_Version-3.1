package paramedic_assistant_version_3_1;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Login page controller Class
public class LoginController {

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Label loginMessage_label;

    @FXML
    private Button loginButton;

    @FXML
    private Label password_label;

    @FXML
    private PasswordField password_textField;

    @FXML
    private Label username_label;

    @FXML
    private TextField username_textFeild;

    // THE login button action
    @FXML
    void loginButtonOnAction(ActionEvent event) throws IOException {

        if (username_textFeild.getText().isBlank() == false && password_textField.getText().isBlank() == false) {
            validateLogin(); // call the validateLogin() method to proceed with the login process
        } else {
            loginMessage_label.setText("Please enter a valid username and password!");
        }
    }

    @FXML // a cancle button that exits the program
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    // a method that will validate the login process by checking the username &
    // password fields in the database
    public void validateLogin() throws IOException {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        // The select statment which will check the all the usernames & passwords from
        // the user_account table
        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, username_textFeild.getText());
            preparedStatement.setString(2, password_textField.getText());

            ResultSet queryResult = preparedStatement.executeQuery();

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginMessage_label.setText("Login success!");
                    switchScene_Home(); // in case the input was correct => switch to the Home page scene

                } else {
                    loginMessage_label.setText("Invalid login. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML // a method that switches between the app pages
    private void switchScene_Home() throws IOException {
        App.setRoot("HomePage");
    }

}
