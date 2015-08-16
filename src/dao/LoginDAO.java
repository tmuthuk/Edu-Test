package dao;

public interface LoginDAO {
	
	public long getUserRole(String uid, String password,String role);
    public boolean newUser(String uname, String email_id,String password,String role_name,long univ_id);
    public long getUserID(String uid, String password);
    public boolean checkUserName(String uname,String roleName);
   
}
