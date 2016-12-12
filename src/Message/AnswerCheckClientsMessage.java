package Message;

import java.io.Serializable;
import java.util.ArrayList;

public class AnswerCheckClientsMessage implements Serializable{
	private ArrayList<String> onlineClientsList=null;
	private ArrayList<String> offlineClientsList=null;
	
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