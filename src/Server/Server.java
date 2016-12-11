package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;

import javax.swing.*;

import Message.*;
import Database.*;

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
				
				//循环接收来自客户端的消息
				while(true){
					if(socket.isClosed())
						break;
					
					Object obj=objfromClient.readObject();
					//判断接收到的消息类型并进行处理
					if(obj instanceof SignupMessage){
						AnswerSignupMessage asm=clientData.HandleSignupMessage((SignupMessage)obj);
						objtoClient.writeObject(asm);
						objtoClient.flush();
					}//是否是注册消息
					else if(obj instanceof LoginMessage){
						AnswerLoginMessage alm=clientData.HandleLoginMessage((LoginMessage)obj);
						if(alm.getDoseNameExist()&&alm.getIsPasswordRight()){
							clientName=((LoginMessage)obj).getName();
						}
						objtoClient.writeObject(alm);
						objtoClient.flush();
					}//是否是登录消息
					else if(obj instanceof WordSearchMessage){
							
					}//是否是搜索单词消息
					else if(obj instanceof LikeUpdateMessage){
							
					}//是否是点赞消息
				}
	
			}
			catch(IOException ex){
				System.err.println(ex);
			}
			catch(ClassNotFoundException ex){
				System.err.println(ex);
			}
			afterClosedOperate();
		}
		
		public void afterClosedOperate(){
			
		}
	}
	
	
	
}