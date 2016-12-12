package Database;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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
	
	public AnswerSignupMessage handleSignupMessage(SignupMessage signup){
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
	
	public AnswerLoginMessage handleLoginMessage(LoginMessage login){
		try{
			String sql="select password from onlinedictionaryclient where NAME = '" + login.getName() + "';";
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next()){			
				if(rs.getString(1).equals(login.getPassword())){
					//���ĵ�¼�û��ĵ�¼��Ϣ����������������ַ
					sql="update onlinedictionaryclient set isonline=true where NAME = '"+login.getName() + "';";
					statement.executeUpdate(sql);
					
					sql="update onlinedictionaryclient set hostname='"+socket.getInetAddress().getHostName()
							+"' where NAME = '"+login.getName() + "';";
					statement.executeUpdate(sql);
					
					sql="update onlinedictionaryclient set hostaddress='"+socket.getInetAddress().getHostAddress()
							+"' where NAME = '"+login.getName() + "';";
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
	
	public void handleLikeUpdateMessage(String clientName,LikeUpdateMessage likeUpdate){
		try{
			if(likeUpdate.getDicName().equals("Baidu")){
				String sql="update onlinedictionaryclient set baiduprefer=baiduprefer+("+likeUpdate.getUpdate()+") where NAME = '"+ clientName + "';";
				statement.executeUpdate(sql);
			}
			else if(likeUpdate.getDicName().equals("Youdao")){
				String sql="update onlinedictionaryclient set youdaoprefer=youdaoprefer+("+likeUpdate.getUpdate()+") where NAME = '"+ clientName + "';";
				statement.executeUpdate(sql);
			}
			else if(likeUpdate.getDicName().equals("Bing")){
				String sql="update onlinedictionaryclient set bingprefer=bingprefer+("+likeUpdate.getUpdate()+") where NAME = '"+ clientName + "';";
				statement.executeUpdate(sql);
			}
		}
		catch(SQLException ex){
			System.err.println(ex);
		}
	}
	
	public String[] handleWordSearchMessage(String clientName,WordSearchMessage wsm){
		String[] dicPriority=new String[3];
		for(int i=0;i<3;i++){
			dicPriority[i]="";
		}
		
		int baiduPrefer=-1;
		int youdaoPrefer=-1;
		int bingPrefer=-1;
		
		try{
			if(wsm.getBaidu()){
				String sql="select baiduprefer from onlinedictionaryclient where NAME = '" + clientName + "';";
				ResultSet rs=statement.executeQuery(sql);
				if(rs.next())
					baiduPrefer=rs.getInt(1);
			}
			if(wsm.getYoudao()){
				String sql="select youdaoprefer from onlinedictionaryclient where NAME = '" + clientName + "';";
				ResultSet rs=statement.executeQuery(sql);
				if(rs.next())
					youdaoPrefer=rs.getInt(1);
			}
			if(wsm.getBing()){
				String sql="select bingprefer from onlinedictionaryclient where NAME = '" + clientName + "';";
				ResultSet rs=statement.executeQuery(sql);
				if(rs.next())
					bingPrefer=rs.getInt(1);
			}
		}
		catch(SQLException ex){
			System.err.println(ex);
		}
		
		if(baiduPrefer>=youdaoPrefer){
			if(baiduPrefer>=bingPrefer){
				if(youdaoPrefer>=bingPrefer){
					if(baiduPrefer!=-1){
						dicPriority[0]="Baidu";
					}
					if(youdaoPrefer!=-1){
						dicPriority[1]="Youdao";
					}
					if(bingPrefer!=-1){
						dicPriority[2]="Bing";
					}
				}
				else{
					if(baiduPrefer!=-1){
						dicPriority[0]="Baidu";
					}
					if(youdaoPrefer!=-1){
						dicPriority[2]="Youdao";
					}
					if(bingPrefer!=-1){
						dicPriority[1]="Bing";
					}
				}
			}
			else{
				if(baiduPrefer!=-1){
					dicPriority[1]="Baidu";
				}
				if(youdaoPrefer!=-1){
					dicPriority[2]="Youdao";
				}
				if(bingPrefer!=-1){
					dicPriority[0]="Bing";
				}
			}
		}
		else{
			if(youdaoPrefer>=bingPrefer){
				if(baiduPrefer>=bingPrefer){
					if(baiduPrefer!=-1){
						dicPriority[1]="Baidu";
					}
					if(youdaoPrefer!=-1){
						dicPriority[0]="Youdao";
					}
					if(bingPrefer!=-1){
						dicPriority[2]="Bing";
					}
				}
				else{
					if(baiduPrefer!=-1){
						dicPriority[2]="Baidu";
					}
					if(youdaoPrefer!=-1){
						dicPriority[0]="Youdao";
					}
					if(bingPrefer!=-1){
						dicPriority[1]="Bing";
					}
				}
			}
			else{
				if(baiduPrefer!=-1){
					dicPriority[2]="Baidu";
				}
				if(youdaoPrefer!=-1){
					dicPriority[1]="Youdao";
				}
				if(bingPrefer!=-1){
					dicPriority[0]="Bing";
				}
			}
		}
	
		return dicPriority;
	}
	
	public AnswerCheckClientsMessage handleCheckClientsMessage(String clientName,CheckClientsMessage ccm){
		ArrayList<String> onlineClientsList=new ArrayList<String>();
		ArrayList<String> offlineClientsList=new ArrayList<String>();
		
		try{
			String sql="select NAME from onlinedictionaryclient where isonline = true;";
			ResultSet rs=statement.executeQuery(sql);
			while(rs.next()){
				String name=rs.getString(1);
				if(!name.equals(clientName)){
					onlineClientsList.add(name);
				}
			}
		}
		catch(SQLException ex){
			System.err.println(ex);
		}
		
		try{
			String sql="select NAME from onlinedictionaryclient where isonline = false;";
			ResultSet rs=statement.executeQuery(sql);
			while(rs.next()){			
				offlineClientsList.add(rs.getString(1));
			}
		}
		catch(SQLException ex){
			System.err.println(ex);
		}
		
		AnswerCheckClientsMessage accm=new AnswerCheckClientsMessage(onlineClientsList, offlineClientsList);
		
		return accm;
	}
	
	public void handleClientShutDown(String clientName){
		try{
			String sql="update onlinedictionaryclient set isonline=false where NAME = '"+ clientName + "';";
			statement.executeUpdate(sql);
		}
		catch(SQLException ex){
			System.err.println(ex);
		}
	}
	
}
