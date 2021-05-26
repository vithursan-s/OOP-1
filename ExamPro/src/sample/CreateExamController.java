package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateExamController implements Initializable {

    DatabaseHandler databaseHandler;

    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField txtExamName;

    @FXML
    private TextField txtClassId;

    @FXML
    private TextField txtQuestionAmount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = DatabaseHandler.getInstance();
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addQuestion(ActionEvent event) throws IOException {
        ExamModel examModel =  createExamAndReturnClass();
        setExamToClassRoom(examModel);

        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuestion.fxml"));
        Parent root = loader.load();

        AddQuestionController addQuestionController = loader.getController();
        addQuestionController.receiveExam(examModel);

        window.setTitle("ExamPro - Add Question");
        window.setScene(new Scene(root));
        window.show();

        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void setExamToClassRoom(ExamModel examModel) {
        int class_id;
        if (databaseHandler.isNumeric(txtExamName.getText())) {
            class_id = Integer.parseInt(txtExamName.getText());
        } else {
            class_id = 0;
        }
        int exam_id = examModel.getExamId();
        String query = ("INSERT INTO classroom_exam (class_id, exam_id) VALUES ('"+class_id+"', '"+exam_id+"')");
        if (databaseHandler.exeAction(query)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong!");
            alert.showAndWait();
        }

    }

    private ExamModel createExamAndReturnClass() {
        ExamModel examModel = null;
        String examName = txtExamName.getText();
        String questionAmount = txtQuestionAmount.getText();
        if (examName.isEmpty()||questionAmount.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("All the fields must be filled in");
            alert.showAndWait();
        }else{
            String insertQuery = ("INSERT INTO exam (exam_name, total_questions) VALUES ('"+examName+"', '"+questionAmount+"')");
            String getMaxQuery = ("SELECT * FROM exam ORDER BY exam_id DESC LIMIT 1");
            if (databaseHandler.exeAction(insertQuery)){
                ResultSet resultSet = databaseHandler.exeQuery(getMaxQuery);
                try {
                    while (resultSet.next()) {
                        int exam_id = resultSet.getInt(1);
                        String exam_name = resultSet.getString(2);
                        int total_questions = resultSet.getInt(3);
                        System.out.println(exam_id);
                        examModel= new ExamModel(exam_id, exam_name, total_questions);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Success");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong!");
                alert.showAndWait();
            }
        }
        return examModel;
    }
}
