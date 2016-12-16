package Message;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
//发送单词卡消息
public class SendWordCardMessage implements Serializable{
	private String senderName;//发送的用户名
	private String receiverName;//接收者
	private String content;//单词卡内容
	private Color backgroundColor;//单词卡背景色
	private Color fontColor;//字体颜色
	private Font font;//字体
	
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
