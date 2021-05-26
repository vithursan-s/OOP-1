package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TakeExamController implements Initializable {
    DatabaseHandler databaseHandler;

    ObservableList<Question> list = FXCollections.observableArrayList();
    ObservableList<Answer> answers = FXCollections.observableArrayList();

    List<Integer> givenAns = new ArrayList<>();

    private int userId;
    private int givenAnswer;
    private boolean isMaths;

    @FXML
    private Label lblQuestionNum;

    @FXML
    private TextArea questionArea;

    @FXML
    private RadioButton radioBtn1;

    @FXML
    private RadioButton radioBtn2;

    @FXML
    private RadioButton radioBtn4;

    @FXML
    private RadioButton radioBtn3;

    @FXML
    private TextField txtAnswer;

    @FXML
    private Button btnNext;

    @FXML
    void getMultiSelect() {
        if (radioBtn1.isSelected()){
            setGivenAnswer(1);
        }else if (radioBtn2.isSelected()){
            setGivenAnswer(2);
        }else if (radioBtn3.isSelected()){
            setGivenAnswer(3);
        }else if (radioBtn4.isSelected()){
            setGivenAnswer(4);
        }else {
            setGivenAnswer(0);
        }

    }

    int count = 0;
    @FXML
    void nextClicked() {
        //get the answer from user...
        //create an arraylist
        //insert into db when the exam finishes
        if (isMaths){
            setGivenAnswer(Integer.parseInt(txtAnswer.getText()));
        }
        givenAns.add(getGivenAnswer());

        clearValues();

        count ++;
        if (count < (long) list.size()) {
            PopulateExam(loadQuestion(count), count);
        }else{
            btnNext.setText("Submit");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Thank you for completing the exam!");
            alert.showAndWait();
            for (int i = 0; i < givenAns.size(); i++){
                if (givenAns.get(i) == Integer.parseInt(loadQuestion(i).getAnswer())) {
                    answers.add(new Answer(loadQuestion(i).getExam_id(), userId ,loadQuestion(i).getQuestion_id(), loadQuestion(i).getMarks()));

                }
            }
            for (Answer answer : answers) {
                System.out.println(answer + " ");
            }
        }

    }

    private void clearValues() {
        radioBtn1.setSelected(false);
        radioBtn2.setSelected(false);
        radioBtn3.setSelected(false);
        radioBtn4.setSelected(false);
        txtAnswer.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = DatabaseHandler.getInstance();
        loadQ();
        Question question = loadQuestion(0);
        PopulateExam(question, 0);
    }

    private void PopulateExam(Question loadQuestion, int index) {
        lblQuestionNum.setText("Question "+ (index+1));
        questionArea.setText(loadQuestion.getQuestion());

        //check question type & check amount of options
        checkQuestion(loadQuestion);
    }


    private void checkQuestion(Question loadQuestion) {
        radioBtn1.setVisible(false);
        radioBtn2.setVisible(false);
        radioBtn3.setVisible(false);
        radioBtn4.setVisible(false);
        txtAnswer.setVisible(false);

        String qType = loadQuestion.getType();
        int qOpts = loadQuestion.getOptCount();
        if (qType.equalsIgnoreCase("MATHS")){
            isMaths = true;
            txtAnswer.setVisible(true);
            txtAnswer.setPromptText("Please enter here");
        }else {
            isMaths = false;
            switch(qOpts) {
                case 2:
                    radioBtn1.setVisible(true);
                    radioBtn1.setText(loadQuestion.getOpt1());
                    radioBtn2.setVisible(true);
                    radioBtn2.setText(loadQuestion.getOpt2());
                    break;
                case 3:
                    radioBtn1.setVisible(true);
                    radioBtn1.setText(loadQuestion.getOpt1());
                    radioBtn2.setVisible(true);
                    radioBtn2.setText(loadQuestion.getOpt2());
                    radioBtn3.setVisible(true);
                    radioBtn3.setText(loadQuestion.getOpt3());
                case 4:
                    radioBtn1.setVisible(true);
                    radioBtn1.setText(loadQuestion.getOpt1());
                    radioBtn2.setVisible(true);
                    radioBtn2.setText(loadQuestion.getOpt2());
                    radioBtn3.setVisible(true);
                    radioBtn3.setText(loadQuestion.getOpt3());
                    radioBtn4.setVisible(true);
                    radioBtn4.setText(loadQuestion.getOpt4());
                default:
                    // code block
            }

        }
    }

    private Question loadQuestion(int i) {
        return list.get(i);
    }

    private void loadQ() {
        databaseHandler = DatabaseHandler.getInstance();
        String query = ("SELECT * FROM question");
        ResultSet resultSet = databaseHandler.exeQuery(query);
        try {
            while (true){
                assert resultSet != null;
                if (!resultSet.next()) break;
                int question_id = resultSet.getInt("question_id");
                int exam_id = resultSet.getInt("exam_id");
                String question = resultSet.getString("question");
                String opt1 = resultSet.getString("choice_1");
                String opt2 = resultSet.getString("choice_2");
                String opt3 = resultSet.getString("choice_3");
                String opt4 = resultSet.getString("choice_4");
                String answer = resultSet.getString("correct_answer");
                int marks = resultSet.getInt("marks");
                String type = resultSet.getString("type");
                int optCount = resultSet.getInt("option_count");

                list.add(new Question(question_id, exam_id,question, opt1, opt2, opt3, opt4, answer, marks, type, optCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initData(int userId) {
        this.userId = userId;
    }

    public int getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(int givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
}
