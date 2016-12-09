package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import Status.*;

public class Server extends JFrame{
	private JTextArea jta=new JTextArea();
	
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
			}
		}
		catch(IOException ex){
			System.err.println(ex);
		}	
		
	}
	
	
	class HandleClient implements Runnable{
		Socket socket;
		
		public HandleClient(Socket socket){
			this.socket=socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				
				ObjectOutputStream objtoClient=new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream objfromClient=new ObjectInputStream(socket.getInputStream());;
					
				while(true){
					Object obj=objfromClient.readObject();
					
					if(obj instanceof WordAndPerfection){
						jta.append(((WordAndPerfection)obj).getWord()+'\n');
					}
				}
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