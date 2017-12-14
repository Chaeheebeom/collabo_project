package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class LoginDAO {

	public Connection getConnection(){
		Connection con=null;
		try {
			//Ŭ���� �ε�
			Class.forName("com.mysql.jdbc.Driver");
			//����̹��ε�
			String url="jdbc:mysql://localhost:3306/javadb?useSSL=true";
			//Ŀ�ؼ� ������
			con=DriverManager.getConnection(url,"root","12345");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	//�ڿ� ����
	public void close(Connection con,PreparedStatement pstmt,ResultSet rs) {
		try {
			if(rs!=null)
				rs.close();
			if(pstmt!=null)
				pstmt.close();
			if(con!=null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void close(Connection con,PreparedStatement pstmt) {
		try {
			if(pstmt!=null)
				pstmt.close();
			if(con!=null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//ȸ������
	public int login_Insert(String id, String pwd, String name, String phonnum,String gender) {
		PreparedStatement pstmt=null;
		Connection con=null;
		String sql="insert into loginTBL(id,pwd,name,phonnum,gender) values(?,?,?,?,?)";
		int result=0;
		try {
			con = getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, phonnum);
			pstmt.setString(5, gender);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(con,pstmt);
		}
		return result;
	}
	//��ü��ȸ
	public Vector<LoginVO> userlist() {
		Connection con=getConnection();
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		String sql="select * from loginTBL";
		Vector<LoginVO> vec=new Vector<>();
		try {
			con = getConnection();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				String id =rs.getString(1);
				String pwd=rs.getString(2);
				String name=rs.getString(3);
				String phonnum=rs.getString(4);
				String gender=rs.getString(5);
				LoginVO vo=new LoginVO();
				vec.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(con,pstmt,rs);
		}
		return vec;
	}

	
}
