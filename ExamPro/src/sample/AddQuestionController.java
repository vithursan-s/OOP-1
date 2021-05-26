package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddQuestionController implements Initializable {
    DatabaseHandler databaseHandler;

    ObservableList<Question> list = FXCollections.observableArrayList();

    ObservableList<String> arrQuestionType = FXCollections.observableArrayList("Multiple Choice", "Maths");
    ObservableList<String> arrOptionsAmount = FXCollections.observableArrayList("2", "3", "4");
    ObservableList<Integer> arrCorrectOption = FXCollections.observableArrayList();

    private int exam_id;
    private String exam_name;
    private int total_questions;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextArea txtQuestion;

    @FXML
    private ComboBox<String> questionTypeCombo;

    @FXML
    private ComboBox<String> optionsAmountCombo;

    @FXML
    private Label optionsAmountLabel;

    @FXML
    private TextField txtOption1;

    @FXML
    private TextField txtOption2;

    @FXML
    private TextField txtOption3;

    @FXML
    private TextField txtOption4;

    @FXML
    private ComboBox<Integer> correctOptionCombo;

    @FXML
    private Label correctOptionLabel;

    @FXML
    private TextField txtMarks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = DatabaseHandler.getInstance();
        questionTypeCombo.setItems(arrQuestionType);
        optionsAmountCombo.setItems(arrOptionsAmount);
        txtMarks.setText("10");
        setInitVisibleFalse();
    }

    private void clearFields() {
        txtQuestion.setText("");
        txtOption1.setText("");
        txtOption2.setText("");
        txtOption3.setText("");
        txtOption4.setText("");
        txtMarks.setText("10");
        questionTypeCombo.valueProperty().set(null);
        optionsAmountCombo.valueProperty().set(null);
        correctOptionCombo.valueProperty().set(null);
    }

    private void setInitVisibleFalse() {
        optionsAmountCombo.setVisible(false);
        optionsAmountLabel.setVisible(false);
        correctOptionCombo.setVisible(false);
        correctOptionLabel.setVisible(false);
        txtOption1.setVisible(false);
        txtOption2.setVisible(false);
        txtOption3.setVisible(false);
        txtOption4.setVisible(false);

    }

    int count = 0;
    @FXML
    void addQuestion(ActionEvent event) throws IOException {
        count ++;
        if (count <= total_questions) {
            String question = txtQuestion.getText();
            String option1 = txtOption1.getText();
            String option2 = txtOption2.getText();
            String option3 = txtOption3.getText();
            String option4 = txtOption4.getText();
            String answer = String.valueOf(correctOptionCombo.getSelectionModel().getSelectedIndex() + 1);
            int marks;
            if (isNumeric(txtMarks.getText())) {
                marks = Integer.parseInt(txtMarks.getText());
            } else {
                marks = 10;
            }
            String type = questionTypeCombo.getSelectionModel().getSelectedItem();
            int optCount = optionsAmountCombo.getSelectionModel().getSelectedIndex() + 2;

            if (question.isEmpty()||option1.isEmpty()) {
                count--;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("All the fields must be filled in");
                alert.showAndWait();
            }else {
                list.add(new Question(0,exam_id,question, option1, option2, option3, option4, answer, marks, type, optCount));
                //Add the question to the database...
                String query = ("insert into question (exam_id, question, choice_1, choice_2, choice_3, choice_4, correct_answer, marks, type, option_count)\n" +
                        "values ('"+exam_id+"', '"+question+"', '"+option1+"', '"+option2+"', '"+option3+"', '"+option4+"', '"+answer+"', '"+marks+"', '"+type+"', '"+optCount+"');");
                if (databaseHandler.exeAction(query)){
                    txtMarks.setText("10");
                    setInitVisibleFalse();
                    clearFields();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Something went wrong!");
                    alert.showAndWait();
                }

            }
        }else{


            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void correctOptionComboAction(ActionEvent event) {

    }

    @FXML
    void optionsAmountComboAction(ActionEvent event) {
        int i = optionsAmountCombo.getSelectionModel().getSelectedIndex();
        arrCorrectOption.clear();
        switch(i) {
            case 0:
                txtOption1.setVisible(true);
                txtOption2.setVisible(true);
                txtOption3.setVisible(false);
                txtOption4.setVisible(false);
                arrCorrectOption.addAll(1,2);
                break;
            case 1:
                txtOption1.setVisible(true);
                txtOption2.setVisible(true);
                txtOption3.setVisible(true);
                txtOption4.setVisible(false);
                arrCorrectOption.addAll(1,2,3);
                break;
            case 2:
                txtOption1.setVisible(true);
                txtOption2.setVisible(true);
                txtOption3.setVisible(true);
                txtOption4.setVisible(true);
                arrCorrectOption.addAll(1,2,3,4);
                break;
            default:
                break;
        }
        correctOptionCombo.setItems(arrCorrectOption);
        correctOptionCombo.setVisible(true);
        correctOptionLabel.setVisible(true);

    }

    @FXML
    void questionTypeComboAction(ActionEvent event) {
        setInitVisibleFalse();
        int i = questionTypeCombo.getSelectionModel().getSelectedIndex();
        switch(i) {
            case 0:
                optionsAmountCombo.setVisible(true);
                optionsAmountLabel.setVisible(true);
                break;
            case 1:
                txtOption1.setVisible(true);
                break;
            default:
                break;
        }

    }

    boolean isNumeric(String value){
        boolean numeric  = value.matches("-?\\d+(\\.\\d+)?");
        return numeric;
    }

    public void receiveExam(ExamModel examModel) {
        exam_id = examModel.getExamId();
        exam_name = examModel.getExamName();
        total_questions = examModel.getQuestionAmount();
    }
}
