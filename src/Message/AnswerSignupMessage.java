package Message;

import java.io.Serializable;

//�ظ�ע����Ϣ
public class AnswerSignupMessage implements Serializable{
	private boolean nameExists=true;//�����Ƿ����
	
	public AnswerSignupMessage(boolean nameExists){
		this.nameExists=nameExists;
	}
	
	public boolean getNameExists(){
		return nameExists;
	}
}
