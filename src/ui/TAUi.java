package ui;


import business.AddTextbookWithTopics;
import business.ProfessorBusiness;
import business.RatingBusiness;

import business.TaBusiness;
import entities.Exercise;
import entities.Questions;
import entities.classToken;

import java.util.*;

public class TAUi {
	/*Used to take input from user.*/
	private Scanner reader=new Scanner(System.in);
	//just for testing purposes please remove
	//public RatingBusiness rb = new RatingBusiness();
	public AddTextbookWithTopics Ab = new AddTextbookWithTopics();
	public TaBusiness tb=new TaBusiness();
	int iterator=0;
	public boolean debug = true;
	public void displayTaUi(long userID) {
		// TODO Auto-generated method stub
		/*
		 * 	Used to display TA options
		 * 
		 * */
		/*
		 * used for testing pusposes.*
		 */
		/* boolean x= rb.giveHomeworkRating(200003, 2, 5);
		 if(x){
			 System.out.println("Working");
			 float rat = rb.seeHomeworkRating(2);
			 System.out.println("RAting"+rat);
		 }
			*/
		
		/*
		 * 
		 * 
		 * */
		
		/**/
		/*This is used for deletion.*/
		/*
		HashMap hm;
		ArrayList al = new ArrayList();
		System.out.println("Enter Textbook Name");
		String str = reader.nextLine();
		hm = Ab.getTextbooks(str);
		if(hm.isEmpty()){
			System.out.println("No Textbook Exisits");
		}else{
			/*now here, we have a list of all the textbook with that name//
			
			 Set set = hm.entrySet();
		      // Get an iterator
		      Iterator i = set.iterator();
		      int iter=0;
		      // Display elements
		      while(i.hasNext()) {
		         Map.Entry me = (Map.Entry)i.next();
		         
		         System.out.println((iter+1)+". "+me.getValue());
		        
		         al.add(me.getKey());
		         iter++;
		      }
		      
		      System.out.println("Enter the  cid: and textbook  you would like to allocate to the course");
		      String cid = reader.nextLine();
		      System.out.println("Enter the  textbook  :");
		      int userChoice = reader.nextInt()-1;
		      
		      int isbn = (int) al.get(userChoice);
		      
		      System.out.println("The isbn is: "+isbn);
		      
		     boolean result =  Ab.deleteTextBook(isbn, cid);
			if(result==true){
				System.out.println("Course is Deleted");
			}else{
				System.out.println("Course is not added");
			}
		
		}
		*/
		/*Please remove after testing
		HashMap hm;
		ArrayList al = new ArrayList();
		System.out.println("Enter Textbook Name");
		String str = reader.nextLine();
		//hm = Ab.getTextbooks(str);
		if(hm.isEmpty()){
			System.out.println("No Textbook Exisits");
		}else{
			/*now here, we have a list of all the textbook with that name
			
			 Set set = hm.entrySet();
		      // Get an iterator
		      Iterator i = set.iterator();
		      int iter=0;
		      // Display elements
		      while(i.hasNext()) {
		         Map.Entry me = (Map.Entry)i.next();
		         
		         System.out.println((iter+1)+". "+me.getValue());
		        
		         al.add(me.getKey());
		         iter++;
		      }
		      
		      System.out.println("Enter the  cid: and textbook  you would like to allocate to the course");
		      String cid = reader.nextLine();
		      System.out.println("Enter the  textbook  :");
		      int userChoice = reader.nextInt()-1;
		      
		      int isbn = (int) al.get(userChoice);
		      
		      System.out.println("The isbn is: "+isbn);
		      
		     boolean result =  Ab.addTextBook(isbn, cid);
			if(result==true){
				System.out.println("Course is Added");
			}else{
				System.out.println("Course is not added");
			}
			//shows topics in the course
			showTopicsInCourse(isbn,cid);
			//shows and asks user to enter the course To Add
			AddTopicsToCourse(isbn,cid);
		}
		
		
		*/
		
		
		ArrayList arrayList;
		String courseToken;
		int courseTokenSelected;
		int courseSelected;
		System.out.println("------->Enter your choice\n");
        System.out.println("\n1. Select Course\n2.Back");
        courseSelected = reader.nextInt();
        switch(courseSelected){
        case 1: /*Call and populate the data structures.*/
        		getCourses(userID);
        	
        	break;
        case 2: System.exit(0);//logged out, as the last one is exiting
        	break;
        default: System.out.println("Invalid Choice");

        }
	}
	/*private void showTopicsInCourse(int isbn, String cid) {
		// TODO Auto-generated method stub
		//add this too 
		ArrayList<classToken> al;
		classToken ct; 
		int iterator=0;
		System.out.println("Would you like to enter course Topics as well Y/N?");
		String userSelect = reader.next();
		if(userSelect.equals("Y")){
			//Add the course
			//System.out.println("Course will be selected");
			al = Ab.getCourseTopic(cid);
			Array list has all the course Topics current
			int arraySize = al.size();
			System.out.println("-----Current Course Topics-------");
			while(iterator<arraySize){
				System.out.println("=====Course Topic "+iterator+"=====");
				ct = al.get(iterator);
				System.out.println("ISBN: "+ct.getIsbn());
				System.out.println("Chapter No: "+ct.getChapterNum());
				System.out.println("Topic Id: "+ct.getTopicID());
				System.out.println("Topic Name: "+ct.getTopicName());
				iterator++;
				
			}
			System.out.println("=====END======");
			
			
			
		}
		else if(userSelect.equals("N")){
			System.out.println("Ok! Thanks for adding textbook");
		}
		else{
			showTopicsInCourse(isbn, cid);
		}
		
		
	}
	private void AddTopicsToCourse(int isbn, String cid) {
		// TODO Add this method also to
		//System.out.println("------New Textbook Topics------");
		//Show all the course topics and then ask to enter one by one.
		HashMap hm = new HashMap<>();
		ArrayList al = new ArrayList();
	 
		//now the course list is inside the hm
		hm = Ab.fetchAddingTopics(isbn);
		if(hm.isEmpty()){
			System.out.println("No Course Topic Exisits in textbook");
		}else{
			now here, we have a list of all the textbook with that name
			AddSelectedTopics(isbn,cid,hm);
			 
		      
		}
	}
	private void RemoveTopicsFromCourse(int isbn, String cid) {
		// TODO Add this method also to
		//System.out.println("------New Textbook Topics------");
		//Show all the course topics and then ask to enter one by one.
		HashMap hm = new HashMap<>();
		ArrayList al = new ArrayList();
	 
		//now the course list is inside the hm
		hm = Ab.fetchAddingTopics(isbn);
		if(hm.isEmpty()){
			System.out.println("No Course Topic Exisits in textbook");
		}else{
			now here, we have a list of all the textbook with that name
			RemoveSelectedTopics(isbn,cid,hm);
			 
		      
		}
	}
	
	private void RemoveSelectedTopics(int isbn, String cid, HashMap hm) {
		// TODO Selected topics and iterate
		classToken ct = new classToken();
		ArrayList ar = new ArrayList();
		Set set = hm.entrySet();
	      // Get an iterator
	    Iterator i = set.iterator();
	    int iter=0;
	      // Display elements
	    while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	         
	         System.out.println((iter+1)+". "+me.getValue());
	        
	         ar.add(me.getKey());
	         iter++;
	      }
	    System.out.println("---->Enter the course topic to Remove: ");
	    int userChoice = reader.nextInt()-1;
	    int courseTopicAdd = (int) ar.get(userChoice);
	    System.out.println("Topic to Remove is: "+courseTopicAdd);
	    ct.setChapterNum(courseTopicAdd);
	    ct.setCid(cid);
	    ct.setIsbn(isbn);
	    ct.setTopicName((String) hm.get(courseTopicAdd));
	    //delete this entry from hashmap.
	    hm.remove(courseTopicAdd);
	    Ab.RemoveCourseTopics(ct);
	    System.out.println("Course Topic Added");
	    System.out.println("Would you like to enter more? Y/N");
	    String userEnter = reader.next();
	    
	    if(userEnter.equals("Y")){
	    	RemoveSelectedTopics(isbn,cid,hm);
	    }
	    else{
	    	//go back to function
	    }
	    	
	}
	
	
	private void AddSelectedTopics(int isbn, String cid, HashMap hm) {
		// TODO Selected topics and iterate
		classToken ct = new classToken();
		ArrayList ar = new ArrayList();
		Set set = hm.entrySet();
	      // Get an iterator
	    Iterator i = set.iterator();
	    int iter=0;
	      // Display elements
	    while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	         
	         System.out.println((iter+1)+". "+me.getValue());
	        
	         ar.add(me.getKey());
	         iter++;
	      }
	    System.out.println("---->Enter the course topic to Add: ");
	    int userChoice = reader.nextInt()-1;
	    int courseTopicAdd = (int) ar.get(userChoice);
	    System.out.println("Topic to add is: "+courseTopicAdd);
	    ct.setChapterNum(courseTopicAdd);
	    ct.setCid(cid);
	    ct.setIsbn(isbn);
	    ct.setTopicName((String) hm.get(courseTopicAdd));
	    //delete this entry from hashmap.
	    hm.remove(courseTopicAdd);
	    Ab.addCourseTopics(ct);
	    System.out.println("Course Topic Added");
	    System.out.println("Would you like to enter more? Y/N");
	    String userEnter = reader.next();
	    
	    if(userEnter.equals("Y")){
	    	AddSelectedTopics(isbn,cid,hm);
	    }
	    else{
	    	//go back to function
	    }
	    	
	}*/
	private void getCourses(long userID) {
		// TODO Auto-generated method stub
		
		ArrayList arrayList;
		String courseToken;
		int courseTokenSelected;
		arrayList = tb.selectTaCourses(userID);
		
		for(iterator=0;iterator<arrayList.size();iterator++){
			System.out.println((iterator+1)+". "+arrayList.get(iterator));
		}
		/*Once user selects the course. 
		 * We call a function which does next branching
		 * */
		courseTokenSelected = reader.nextInt()-1;
		if(courseTokenSelected<arrayList.size()){
			//Call a function which do the further iterations.
			courseToken = arrayList.get(courseTokenSelected).toString();
			
			
			ShowHomeworks(userID,courseToken);
		}
		else{
			System.out.println("Invalid choice Entered!!!! ");
			System.out.println("Enter 1. Re-enter Choice 2. go Back");
			int secondChoice = reader.nextInt();
			switch(secondChoice){
			case 1: getCourses(userID);
			break;
			case 2: displayTaUi(userID);
			break;
			}
					
		}
	}
	private void ShowHomeworks(long userID,String courseToken) {
		// TODO used for showing the next step of homeworks/reports.
		int choice;
		
    	
    	int iterator=100;
    	
		System.out.println("------->Enter your choice for course \n"+courseToken);
        System.out.println("\n1. View Homeworks \n2.Reports \n 3.back");
        choice = reader.nextInt();
        
		switch(choice){
		
        case 1: /*Call and show homeworks for */
        		/*have a UI function to call*/
        		getHomeworks(userID,courseToken);
        	
        	break;
        case 2: /*used for showing the report*/
        	showReport(userID,courseToken);
        	break;
        case 3: getCourses(userID);
        	break;
        default: System.out.println("Invalid Choice");
        	break;
        }
		
		
	}
	private void showReport(long userID, String courseToken) {
		// TODO used to generate reports
		System.out.println("------>  1. Enter Query \n 2. go Back");
		int choice = reader.nextInt();
		switch(choice){
		case 1: /*call function to input and create the query.*/
				generateQuery(userID,courseToken);
			break;
		case 2: ShowHomeworks(userID, courseToken);
			break;
		default: System.out.println("Invalid choice");
			showReport(userID,courseToken);
			break;
		}
	}
	private void generateQuery(long userID, String courseToken) {
		// TODO take input from user for report generation.
		System.out.println("--------> Enter Query of your choice: ");
		System.out.println("1. Find students who did not take homework\n"
				+ "2. Find students who scored the maximum score on the first attempt for homework \n"
				+ "3. Find students who scored the maximum score on the first attempt for any homework.\n"
				+ "4. For each student, show total score for each homework and average score across all homeworks.\n"
				+ "5. For each homework, show average number of attempts\n"
				+ "6. go Back");
		
		int choice = reader.nextInt();
		switch(choice)
		{
		case 1:firstQuery(userID,courseToken);
			break;
		case 2: secondQuery(userID,courseToken);
			break;
		case 3: thirdQuery(userID,courseToken);
			break;
		case 4:fourthQuery(userID,courseToken);
			
			break;
		case 5: fifthQuery(userID,courseToken);
			break;
		
		case 6:
			/*go back*/
			showReport(userID,courseToken);
			break;
		default:
			System.out.println("Invalid input");
			generateQuery(userID, courseToken);
			break;
		}
		
		
	}
	
	
	
	private void fourthQuery(long userID, String courseToken) {
		// TODO For each student, show total score for each homework and average score across all homeworks.
		
	}
	private void fifthQuery(long userID, String courseToken) {
		// TODO For each homework, show average number of attempts
		
	}
	private void thirdQuery(long userID, String courseToken) {
		// TODO Find students who scored the maximum score on the first attempt for any homework.
		ArrayList ar;
		ar = tb.executeThirdQuery(userID,courseToken);
		if(ar.size()==0){
			//all worked perfectly, go back show students name 
			System.out.println("-------List of Students------");
			for(iterator=0;iterator<ar.size();iterator++){
				System.out.println(ar.get(iterator));
				
			}
			
			generateQuery(userID, courseToken);
		}
		else{
			//homework id entered was wrong or something went wrong.
			firstQuery(userID, courseToken);
		}
	}
	private void secondQuery(long userID, String courseToken) {
		// TODO Find students who scored the maximum score on the first attempt for homework
		System.out.println("-------> Enter the homework/Eid for which students are to be found");
		int homeworkID = reader.nextInt();
		ArrayList ar;
		ar = tb.executeSecondQuery(userID,courseToken,homeworkID);
		if(ar.size()==0){
			//all worked perfectly, go back show students name 
			System.out.println("-------List of Students------");
			for(iterator=0;iterator<ar.size();iterator++){
				System.out.println(ar.get(iterator));
				
			}
			
			generateQuery(userID, courseToken);
		}
		else{
			//homework id entered was wrong or something went wrong.
			firstQuery(userID, courseToken);
		}
		
	}
	
	
	private void firstQuery(long userID, String courseToken) {
		// TODO Find students who did not take homework
		System.out.println("------> Enter the homework/Eid for which students are to be found");
		int homeworkID = reader.nextInt();
		int iterator=0;
		ArrayList ar;
		ar = tb.executeFirstQuery(userID,courseToken,homeworkID);
		if(ar.size()==0){
			//all worked perfectly, go back
			//showing names of students
			System.out.println("-------List of Students------");
			for(iterator=0;iterator<ar.size();iterator++){
				System.out.println(ar.get(iterator));
				
			}
			
			generateQuery(userID, courseToken);
			
		}
		else{
			//homework id entered was wrong or something went wrong.
			firstQuery(userID, courseToken);
		}
	}
	
	
	private void getHomeworks(long userID, String courseToken) {
		// TODO Auto-generated method stub
		HashMap hashMap;
		int homeworkTokenSelected;
		hashMap = tb.getAllHomeworks(courseToken);
		
  	  // Get a set of the entries
      Set set = hashMap.entrySet();
      // Get an iterator
      Iterator i = set.iterator();
      // Display elements
      while(i.hasNext()) {
         Map.Entry me = (Map.Entry)i.next();
         
         System.out.println(me.getKey()+". "+me.getValue());
        
      }
  	System.out.println("* press any number to go back");
  	
  		/*Once user selects the course. 
  		 * We call a function which does next branching
  		 * */
  		homeworkTokenSelected = reader.nextInt();
  		String homeworkVal = (String) hashMap.get(homeworkTokenSelected);
  		if(hashMap.containsKey(homeworkTokenSelected)){
  			//Call a function which do the further iterations.
  			showDetailHomework(courseToken,homeworkTokenSelected,homeworkVal,userID);
  		}
  		else{
  			/*go back. for me this is calling the same function again.*/
  			ShowHomeworks(userID,courseToken);
  		}
	}
	
	
	private void showDetailHomework(String courseToken,
			int homeworkID, String homeworkName,long userID) {
		// TODO Auto-generated method stub
		
		ArrayList questionsArrayList;
		Questions q = null;
		int value;
		System.out.println("-------Details of Homeworks: "+homeworkName+"-------");
		Exercise exReturnVal = tb.getHomeworkDetails(homeworkID);
		
       System.out.println("Excercise Name:"+exReturnVal.getExerciseName());
       System.out.println("Start Date:"+exReturnVal.getStartDate());
       System.out.println("End Date:"+exReturnVal.getEndDate());
       System.out.println("No Of Questions:"+exReturnVal.getNoOfQuestions());
       System.out.println("Points Correct: "+exReturnVal.getPointsCorrect());
       System.out.println("Points Incorrect:"+exReturnVal.getPointsInCorrect());
       /*
        * Getting all the question list associated with this excercise
        * */
       questionsArrayList = tb.getQuestionsOfHomework(homeworkID);
       System.out.println("\n----Questions List----");
       for(int iterator=0;iterator<questionsArrayList.size();iterator++){
    	 
    	   value = (int) questionsArrayList.get(iterator);
    	  q = tb.getQuestion(value);
    	   System.out.println("----Question "+(iterator+1)+"----");
    	   System.out.println("Question Text: "+q.getQuestionText());
    	   System.out.println("Questions Hint: "+q.getHint());

       }
       System.out.println("------> 1.back");
       
       int choice = reader.nextInt();
       switch(choice){
       case 1: /*go back*/
    	   getHomeworks(userID,courseToken);
    	   break;
       default: 
    	   System.out.println("Invalid Choice");
       }
       
	}
	
}
