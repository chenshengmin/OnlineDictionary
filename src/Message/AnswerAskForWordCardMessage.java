package Message;

import java.io.Serializable;
import java.util.ArrayList;
//�ظ�ѯ���Ƿ��е��ʿ���Ϣ
public class AnswerAskForWordCardMessage implements Serializable{
	private ArrayList<SendWordCardMessage> wordCardsToBeSent=null;//���ش����ĵ��ʿ��б�
	private boolean isThereWordCard=false;//�Ƿ��е��ʿ�δ�յ�
	
	public AnswerAskForWordCardMessage(ArrayList<SendWordCardMessage> wordCardsToBeSent,boolean isThereWordCard){
		this.wordCardsToBeSent=wordCardsToBeSent;
		this.isThereWordCard=isThereWordCard;
	}
	
	public ArrayList<SendWordCardMessage> getWordCardsToBeSent(){
		return wordCardsToBeSent;
	}
	
	public boolean getIsThereWordCard(){
		return isThereWordCard;
	}
}
