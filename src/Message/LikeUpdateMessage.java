package Message;

import java.io.Serializable;
//点赞消息
public class LikeUpdateMessage implements Serializable{
	private String dicName;//要点赞的字典名
	private int update;//点赞还是取消点赞
	
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