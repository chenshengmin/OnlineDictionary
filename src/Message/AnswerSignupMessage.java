package Message;

import java.io.Serializable;


public class AnswerSignupMessage implements Serializable{
	private boolean nameExists=true;
	
	public AnswerSignupMessage(boolean nameExists){
		this.nameExists=nameExists;
	}
	
	public boolean getNameExists(){
		return nameExists;
	}
}
