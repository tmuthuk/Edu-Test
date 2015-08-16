package entities;

import java.sql.Timestamp;
import java.util.Set;

public class Exercise {

   private int exerciseId;
   private String exerciseName;
   private String token;
   private Timestamp startDate;
   private Timestamp endDate;
   private int isUnlimited;
   private int NoOfRetries;
   private float pointsCorrect;
   private float pointsInCorrect;
   private int minDifficulty;
   private int maxDifficulty;
   private String scoreSelectId;
   private int noOfQuestions;
   private Set<Integer> topicId;




   public int getExerciseId() {
	return exerciseId;
   }
   
   public void setExerciseId(int exerciseId) {
	this.exerciseId = exerciseId;
   }

   public String getExerciseName() {
	return exerciseName;
   }

   public void setExerciseName(String exerciseName) {
	this.exerciseName = exerciseName;
   }

   public String getToken() {
	return token;
   }

   public void setToken(String token) {
	this.token = token;
   }


   public Timestamp getStartDate() {
	return startDate;
   }

   public void setStartDate(Timestamp startDate) {
	this.startDate = startDate;
   }

   public Timestamp getEndDate() {
	return endDate;
   }

   public void setEndDate(Timestamp endDate) {
	this.endDate = endDate;
   }  		


   public int getIsUnlimited() {
	return isUnlimited;
   }

   public void setIsUnlimited(int isUnlimited) {
	this.isUnlimited = isUnlimited;
   }

   public int getNoOfRetries() {
	return NoOfRetries;
   }

   public void setNoOfRetries(int noOfRetries) {
	NoOfRetries = noOfRetries;
   }

   public float getPointsCorrect() {
	return pointsCorrect;
   }

   public void setPointsCorrect(float pointsCorrect) {
	this.pointsCorrect = pointsCorrect;
   }

   public float getPointsInCorrect() {
	return pointsInCorrect;
   }

   public void setPointsInCorrect(float pointsInCorrect) {
	this.pointsInCorrect = pointsInCorrect;
   }

   public int getMinDifficulty() {
	return minDifficulty;
   }

   public void setMinDifficulty(int minDifficulty) {
	this.minDifficulty = minDifficulty;
   }

   public int getMaxDifficulty() {
	return maxDifficulty;
   }

   public void setMaxDifficulty(int maxDifficulty) {
	this.maxDifficulty = maxDifficulty;
   }

   public String getScoreSelectId() {
	return scoreSelectId;
   }

   public void setScoreSelectId(String scoreSelectId) {
	this.scoreSelectId = scoreSelectId;
   }

   public int getNoOfQuestions() {
	return noOfQuestions;
   }

   public void setNoOfQuestions(int noOfQuestions) {
	this.noOfQuestions = noOfQuestions;
   }

   public Set<Integer> getTopicId() {
	return topicId;
   }

   public void setTopicId(Set<Integer> topicId) {
	this.topicId = topicId;
   }

}
