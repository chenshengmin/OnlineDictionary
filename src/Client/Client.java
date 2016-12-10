package Client;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Status.*;

public class Client extends JFrame{
	private JTextField jtf=new JTextField();
	private JButton jbt=new JButton("translate");
	
	private JCheckBox jcbYoudao=new JCheckBox("有道");
	private JCheckBox jcbBaidu=new JCheckBox("百度");
	private JCheckBox jcbBing=new JCheckBox("必应");
	
	Socket socket=null;
	private ObjectOutputStream objtoServer=null;
	private ObjectInputStream objfromServer=null;
	
	
	public static void main(String args[]){
		new Client();
	}
	
	public Client(){
		connectToServer();
		new LogIn(this,socket);
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
		p2.add(new Like(socket));
		p2.add(new Like(socket));
		p2.add(new Like(socket));
		
		setLayout(new BorderLayout());
		add(p,BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);
		//将窗口置于屏幕中央
		setLocationRelativeTo(null);
		setTitle("Client");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
	}
	
	public void registerListener(){
		jbt.addActionListener(new WordSearchListener());
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
			/*try{	
				WordAndPerfection word=new WordAndPerfection(jtf.getText());
				objtoServer.writeObject(word);
				objtoServer.flush();
			}
			catch(IOException ex){
				System.err.println(ex);
			}*/
		}
	}	
}

class Like extends JPanel{
	Socket socket=null;
	
	JCheckBox jcbLikeBox=new JCheckBox("点赞");
	JTextArea jtaTrans=new JTextArea();
	
	public Like(Socket socket){
		this.socket=socket;
		setLikeGui();
		registerLikeListener();
	}
	
	public void setLikeGui(){
		setLayout(new BorderLayout());
		add(jcbLikeBox,BorderLayout.EAST);
		add(new JScrollPane(jtaTrans),BorderLayout.CENTER);
	}
	
	public void registerLikeListener(){
		jcbLikeBox.addActionListener(new LikeChoosingListener());
	}
	
	private class LikeChoosingListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}	
}