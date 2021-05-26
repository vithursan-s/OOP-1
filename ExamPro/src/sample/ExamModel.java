package sample;

public class ExamModel {
    private int examId;
    private String examName;
    private int questionAmount;

    public ExamModel( int examId, String examName, int questionAmount) {
        this.examId = examId;
        this.examName = examName;
        this.questionAmount = questionAmount;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }
}
