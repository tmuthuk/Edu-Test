package ui;

import business.LoginBusiness;

import java.util.Scanner;

public class LoginUi {

	LoginBusiness biz;
	StudentUi student;
	ProfessorUi professor;
	TAUi TA;
	String role;
	boolean resultCreateNew;
	Scanner reader = new Scanner(System.in);
	String username;
	String password;
	String roleName = null;
	int roleID;
	long univID,userID;

	public LoginUi() {
		biz = new LoginBusiness();
	}

	// Displays menu options based on menu level
	public void displayLoginOptions() {

		System.out.println("\n1.Login\n2.Create User\n3.Exit");
		int level = reader.nextInt();
		switch (level) {

		case 1:
			login();
			break;
		case 2:
			createUser();
			break;
		case 3:

			System.out.println("Logged Out successfully");
			System.exit(0);

		default:
			System.out
					.println("Invalid Choice! Choose from the options given.\n");
			displayLoginOptions();
		}

	}

	public void login() {
		// Choosing to login
		System.out.println("\nEnter username:");
		username = reader.next();
		System.out.println("\nEnter password:");
		password = reader.next();
		System.out.println("Are you a \n1.Student \n2.Professor \n3.TA");
		int roleChoice = reader.nextInt();
		switch(roleChoice){
		case 1:
			role = "student";
			break;
		case 2:
			role = "professor";
			break;
		case 3:
			role = "ta";
			break;
		default:
			System.out.println("Invalid option..Please try again");
			login();
			break;
		}
		userID = biz.authenticate(username,password,role);
		System.out.println("Roleee" + userID);
		// TODO: use a function to retrieve student/professor/TA ID too
		//userID = biz.getID(username, password);
		
		if(userID!=0){
		
		if (role.equals("student")) {
			System.out.println("Hello " + username);
			student = new StudentUi();
            student.setStudentID(userID);
			// function parameter will send studentID as well
			student.studentDisplay();
		} else if (role.equals("professor")) {
			System.out.println("Hello " + username);
			professor = new ProfessorUi();
			professor.setProfessorID(userID);
			professor.professorDisplay();
		} else if (role.equals("ta")) {	
				System.out.println("Hello " + username);
				TAUi TA = new TAUi();
				//TA.displayTaUi(userID);			
		}
		
		}else {
			System.out.println("\nInvalid Login...Please try again");
			displayLoginOptions();
		}
	}

	public void createUser() {
		boolean status = false;
		System.out.println("\nEnter your username:");
		username = reader.next();
		System.out.println("Enter your role: Are you a\n1. Student\n2.Professor\n3.TA");
		roleID = reader.nextInt();
		switch (roleID) {
		case 1:
			roleName = "student";
			break;
		case 2:
			roleName = "professor";
			break;
		case 3:
			roleName = "ta";
			break;
		default:
			System.out.println("Invalid Option");
			displayLoginOptions();
		}
		
		System.out.println("Enter your University ID:");
		univID = reader.nextLong();
		status = biz.checkUserName(username,roleName);
		System.out.println("Status" + status);
		if (status) {
			System.out.println("Username already exists..Please try again");
			createUser();
		}
		System.out.println("Enter your email-id:");
		String emailID = reader.next();
		System.out.println("Enter your password:");
		password = reader.next();
		

		
		resultCreateNew = biz.createNewUser(username, emailID, password,
				roleName, univID);
		System.out.println("ResultCreateNew" + resultCreateNew);
		if (resultCreateNew) // That is if a new user is created
		{
			System.out
					.println("\nAccount Created ..Login using your username and password!");
		} else {
			System.out.println("\nNew account not created! Please try again");
		}
		displayLoginOptions();
	}

}
