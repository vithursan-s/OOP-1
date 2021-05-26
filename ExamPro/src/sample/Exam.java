package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Exam {
    private final SimpleIntegerProperty examId;
    private final SimpleStringProperty examName;
    private final SimpleStringProperty totalQuestions;

    public Exam(int examId, String examName, String totalQuestions) {
        this.examId = new SimpleIntegerProperty(examId);
        this.examName = new SimpleStringProperty(examName);
        this.totalQuestions = new SimpleStringProperty(totalQuestions);
    }

    public int getExamId() {
        return examId.get();
    }

    public SimpleIntegerProperty examIdProperty() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId.set(examId);
    }

    public String getExamName() {
        return examName.get();
    }

    public SimpleStringProperty examNameProperty() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName.set(examName);
    }

    public String getTotalQuestions() {
        return totalQuestions.get();
    }

    public SimpleStringProperty totalQuestionsProperty() {
        return totalQuestions;
    }

    public void setTotalQuestions(String totalQuestions) {
        this.totalQuestions.set(totalQuestions);
    }
}
