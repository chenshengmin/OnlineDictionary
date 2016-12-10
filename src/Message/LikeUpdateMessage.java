package Message;

import java.io.Serializable;

public class LikeUpdateMessage implements Serializable{
	private String dicName;
	private int update;
	
	public LikeUpdateMessage(String dicName,int update){
		this.dicName=dicName;
		this.update=update;
	}
	
	public String getDicName(){
		return dicName;
	}
	
	public int getUpdate(){
		return update;
	}
}