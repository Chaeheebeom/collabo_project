package javaTeam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class LoginDAO {
	//입 출력 기타등등
	public Connection getConnection(){
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/javadb?useSSL=true";
			con=DriverManager.getConnection(url,"root","12345");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
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
	//회원가입을 하는부분
	public int login_Insert(String id, String pwd, String name,int age, int phonenum,String gender) {
		PreparedStatement pstmt=null;
		Connection con=null;
		String sql="insert into loginTBL(id,pwd,name,age,phonenum,gender) values(?,?,?,?,?,?)";
		int result=0;
		try {
			con = getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setInt(4, age);
			pstmt.setInt(5, phonenum);
			pstmt.setString(6, gender);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(con,pstmt);
		}
		return result;
	}
	//유저정보를 받아오는부분
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
				int prcode =rs.getInt(1);
				String id=rs.getString(2);
				String pwd=rs.getString(3);
				String name=rs.getString(4);
				int age=rs.getInt(5);
				int phonenum=rs.getInt(6);
				String gender=rs.getString(7);
				LoginVO vo=new LoginVO(prcode, id, pwd, name,phonenum,gender,age);
				vec.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(con,pstmt,rs);
		}
		return vec;
	}
	
	//아이디를 받아오는부분
	public Vector<String> getId() {
		Connection con=getConnection();
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		String sql="select * from loginTBL";
		Vector<String> vec=new Vector<>();
		try {
			con = getConnection();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				String id =rs.getString(2);
				vec.add(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(con,pstmt,rs);
		}
		return vec;
	}//아이디와 비밀번호를 받아오는부분
	public Vector<LoginVO> get_pw_id() {
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
				String id=rs.getString(2);
				String pwd=rs.getString(3);
				LoginVO vo=new LoginVO(id, pwd);
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
