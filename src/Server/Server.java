package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;

import javax.swing.*;

import Message.*;
import Database.*;
import Web.*;
//服务器类
public class Server extends JFrame{
	// 设置界面风格   
    {   
        try {   
            UIManager.setLookAndFeel(/*javax.swing.UIManager.getSystemLookAndFeelClassName()*/"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        }   
    }
	
	private JTextArea jta=new JTextArea();//显示用户登录登出消息的文本域
	private Set<HandleClient> clientsSet=Collections.synchronizedSet(new HashSet<HandleClient>());//存储用户线程的同步集合
	
	public static void main(String[] args){
		new Server();
	}
	
	public Server(){
		initGui();
		connectToClient();
	}	
	//初始化界面
	public void initGui(){
		setLayout(new BorderLayout());
		add(new JScrollPane(jta),BorderLayout.CENTER);
		setTitle("Server");
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	//尝试连接到客户端，给每个成功连接的客户端一个线程
	public void connectToClient(){
		
		try{
			ServerSocket serverSocket=new ServerSocket(8000);
			jta.append("Server started at"+new Date()+"\n");
			
			while(true){
				Socket socket=serverSocket.accept();
				HandleClient task=new HandleClient(socket);
				new Thread(task).start();
			}
		}
		catch(IOException ex){
			System.err.println(ex);
		}	
		
	}
	//处理客户的线程任务
	class HandleClient implements Runnable{
		private Socket socket;
		private String clientName;
		private ClientData clientData; //每个客户端都给一个连接到数据库类的对象
		private ObjectOutputStream objtoClient=null;
		private ObjectInputStream objfromClient=null;
		private ArrayList<SendWordCardMessage> wordCardsToBeSent=null;//待收的单词卡集合
		
		public HandleClient(Socket socket){
			this.socket=socket;
			clientData=new ClientData(socket);
			wordCardsToBeSent=new ArrayList<SendWordCardMessage>();
			try{
				objtoClient=new ObjectOutputStream(socket.getOutputStream());
				objfromClient=new ObjectInputStream(socket.getInputStream());
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{	
				//循环接收来自客户端的消息
				while(!socket.isClosed()){
					
					Object obj=objfromClient.readObject();
					//判断接收到的消息类型并进行处理
					if(obj instanceof SignupMessage){
						AnswerSignupMessage asm=clientData.handleSignupMessage((SignupMessage)obj);
						objtoClient.writeObject(asm);
						objtoClient.flush();
					}//是否是注册消息
					else if(obj instanceof LoginMessage){
						AnswerLoginMessage alm=clientData.handleLoginMessage((LoginMessage)obj);
						if(alm.getDoseNameExist()&&alm.getIsPasswordRight()){
							clientName=((LoginMessage)obj).getName();
							jta.append("User "+clientName+" logged in."+"\n");
							synchronized (clientsSet) {
								clientsSet.add(this);
							}	
						}
						objtoClient.writeObject(alm);
						objtoClient.flush();
					}//是否是登录消息
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
					}//是否是搜索单词消息
					else if(obj instanceof LikeUpdateMessage){
						clientData.handleLikeUpdateMessage(clientName, (LikeUpdateMessage)obj);
					}//是否是点赞消息
					else if(obj instanceof CheckClientsMessage){
						AnswerCheckClientsMessage accm=clientData.handleCheckClientsMessage(clientName,(CheckClientsMessage)obj);
						objtoClient.writeObject(accm);
						objtoClient.flush();
					}//查询用户在线情况消息
					else if(obj instanceof SendWordCardMessage){
						synchronized (clientsSet) {
							Iterator<HandleClient> iterator=clientsSet.iterator();
							boolean hasFound=false;
							String receiverName=((SendWordCardMessage)obj).getReceiverName();
							while(iterator.hasNext()){
								HandleClient hc=iterator.next();
								if(hc.clientName.equals(receiverName)){			
									
									String senderName=((SendWordCardMessage)obj).getSenderName();
									String content=((SendWordCardMessage)obj).getContent();
									Color backgroundColor=((SendWordCardMessage)obj).getBackgroundColor();
									Color fontColor=((SendWordCardMessage)obj).getFontColor();
									Font font=((SendWordCardMessage)obj).getFont();
									SendWordCardMessage swcm=new SendWordCardMessage(senderName, receiverName, content, backgroundColor, fontColor, font);      
									hc.wordCardsToBeSent.add(swcm);
									
									AnswerSendWordCardMessage aswcm=new AnswerSendWordCardMessage(true, receiverName);
									objtoClient.writeObject(aswcm);
									objtoClient.flush();
									hasFound=true;
									
									break;
								}
							}
							if(!hasFound){
								AnswerSendWordCardMessage aswcm=new AnswerSendWordCardMessage(false, receiverName);
								objtoClient.writeObject(aswcm);
								objtoClient.flush();
							}
						}	
					}//发送单词卡消息
					else if(obj instanceof AskForWordCardMessage){
						AnswerAskForWordCardMessage aafwcm=null;
						if(wordCardsToBeSent.size()==0)
							aafwcm=new AnswerAskForWordCardMessage(wordCardsToBeSent, false);
						else {
							aafwcm=new AnswerAskForWordCardMessage(wordCardsToBeSent, true);
							wordCardsToBeSent=new ArrayList<SendWordCardMessage>();
						}
						objtoClient.writeObject(aafwcm);
						objtoClient.flush();
					}//询问是否收到单词卡消息
				}
	
			}
			catch(IOException ex){
				System.err.println(ex);
			}
			catch(ClassNotFoundException ex){
				System.err.println(ex);
			}
			clientData.handleClientShutDown(clientName); //数据库处理客户端离线
			jta.append("User "+clientName+" logged out."+"\n");
			synchronized (clientsSet) {
				clientsSet.remove(this); //从线程任务列表中删除本线程
			}	
		}
	
	}
	
	
	
}