package Client;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Status.*;

public class Client extends JFrame{
	private JTextField jtf=new JTextField();
	private JTextArea jta=new JTextArea();
	private JButton jbt=new JButton("view");
	
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
		JPanel p=new JPanel();
		
		p.setLayout(new BorderLayout());
		
		p.add(new JLabel("Search word"),BorderLayout.WEST);
		p.add(jtf,BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.LEFT);
		p.add(jbt,BorderLayout.EAST);
		
		setLayout(new BorderLayout());
		add(p,BorderLayout.NORTH);
		add(new JScrollPane(jta),BorderLayout.CENTER);
		
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
			jta.append(ex.toString()+'\n');
		}
	}
	
	private class WordSearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try{	
				WordAndPerfection word=new WordAndPerfection(jtf.getText());
				objtoServer.writeObject(word);
				objtoServer.flush();
			}
			catch(IOException ex){
				System.err.println(ex);
			}
		}
	}
	
	
}

