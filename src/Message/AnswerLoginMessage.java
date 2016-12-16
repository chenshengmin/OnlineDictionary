package Message;

import java.io.Serializable;
//回复登录消息
public class AnswerLoginMessage implements Serializable{
	private boolean doseNameExist=false;//用户名是否存在
	private boolean isPasswordRight=false;//密码是否正确
	
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