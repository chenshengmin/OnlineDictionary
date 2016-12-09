package Status;

import java.io.Serializable;

public class WordAndPerfection implements Serializable{
	private String word;
	
	public WordAndPerfection(String word) {
		// TODO Auto-generated constructor stub
		this.word=word;
	}
	
	public String getWord(){
		return word;
	}
}

