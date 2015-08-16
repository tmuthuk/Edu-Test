/**
 * 
 */
package  dao;

/**
 * @author rituraj
 *
 */
public interface RatingDAO {
	public boolean setHomeworkRating(int uid,int homeworkID,int rating);

	public float getHomeworkRating(int eid);
}
