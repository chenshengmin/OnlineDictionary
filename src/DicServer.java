import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;

import javax.swing.*;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.rmi.server.SocketSecurityException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;


public class DicServer extends JFrame{
	private JTextArea jta=new JTextArea();
	
	public static void main(String args[]){
		new DicServer();
	}
	
	public DicServer(){
		setGUI();
	}
	
	//set server gui
	public void setGUI(){
		//place text area on the frame
		setLayout(new BorderLayout());
		add(new JScrollPane(jta),BorderLayout.CENTER);
		
		setTitle("OnlineDictionary Server");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//connect to clients
	public void getConnection(){
		try{
			ServerSocket serverSocket=new ServerSocket(8000);
			jta.append("OnlineDictionary Server started at "+new Date()+'\n');
			
			int clientNo=1;
			
			while(true){
				//listen for a new connection request
				Socket socket=serverSocket.accept();
				
				//display the client number
				jta.append("Starting thread for client"+clientNo+" at "+new Date()+'\n');
				
				//find the host's hostname and ip address
				InetAddress inetAddress=socket.getInetAddress();
				jta.append("Client "+clientNo+" 's host name is"+inetAddress.getHostName()+"\n");
				jta.append("Client "+clientNo+" 's host IP is"+inetAddress.getHostAddress()+"\n");
				
				//create a new thread for the connection
				HandleAClient task=new HandleAClient(socket);
				//Start the new thread
				new Thread(task).start();
				//Increment clientNo
				clientNo++;
			}
		}
		catch(IOException ex){
			System.err.println(ex);
		}
	}
}

class HandleAClient implements Runnable{
	private Socket socket;
	
	public HandleAClient(Socket socket){
		this.socket=socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}