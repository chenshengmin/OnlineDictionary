package Message;

import java.io.Serializable;
//��¼��Ϣ
public class LoginMessage implements Serializable{
	private String name;//��¼��
	private String password;//���������
	
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