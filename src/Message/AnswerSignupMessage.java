package Message;

import java.io.Serializable;

//回复注册消息
public class AnswerSignupMessage implements Serializable{
	private boolean nameExists=true;//名字是否存在
	
	public AnswerSignupMessage(boolean nameExists){
		this.nameExists=nameExists;
	}
	
	public boolean getNameExists(){
		return nameExists;
	}
}
