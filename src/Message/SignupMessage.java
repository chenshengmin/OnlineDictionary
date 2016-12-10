package Message;

import java.io.Serializable;

public class SignupMessage implements Serializable{
	private String name;
	private String password;
	
	public SignupMessage(String name,String password){
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

class AnswerSignupMessage implements Serializable{
	private boolean nameExists;
	
	public AnswerSignupMessage(boolean nameExists){
		this.nameExists=nameExists;
	}
	
	public boolean getNameExists(){
		return nameExists;
	}
}