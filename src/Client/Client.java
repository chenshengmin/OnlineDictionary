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
	
	private ObjectOutputStream objtoServer;
	private ObjectInputStream objfromServer;
	
	
	public static void main(String args[]){
		new Client();
	}
	
	public Client(){
		new LogIn();
		initGui();
		registerListener();
		connectToServer();
	}
	
	public void initGui(){
		JPanel p=new JPanel();
		
		p.setLayout(new BorderLayout());
		
		p.add(new JLabel("Enter radius"),BorderLayout.WEST);
		p.add(jtf,BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.LEFT);
		p.add(jbt,BorderLayout.EAST);
		
		setLayout(new BorderLayout());
		add(p,BorderLayout.NORTH);
		add(new JScrollPane(jta),BorderLayout.CENTER);
		
		setTitle("Client");
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
	}
	
	public void registerListener(){
		jbt.addActionListener(new WordSearchListener());
	}
	
	public void connectToServer(){
		try{
			Socket socket=new Socket("localhost",8000);
			
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
	
	
	class LogIn extends JFrame{
		private JTextField jtfNameField=new JTextField();
		private JTextField jtfPassWordField=new JTextField();
		private JButton jbtLogInButton=new JButton("Log in");
		
		public LogIn(){
			setLogInGui();
			registerlogInListener();
		}
		
		public void setLogInGui(){
			JPanel p1=new JPanel();
			p1.setLayout(new BorderLayout());
			p1.add(new JLabel("Enter Your Name"),BorderLayout.WEST);
			p1.add(jtfNameField,BorderLayout.CENTER);
			
			JPanel p2=new JPanel();
			p2.setLayout(new BorderLayout());
			p2.add(new JLabel("Enter Your Password"),BorderLayout.WEST);
			p2.add(jtfPassWordField,BorderLayout.CENTER);
			
			JPanel p3=new JPanel();
			p3.setLayout(new BorderLayout());
			p3.add(p1,BorderLayout.NORTH);
			p3.add(p2,BorderLayout.SOUTH);
			
			setLayout(new BorderLayout());
			setTitle("Log In");
			setSize(240,180);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			
			add(p3,BorderLayout.CENTER);
			add(jbtLogInButton,BorderLayout.SOUTH);
		}
		
		public void registerlogInListener(){
			jbtLogInButton.addActionListener(new LogInListener());
		}
		
		private class LogInListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				Client.this.setVisible(true);
			}
			
		}

	
	}
	
}

