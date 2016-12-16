package Message;

import java.io.Serializable;
//回复发送单词卡消息
public class AnswerSendWordCardMessage implements Serializable{
	private boolean isClientOnline=true;//接收者是否在线
	private String receiver;//接收者姓名
	
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
