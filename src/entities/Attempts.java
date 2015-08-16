package entities;

import java.sql.Time;


public class Attempts {

    private int studentId;
    private int exerciseId;
    private int attemptId;
    private int attemptNumber;
    private float score;
    private Time timeOfSubmission;
    private int countOfAttempts;

    public int getCountOfAttempts() {
        return countOfAttempts;
    }

    public void setCountOfAttempts(int countOfAttempts) {
        this.countOfAttempts = countOfAttempts;
    }


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Time getTimeOfSubmission() {
        return timeOfSubmission;
    }

    public void setTimeOfSubmission(Time timeOfSubmission) {
        this.timeOfSubmission = timeOfSubmission;
    }





}


