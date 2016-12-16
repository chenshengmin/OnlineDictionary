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

//登录功能类
public class LogIn extends JFrame{
	// 设置界面风格   
    {   
        try {   
            UIManager.setLookAndFeel(/*javax.swing.UIManager.getSystemLookAndFeelClassName()*/"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        }   
    } 
	
	private Client client; //需要获得客户端主界面引用
	private Socket socket; 
	private ObjectOutputStream objtoServer=null;
	private ObjectInputStream objfromServer=null;
	private SignUp signUp; //可能需要弹出的注册界面
	
	private JTextField jtfNameField=new JTextField(); //输入用户名
	private JPasswordField jtfPassWordField=new JPasswordField(); //输入密码
	private JButton jbtLogInButton=new JButton("Log in"); //确认登录按钮
	private JButton jbtSignUpButton=new JButton("Sign up"); //注册按钮
	
	public LogIn(Client client,Socket socket,ObjectInputStream objfromServer,ObjectOutputStream objtoServer){
		this.client=client;
		this.socket=socket;
		this.objfromServer=objfromServer;
		this.objtoServer=objtoServer;
		

		setLogInGui();
		registerlogInListener();
	}
	//画界面
	public void setLogInGui(){
		
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
		//将窗口置于屏幕中央
		setLocationRelativeTo(null); 
	}
	//注册监听器
	public void registerlogInListener(){
		jbtLogInButton.addActionListener(new LogInListener());
		jbtSignUpButton.addActionListener(new SignUpListener());
	}
	//监听登录事件
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
	//监听注册事件
	private class SignUpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			signUp=new SignUp(socket,objfromServer,objtoServer);
		}
		
	}

}