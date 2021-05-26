package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDashboardController {
    DatabaseHandler databaseHandler;

    private String username;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Button btnViewResults;

    @FXML
    private Button btnTakeExam;

    @FXML
    private Label lblUsername;

    @FXML
    private Button btnLogOut;

    @FXML
    void logOut(ActionEvent event) {
        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        stage.close();

        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(getClass().getResource("Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setTitle("ExamPro - Log in");
        window.setScene(new Scene(root));
        window.show();
    }

    @FXML
    void takeExam(ActionEvent event) throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TakeExam.fxml"));
        Parent root = loader.load();
        int userId = getUserId(username);
        TakeExamController takeExamController = loader.getController();
        takeExamController.initData(userId);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ExamPro - Taking Exam");
        window.setScene(new Scene(root));
        window.show();
    }

    private int getUserId(String username) {
        databaseHandler = DatabaseHandler.getInstance();
        int user_id = 0;
        String query = ("SELECT user_id FROM users WHERE username = '"+username+"' ORDER BY user_id DESC LIMIT 1");
        ResultSet resultSet = databaseHandler.exeQuery(query);
        try {
            while (resultSet.next()){
                user_id = resultSet.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user_id;
    }

    @FXML
    void viewResults(ActionEvent event) {

    }

    void initData(String username){
        lblUsername.setText("Hello, " + username);
        this.username = username;
    }
}
