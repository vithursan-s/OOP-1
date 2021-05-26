package sample;

public class Answer {
    private int exam_id;
    private int student_id;
    private int question_id;
    private int marks;

    public Answer(int exam_id, int student_id, int question_id, int marks) {
        this.exam_id = exam_id;
        this.student_id = student_id;
        this.question_id = question_id;
        this.marks = marks;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
