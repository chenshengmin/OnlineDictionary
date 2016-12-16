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

import Message.*;
//���͵��ʿ��Ľ���
public class SendWordCard extends JFrame{
	// ���ý�����   
    {   
        try {   
            UIManager.setLookAndFeel(/*javax.swing.UIManager.getSystemLookAndFeelClassName()*/"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        }   
    }
	
	private Socket socket;
	private ObjectOutputStream objtoServer=null;
	private ObjectInputStream objfromServer=null;
	
	private String senderName=null; //����������
	private String content=null; //��������
	private Color backgroundColor=Color.WHITE; //���ʿ�����ɫ
	private Color fontColor=Color.BLACK; //���ʿ�������ɫ
	private Font font=new Font("Dialog", Font.PLAIN, 20); //���ʿ�����
	
	JList jlClients=new JList<String>(); //��ѡ����û��б�
	JList jlExpressions=new JList<String>(); //��ѡ��ı����б�
	JButton jbtChooseBackgroundColor=new JButton("ѡ�񵥴ʿ�����ɫ"); //ѡ����ɫ��ť
	JButton jbtChooseFont=new JButton("ѡ���������ɫ"); //ѡ�������ɫ��ť
	JButton jbtReview=new JButton("Ԥ��"); //Ԥ����ť
	JButton jbtSend=new JButton("����"); //���Ͱ�ť
	
	
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
	//������
	public void setGui(){
		jlClients.setBorder(BorderFactory.createTitledBorder("��Ҫ���͸�����סCtrl��Ⱥ������"));
		jlExpressions.setBorder(BorderFactory.createTitledBorder("�������Ե��Ϊ���ʿ�������±��飺"));
		drawExpressions();
		
		jlClients.setBounds(40, 40, 240, 480);
		
		jlExpressions.setBounds(320, 40, 240, 480);
		
		jbtChooseBackgroundColor.setBounds(600, 95, 160, 40);
		
		jbtChooseFont.setBounds(600, 205, 160, 40);
		
		jbtReview.setBounds(600, 315, 160, 40);
		
		jbtSend.setBounds(600, 425, 160, 40);


		add(jbtChooseBackgroundColor);
		add(jbtChooseFont);
		add(jbtReview);
		add(jbtSend);
		
		add(jlClients);
		add(jlExpressions);
			
		setLayout(null);
		setTitle("Send Wordcard");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setSize(800,600);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	//��ʾ��ѡ����
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
	//ע�������
	public void registerListener(){
		jbtChooseBackgroundColor.addActionListener(new ChooseBackgroundListener());
		jbtChooseFont.addActionListener(new ChooseFontListener());
		jbtReview.addActionListener(new ReviewListener());
		jbtSend.addActionListener(new SendListener());
	}
	//����ɫѡ���������
	private class ChooseBackgroundListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			backgroundColor=JColorChooser.showDialog(SendWordCard.this, "��ѡ�񵥴ʿ�������ɫ", backgroundColor);
		}
		
	}
	//����ѡ���������
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
	//Ԥ���¼���������
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
	//�����¼���������
	private class SendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object[] tempReceivers=jlClients.getSelectedValues();
			if(tempReceivers.length==0||(String)tempReceivers[0]==null||((String)tempReceivers[0]).equals("")){
				JOptionPane.showMessageDialog(null, "����δѡ����Ч�ķ����û�", "alert", JOptionPane.ERROR_MESSAGE);
			}
			else{
				String[] receivers=new String[tempReceivers.length];
				for(int i=0;i<tempReceivers.length;i++){
					receivers[i]=(String)tempReceivers[i];
				}
				
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
						if(!aswcm.getIsClientOnline()){
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
	//չʾ���Է��͵��ʿ����û��б�
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
