package Client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Message.*;

public class Client extends JFrame{
	private String myName;
	private LogIn logIn;
	private CheckClients checkClients;
	
	private JTextField jtf=new JTextField();
	private JButton jbt=new JButton("translate");
	private JButton jbtCheckClients=new JButton("�鿴�����û�����״̬");
	private JButton jbtCheckWordCard=new JButton("�鿴�Ƿ��յ����ʿ�");
	
	private JCheckBox jcbYoudao=new JCheckBox("�е�");
	private JCheckBox jcbBaidu=new JCheckBox("�ٶ�");
	private JCheckBox jcbBing=new JCheckBox("��Ӧ");
	
	private Like firstLike=new Like();
	private Like secondLike=new Like();
	private Like thirdLike=new Like();
	private boolean likeLock=true;
	
	
	Socket socket=null;
	private ObjectOutputStream objtoServer=null;
	private ObjectInputStream objfromServer=null;
	
	public void setMyName(String name){
		myName=name;
	}
	
	public static void main(String args[]){
		new Client();
	}
	
	public Client(){
		connectToServer();
		logIn=new LogIn(this,socket,objfromServer,objtoServer);
		initGui();
		registerListener();
	}
	
	public void initGui(){
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(jcbYoudao,BorderLayout.WEST);
		p1.add(jcbBaidu,BorderLayout.CENTER);
		p1.add(jcbBing,BorderLayout.EAST);
				
		JPanel p=new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("Search word"),BorderLayout.WEST);
		p.add(jtf,BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.LEFT);
		p.add(jbt,BorderLayout.EAST);
		p.add(p1,BorderLayout.SOUTH);
		
		JPanel p2=new JPanel();
		p2.setLayout(new BoxLayout(p2,BoxLayout.Y_AXIS));
		p2.add(firstLike);
		p2.add(secondLike);
		p2.add(thirdLike);
		
		JPanel p3=new JPanel();
		p3.setLayout(new BoxLayout(p3,BoxLayout.X_AXIS));
		p3.add(jbtCheckClients);
		p3.add(jbtCheckWordCard);
		
		setLayout(new BorderLayout());
		add(p,BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);
		add(p3,BorderLayout.SOUTH);
		//������������Ļ����
		setLocationRelativeTo(null);
		setTitle("Client");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
	}
	
	public void registerListener(){
		jbt.addActionListener(new WordSearchListener());
		jbtCheckClients.addActionListener(new CheckClientsListener());
		jbtCheckWordCard.addActionListener(new CheckWordCardListener());
	}
	
	private class CheckWordCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try{
				AskForWordCardMessage afwcm=new AskForWordCardMessage();
				objtoServer.writeObject(afwcm);
				objtoServer.flush();
				
				AnswerAskForWordCardMessage aafwcm=(AnswerAskForWordCardMessage)objfromServer.readObject();
				if(aafwcm.getIsThereWordCard()){
					ArrayList<SendWordCardMessage> wordCards=aafwcm.getWordCardsToBeSent();
					
					for(int i=0;i<wordCards.size();i++){
						int res=JOptionPane.showConfirmDialog(null,  "���յ�һ������"+wordCards.get(i).getSenderName()+"�ĵ��ʿ����Ƿ�鿴��", "�е��ʿ���", JOptionPane.YES_NO_OPTION);
						if(res==0){
							new WordCard(wordCards.get(i).getContent(),wordCards.get(i).getBackgroundColor(),wordCards.get(i).getFontColor(),wordCards.get(i).getFont(),wordCards.get(i).getSenderName());
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "����δ�յ��κε��ʿ�~", "alert", JOptionPane.ERROR_MESSAGE);
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
	
	public void connectToServer(){
		try{
			socket=new Socket("localhost",8000);
			
			objfromServer=new ObjectInputStream(socket.getInputStream());
			
			objtoServer=new ObjectOutputStream(socket.getOutputStream());
			
		}
		catch(IOException ex){
			System.err.println(ex);
		}
	}
	
	private class WordSearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			likeLock=true;
			
			String toBeTranslated=jtf.getText();
			//����Ϊ�ձ���
			if(toBeTranslated.equals("")){
				JOptionPane.showMessageDialog(null, "���벻��Ϊ�գ�", "alert", JOptionPane.ERROR_MESSAGE);
				return;
			}
			//����Ӣ�ĵ����򱨴�
			for(int i=0;i<toBeTranslated.length();i++){
				char c=toBeTranslated.charAt(i);
				if(!((c>='a'&&c<='z')||(c>='A'&&c<='Z'))){
					JOptionPane.showMessageDialog(null, "����ǵ��ʣ�", "alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			WordSearchMessage wsm=new WordSearchMessage(toBeTranslated);
			
			wsm.setBaidu(jcbBaidu.isSelected());
			wsm.setBing(jcbBing.isSelected());
			wsm.setYoudao(jcbYoudao.isSelected());
			if(!jcbBaidu.isSelected()&&!jcbBing.isSelected()&&!jcbYoudao.isSelected()){
				wsm.setBaidu(true);
				wsm.setBing(true);
				wsm.setYoudao(true);
			}
			
			try{
				objtoServer.writeObject(wsm);
				objtoServer.flush();
				
				AnswerWordSearchMessage awsm=(AnswerWordSearchMessage)objfromServer.readObject();
				
				firstLike.refresh("", "");
				secondLike.refresh("", "");
				thirdLike.refresh("", "");
				
				if(!awsm.getWordExists()){
					JOptionPane.showMessageDialog(null, "�õ��ʲ����ڣ�", "alert", JOptionPane.ERROR_MESSAGE);
				}
				else{
					String[] dicPriority=awsm.getDicPriority();
					String[] translation=awsm.getTranslation();

					if(!(dicPriority[0]==null||translation[0]==null||dicPriority[0].equals("")||translation[0].equals(""))){
						firstLike.refresh(dicPriority[0], translation[0]);
					}
					
					if(!(dicPriority[1]==null||translation[1]==null||dicPriority[1].equals("")||translation[1].equals(""))){
						secondLike.refresh(dicPriority[1], translation[1]);
					}
					
					if(!(dicPriority[2]==null||translation[2]==null||dicPriority[2].equals("")||translation[2].equals(""))){
						thirdLike.refresh(dicPriority[2], translation[2]);
					}
				}
			}
			catch(IOException ex){
				System.err.println(ex);
			}
			catch(ClassNotFoundException ex){
				System.err.println(ex);
			}
			
			likeLock=false;
		}
	}
	
	private class CheckClientsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			checkClients=new CheckClients(socket,objfromServer,objtoServer);
		}
		
	}
	
	class Like extends JPanel{
		String labelString=null;
		JTextField jtfDic=new JTextField("Dictionary");
		JCheckBox jcbLike=new JCheckBox("����");
		JButton jbtSend=new JButton("���͵��ʿ�");
		JTextArea jtaTrans=new JTextArea();
		
		public Like(){
			setLikeGui();
			registerLikeListener();
		}
		
		public void setLikeGui(){
			
			JPanel p=new JPanel();
			p.setLayout(new BorderLayout());
			p.add(jcbLike,BorderLayout.NORTH);
			p.add(jbtSend,BorderLayout.SOUTH);
			
			setLayout(new BorderLayout(10,10));
			jtfDic.setEditable(false);
			jtaTrans.setEditable(false);
			add(p,BorderLayout.EAST);
			add(jtfDic,BorderLayout.WEST);
			add(new JScrollPane(jtaTrans),BorderLayout.CENTER);
		}
		
		public void registerLikeListener(){
			jcbLike.addActionListener(new LikeChoosingListener());
			jbtSend.addActionListener(new SendWordCardListener());
		}
		
		private class SendWordCardListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(jtaTrans.getText()==null||jtaTrans.getText().equals(""))
					JOptionPane.showMessageDialog(null, "�÷�����Ϊ�գ�", "alert", JOptionPane.ERROR_MESSAGE);
				else
					new SendWordCard(socket,objfromServer,objtoServer,myName,jtaTrans.getText());
			}
		}
		
		private class LikeChoosingListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!likeLock){
					if(labelString==null||labelString.equals("")||(!(labelString.equals("Baidu")||labelString.equals("Youdao")||labelString.equals("Bing")))){
						return;
					}
					else if(jcbLike.isSelected()){
						try{
							LikeUpdateMessage lum=new LikeUpdateMessage(labelString, 1);
							objtoServer.writeObject(lum);
							objtoServer.flush();
						}
						catch(IOException ex){
							System.err.println(ex);
						}
					}
					else{
						try{
							LikeUpdateMessage lum=new LikeUpdateMessage(labelString, -1);
							objtoServer.writeObject(lum);
							objtoServer.flush();
						}
						catch(IOException ex){
							System.err.println(ex);
						}
					}
				}
			}
		}
		
		public void refresh(String dicName,String translation){
			labelString=dicName;
			
			if(!dicName.equals(""))
				jtfDic.setText(dicName);
			else
				jtfDic.setText("Dictionary");
			
			jtaTrans.setText(translation);
			
			jcbLike.setSelected(false);
		}
	}
}