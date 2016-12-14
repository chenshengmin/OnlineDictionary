package Message;

import java.io.Serializable;

public class AnswerSendWordCardMessage implements Serializable{
	private boolean isClientOnline=true;
	private String receiver;
	
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
