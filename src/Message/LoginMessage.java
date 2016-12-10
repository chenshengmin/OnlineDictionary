package Message;

import java.io.Serializable;

public class LoginMessage implements Serializable{
	private String name;
	private String password;
	
	public LoginMessage(String name,String password){
		this.name=name;
		this.password=password;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
}

class AnswerLoginMessage implements Serializable{
	private boolean isSuccessful;
	
	public AnswerLoginMessage(boolean isSuccessful){
		this.isSuccessful=isSuccessful;
	}
	
	public boolean getIsSuccessful(){
		return isSuccessful;
	}
}