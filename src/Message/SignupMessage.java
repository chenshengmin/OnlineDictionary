package Message;

import java.io.Serializable;
//ע����Ϣ
public class SignupMessage implements Serializable{
	private String name;//ע����û���
	private String password;//��Ӧ������
	
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