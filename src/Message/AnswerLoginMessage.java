package Message;

import java.io.Serializable;

public class AnswerLoginMessage implements Serializable{
	private boolean doseNameExist=false;
	private boolean isPasswordRight=false;
	
	public AnswerLoginMessage(boolean doseNameExist,boolean isPasswordRight){
		this.doseNameExist=doseNameExist;
		this.isPasswordRight=isPasswordRight;
	}
	
	public boolean getDoseNameExist(){
		return doseNameExist;
	}
	
	public boolean getIsPasswordRight(){
		return isPasswordRight;
	}
}