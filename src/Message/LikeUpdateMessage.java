package Message;

import java.io.Serializable;
//������Ϣ
public class LikeUpdateMessage implements Serializable{
	private String dicName;//Ҫ���޵��ֵ���
	private int update;//���޻���ȡ������
	
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