package Message;

import java.io.Serializable;
import java.util.ArrayList;
//回复询问是否有单词卡消息
public class AnswerAskForWordCardMessage implements Serializable{
	private ArrayList<SendWordCardMessage> wordCardsToBeSent=null;//返回待发的单词卡列表
	private boolean isThereWordCard=false;//是否有单词卡未收到
	
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
