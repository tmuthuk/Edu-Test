package business;


import dao.RatingDAO;
import dao.impl.RatingDAOImpl;
import dao.impl.TaDAOImpl;


public class RatingBusiness {
public RatingDAO rdata;
	
	public RatingBusiness(){
		rdata = new RatingDAOImpl();
	}
	
	//call this function with userID, homeworkID,rating
	public boolean giveHomeworkRating(int uid,int homeworkID,int rating){
		return rdata.setHomeworkRating(uid,homeworkID,rating);
	}

	public float seeHomeworkRating(int eid) {
		// TODO Auto-generated method stub
		return rdata.getHomeworkRating(eid);
	}


	
}
