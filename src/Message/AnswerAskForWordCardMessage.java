package Message;

import java.io.Serializable;
import java.util.ArrayList;

public class AnswerAskForWordCardMessage implements Serializable{
	private ArrayList<SendWordCardMessage> wordCardsToBeSent=null;
	private boolean isThereWordCard=false;
	
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
