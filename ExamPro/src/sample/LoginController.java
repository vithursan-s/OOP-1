package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public LoginModel loginModel = new LoginModel();
    private int userType;

    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Button loginButton;

    public void loginButtonClicked(){
        if (loginModel.isDbConnected()){
            Alert alert;
            try {
                if (loginModel.verifyLogin(loginUsername.getText(), loginPassword.getText())){
                    userType = loginModel.getUser(loginUsername.getText(), loginPassword.getText());
                    displayDashboard(userType, loginUsername.getText());
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();
                }else {
                    alert = new Alert(Alert.AlertType.ERROR, "Username or Password is incorrect!");
                    alert.show();
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Something went wrong with the database.");
        }
    }

    private void displayDashboard(int userType, String userName) throws IOException {
        if (userType == 1){
            Stage window = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Teacher.fxml"));
            Parent root = loader.load();
            TeacherController teacherController = loader.getController();
            teacherController.initData(userName);
            window.setTitle("ExamPro - Teacher Dashboard");
            window.setScene(new Scene(root));
            window.show();
        }else if (userType == 2){
            Stage window = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDashboard.fxml"));
            Parent root = loader.load();
            StudentDashboardController studentDashboardController = loader.getController();
            studentDashboardController.initData(userName);
            window.setTitle("ExamPro - Student Dashboard");
            window.setScene(new Scene(root));
            window.show();
        }
    }

}
