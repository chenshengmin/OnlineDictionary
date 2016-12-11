package Database;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Message.*;

public class ClientData {
	
	public static void main(String[] args) throws Exception {
        /*
		Connection conn = null;
        String sql;
        // MySQL��JDBC URL��д��ʽ��jdbc:mysql://�������ƣ����Ӷ˿�/���ݿ������?����=ֵ
        // ������������Ҫָ��useUnicode��characterEncoding
        // ִ�����ݿ����֮ǰҪ�����ݿ����ϵͳ�ϴ���һ�����ݿ⣬�����Լ�����
        // �������֮ǰ��Ҫ�ȴ���javademo���ݿ�
        String url = "jdbc:mysql://localhost:3306/clientdata?"
                + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
        try {
            // ֮����Ҫʹ������������䣬����ΪҪʹ��MySQL����������������Ҫ��������������
            // ����ͨ��Class.forName�������ؽ�ȥ��Ҳ����ͨ����ʼ������������������������ʽ������
            Class.forName("com.mysql.jdbc.Driver");// ��̬����mysql����
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or��
            // new com.mysql.jdbc.Driver();
            System.out.println("�ɹ�����MySQL��������");
            // һ��Connection����һ�����ݿ�����
            conn = DriverManager.getConnection(url);
            // Statement������кܶ෽��������executeUpdate����ʵ�ֲ��룬���º�ɾ����
            Statement stmt = conn.createStatement();
            sql = "create table onlinedictionaryclient(NAME char(32),password varchar(32),isonline boolean,hostname varchar(32),hostaddress varchar(32),baiduprefer INT,youdaoprefer INT,bingprefer INT,primary key(NAME))";
            int result = stmt.executeUpdate(sql);// executeUpdate���᷵��һ����Ӱ����������������-1��û�гɹ�
            if (result != -1) {
                System.out.println("�������ݱ�ɹ�");
                sql = "insert into onlinedictionaryclient(NAME,password,isonline,hostname,hostaddress,baiduprefer,youdaoprefer,bingprefer) values('firstclient','123456',false,'unknown','unknown',0,0,0)";
                result = stmt.executeUpdate(sql);

                sql = "select * from onlinedictionaryclient";
                ResultSet rs = stmt.executeQuery(sql);// executeQuery�᷵�ؽ���ļ��ϣ����򷵻ؿ�ֵ
              
                System.out.println("�ͻ��ǳ�\t�ͻ�����\t�Ƿ�����\t������\t������ַ\t�ٶȵ��޴���\t�е����޴���\t��Ӧ���޴���\t");
                while (rs.next()) {
                    System.out
                            .println(rs.getString(1) + "\t" + rs.getString(2)+"\t"+rs.getBoolean(3)+"\t"+rs.getString(4) + "\t" + rs.getString(5)+ "\t" + rs.getInt(6)+"\t"+rs.getInt(7) + "\t" + rs.getInt(8)+"\t");// ��������ص���int���Ϳ�����getInt()
                }
            }
        } catch (SQLException e) {
            System.out.println("MySQL��������");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }*/
    }
	
	private Connection connection=null;
	private Statement statement=null;
	
	private Socket socket;
	
	public ClientData(Socket socket){
		this.socket=socket;
		try{
			String url = "jdbc:mysql://localhost:3306/clientdata?" + "user=root&password=root&useUnicode=true&characterEncoding=UTF8&useSSL=true";
			Class.forName("com.mysql.jdbc.Driver");// ��̬����mysql����
			connection=DriverManager.getConnection(url);
			statement=connection.createStatement();
		}
		catch(SQLException ex){
			System.err.println(ex);
		}
		catch(ClassNotFoundException ex){
			System.err.println(ex);
		}
	}
	
	public AnswerSignupMessage HandleSignupMessage(SignupMessage signup){
		try{
			String sql="select * from onlinedictionaryclient where NAME = '" + signup.getName() + "'";
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next()){			
				return new AnswerSignupMessage(true);
			}
			else{	
				sql="insert into onlinedictionaryclient(NAME,password,isonline,hostname,hostaddress,baiduprefer,youdaoprefer,bingprefer) "
						+"values('"+signup.getName()+"','"+signup.getPassword()+"',false,'"
						+socket.getInetAddress().getHostName()+"','"+socket.getInetAddress().getHostAddress()+"',0,0,0)";
				int result=statement.executeUpdate(sql);
				if(result!=-1){
					return new AnswerSignupMessage(false);
				}
			}
		}
		catch(SQLException ex){
			System.err.println(ex);
		}
		return new AnswerSignupMessage(true);
	}
	
	public AnswerLoginMessage HandleLoginMessage(LoginMessage login){
		try{
			String sql="select password from onlinedictionaryclient where NAME = '" + login.getName() + "'";
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next()){			
				if(rs.getString(1).equals(login.getPassword())){
					sql="update onlinedictionary set isonline=true;";
					statement.executeUpdate(sql);
					sql="update onlinedictionary set hostname='"+socket.getInetAddress().getHostName()+"';";
					statement.executeUpdate(sql);
					sql="update onlinedictionary set hostaddress='"+socket.getInetAddress().getHostAddress()+"';";
					statement.executeUpdate(sql);
					return new AnswerLoginMessage(true, true);
				}
				else{
					return new AnswerLoginMessage(true, false);
				}
			}
			else{	
				return new AnswerLoginMessage(false, false);
			}
		}
		catch(SQLException ex){
			System.err.println(ex);
		}
		return new AnswerLoginMessage(false, false);
	}
	
	
}
