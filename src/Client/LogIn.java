package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Message.*;


public class LogIn extends JFrame{
	// ���ý�����   
    {   
        try {   
            UIManager.setLookAndFeel(/*javax.swing.UIManager.getSystemLookAndFeelClassName()*/"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        }   
    } 
	
	private Client client;
	private Socket socket;
	private ObjectOutputStream objtoServer=null;
	private ObjectInputStream objfromServer=null;
	private SignUp signUp;
	
	private JTextField jtfNameField=new JTextField();
	private JPasswordField jtfPassWordField=new JPasswordField();
	private JButton jbtLogInButton=new JButton("Log in");
	private JButton jbtSignUpButton=new JButton("Sign up");
	
	public LogIn(Client client,Socket socket,ObjectInputStream objfromServer,ObjectOutputStream objtoServer){
		this.client=client;
		this.socket=socket;
		this.objfromServer=objfromServer;
		this.objtoServer=objtoServer;
		

		setLogInGui();
		registerlogInListener();
	}
	
	public void setLogInGui(){
		/*
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(new JLabel("Enter Your Name"));
		p1.add(jtfNameField);
		
		JPanel p2=new JPanel();
		p2.setLayout(new FlowLayout());
		p2.add(new JLabel("Enter Your Password"));
		p2.add(jtfPassWordField);
		
		JPanel p3=new JPanel();
		p3.setLayout(new GridLayout(1,2));
		p3.add(jbtLogInButton);
		p3.add(jbtSignUpButton);*/
		
		JLabel jlb1=new JLabel("Name");
		jlb1.setBounds(100, 50, 100, 50);
		jtfNameField.setBounds(250, 50, 250, 50);
		
		JLabel jlb2=new JLabel("Password");
		jlb2.setBounds(100, 150, 100, 50);
		jtfPassWordField.setBounds(250, 150, 250, 50);
		
		jbtLogInButton.setBounds(100,250, 150, 36);
		jbtSignUpButton.setBounds(350,250, 150 ,36);
		
		setLayout(null);
		setTitle("Log In");
		setSize(625,375);
		setBackground(Color.LIGHT_GRAY);
		add(jlb1);
		add(jlb2);
		add(jtfNameField);
		add(jtfPassWordField);
		add(jbtLogInButton);
		add(jbtSignUpButton);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//������������Ļ����
		setLocationRelativeTo(null); 
	}
	
	public void registerlogInListener(){
		jbtLogInButton.addActionListener(new LogInListener());
		jbtSignUpButton.addActionListener(new SignUpListener());
	}
	
	private class LogInListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name=jtfNameField.getText();
			String password=jtfPassWordField.getText();
			if(name==null||password==null||name.equals("")||password.equals("")){
				JOptionPane.showMessageDialog(null, "���������ֵ", "alert", JOptionPane.ERROR_MESSAGE); 
			}
			else{
				//
				try {
					LoginMessage lgnm=new LoginMessage(name, password);
					objtoServer.writeObject(lgnm);
					objtoServer.flush();
					
					AnswerLoginMessage alm=(AnswerLoginMessage)objfromServer.readObject();
					
					if(!alm.getDoseNameExist()){
						JOptionPane.showMessageDialog(null, "�����ڸ��û�", "����",JOptionPane.ERROR_MESSAGE); 
					}
					else if(!alm.getIsPasswordRight()){
						JOptionPane.showMessageDialog(null, "�������", "����",JOptionPane.ERROR_MESSAGE);
					}
					else{
						client.setMyName(name);
						client.setTitle("Client  \""+name+"\"");
						setVisible(false);
						client.setVisible(true);
					}
				} catch (IOException ex) {
					// TODO: handle exception
					System.err.println(ex);
				} catch(ClassNotFoundException ex){
					System.err.println(ex);
				}
			}
		}
		
	}
	
	private class SignUpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			signUp=new SignUp(socket,objfromServer,objtoServer);
		}
		
	}

}