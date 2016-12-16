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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Message.AnswerSignupMessage;
import Message.SignupMessage;

//ע�������
public class SignUp extends JFrame{
	// ���ý�����   
    {   
        try {   
            UIManager.setLookAndFeel(/*javax.swing.UIManager.getSystemLookAndFeelClassName()*/"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        }   
    } 
	
	private Socket socket=null;
	private ObjectOutputStream objtoServer=null;
	private ObjectInputStream objfromServer=null;
	
	private JTextField jtfNameField=new JTextField(); //�û�����д
	private JPasswordField jtfPassWordField=new JPasswordField(); //������д
	private JPasswordField jtfConfirmPassWord=new JPasswordField(); //����ȷ��
	private JButton jbtSignUpButton=new JButton("Sign up"); //ע�ᰴť
	
	public SignUp(Socket socket,ObjectInputStream objfromServer,ObjectOutputStream objtoServer){
		this.socket=socket;
		this.objfromServer=objfromServer;
		this.objtoServer=objtoServer;
		setSignUpGui();
		registerSignUpListener();
	}
	//������
	public void setSignUpGui(){
		
		JLabel jlb1=new JLabel("Name");
		jlb1.setBounds(80, 50, 80, 50);
		jtfNameField.setBounds(160, 50, 200, 50);
		
		JLabel jlb2=new JLabel("Password");
		jlb2.setBounds(80, 150, 80, 50);
		jtfPassWordField.setBounds(160, 150, 200, 50);
		
		JLabel jlb3=new JLabel("Confirm");
		jlb3.setBounds(80, 250, 80, 50);
		jtfConfirmPassWord.setBounds(160, 250, 200, 50);
		
		jbtSignUpButton.setBounds(160 ,350, 160 ,36);
		
		add(jlb1);
		add(jlb2);
		add(jlb3);
		add(jtfNameField);
		add(jtfPassWordField);
		add(jtfConfirmPassWord);
		add(jbtSignUpButton);
		
		setLayout(null);
		setTitle("Sign up");
		setSize(480,480);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//������������Ļ����
		setLocationRelativeTo(null); 
	}
	//ע�������
	public void registerSignUpListener(){
		jbtSignUpButton.addActionListener(new SignUpListener());
	}
	//ע������¼�
	private class SignUpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name=jtfNameField.getText();
			String password=jtfPassWordField.getText();
			String passwordConfirm=jtfConfirmPassWord.getText();
			if(name==null||password==null||name.equals("")||password.equals("")){
				JOptionPane.showMessageDialog(null, "���벻��Ϊ��~~", "����", JOptionPane.ERROR_MESSAGE); 
			}
			else if(!password.equals(passwordConfirm)){
				JOptionPane.showMessageDialog(null, "��������������벻һ��Ŷ��","����",JOptionPane.ERROR_MESSAGE);
			}
			else{
				//
				try {
					SignupMessage supm=new SignupMessage(name, password);
					objtoServer.writeObject(supm);
					objtoServer.flush();
					
					AnswerSignupMessage asm=(AnswerSignupMessage)objfromServer.readObject();
					
					if(asm.getNameExists()){
						JOptionPane.showMessageDialog(null, "���û����Ѵ���","����",JOptionPane.ERROR_MESSAGE); 
					}
					else{
						JOptionPane.showMessageDialog(null, "ע��ɹ���");
						setVisible(false);
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
	
	
}
