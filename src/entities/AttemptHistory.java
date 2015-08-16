package entities;

/**
 * Created with IntelliJ IDEA.
 * User: Priyanka
 * Date: 10/27/14
 * Time: 3:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class AttemptHistory {
    int questionID;
    int answerID;
    int attemptID;
    int isChosen;

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public int getAttemptID() {
        return attemptID;
    }

    public void setAttemptID(int attemptID) {
        this.attemptID = attemptID;
    }

    public int getChosen() {
        return isChosen;
    }

    public void setChosen(int chosen) {
        isChosen = chosen;
    }
}
