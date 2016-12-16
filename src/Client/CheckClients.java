package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;

import Message.AnswerCheckClientsMessage;
import Message.CheckClientsMessage;
//查询其他在线用户的类，生成查询用户的gui，并发送查询用户消息
public class CheckClients extends JFrame{
	// 设置界面风格   
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
	JList jlonlineClients=new JList<String>();//在线用户列表
	JList jlofflineClients=new JList<String>();//下线用户列表
	private JButton jbtRefresh=new JButton("刷新");//刷新按钮
	
	public CheckClients(Socket socket,ObjectInputStream objfromServer,ObjectOutputStream objtoServer){
		this.socket=socket;
		this.objfromServer=objfromServer;
		this.objtoServer=objtoServer;
		setGui();//画界面
		jbtRefresh.addActionListener(new RefreshListener());//添加监听器
		showClients();//显示列表内容
	}
	//界面不用布局管理器，自由布局
	public void setGui(){
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		//p1.add(new JLabel("在线用户"),BorderLayout.NORTH);
		p1.add(jlonlineClients,BorderLayout.CENTER);
		p1.setBorder(javax.swing.BorderFactory.createTitledBorder("在线用户"));
		p1.setBounds(60, 40, 200, 400);
		
		JPanel p2=new JPanel();
		p2.setLayout(new BorderLayout());
		//p2.add(new JLabel("离线用户"),BorderLayout.NORTH);
		p2.add(jlofflineClients,BorderLayout.CENTER);
		p2.setBorder(javax.swing.BorderFactory.createTitledBorder("离线用户"));
		p2.setBounds(300, 40, 200, 400);
		
		jbtRefresh.setBounds(217, 475, 125, 36);
		
		setLayout(null);
		add(p1);
		add(p2);
		add(jbtRefresh);
		pack();
		setSize(577,580);
		//将窗口置于屏幕中央
		setLocationRelativeTo(null);
		setTitle("CheckClient");
		setVisible(true);
	}
	//发送消息请求查看其他用户，接受其他用户消息并重绘列表
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
			jlonlineClients.setModel(dlm);
			
			dlm = new DefaultListModel();
			for(int i=0;i<offlineClientsList.size();i++){
				dlm.addElement(offlineClientsList.get(i));
			}
			jlofflineClients.setModel(dlm);
			
		}
		catch(IOException ex){
			System.err.println(ex);
		}
		catch(ClassNotFoundException ex){
			System.err.println(ex);
		}
	}
	//刷新，相当于再次调用showclients方法
	private class RefreshListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
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
				jlonlineClients.setModel(dlm);
				
				dlm = new DefaultListModel();
				for(int i=0;i<offlineClientsList.size();i++){
					dlm.addElement(offlineClientsList.get(i));
				}
				jlofflineClients.setModel(dlm);
				
			}
			catch(IOException ex){
				System.err.println(ex);
			}
			catch(ClassNotFoundException ex){
				System.err.println(ex);
			}
		}
		
	}

}
