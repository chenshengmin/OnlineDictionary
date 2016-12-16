package Message;

import java.io.Serializable;
import java.util.ArrayList;
//回复查看其他用户消息
public class AnswerCheckClientsMessage implements Serializable{
	private ArrayList<String> onlineClientsList=null;//在线用户列表
	private ArrayList<String> offlineClientsList=null;//离线用户列表
	
	public AnswerCheckClientsMessage(ArrayList<String> onlineClientsList,ArrayList<String> offlineClientsList){
		this.onlineClientsList=onlineClientsList;
		this.offlineClientsList=offlineClientsList;
	}
	
	public ArrayList<String> getOnlineClientsList(){
		return onlineClientsList;
	}
	
	public ArrayList<String> getOfflineClientsList(){
		return offlineClientsList;
	}
}