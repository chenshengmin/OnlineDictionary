import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.naming.InitialContext;
import javax.swing.*;

public class DicClient extends JFrame{
	private JButton jbtView=new JButton("view");
	private JTextField jtfWord=new JTextField();
	private JTextArea jtaTrans=new JTextArea();
	Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
	
	public static void main(String args[]){
		new	DicClient();
	}
	
	public DicClient(){
		//画框架
		setGUI();
		//获取服务器套接字，设置输入输出端口
		connectToServer();
		//定义并注册事件监听器
		setListener();
	}
	
	public void setGUI(){
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(new JLabel("Search"),BorderLayout.WEST);
		p1.add(jtfWord,BorderLayout.CENTER);
		p1.add(jbtView,BorderLayout.EAST);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(jtaTrans),BorderLayout.CENTER);
		add(p1,BorderLayout.NORTH);
		
		
		setSize(800,600);
		setTitle("OnlineDictionary Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void connectToServer(){
		try{
			socket=new Socket("localhost",8000);
			fromServer=new ObjectInputStream(socket.getInputStream());	
			toServer=new ObjectOutputStream(socket.getOutputStream());
		}
		catch(IOException ex){
			jtaTrans.setText(ex.toString()+'\n');
		}
	}
	
	public void setListener(){
		jbtView.addActionListener(new SearchListener());
	}
	
	private class SearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			try{			
				
				DataOutputStream data=new DataOutputStream(socket.getOutputStream());
				data.writeDouble(100);
				data.flush();
				
				/*String word=jtfWord.getText();

				WordPreference a=new WordPreference(word);
				
				toServer.writeObject(a);
				toServer.flush();*/
			
				/*Object obj=fromServer.readObject();
			
			if(obj instanceof Translation){
				jtaTrans.setText(((Translation) obj).getTranslation());
			}*/
			
			}
			catch(IOException ex){
				jtaTrans.setText(ex.toString()+'\n');
			}
			/*catch(ClassNotFoundException ex){
				jtaTrans.setText(ex.toString()+'\n');
			}*/
		}	
	}
	
}
