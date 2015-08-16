/**
 * 
 */
package entities;

import java.util.Date;

/**
 * @author rituraj
 *
 */
public class classToken {
	int isbn;
	int chapterNum;
	String cid;
	int topicID;
	String topicName;
	/**
	 * @return the isbn
	 */
	public int getIsbn() {
		return isbn;
	}
	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}
	/**
	 * @return the chapterNum
	 */
	public int getChapterNum() {
		return chapterNum;
	}
	/**
	 * @param chapterNum the chapterNum to set
	 */
	public void setChapterNum(int chapterNum) {
		this.chapterNum = chapterNum;
	}
	/**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}
	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	/**
	 * @return the topicID
	 */
	public int getTopicID() {
		return topicID;
	}
	/**
	 * @param topicID the topicID to set
	 */
	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}
	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}
	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
}
