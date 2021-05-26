package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherController {

    @FXML
    private Label lblUsername;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button tUpdateExam;

    @FXML
    private Button tCreateExam;

    @FXML
    private Button tDeleteExam;

    @FXML
    private Button tViewResults;

    void initData(String username){
        lblUsername.setText("Hello, " + username);
    }

    @FXML
    void logOut() throws IOException {
        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        stage.close();

        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("Login.fxml"));
        window.setTitle("ExamPro - Log in");
        window.setScene(new Scene(root));
        window.show();
    }

    @FXML
    void createExam(ActionEvent event) throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateExam.fxml"));
        Parent root = loader.load();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ExamPro - Create Exam");

        window.setScene(new Scene(root));
        window.show();
    }

    @FXML
    void deleteExam(ActionEvent event) {

    }

    @FXML
    void updateExam(ActionEvent event) {

    }

    @FXML
    void viewResults(ActionEvent event) {

    }

}
