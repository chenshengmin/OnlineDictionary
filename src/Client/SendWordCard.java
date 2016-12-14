package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

import Client.CheckClients.Refresh;
import Message.*;

public class SendWordCard extends JFrame{
	private Socket socket;
	private ObjectOutputStream objtoServer=null;
	private ObjectInputStream objfromServer=null;
	
	private String senderName=null;
	private String content=null;
	private Color backgroundColor=Color.WHITE;
	private Color fontColor=Color.BLACK;
	private Font font=new Font("Dialog", Font.PLAIN, 20);
	
	JList jlClients=new JList<String>();
	JList jlExpressions=new JList<String>();
	JButton jbtChooseBackgroundColor=new JButton("ѡ�񵥴ʿ�����ɫ");
	JButton jbtChooseFont=new JButton("ѡ���������ɫ");
	JButton jbtReview=new JButton("Ԥ��");
	JButton jbtSend=new JButton("����");
	
	
	public SendWordCard(Socket socket,ObjectInputStream objfromServer,ObjectOutputStream objtoServer,String senderName,String content){
		this.socket=socket;
		this.objfromServer=objfromServer;
		this.objtoServer=objtoServer;
		
		this.senderName=senderName;
		this.content=content;
		
		setGui();
		registerListener();
		showClients();
	}
	
	public void setGui(){
		drawExpressions();
		jlClients.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(new JLabel("���͸�:"),BorderLayout.NORTH);
		p1.add(jlClients,BorderLayout.CENTER);
		
		JPanel p2=new JPanel();
		p2.setLayout(new BoxLayout(p2,BoxLayout.Y_AXIS));
		p2.add(new JPanel());
		p2.add(jbtChooseBackgroundColor);
		p2.add(new JPanel());
		p2.add(jbtChooseFont);
		p2.add(new JPanel());
		p2.add(jbtReview);
		p2.add(new JPanel());
		p2.add(jbtSend);
		
		JPanel p3=new JPanel();
		p3.setLayout(new BorderLayout());
		p3.add(new JLabel("������Ϊ���ʿ�������±���:"),BorderLayout.NORTH);
		p3.add(jlExpressions,BorderLayout.CENTER);
		
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.X_AXIS));
		add(p1);
		add(p3);
		add(p2);
		setLocationRelativeTo(null);
		setTitle("Client");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600,400);
		setVisible(true);
	}
	
	public void drawExpressions(){
		DefaultListModel dlm = new DefaultListModel();
		dlm.addElement("( �� o �� )");
		dlm.addElement("/(��o��)/~~");
		dlm.addElement("O(��_��)O~~");
		dlm.addElement("���أ�");
		dlm.addElement("(�ѩn��)");
		dlm.addElement("(�Φ�<��)��))��.��");
		dlm.addElement("o(>�n<)o");
		dlm.addElement("(�s�n�t)");
		jlExpressions.setModel(dlm);
	}
	
	public void registerListener(){
		jbtChooseBackgroundColor.addActionListener(new ChooseBackgroundListener());
		jbtChooseFont.addActionListener(new ChooseFontListener());
		jbtReview.addActionListener(new ReviewListener());
		jbtSend.addActionListener(new SendListener());
	}
	
	private class ChooseBackgroundListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			backgroundColor=JColorChooser.showDialog(SendWordCard.this, "��ѡ�񵥴ʿ�������ɫ", backgroundColor);
		}
		
	}
	
	private class ChooseFontListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFontChooser jfc=new JFontChooser();
			jfc.showDialog(SendWordCard.this, 600, 300);
			font=jfc.getSelectedfont();
			fontColor=jfc.getSelectedcolor();
			if(font!=null&&fontColor!=null){   
	        /*��ӡ�û�ѡ����������ɫ*/   
				System.out.println(font);   
				System.out.println(fontColor);   
	        }
		}	
	}
	
	private class ReviewListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String tempContent=content;
			String exp=(String)jlExpressions.getSelectedValue();
			if(exp!=null&&!exp.equals("")){
				tempContent=exp+"\n"+tempContent;
			}
			new WordCard(tempContent, backgroundColor, fontColor, font, senderName);
		}
	}
	
	private class SendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String[] receivers=(String[])jlClients.getSelectedValues();
			if(receivers.length==0||receivers[0]==null||receivers[0].equals("")){
				JOptionPane.showMessageDialog(null, "����δѡ����Ч�ķ����û�", "alert", JOptionPane.ERROR_MESSAGE);
			}
			else{
				try{
					String tempContent=content;
					String exp=(String)jlExpressions.getSelectedValue();
					if(exp!=null&&!exp.equals("")){
						tempContent=exp+"\n"+tempContent;
					}
					
					for(int i=0;i<receivers.length;i++){
						SendWordCardMessage swcm=new SendWordCardMessage(senderName, receivers[i], tempContent, backgroundColor, fontColor, font);
						objtoServer.writeObject(swcm);
						objtoServer.flush();
					}
					
					for(int i=0;i<receivers.length;i++){
						AnswerSendWordCardMessage aswcm=(AnswerSendWordCardMessage)objfromServer.readObject();
						if(aswcm.getIsClientOnline()==true){
							JOptionPane.showMessageDialog(null, "o(>�n<)o���ã�"+aswcm.getReceiver()+"��Ȼ����ѧϰ����ȥ����~~", "alert", JOptionPane.ERROR_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null, "���͸�"+aswcm.getReceiver()+"�ɹ���O(��_��)O~~");
						}
					}
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
				catch(ClassNotFoundException ex){
					ex.printStackTrace();
				}
			}
		}
		
	}
	
	public void showClients(){
		try{
			CheckClientsMessage ccm=new CheckClientsMessage();
			objtoServer.writeObject(ccm);
			objtoServer.flush();
			
			AnswerCheckClientsMessage accm=(AnswerCheckClientsMessage)objfromServer.readObject();
			ArrayList<String> onlineClientsList=accm.getOnlineClientsList();
			ArrayList<String> offlineClientsList=accm.getOfflineClientsList();
			
			DefaultListModel dlm = new DefaultListModel();
			for(int i=0;i<onlineClientsList.size();i++){
					dlm.addElement(onlineClientsList.get(i));
			}
			jlClients.setModel(dlm);
		
			dlm = new DefaultListModel();
			for(int i=0;i<offlineClientsList.size();i++){
					dlm.addElement(offlineClientsList.get(i));
			}
			jlClients.setModel(dlm);
			
		}
		catch(IOException ex){
			System.err.println(ex);
		}
		catch(ClassNotFoundException ex){
			System.err.println(ex);
		}
	}
}
