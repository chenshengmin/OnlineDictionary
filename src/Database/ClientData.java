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
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/clientdata?"
                + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();
            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            sql = "create table onlinedictionaryclient(NAME char(32),password varchar(32),isonline boolean,hostname varchar(32),hostaddress varchar(32),baiduprefer INT,youdaoprefer INT,bingprefer INT,primary key(NAME))";
            int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            if (result != -1) {
                System.out.println("创建数据表成功");
                sql = "insert into onlinedictionaryclient(NAME,password,isonline,hostname,hostaddress,baiduprefer,youdaoprefer,bingprefer) values('firstclient','123456',false,'unknown','unknown',0,0,0)";
                result = stmt.executeUpdate(sql);

                sql = "select * from onlinedictionaryclient";
                ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
              
                System.out.println("客户昵称\t客户密码\t是否在线\t主机名\t主机地址\t百度点赞次数\t有道点赞次数\t必应点赞次数\t");
                while (rs.next()) {
                    System.out
                            .println(rs.getString(1) + "\t" + rs.getString(2)+"\t"+rs.getBoolean(3)+"\t"+rs.getString(4) + "\t" + rs.getString(5)+ "\t" + rs.getInt(6)+"\t"+rs.getInt(7) + "\t" + rs.getInt(8)+"\t");// 入如果返回的是int类型可以用getInt()
                }
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
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
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
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
