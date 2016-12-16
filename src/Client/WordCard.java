package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.*;
//单词卡生成类
public class WordCard extends JFrame{
	private String message;
	private String[] messageLines=null;
	private Font font=null;
	private int lines=0;//文字总行数
	private int stringWidth=0;//字体总宽度
	private int stringAscent=0;//每个字的高度
	private int stringHeight=0;//字体总高度
	
	public static void main(String[] args){
		new WordCard(":-D\n/(ㄒoㄒ)/~~\no(>﹏<)o\n( ⊙ o ⊙ )\nO(∩_∩)O~~\nWord\n但此次fffffffffffffffffffff此次", Color.ORANGE, Color.LIGHT_GRAY,new Font("宋体",Font.BOLD+Font.ITALIC,60), "silver bear");
    }
	//自动调整内容居中
	public void adjustSize(){
		
		messageLines=message.split("\n");
		lines=messageLines.length;
		
		FontMetrics fm = new JPanel().getFontMetrics(font);
		stringAscent=fm.getAscent();
		stringHeight=lines*stringAscent;
		
		int tempStringWidth =0;
		for(int i=0;i<lines;i++){
			if(tempStringWidth<fm.stringWidth(messageLines[i]))
				tempStringWidth=fm.stringWidth(messageLines[i]);
		}
		stringWidth=tempStringWidth;
		
		setSize((int)(stringWidth*(1/0.618)), (int)(stringHeight*(1/0.618)*1.44));
	}
	
	public WordCard(String text,Color backgroundColor,Color fontColor,Font font,String senderName){
		this.message=text;
		this.font=font;
		adjustSize();
		
		MyMessagePanel mp=new MyMessagePanel();
		mp.setFont(font);
		mp.setForeground(fontColor);
		mp.setBackground(backgroundColor);
		
		add(mp);
		setTitle("您来自 "+senderName+" 的单词卡");
		setLocationRelativeTo(null);
		setVisible(true);
	}
	//画图形
	class MyMessagePanel extends JPanel{
		protected void paintComponent(Graphics g){		          
			super.paintComponent(g);
			
			int xCoordinate=getWidth()/2-stringWidth/2;
			int yCoordinate=getHeight()/2-stringHeight/2+stringAscent;
		             	
			for(int i=0;i<lines;i++){
				g.drawString(messageLines[i], xCoordinate, yCoordinate);
				yCoordinate+=stringAscent;
			}  
		}
	}
}
