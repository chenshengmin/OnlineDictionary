package Message;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

public class SendWordCardMessage implements Serializable{
	private String senderName;
	private String receiverName;
	private String content;
	private Color backgroundColor;
	private Color fontColor;
	private Font font;
	
	public SendWordCardMessage(String senderName,String receiverName,String content,Color backgroundColor,Color fontColor,Font font){
		this.senderName=senderName;
		this.receiverName=receiverName;
		this.content=content;
		this.backgroundColor=backgroundColor;
		this.fontColor=fontColor;
		this.font=font;
	}
	
	public String getSenderName(){
		return senderName;
	}
	
	public String getReceiverName(){
		return receiverName;
	}
	
	public String getContent(){
		return content;
	}
	
	public Color getBackgroundColor(){
		return backgroundColor;
	}
	
	public Color getFontColor(){
		return fontColor;
	}
	
	public Font getFont(){
		return font;
	}
}
