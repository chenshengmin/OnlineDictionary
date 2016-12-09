import java.io.*;

public class WordPreference implements Serializable{
	private String word;
	
	public WordPreference(String word){
		this.word=word;
	}
	
	String getWord(){
		return word;
	}
}

/*class FeedBack implements Serializable{
	
}

class Translation implements Serializable{
	private String translation;
	public Translation(String translation){
		this.translation=translation;
	}
	
	String getTranslation(){
		return translation;
	}
}*/