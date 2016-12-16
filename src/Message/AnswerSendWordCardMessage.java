package Message;

import java.io.Serializable;
//�ظ����͵��ʿ���Ϣ
public class AnswerSendWordCardMessage implements Serializable{
	private boolean isClientOnline=true;//�������Ƿ�����
	private String receiver;//����������
	
	public AnswerSendWordCardMessage(boolean isClientOnline,String receiver){
		this.isClientOnline=isClientOnline;
		this.receiver=receiver;
	}
	
	public boolean getIsClientOnline(){
		return isClientOnline;
	}
	
	public String getReceiver(){
		return receiver;
	}
}
