package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Question {
    private final SimpleIntegerProperty question_id;
    private final SimpleIntegerProperty exam_id;
    private final SimpleStringProperty question;
    private final SimpleStringProperty opt1;
    private final SimpleStringProperty opt2;
    private final SimpleStringProperty opt3;
    private final SimpleStringProperty opt4;
    private final SimpleStringProperty answer;
    private final SimpleIntegerProperty marks;
    private final SimpleStringProperty type;
    private final SimpleIntegerProperty optCount;

    public Question(int question_id,int exam_id, String question, String opt1, String opt2, String opt3, String opt4, String answer, int marks, String type, int optCount) {
        this.question_id = new SimpleIntegerProperty(question_id);
        this.exam_id = new SimpleIntegerProperty(exam_id);
        this.question = new SimpleStringProperty(question);
        this.opt1 = new SimpleStringProperty(opt1);
        this.opt2 =  new SimpleStringProperty(opt2);
        this.opt3 =  new SimpleStringProperty(opt3);
        this.opt4 =  new SimpleStringProperty(opt4);
        this.answer = new SimpleStringProperty(answer);
        this.marks = new SimpleIntegerProperty(marks);
        this.type = new SimpleStringProperty(type);
        this.optCount = new SimpleIntegerProperty(optCount);
    }

    public String getQuestion() {
        return question.get();
    }

    public String getOpt1() {
        return opt1.get();
    }

    public String getOpt2() {
        return opt2.get();
    }

    public String getOpt3() {
        return opt3.get();
    }

    public String getOpt4() {
        return opt4.get();
    }

    public String getAnswer() {
        return answer.get();
    }

    public int getMarks() {
        return marks.get();
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public int getOptCount() {
        return optCount.get();
    }

    public SimpleIntegerProperty optCountProperty() {
        return optCount;
    }

    public int getQuestion_id() {
        return question_id.get();
    }

    public SimpleIntegerProperty question_idProperty() {
        return question_id;
    }

    public int getExam_id() {
        return exam_id.get();
    }

    public SimpleIntegerProperty exam_idProperty() {
        return exam_id;
    }
}
