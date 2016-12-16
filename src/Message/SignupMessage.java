package Message;

import java.io.Serializable;
//注册消息
public class SignupMessage implements Serializable{
	private String name;//注册的用户名
	private String password;//相应的密码
	
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