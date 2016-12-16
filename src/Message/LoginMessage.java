package Message;

import java.io.Serializable;
//登录消息
public class LoginMessage implements Serializable{
	private String name;//登录名
	private String password;//输入的密码
	
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