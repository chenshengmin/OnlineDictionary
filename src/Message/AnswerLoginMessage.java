package Message;

import java.io.Serializable;
//�ظ���¼��Ϣ
public class AnswerLoginMessage implements Serializable{
	private boolean doseNameExist=false;//�û����Ƿ����
	private boolean isPasswordRight=false;//�����Ƿ���ȷ
	
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