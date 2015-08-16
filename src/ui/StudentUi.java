package ui;

import business.StudentBusiness;
import entities.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentUi {

	Scanner reader = new Scanner(System.in);
	StudentBusiness sb;
	LoginUi log;
	String token;
	long studentID;
	int enroll_result;
	private HashMap<Integer, String> enrolled_courses = new HashMap<Integer, String>();
	private HashMap<Exercise, ArrayList<Attempts>> homeworkAttemptInfo = new HashMap<Exercise, ArrayList<Attempts>>();

	public long getStudentID() {
		return studentID;
	}

	public void setStudentID(long studentID) {
		this.studentID = studentID;
	}

	public StudentUi() {
		sb = new StudentBusiness();
		log = new LoginUi();
	}

	public void studentDisplay() {

		int level;
		System.out.println("\n1. Select Course\n2.Add Course\n3.Back");
		level = reader.nextInt();
		switch (level) {
		case 1:
			// function to display all courses and return string array of course
			// tokens
			// count courses and display next screen
			showEnrolledCourses(studentID);
			break;

		case 2: // function to Add course
			System.out.println("\nEnter course token:\n");
			token = reader.next();
			enroll_result = sb.addToEnroll(studentID, token); // returns a
																// string with
																// the result
			// display different comments if there are different integers
			// returned
			switch (enroll_result) {
			case 0:
				System.out.println("Course full, Cannot Register\n");
				studentDisplay();
				break;

			case 1:
				System.out.println("Course over, Cannot Register\n");
				studentDisplay();
				break;

			case 2:
				System.out.println("Invalid ID\n");
				studentDisplay();
				break;

			case 3:
				System.out.println("Enrolled in: " + token);
				showEnrolledCourses(studentID);
				break;

			}
			break;
		case 3: // back //TODO: check if this logs out or not
			log.displayLoginOptions();
			break;
		}
	}

	public void courseSelectionOptions() {
		System.out
				.println("\nCourse options:\n 1. View Scores\n 2. Attempt Homework\n3. View Past Submission\n4.View Notifications\n5. Back\n");
		int level = reader.nextInt();
		int i;
		switch (level) {
		case 1:
			showScores(studentID, token);
			break;
		case 2:

			HashMap<Exercise, Attempts> hashMap = new HashMap<Exercise, Attempts>();
            HashMap<Exercise, Integer> hashMap1 = new HashMap<Exercise, Integer>();
			hashMap = sb.getCurrentHomeworkInfo(studentID, token);
			//showOpenHomeworksAttempts(studentID, hashMap, token);

            hashMap1 = sb.getCurrentHomeworkInfo1(studentID, token);
            showOpenHomeworksAttempts(studentID, hashMap1, token);
			break;
		case 3:
			viewPastSubmission();
			break;
		case 4:
			viewNotifications();
			break;
		case 5: // GO BACK
			break;
		default:
			System.out.println("Invalid option!\n");
			break;
		}
	}

	public void showEnrolledCourses(long studentID) {
		enrolled_courses = sb.selectEnrolledCourses(studentID);// returns a
																// string array
																// of the list
																// of enrolled
																// courses
		System.out.println("\nSelect Course:\n");
		int i;
		for (Map.Entry<Integer, String> kv : enrolled_courses.entrySet())
		{
			System.out.println(kv.getKey() + ". " + kv.getValue() + "\n");
		}
		System.out.println((enrolled_courses.size() + 1) + ". " + "Back");
		// Another switch case now
		int selection = reader.nextInt();
		if (enrolled_courses.containsKey(selection)) {
			token = enrolled_courses.get(selection);
			courseSelectionOptions();
		} else if (selection == enrolled_courses.size() + 1) {
			studentDisplay();
		}
	}

	public void showOpenHomeworksAttempts(long studentID,
			HashMap<Exercise, Integer> hash, String course_token) {
		int size = hash.size();
		// For saving and passing on a particular exercise and attempts
		// attribute
		Exercise[] exer = new Exercise[20];
        int[] count_attempts=new int[20];
		int i = 0, k = 0;

		System.out.println("\nAttempt Homework:\n");

		for (Map.Entry<Exercise, Integer> kv : hash.entrySet()) {
			exer[k] = kv.getKey();
			count_attempts[k]= kv.getValue();
			System.out.println(i + 1 + ". " + exer[k].getExerciseName());
			if (exer[k].getIsUnlimited() == 1) {
				System.out.println(" (Unlimited)\n");
			} else if (exer[k].getNoOfRetries() >= count_attempts[k]) {
				int attempts_left = exer[k].getNoOfRetries()
						- count_attempts[k];
				System.out.println(" (" + attempts_left + ")\n");
			}

			i++;
			k++;
		}
		System.out.println(i + 1 + ". Back\n");

		int selection = reader.nextInt();

		// Pass the chosen homework and attempts objects to the next function

		if (selection <= k && selection > 0) {
			if (exer[selection - 1].getNoOfRetries()
					- count_attempts[selection - 1] != 0) {
				attemptHomework(exer[selection - 1]);
			} else {
				System.out.println("No more attempts!\n");
				courseSelectionOptions();
			}
		} else if (selection == k + 1) {
			// GO back
			studentDisplay();
		}
	}

	/*
	 * This method allows a student to attempt the homework Records the options
	 * that he selects for every attempt for every question Reports a score for
	 * the particular attempt he made for an exercise
	 */

	public void attemptHomework(Exercise e) {
		// When a user starts with a new attempt, a new record in the Attempts
		// table has to be created
		int attid = sb.createNewAttempt(e, studentID);
		if (attid == 0) {
			System.out.println("Error  Occured...Please Try again");
			// showOpenHomeworksAttempts(studentID, hash);
			studentDisplay();
		}

		Attempts at = new Attempts();
		// FetchQuestions
		ArrayList<Questions> questions = new ArrayList<Questions>();
		questions = sb.fetchExerciseQuestions(e);
		ArrayList<Questions> qsResult = new ArrayList<Questions>();
		// Display the Question Along with Parameters and answers
		Iterator<Questions> qsIterator = questions.iterator();
		int i = 1;
		while (qsIterator.hasNext()) {
			Questions qs = qsIterator.next();
			System.out.println(i + "." + qs.getQuestionText());
			// Display Parameters
			if (qs.getIsParameterized() == 1) {
				Iterator<Parameters> paramIterator = qs.getParams().iterator();
				System.out.println("Parmater/Value");
				while (paramIterator.hasNext()) {
					Parameters p = paramIterator.next();
					System.out.println(p.getpName() + " / " + p.getValue());
				}
			}
			Iterator<Answers> ansIterator = qs.getAns().iterator();
			int j = 1;
			while (ansIterator.hasNext()) {
				Answers ans = ansIterator.next();
				System.out.println(j + " " + ans.getAnswerText());
				j++;
			}
			System.out.println("Enter your answer option");
			int choice = reader.nextInt();
			
			ArrayList<Answers> ansList = qs.getAns();
			Iterator<Answers> ansListiter = ansList.iterator();
			ArrayList<Answers> updatedAnsList = new ArrayList<Answers>();
			Answers ans = ansList.get(choice-1);
			while(ansListiter.hasNext()){
				Answers ans1 = ansListiter.next();
				if(ans1==ans){
					ans1.setIsChosen(1);
					updatedAnsList.add(ans1);
				}
				else{
					updatedAnsList.add(ans1);
				}
			}	
			System.out.println("Print"+ansList.get(choice-1));	
			qs.setAns(updatedAnsList);
			qsResult.add(qs);
			i++;
		}

		System.out.println("Homework Completed");
    	sb.updateAttemptsHistory(attid,questions);
    	float totalScore=calculateScore(e,qsResult,attid);
        displayReport(totalScore, qsResult);

	}

    public void viewPastSubmission() {
        // Display Homeworks past due date
        HashMap<Exercise, ArrayList<Attempts>> hwPastDate = new HashMap<Exercise, ArrayList<Attempts>>();
        HashMap<Exercise, ArrayList<Attempts>> hwWithinDate = new HashMap<Exercise, ArrayList<Attempts>>();
        hwPastDate = sb.getHWPastDate(studentID, token);
        int i = 1;
        ArrayList<Exercise> exList = new ArrayList<Exercise>();
        ArrayList<Attempts> attList = new ArrayList<Attempts>();
        System.out.println("Homeworks Past Due Date:");
        for (Map.Entry<Exercise, ArrayList<Attempts>> entry : hwPastDate.entrySet()) {
            Exercise ex = entry.getKey();
            ArrayList<Attempts> at = entry.getValue();
            Iterator<Attempts> atiter = at.iterator();
            while(atiter.hasNext()){
                Attempts a = atiter.next();
                System.out.println(i + ". " + ex.getExerciseName() + ": "
                        + "Attempt " + a.getAttemptNumber());

                exList.add(ex);
                attList.add(a);
                i++;
            }


        }
        // List homeworks within due date
        hwWithinDate = sb.getHWWithinDate(studentID, token);
        System.out.println("Homeworks Within Due Date:");

        for (Map.Entry<Exercise,ArrayList< Attempts>> entry : hwWithinDate.entrySet()) {
            Exercise ex = entry.getKey();
            ArrayList<Attempts> at = entry.getValue();
            Iterator<Attempts> atiter = at.iterator();
            while(atiter.hasNext()){
                Attempts a = atiter.next();
                System.out.println(i + ". " + ex.getExerciseName() + ": "
                        + "Attempt " + a.getAttemptNumber());

                exList.add(ex);
                attList.add(a);
                i++;
            }

        }
        System.out.println(exList.size()+1+". Back ");
        System.out.println("Select the Homework that you want to View");
        int choice = reader.nextInt();
        if (!(choice >= exList.size() && choice >= attList.size())) {
            Exercise ex1 = exList.get(choice-1);
            Attempts at1= attList.get(choice-1);
            // To do: Method call to View each submission
            viewPastHomeworkAttempts(ex1,at1);
        } else if(choice==exList.size()) {
            //TODO: function call to course
            courseSelectionOptions();
        }
        else{
            System.out.println("Invalid Option.. Please try again");
            viewPastSubmission();
        }

    }

        public void displayReport(float totalScore,ArrayList<Questions> questions)
        {
         System.out.println("Your total score for this attempt is: "+ totalScore +"\n");
            Iterator<Questions> iterator=questions.iterator();
            int i=1,j;
            int flag=0;
            int answerChosen = 0;
            int groupID;
            String incorrectAnsExplanation = null;
            while (iterator.hasNext())
            {
                Questions qs=new Questions();
                qs=iterator.next();
                if (qs.getIsParameterized()!=1)
                {
                System.out.println(i+"."+qs.getQuestionText()+"\n");
                }
                else
                {
                System.out.println(i+"."+qs.getQuestionText()+"\n");
                Iterator<Parameters> paramList=qs.getParams().iterator();

                    while (paramList.hasNext())
                    {
                        Parameters p=new Parameters();
                        p=paramList.next();
                        System.out.println(p.getpName()+ " : "+ p.getValue()+"\n");
                    }
                }
                Iterator<Answers> ansIt=qs.getAns().iterator();
                 i++;
                j=1;
                while ( ansIt.hasNext())
                {
                    Answers ans=new Answers();
                    ans=ansIt.next();
                    System.out.println(j+ ". "+ ans.getAnswerText()+"\n");
                    if (ans.getIsChosen()==1)
                    {
                        if(ans.getValid().equals("C"))
                        {
                        flag=1;
                         answerChosen=j;
                        }
                        else if (ans.getValid().equals("IC"))
                        {
                        flag=0;
                        incorrectAnsExplanation=ans.getExplanation();
                         answerChosen=j;
                        }

                    }
                    j++;
                }
                if (flag==1)
                {
                    System.out.println("Your answer is correct! You chose answer number: "+ answerChosen+"\n");
                }
                else if (flag==0)
                {
                    System.out.println("Your answer is incorrect. You answered with answer number: "+ answerChosen+ "\n");
                    System.out.println("A short explanation is given as follows:\n "+ incorrectAnsExplanation+"\n");
                }
            }
            System.out.println("1. Back");
            int select=reader.nextInt();
            if(select==1)
            {
                courseSelectionOptions();
            }
        }

        public void viewPastHomeworkAttempts(Exercise e,Attempts a)
        {
        ArrayList<Questions> questions=new ArrayList<Questions>();
           questions=sb.fetchPastAttempt(e,a);
            //Get current date
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            java.util.Date date = cal.getTime();
            dateFormat.format(date);
            java.sql.Timestamp todaysDate = new java.sql.Timestamp(date.getTime());

            float totalScore=sb.fetchScoreForAttempt(a);
            if (todaysDate.after(e.getEndDate()))
            {
             displayReportAfterEndDate(totalScore,questions);
            }
            else if (todaysDate.before(e.getEndDate()))
            {
              displayReport(totalScore,questions);
            }


        }


        public void displayReportAfterEndDate(float totalScore,ArrayList<Questions> questions)
        {
            System.out.println("Your total score for this attempt is: "+ totalScore +"\n");
            Iterator<Questions> iterator=questions.iterator();
            int i=1,j;
            int flag=0;
            int answerChosen = 0;
            int groupID;
            String incorrectAnsExplanation = null;
            while (iterator.hasNext())
            {
                Questions qs=new Questions();
                qs=iterator.next();
                System.out.println("\n\n");
                if (qs.getIsParameterized()!=1)
                {
                    System.out.println(i+"."+qs.getQuestionText()+"\n");
                }
                else
                {
                    System.out.println(i+"."+qs.getQuestionText()+"\n");
                    Iterator<Parameters> paramList=qs.getParams().iterator();

                    while (paramList.hasNext())
                    {
                        Parameters p=new Parameters();
                        p=paramList.next();
                        System.out.println(p.getpName()+ " : "+ p.getValue()+"\n");
                    }
                }
                Iterator<Answers> ansIt=qs.getAns().iterator();
                i++;
                j=1;
                while ( ansIt.hasNext())
                {
                    Answers ans=new Answers();
                    ans=ansIt.next();
                    System.out.println(j+ ". "+ ans.getAnswerText()+"\n");
                    if (ans.getIsChosen()==1)
                    {
                        if(ans.getValid().equals("C"))
                        {
                            flag=1;
                            answerChosen=j;
                        }
                        else if (ans.getValid().equals("IC"))
                        {
                            flag=0;
                            incorrectAnsExplanation=ans.getExplanation();
                            answerChosen=j;
                        }

                    }
                    j++;
                }
                if (flag==1)
                {
                    System.out.println("Your answer is correct! You chose answer number: "+ answerChosen+"\n");
                }
                else if (flag==0)
                {
                    System.out.println("Your answer is incorrect. You answered with answer number: "+ answerChosen+ "\n");
                }
                System.out.println("Explanation:\n "+ qs.getAnsExplanation());
            }

        }
        
  	  public void viewNotifications(){
		   HashMap<Integer,String> notifications = new HashMap<Integer, String>();
		   notifications = sb.fetchNotifications();
		   if(!(notifications.isEmpty())){
			   System.out.println("No Notifications at this time");
		   }
	   }
  	  
  	public void showScores(long student_id, String course_token)
    {
        int i=1;
        homeworkAttemptInfo=sb.getHomeworkAttemptInfo(student_id, course_token);
        HashMap<Integer,Float> hash=null;
        System.out.println("Coming here?");
        for (Map.Entry<Exercise, ArrayList<Attempts>> kv: homeworkAttemptInfo.entrySet())
        {
            ArrayList<Attempts> at=new ArrayList<Attempts>();
            Exercise ex=new Exercise();
            ex=kv.getKey();
            at=kv.getValue();
            Iterator<Attempts> iterator=at.iterator();
            hash=new HashMap<Integer, Float>();
            while (iterator.hasNext())
            {
                Attempts att=new Attempts();
                att=iterator.next();
                System.out.println(i +". "+ ex.getExerciseName()+ "| " + "Attempt "+ att.getAttemptNumber() );
                hash.put(i,att.getScore());
                i++;
            }

        }
        System.out.println(i+ ". Back\n");

        int selection=reader.nextInt();

        if (hash.containsKey(selection))
        {
            System.out.println("The score for this attempt is: "+ hash.get(selection)+ "\n");
            showScores(student_id, course_token);
        }
        else if (selection==hash.size()+1)
        {
            courseSelectionOptions();
        }
        else
        {
            System.out.println("Invalid Option! ");
        }
    }
  	
	public float calculateScore(Exercise e, ArrayList<Questions> qsResult,
			int attId) {
		// Calculate Score
		float totalScore = 0;
		Iterator<Questions> qsList = qsResult.iterator();
		while (qsList.hasNext()) {
			Questions qs = qsList.next();
			Iterator<Answers> ansList = qs.getAns().iterator();
			boolean valid = false;
			while (ansList.hasNext()) {
				Answers ans = ansList.next();
				if (ans.getValid().equals("C") && ans.getIsChosen() == 1) {
					valid = true;
					break;
				}
			}
			if (valid) {

				totalScore = totalScore + e.getPointsCorrect();
			} else {
				totalScore = totalScore - e.getPointsInCorrect();
			}
		}

		// Record it in DB
		sb.saveScore(e, totalScore, attId, studentID);
		return totalScore;
	}




}
