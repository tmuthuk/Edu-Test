package entities;

import java.util.ArrayList;

public class Questions {
	
	int qid;
	String cid;
	String questionText;
	long isbn;
	String token;
	int topicid;
	int difficultyLevel;
	String hint;
	int isParameterized;
    ArrayList<Answers> ans;
    ArrayList<Parameters> params;
    int groupID;
    String ansExplanation;

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {

        this.groupID = groupID;
    }

   


    public ArrayList<Answers> getAns() {
        return ans;
    }

    public void setAns(ArrayList<Answers> ans) {
        this.ans = ans;
    }

    public ArrayList<Parameters> getParams() {
        return params;
    }

    public void setParams(ArrayList<Parameters> params) {
        this.params = params;
    }
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public long getIsbn() {
		return isbn;
	}
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getTopicid() {
		return topicid;
	}
	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}
	public int getDifficultyLevel() {
		return difficultyLevel;
	}
	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public int getIsParameterized() {
		return isParameterized;
	}
	public void setIsParameterized(int isParameterized) {
		this.isParameterized = isParameterized;
	}

	public String getAnsExplanation() {
		return ansExplanation;
	}

	public void setAnsExplanation(String ansExplanation) {
		this.ansExplanation = ansExplanation;
	}
	
	
	
}
