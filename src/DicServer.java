import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;

import javax.swing.*;

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
	
	public void setGUI(){
		//place text area on the frame
		setLayout(new BorderLayout());
		add(new JScrollPane(jta),BorderLayout.CENTER);
		
		setTitle("OnlineDictionary Server");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
