package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Message.*;


public class LogIn extends JFrame{
	private Client client;
	private Socket socket;
	private ObjectOutputStream objtoServer=null;
	private ObjectInputStream objfromServer=null;
	private SignUp signUp;
	
	private JTextField jtfNameField=new JTextField();
	private JTextField jtfPassWordField=new JTextField();
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
		p3.add(jbtLogInButton,BorderLayout.WEST);
		p3.add(jbtSignUpButton,BorderLayout.EAST);
		
		setLayout(new BorderLayout());
		setTitle("Log In");
		setSize(640,480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//将窗口置于屏幕中央
		setLocationRelativeTo(null); 
		
		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);
		add(p3,BorderLayout.SOUTH);
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
				JOptionPane.showMessageDialog(null, "请勿输入空值", "alert", JOptionPane.ERROR_MESSAGE); 
			}
			else{
				//
				try {
					LoginMessage lgnm=new LoginMessage(name, password);
					objtoServer.writeObject(lgnm);
					objtoServer.flush();
					
					AnswerLoginMessage alm=(AnswerLoginMessage)objfromServer.readObject();
					
					if(!alm.getDoseNameExist()){
						JOptionPane.showMessageDialog(null, "不存在该用户", "警告",JOptionPane.ERROR_MESSAGE); 
					}
					else if(!alm.getIsPasswordRight()){
						JOptionPane.showMessageDialog(null, "密码错误", "警告",JOptionPane.ERROR_MESSAGE);
					}
					else{
						client.setMyName(name);
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