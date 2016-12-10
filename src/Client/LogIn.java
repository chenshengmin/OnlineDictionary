package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LogIn extends JFrame{
	private Client client;
	private Socket socket;
	
	private JTextField jtfNameField=new JTextField();
	private JTextField jtfPassWordField=new JTextField();
	private JButton jbtLogInButton=new JButton("Log in");
	private JButton jbtSignUpButton=new JButton("Sign up");
	
	public LogIn(Client client,Socket socket){
		this.client=client;
		this.socket=socket;
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
			setVisible(false);
			client.setVisible(true);
		}
		
	}
	
	private class SignUpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new SignUp(socket);
		}
		
	}

}