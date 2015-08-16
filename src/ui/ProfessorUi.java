package ui;

import business.ProfessorBusiness;
import entities.Exercise;
import entities.Parameters;
import entities.Questions;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Date;

import business.ProfessorBusiness;
import entities.Exercise;
import entities.Questions;
import entities.Parameters;
import entities.Answers;
import ui.LoginUi;

import java.util.*;


import java.util.HashMap;
import java.io.*;

public class ProfessorUi {

	private ProfessorBusiness pb;
	private HashMap<Integer, String> taught_courses = new HashMap<Integer, String>();
	private String courseChoice;
	private Scanner reader = new Scanner(System.in);
	private SimpleDateFormat formatter = new SimpleDateFormat(
			"MMM dd, yyyy HH:mm:ss a");
	Date d;
	private Exercise exercise;
	private long professorID;

	public long getProfessorID() {
		return professorID;
	}

	public void setProfessorID(long professorID) {
		this.professorID = professorID;
	}

	public ProfessorUi() {
		exercise = new Exercise();
		pb = new ProfessorBusiness();
	}

	public void professorDisplay() {
		int level;
		System.out.println("Enter your choice\n");
		System.out.println("\n1. Select Course\n2.Add Course\n3.Back");
		level = reader.nextInt();
		switch (level) {
		case 1:
			selectCourse();
			break;
		case 2: // Add course //Nothing given by document to be done
			break;
		case 3: // back
			LoginUi loginUi = new LoginUi();
			loginUi.displayLoginOptions();
			break;
		}
	}

	public void selectCourse() {
		taught_courses = pb.selectTaughtCourses(professorID);
		// count courses and display next screen
		System.out.println("\nSelect Course:");
		for (Map.Entry<Integer, String> entry : taught_courses.entrySet()) {
			System.out.println(entry.getKey() + "." + entry.getValue());
		}
		System.out.println(taught_courses.size() + 1 + ".Back");
		int choice = reader.nextInt();
		if (choice == taught_courses.size() + 1) {
			professorDisplay();
		} else if (taught_courses.containsKey(choice)) {
			courseChoice = taught_courses.get(choice);
			displayCourseOptions();
		} else {
			System.out.println("Invlaid option .. Try Again");
			selectCourse();
		}
	}

	/**
	 * Display Course Options 1.Add Homework 2.Add/Remove Questions to Homework
	 * 3.Edit Homework 4.View Homework 5.View Notification 6.Reports 7.Back
	 */
	public void displayCourseOptions() {
		int level;
		System.out.println("Enter your choice\n");
		System.out
				.println("\n1.Add Homework \n2.Add/Remove Questions to Homework"
						+ "\n3.Edit Homework\n4.View Homework\n"
						+ "5.View Notification\n6.Reports\n7.Add textbook\n8.Back");
		level = reader.nextInt();
		switch (level) {
		case 1:
			addHomework();
			break;

		case 2:
			// Add/Remove Questions to Homework
			/*
			 * 1.Display the list of homeworks2.Get Homework to edit3.get choice
			 * to add or remove4.
			 */
			addRemoveQuestions();
			break;

		case 3:
			// 3.Edit Homework
			editHomework();
			break;

		case 4:
			// 4.View Homework
			Exercise ex = displayHomeworks();
			viewHomework(ex);
			break;

		case 5:
			// 5.View Notification
			viewNotifications();
			break;

		case 6:
			// 6.Reports
			break;

		case 7:
			// 7.Add Textbook
			
			break;

		case 8:
			//8.Back
			professorDisplay();
			break;
		default:
			System.out.println("Please enter a correct option");
			break;
		}

	}

	public void addHomework() {
		boolean result;
		exercise.setToken(courseChoice);
		setHomeworkName();
		setStartDate();
		setEndDate(exercise);
		setAttempts();
		setTopics();
		setDifficultyRange();
		setScoreSelectionScheme();
		setNumberOfQuestions();
		setCorrectPoints();
		setInCorrectPoints();
		result = pb.addHomework(exercise);
		if (result) {
			System.out.println("Homework" + exercise.getExerciseName()
					+ " successfully created");
		} else {
			System.out.println("Error Occured..Please Try Again");
			addHomework();
		}
	}

	public void setHomeworkName() {
		System.out.println("Select Choice:\n1.To Proceed \n2.Back");
		int choice = reader.nextInt();
		if (choice == 1) {
			System.out.println("Please enter Homework Name");
			String name = reader.next();
			exercise.setExerciseName(name);
		}

		else if (choice == 2) {
			displayCourseOptions();
		} else {
			System.out.println("Invlid option...Please try again");
			setHomeworkName();
		}
	}

	public void setStartDate() {
		int level;
		Date currentDate = new Date();
		// Start Date
		System.out
				.println("Please enter start date in Jun 7, 2013 12:10:56 PM Format."
						+ "Note: The start date shouldn't be before today's date");
		// String dateInp = reader.nextLine();
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);
		try {

			String dateInp = reader.readLine();
			System.out.println("StartDate" + dateInp);
			Date d = formatter.parse(dateInp);
			System.out.println("StartDate" + d);
			if (d.after(currentDate)) {
				java.sql.Timestamp startDate = new java.sql.Timestamp(
						d.getTime());
				System.out.println("timestamp" + startDate);
				exercise.setStartDate(startDate);
			} else {
				addHomework();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Exercise setEndDate(Exercise ex) {
		System.out.println("Please enter End date in yyyy-MM-DD HH:mm:s Format"
				+ "Note: The End date shouldn't be before start date");
		// String dateInp = reader.nextLine();
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);
		try {
			String dateInp = reader.readLine();
			d = formatter.parse(dateInp);
			java.sql.Timestamp endDate = new java.sql.Timestamp(d.getTime());
			System.out.println("end Date" + endDate);
			// Check if the end date is after start date
			if (endDate.after(ex.getStartDate())) {
				ex.setEndDate(endDate);
			} else {
				setEndDate(ex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ex;
	}

	public void setAttempts() {
		System.out
				.println(" Select Choice\nNumber of Attempts: \n1.Specific number of Attempts \n2.unlimited");
		int level = reader.nextInt();
		switch (level) {
		case 1:
			System.out.println("Enter the Number of Attempts");
			int num = reader.nextInt();
			if (num > 0) {
				exercise.setNoOfRetries(num);
				exercise.setIsUnlimited(0);
			} else {
				setAttempts();
			}
			break;

		case 2:
			exercise.setNoOfRetries(0);
			exercise.setIsUnlimited(1);
			break;
		}
	}

	public void setTopics() {
		System.out.println("\nSelect Topics from the list");
		String[] topics = new String[20];
		HashMap<Integer, String> topicsList = pb.getTopicsList(courseChoice);
		HashMap<Integer, Integer> topicUilist = new HashMap<Integer, Integer>();
		HashMap<Integer, String> chosen = new HashMap<Integer, String>();
		int i = 1;

		for (Map.Entry<Integer, String> entry : topicsList.entrySet()) {
			topicUilist.put(i, entry.getKey());
			System.out.println(i + "." + entry.getValue());
			i++;
		}

		System.out.println("Enter total number of topics in exercise");
		int tot = reader.nextInt();
		System.out.println("\nSelect Topics from the list");

		for (int num = 0; num < tot; num++) {
			int c = reader.nextInt();
			if (topicUilist.containsKey(c)) {
				chosen.put(topicUilist.get(c),
						topicsList.get(topicUilist.get(c)));
			}
		}

		
		exercise.setTopicId(chosen.keySet());
	}

	public void setDifficultyRange() {
		System.out.println("Set Difficulty Range");
		System.out.println("Enter Minimum Difficulty Level 1-6 scale");
		Scanner reader1 = new Scanner(System.in);
		String min1 = reader1.next();
		int min = Integer.parseInt(min1);
		if (min > 0 && min < 7) {
			exercise.setMinDifficulty(min);
		} else {
			setDifficultyRange();
		}
		System.out.println("\nEnter Maximum Difficulty Level 1-6");
		int max = reader.nextInt();
		if (max >= min && max < 7) {
			exercise.setMaxDifficulty(max);
		} else {
			setDifficultyRange();
		}
	}

	public void setScoreSelectionScheme() {
		System.out
				.println("\nScore Selection Method\n Select Choice 1.Latest Attempt\n2.Average Score \n3.Maximum Score");
		int level = reader.nextInt();
		switch (level) {
		case 1:
			exercise.setScoreSelectId("Latest Attempt");
			break;

		case 2:
			exercise.setScoreSelectId("Average Score");
			break;

		case 3:
			exercise.setScoreSelectId("Maximum Score");
			break;

		default:
			setScoreSelectionScheme();
			break;
		}
	}

	public void  setNumberOfQuestions() {
		System.out.println("\nEnter Number of Questions");
		int qs = reader.nextInt();
		if (qs > 0) {
			exercise.setNoOfQuestions(qs);
		} else {
			setNumberOfQuestions();
		}
	}

	public void setCorrectPoints() {
		System.out.println("\nEnter Correct Answer Points");
		float points = reader.nextFloat();
		if (points > 0.0) {
			exercise.setPointsCorrect(points);
		} else {
			setCorrectPoints();
		}
	}

	public void setInCorrectPoints() {
		System.out.println("\nEnter InCorrect Answer Points");
		float points = reader.nextFloat();
		if (points >= 0.0) {
			exercise.setPointsInCorrect(points);
		} else {
			setCorrectPoints();
		}

	}

	public void addRemoveQuestions() {
		HashMap<Integer, Exercise> exlist = new HashMap<Integer, Exercise>();

		exlist = pb.getHomeworks(courseChoice);
		Exercise ex = displayHomeworks();
		if (ex != null) {
			displayHomeworkOptions(ex);
		}
	}

	public void displayHomeworkOptions(Exercise ex) {
		// Exercise ex = exlist.get(choice);
		System.out.println(ex.getExerciseName() + ":");
		System.out
				.println("1.Search and Add Questions \n 2.Remove Questions \n3.Back");
		int option = reader.nextInt();
		switch (option) {
		case 1:
			// Search Questions
			HashMap<Integer, Questions> qsList = new HashMap<Integer, Questions>();
			qsList = searchQuestions(ex);
			addQuestions(ex, qsList);
			break;

		case 2:
			// Remove Questions
			removeQuestions(ex);
			break;

		case 3:
			addRemoveQuestions();
			break;
		default:
			System.out.println("Please enter correct choice");
			displayHomeworkOptions(ex);
		}

	}

	public HashMap<Integer, Questions> searchQuestions(Exercise ex) {
		HashMap<Integer, Questions> qsList = new HashMap<Integer, Questions>();
		HashMap<Integer, Parameters[]> params = new HashMap<Integer, Parameters[]>();
		qsList = pb.fetchQuestionsList(ex, courseChoice);

		displayQuestions(qsList);
		return qsList;
	}

	public void addQuestions(Exercise ex, HashMap<Integer, Questions> qsList) {

		ArrayList<Integer> qsSelectedlist = new ArrayList<Integer>();
		qsSelectedlist = getQidInput(qsList);
		boolean status = pb.addQuestions(ex, qsSelectedlist);
		if (status) {
			System.out
					.println("Questions have been successfully added to Exercise "
							+ ex.getExerciseName());
			displayHomeworkOptions(ex);
		} else {
			System.out.println("Error Occured...Please try again !!");
			displayHomeworkOptions(ex);
		}
	}

	public void removeQuestions(Exercise ex) {
		// First fetch the QSList from the Add/Removes table
		HashMap<Integer, Questions> qsList = new HashMap<Integer, Questions>();
		ArrayList<Integer> qsSelectedlist = new ArrayList<Integer>();
		qsList = pb.fetchQuestions(ex);
		displayQuestions(qsList);
		qsSelectedlist = getQidInput(qsList);
		boolean status = pb.removeQuestions(ex, qsSelectedlist);
		if (status) {
			System.out
					.println("Questions have been successfully Removed from Exercise "
							+ ex.getExerciseName());
		} else {
			System.out.println("Error Occured...Please try again !!");
			displayHomeworkOptions(ex);
		}
	}

	public ArrayList<Integer> getQidInput(HashMap<Integer, Questions> qsList) {
		ArrayList<Integer> qsSelectedlist = new ArrayList<Integer>();
		System.out
				.println("Enter total number of Questions that you are going to select");
		int tot = reader.nextInt();
		System.out.println("Select the question numbers");

		for (int num = 0; num < tot; num++) {
			int c = reader.nextInt();
			if (qsList.containsKey(c)) {
				Questions qs = qsList.get(c);
				qsSelectedlist.add(qs.getQid());
			}
			// Go back option
			else if (c == qsList.size() + 1) {
				addRemoveQuestions();
				break;
			}
		}

		return qsSelectedlist;
	}

	public void displayQuestions(HashMap<Integer, Questions> qsList) {
		HashMap<Integer, ArrayList<Parameters>> params = new HashMap<Integer, ArrayList<Parameters>>();
		System.out.println("Question List");
		if (!(qsList.isEmpty())) {
			for (Map.Entry<Integer, Questions> entry : qsList.entrySet()) {
				Questions qs = new Questions();
				qs = entry.getValue();
				// TODO:Display topic name for the question

				// Display Question
				System.out.println(entry.getKey() + "." + qs.getQuestionText());
				if (qs.getIsParameterized() == 1) {
					params = pb.fetchParams(qs);
					if (!(params.isEmpty())) {
						for (Map.Entry<Integer, ArrayList<Parameters>> pentry : params
								.entrySet()) {
							System.out.println("Parameter Group "
									+ pentry.getKey());
							System.out.println("Parameter/Value");
							ArrayList<Parameters> pList = (ArrayList<Parameters>) pentry
									.getValue();
							for (int i = 0; i < pList.size(); i++) {
								Parameters p = pList.get(i);
								System.out.println(p.getpName() + "/"
										+ p.getValue());
							}
						// Fetch the answers for Parameterized questions
						ArrayList<Answers> ans = new ArrayList<Answers>();
						String query="SELECT aid,answer_text,valid,explanation from answers where qid="+qs.getQid()+" and groupid="+pentry.getKey();
						ans = pb.fetchAnswers(query);
						displayAnswerList(ans);
						}
					}
				}
				else if(qs.getIsParameterized() == 0){
					ArrayList<Answers> ans = new ArrayList<Answers>();
					String query="SELECT aid,answer_text,valid,explanation from answers where qid="+qs.getQid();
					ans = pb.fetchAnswers(query);
					displayAnswerList(ans);
				}
				// TODO:Display Hint for the Question
				System.out.println("Hint:" + qs.getHint());
				// TODO:Display Difficulty LEvel
				System.out
						.println("Difficulty Level" + qs.getDifficultyLevel());
				// TODO:Display Detailed Explanation
			}
		} else {
			System.out.println("No Questions Available !!");
		}

		System.out.println(qsList.size() + 1 + ".Back");
	}

	public Exercise displayHomeworks() {
		HashMap<Integer, Exercise> exlist = new HashMap<Integer, Exercise>();
		Exercise ex = null;
		exlist = pb.getHomeworks(courseChoice);
		if (!(exlist.isEmpty())) {
			System.out.println("List of Homeworks for the Course"
					+ courseChoice);

			for (Map.Entry<Integer, Exercise> entry : exlist.entrySet()) {
				Exercise e = entry.getValue();
				System.out.println(entry.getKey() + "." + e.getExerciseName());
			}
			System.out.println(exlist.size() + 1 + ". Back");
			System.out.println("Select a Homework to Add/Remove Questions");
			int choice = reader.nextInt();
			if (choice == exlist.size() + 1) {
				displayCourseOptions();
			} else if (exlist.containsKey(choice)) {
				ex = exlist.get(choice);
			} else {
				System.out.println("Enter Correct Option");
				displayHomeworks();
			}
		}

		else {
			System.out.println("No Homeworks Available for the Course:"
					+ courseChoice);
			displayCourseOptions();
		}
		return ex;
	}

	public void editHomework() {
		Exercise ex = displayHomeworks();
		if (ex != null) {
			System.out
					.println("Choose what to update: \n1.Start date \n2.End Date \n3.Number of attempts \n4.Topics "
							+ "\n5.Difficulty Level \n6.Score Selection \n7.Number of Questions \n8.Correct Answer Points "
							+ "\n9.Incorrect Answer points \n10.Back");
			int choice = reader.nextInt();
			switch (choice) {
			case 1:
				// Update Start date
				break;
			case 2:
				// update end date
				updateEndDate(ex);
				break;
			case 3:
				// Number of attempts
				break;
			case 4:
				// Topics
				break;
			case 5:
				// Difficulty level
				break;
			case 6:
				// score selection
				break;
			case 7:
				// Number of questions
				updateNoOfQuestions(ex);
				break;
			case 8:
				// Correct answer points
				break;
			case 9:
				// Incorrect answer points
				break;
			case 10:
				// back
				displayCourseOptions();
				break;
			default:
				break;
			}
		}
	}

	public void updateEndDate(Exercise ex) {
		ex = setEndDate(ex);
		boolean status = pb.updateEndDate(ex);
		if (status) {
			System.out.println("End Date updated Successfully");
			editHomework();
		} else {
			System.out.println("Error Occured...Please try again !!");

		}

	}
	
	public void updateNoOfQuestions(Exercise ex){
		System.out.println("\nEnter Number of Questions");
		int qs = reader.nextInt();
		if (qs > 0) {
			ex.setNoOfQuestions(qs);
		} else {
			setNumberOfQuestions();
		}
		boolean status = pb.updateNoOfQuestions(ex);
		if (status) {
			System.out.println("No Of Questions updated Successfully");
			editHomework();
		} else {
			System.out.println("Error Occured...Please try again !!");

		}
	}

	public void viewHomework(Exercise ex) {
		HashMap<Integer, Questions> qsList = new HashMap<Integer, Questions>();
		System.out.println("Homework Details:");
		System.out.println("Exercise Name: " + ex.getExerciseName());
		System.out.println("Start Date: " + ex.getStartDate());
		System.out.println("End Date: " + ex.getEndDate());
		System.out.println("Points Correct: " + ex.getPointsCorrect());
		System.out.println("Points Incorrect: " + ex.getPointsInCorrect());
		System.out.println("Number of Questions: " + ex.getNoOfQuestions());
		if (ex.getIsUnlimited() == 1) {
			System.out.println("Number of Retries: Unlimited");
		} else {
			System.out.println("Number of Retries:" + ex.getNoOfRetries());
		}

		qsList = pb.fetchQuestions(ex);
		displayQuestions(qsList);
		int choice = reader.nextInt();
		if (choice == qsList.size() + 1) {
			displayCourseOptions();
		}
	}

	public void viewNotifications() {
		HashMap<Integer, String> notifications = new HashMap<Integer, String>();
		notifications = pb.fetchNotifications(professorID);
		if (!(notifications.isEmpty())) {
			System.out.println("Notifications");
			for(Map.Entry<Integer, String> entry : notifications.entrySet()){
			System.out.println(entry.getKey()+". "+entry.getValue());
			displayCourseOptions();
			}
		}else{
			System.out.println("No notifications at this time !!");
			displayCourseOptions();
		}
	}


public void displayAnswerList(ArrayList<Answers> ans){
	
	Iterator<Answers> ansIter = ans.iterator();
	int i = 1;
	// Display Answers
	while (ansIter.hasNext()) {
		Answers ans1 = ansIter.next();
		String valid = "";
		String explanation = "";
		if (ans1.getValid().equals("C")) {
			valid = "Correct Answer";
		} else if (ans1.getValid().equals("IC")) {
			valid = "Incorrect ";
			explanation = ans1.getExplanation();
		}
		System.out.println("Ans" + i + ": " + valid
				+ ": " + ans1.getAnswerText());
		System.out.println("Short Explanation:"
				+ explanation);
		i++;
	}
}
}
