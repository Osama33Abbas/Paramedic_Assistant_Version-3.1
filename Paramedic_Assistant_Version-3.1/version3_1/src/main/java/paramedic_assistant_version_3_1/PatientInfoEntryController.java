package paramedic_assistant_version_3_1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//Patient Info Entry page Controller Class
public class PatientInfoEntryController {

    @FXML
    private Label checkSubmitLabel;

    @FXML
    private TextField Q8_bloodSugar_textField;

    @FXML
    private TextField Q8_pressure_textField;

    @FXML
    private TextArea Q9_additionalInfo_textArea;

    @FXML
    private Button changeYourLocationButton;

    @FXML
    private ToggleGroup group1_Q1;

    @FXML
    private ToggleGroup group2_Q2;

    @FXML
    private ToggleGroup group3_Q3;

    @FXML
    private ToggleGroup group4_Q4;

    @FXML
    private ToggleGroup group5_Q5;

    @FXML
    private ToggleGroup group6_Q6;

    @FXML
    private ToggleGroup group7_Q7;

    @FXML
    private Label lblHeading;

    @FXML
    private Label lblHeading1;

    @FXML
    private Label lblQ1_gender;

    @FXML
    private Label lblQ2_age;

    @FXML
    private Label lblQ3;

    @FXML
    private Label lblQ31;

    @FXML
    private Label lblQ311;

    @FXML
    private Label lblQ3111;

    @FXML
    private Label lblQ31111;

    @FXML
    private Label lblQ3112;

    @FXML
    private Label lblQ31121;

    @FXML
    private Label lblQ3_condition;

    @FXML
    private RadioButton rbQ1_female;

    @FXML
    private RadioButton rbQ1_male;

    @FXML
    private RadioButton rbQ2_1;

    @FXML
    private RadioButton rbQ2_2;

    @FXML
    private RadioButton rbQ2_3;

    @FXML
    private RadioButton rbQ2_4;

    @FXML
    private RadioButton rbQ2_5;

    @FXML
    private RadioButton rbQ3_condition_extreme;

    @FXML
    private RadioButton rbQ3_condition_major;

    @FXML
    private RadioButton rbQ3_condition_minor;

    @FXML
    private RadioButton rbQ3_condition_moderate;

    @FXML
    private RadioButton rbQ4_conscious;

    @FXML
    private RadioButton rbQ4_unconscious;

    @FXML
    private RadioButton rbQ5_no;

    @FXML
    private RadioButton rbQ5_yes;

    @FXML
    private RadioButton rbQ6_no;

    @FXML
    private RadioButton rbQ6_yes;

    @FXML
    private RadioButton rbQ7_no;

    @FXML
    private RadioButton rbQ7_yes;

    @FXML
    private Button submitButton;

    @FXML
    void changeYourLocationButtonOnAction(ActionEvent event) throws IOException {
        switchScene_ParamedicLocation();
    }

    // Inside submitButtonOnAction method
    @FXML
    void submitButtonOnAction(ActionEvent event) throws IOException {
        // Check if all required radio buttons are selected
        if (areAllRadioButtonsSelected()) {
            // Collect user responses
            String gender = getSelectedRadioButtonValue(group1_Q1);
            String ageGroup = getSelectedRadioButtonValue(group2_Q2);
            String conditionSeverity = getSelectedRadioButtonValue(group3_Q3);
            String consciousnessLevel = getSelectedRadioButtonValue(group4_Q4);
            boolean chronicIllnessHistory = rbQ5_yes.isSelected();
            boolean currentMedications = rbQ6_yes.isSelected();
            boolean previousMedicalConditions = rbQ7_yes.isSelected();
            int bloodSugar = parseTextField(Q8_bloodSugar_textField);
            int pressure = parseTextField(Q8_pressure_textField);
            String additionalInfo = Q9_additionalInfo_textArea.getText();

            // Insert into the database
            insertUserResponse(gender, ageGroup, conditionSeverity, consciousnessLevel,
                    chronicIllnessHistory, currentMedications, previousMedicalConditions,
                    bloodSugar, pressure, additionalInfo);

            // Switch to the home page or perform other actions
            switchScene_Home();
        } else {
            // Inform the user to select all required options before submitting
            System.out.println("Please select all required options before submitting.");
            checkSubmitLabel.setVisible(true);
            checkSubmitLabel.setText("Please select all required options before submitting!");
        }
    }

    private String getSelectedRadioButtonValue(ToggleGroup toggleGroup) {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        return selectedRadioButton != null ? selectedRadioButton.getText() : null;
    }

    private int parseTextField(TextField textField) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return 0; // Handle the case where the text field is not a valid integer
        }
    }

    private void insertUserResponse(String gender, String ageGroup, String conditionSeverity,
            String consciousnessLevel, boolean chronicIllnessHistory,
            boolean currentMedications, boolean previousMedicalConditions,
            int bloodSugar, int pressure, String additionalInfo) {
        // Connect to the database (Assuming you have a DatabaseConnection class)
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB != null) {
            try {
                // Query to insert user responses into the database
                String query = "INSERT INTO user_responses " +
                        "(gender, age_group, condition_severity, consciousness_level, " +
                        "chronic_illness_history, current_medications, previous_medical_conditions, " +
                        "blood_sugar, pressure, additional_info) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // Use a prepared statement to avoid SQL injection
                try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
                    preparedStatement.setString(1, gender);
                    preparedStatement.setString(2, ageGroup);
                    preparedStatement.setString(3, conditionSeverity);
                    preparedStatement.setString(4, consciousnessLevel);
                    preparedStatement.setBoolean(5, chronicIllnessHistory);
                    preparedStatement.setBoolean(6, currentMedications);
                    preparedStatement.setBoolean(7, previousMedicalConditions);
                    preparedStatement.setInt(8, bloodSugar);
                    preparedStatement.setInt(9, pressure);
                    preparedStatement.setString(10, additionalInfo);

                    // Execute the query
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            } finally {
                // Close the database connection
                try {
                    connectDB.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
        }
    }

    private boolean areAllRadioButtonsSelected() {
        // Check if all required radio buttons are selected
        return group1_Q1.getSelectedToggle() != null &&
                group2_Q2.getSelectedToggle() != null &&
                group3_Q3.getSelectedToggle() != null &&
                group4_Q4.getSelectedToggle() != null &&
                group5_Q5.getSelectedToggle() != null &&
                group6_Q6.getSelectedToggle() != null &&
                group7_Q7.getSelectedToggle() != null;
    }

    @FXML
    private void switchScene_Logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void switchScene_Home() throws IOException {
        App.setRoot("HomePage");
    }

    @FXML // a method that switches between the app pages
    private void switchScene_ParamedicLocation() throws IOException {
        App.setRoot("ParamedicLocation");
    }
}
