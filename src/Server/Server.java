package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;

import javax.swing.*;

import Message.*;
import Database.*;
import Web.*;

public class Server extends JFrame{
	private JTextArea jta=new JTextArea();
	Set<Socket> socketSet=Collections.synchronizedSet(new HashSet<Socket>());
	
	public static void main(String[] args){
		new Server();
	}
	
	public Server(){
		initGui();
		connectToClient();
	}	
	
	public void initGui(){
		setLayout(new BorderLayout());
		add(new JScrollPane(jta),BorderLayout.CENTER);
		setTitle("Server");
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void connectToClient(){
		
		try{
			ServerSocket serverSocket=new ServerSocket(8000);
			jta.append("Server started at"+new Date()+"\n");
			
			while(true){
				Socket socket=serverSocket.accept();
				HandleClient task=new HandleClient(socket);
				new Thread(task).start();
				socketSet.add(socket);
			}
		}
		catch(IOException ex){
			System.err.println(ex);
		}	
		
	}
	
	
	class HandleClient implements Runnable{
		Socket socket;
		String clientName;
		ClientData clientData;
		
		public HandleClient(Socket socket){
			this.socket=socket;
			clientData=new ClientData(socket);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				
				ObjectOutputStream objtoClient=new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream objfromClient=new ObjectInputStream(socket.getInputStream());;
				
				//ѭ���������Կͻ��˵���Ϣ
				while(true){
					if(socket.isClosed())
						break;
					
					Object obj=objfromClient.readObject();
					//�жϽ��յ�����Ϣ���Ͳ����д���
					if(obj instanceof SignupMessage){
						AnswerSignupMessage asm=clientData.handleSignupMessage((SignupMessage)obj);
						objtoClient.writeObject(asm);
						objtoClient.flush();
					}//�Ƿ���ע����Ϣ
					else if(obj instanceof LoginMessage){
						AnswerLoginMessage alm=clientData.handleLoginMessage((LoginMessage)obj);
						if(alm.getDoseNameExist()&&alm.getIsPasswordRight()){
							clientName=((LoginMessage)obj).getName();
							jta.append("User "+clientName+" logged in."+"\n");
						}
						objtoClient.writeObject(alm);
						objtoClient.flush();
					}//�Ƿ��ǵ�¼��Ϣ
					else if(obj instanceof WordSearchMessage){
						String[] translation=new String[3];
						for(int i=0;i<3;i++)
							translation[i]="";
						boolean[] wordExists=new boolean[3];
						for(int i=0;i<3;i++)
							wordExists[i]=false;
						AnswerWordSearchMessage awsm=null;
						String[] dicPriority=clientData.handleWordSearchMessage(clientName, (WordSearchMessage)obj);
						
						for(int i=0;i<3;i++){
							if(dicPriority[i].equals("Baidu")){
								translation[i]=Baidu.lookUp(((WordSearchMessage)obj).getWord());
								if(!(translation[i]==null||translation[i].equals("")))
									wordExists[i]=true;
							}
							else if(dicPriority[i].equals("Youdao")){
								translation[i]=Youdao.lookUp(((WordSearchMessage)obj).getWord());
								if(!(translation[i]==null||translation[i].equals("")))
									wordExists[i]=true;
							}
							else if(dicPriority[i].equals("Bing")){
								translation[i]=Bing.lookUp(((WordSearchMessage)obj).getWord());
								if(!(translation[i]==null||translation[i].equals("")))
									wordExists[i]=true;
							}
							else if(dicPriority[i].equals("")){
								translation[i]="";
							}
						}
						
						awsm=new AnswerWordSearchMessage(wordExists[0]||wordExists[1]||wordExists[2], dicPriority, translation);
						objtoClient.writeObject(awsm);
						objtoClient.flush();
					}//�Ƿ�������������Ϣ
					else if(obj instanceof LikeUpdateMessage){
						clientData.handleLikeUpdateMessage(clientName, (LikeUpdateMessage)obj);
					}//�Ƿ��ǵ�����Ϣ
					else if(obj instanceof CheckClientsMessage){
						AnswerCheckClientsMessage accm=clientData.handleCheckClientsMessage(clientName,(CheckClientsMessage)obj);
						objtoClient.writeObject(accm);
						objtoClient.flush();
					}//��ѯ�û����������Ϣ
				}
	
			}
			catch(IOException ex){
				System.err.println(ex);
			}
			catch(ClassNotFoundException ex){
				System.err.println(ex);
			}
			clientData.handleClientShutDown(clientName);
			jta.append("User "+clientName+" logged out."+"\n");
		}
	
	}
	
	
	
}