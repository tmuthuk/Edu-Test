package business;

import dao.LoginDAO;
import dao.impl.LoginDAOImpl;

public class LoginBusiness {
	
	LoginDAO dao = null;
	public LoginBusiness(){
		 dao = new LoginDAOImpl();
	}
	
	//Validates username and password
	//Returns user role retrieved from DB
	public long authenticate(String uid, String password,String role)
    {
		return dao.getUserRole(uid, password,role);
	}

    public boolean createNewUser(String uname, String email_id, String password,String role_name, long univ_id)
    {
        // Goes to database and enters info if username is not already taken       
        boolean status = dao.newUser(uname, email_id,password,role_name,univ_id);
        return status;
    }
    public long getID(String uid, String password)
    {    	
    	return dao.getUserID(uid, password);
    }

    public boolean checkUserName(String uname,String roleName){
    	boolean status = dao.checkUserName(uname,roleName);
    	return status;    	
    }
    
  
}
