package paramedic_assistant_version_3_1;

import java.io.IOException;
import java.util.ResourceBundle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

// Paramedic Location page Controller class
public class ParamedicLocationController implements Initializable {

    private String[] areaLocationArray = {"Azizia", "Al-Awali", "Batha quraish", "Az zahir"};

    @FXML //comboBox to contain the cboLocation
    private ComboBox<String> cboLocation;

    @FXML
    private Label chooseLocationLabel;

    @FXML
    private Button homePageButton;

    @FXML
    private Label lblLocation;

    @FXML
    private Button logOutButton;

    @FXML
    private Button nextButton;

    @FXML // the textField that will contain the available hospitals data
    private TextArea taLocation;

    //initialize method will be automatically called when the FXML file is loaded. in this case we will use it
    //to set up the comboBox options
    @Override 
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> areaLocation = FXCollections.observableArrayList(areaLocationArray);
        this.cboLocation.getItems().addAll(areaLocation);  
    }

    public String getcboLocationValue(){
        String cboLocationValue = cboLocation.getValue();
        return cboLocationValue;
    }

    @FXML //this is the comoboBox handle method 
    void cboLocationHandler(ActionEvent event) {
        String selectedLocation = getcboLocationValue();

    if (selectedLocation != null && !selectedLocation.isEmpty()) {
        // Connect to the database (Assuming you have a DatabaseConnection class)
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB != null) {
            try {
                // Query to retrieve hospitals for the selected location
                String query = "SELECT hospital_name, location, url FROM hospitals WHERE location = ?";
                
                // Use a prepared statement to avoid SQL injection
                try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
                    preparedStatement.setString(1, selectedLocation);

                    // Execute the query
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        StringBuilder hospitalsInfo = new StringBuilder();

                        // Append hospital information to StringBuilder
                        while (resultSet.next()) {
                            String hospitalName = resultSet.getString("hospital_name");
                            String location = resultSet.getString("location");
                            String url = resultSet.getString("url");

                            hospitalsInfo.append("Location: ").append(location).append("\n");
                            hospitalsInfo.append("Hospital: ").append(hospitalName).append("\n\n");
                            hospitalsInfo.append("Google Map URL: ").append(url).append("\n\n\n");

                        }

                        // Set the formatted information to the TextArea
                        taLocation.setVisible(true);
                        taLocation.setText(hospitalsInfo.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Close the database connection
                try {
                    connectDB.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
        
    }


    @FXML
    void homePageButtonOnAction(ActionEvent event) throws IOException {
        switchScene_Home();
    }


    @FXML
    void logOutButtonOnAction(ActionEvent event) throws IOException {
        switchScene_Logout();
    }


    @FXML
    void nextButtonOnClick(ActionEvent event) throws IOException {
        if(getcboLocationValue() != null){
            switchScene_PatientInfoEntry();
        }else{
            chooseLocationLabel.setVisible(true);
            chooseLocationLabel.setText("Please choose a location!");
        }
    }

    @FXML
    private void switchScene_PatientInfoEntry() throws IOException {
        App.setRoot("PatientInfoEntry");
    }

    @FXML
    private void switchScene_Logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void switchScene_Home() throws IOException {
        App.setRoot("HomePage");
    }
}

