package Message;

import java.io.Serializable;

//回复查单词消息
public class AnswerWordSearchMessage implements Serializable{
	private boolean wordExists=true;//单词是否存在
	String[] dicPriority=new String[3];//字典偏好度
	String[] translation=new String[3];//翻译结果
	
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