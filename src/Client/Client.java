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
		initGui();
		registListener();
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
		setVisible(true);
	}
	
	public void registListener(){
		jbt.addActionListener(new TextFieldListener());
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
	
	private class TextFieldListener implements ActionListener{

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

