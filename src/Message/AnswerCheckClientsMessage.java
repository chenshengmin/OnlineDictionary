package Message;

import java.io.Serializable;
import java.util.ArrayList;
//�ظ��鿴�����û���Ϣ
public class AnswerCheckClientsMessage implements Serializable{
	private ArrayList<String> onlineClientsList=null;//�����û��б�
	private ArrayList<String> offlineClientsList=null;//�����û��б�
	
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