package Message;

import java.io.Serializable;


public class AnswerWordSearchMessage implements Serializable{
	private String translation;
	private boolean wordExists=true;
	
	public AnswerWordSearchMessage(String translation,boolean wordExists){
		this.translation=translation;
		this.wordExists=wordExists;
	}
	
	public String getTranslation(){
		return translation;
	}
	
	public boolean getWordExists(){
		return wordExists;
	}
}