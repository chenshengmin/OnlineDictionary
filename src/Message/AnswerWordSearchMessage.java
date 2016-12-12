package Message;

import java.io.Serializable;


public class AnswerWordSearchMessage implements Serializable{
	private boolean wordExists=true;
	String[] dicPriority=new String[3];
	String[] translation=new String[3];
	
	public AnswerWordSearchMessage(boolean wordExists,String []dicPriority,String []translation){
		this.wordExists=wordExists;
		for(int i=0;i<3;i++){
			this.dicPriority[i]=dicPriority[i];
			this.translation[i]=translation[i];
		}
	}

	public boolean getWordExists(){
		return wordExists;
	}
	
	public String[] getDicPriority(){
		return dicPriority;
	}
	
	public String[] getTranslation(){
		return translation;
	}
}