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


public class SignUp extends JFrame{
	private Socket socket;
	
	private JTextField jtfNameField=new JTextField();
	private JTextField jtfPassWordField=new JTextField();
	private JButton jbtSignUpButton=new JButton("Sign up");
	
	public SignUp(Socket socket){
		this.socket=socket;
		
		setSignUpGui();
		registerSignUpListener();
	}
	
	public void setSignUpGui(){
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(new JLabel("Enter Your Name"),BorderLayout.WEST);
		p1.add(jtfNameField,BorderLayout.CENTER);
		
		JPanel p2=new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(new JLabel("Enter Your Password"),BorderLayout.WEST);
		p2.add(jtfPassWordField,BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		setTitle("Sign up");
		setSize(640,480);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//将窗口置于屏幕中央
		setLocationRelativeTo(null); 
		
		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);
		add(jbtSignUpButton,BorderLayout.SOUTH);
	}
	
	public void registerSignUpListener(){
		jbtSignUpButton.addActionListener(new SignUpListener());
	}
	
	private class SignUpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
		
	}
}
