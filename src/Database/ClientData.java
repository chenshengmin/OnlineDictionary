package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ClientData {
	
	/*public static void main(String[] args) throws Exception {
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
            sql = "create table onlinedictionaryclient(NAME char(32),password varchar(32),hostname varchar(32),hostaddress varchar(32),baiduprefer INT,youdaoprefer INT,bingprefer INT,primary key(NAME))";
            int result = stmt.executeUpdate(sql);// executeUpdate���᷵��һ����Ӱ����������������-1��û�гɹ�
            if (result != -1) {
                System.out.println("�������ݱ�ɹ�");
                sql = "insert into onlinedictionaryclient(NAME,password,hostname,hostaddress,baiduprefer,youdaoprefer,bingprefer) values('firstclient','123456','null','null',0,0,0)";
                result = stmt.executeUpdate(sql);

                sql = "select * from onlinedictionaryclient";
                ResultSet rs = stmt.executeQuery(sql);// executeQuery�᷵�ؽ���ļ��ϣ����򷵻ؿ�ֵ
               
                System.out.println("�ͻ��ǳ�\t�ͻ�����\t������\t������ַ\t�ٶȵ��޴���\t�е����޴���\t��Ӧ���޴���\t");
                while (rs.next()) {
                    System.out
                            .println(rs.getString(1) + "\t" + rs.getString(2)+"\t"+rs.getString(3) + "\t" + rs.getString(4)+ "\t" + rs.getInt(5)+"\t"+rs.getInt(6) + "\t" + rs.getInt(7)+"\t");// ��������ص���int���Ϳ�����getInt()
                }
            }
        } catch (SQLException e) {
            System.out.println("MySQL��������");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }*/
	
	
}
