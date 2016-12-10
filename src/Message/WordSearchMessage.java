package Message;

import java.io.Serializable;

public class WordSearchMessage implements Serializable{
	private String word;
	private boolean isBaiduChosen=false;
	private boolean isYoudaoChosen=false;
	private boolean isBingChosen=false;
	
	public WordSearchMessage(String word) {
		// TODO Auto-generated constructor stub
		this.word=word;
	}
	
	public String getWord(){
		return word;
	}
	
	public boolean getBaidu(){
		return isBaiduChosen;
	}
	
	public boolean getYoudao(){
		return isYoudaoChosen;
	}

	public boolean getBing(){
		return isBingChosen;
	}
	
	public void setBaidu(boolean isBaiduChosen){
		this.isBaiduChosen=isBaiduChosen;
	}
	
	public void setYoudao(boolean isYoudaoChosen){
		this.isYoudaoChosen=isYoudaoChosen;
	}
	
	public void setBing(boolean isBingChosen){
		this.isBingChosen=isBingChosen;
	}
}


class AnswerWordSearchMessage implements Serializable{
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