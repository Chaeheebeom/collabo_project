package javaTeam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RoomDAO {
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
		//방정보를 입력하는 부분//생성
		public int roomMake(int roomNumber, String roomName,String roomPasswd) {
			PreparedStatement pstmt=null;
			Connection con=null;
			String sql="insert into secretroomtbl(roomnumber, roomname, roompasswd) values(?,?)";
			int result=0;
			try {
				con = getConnection();
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, roomNumber);
				pstmt.setString(2, roomName);
				pstmt.setString(3, roomPasswd);
				result=pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(con,pstmt);
			}
			return result;
		}
		//방정보를 받아오는 것
		public Vector<RoomVO> roomList() {
			Connection con=getConnection();
			PreparedStatement pstmt =null;
			ResultSet rs=null;
			String sql="select * from secretroomtbl";
			Vector<RoomVO> vec=new Vector<>();
			try {
				con = getConnection();
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					int roomNumber =rs.getInt(1);
					String roomName=rs.getString(2);
					String roomPasswd=rs.getString(3);
					RoomVO vo=new RoomVO(roomNumber,roomName,roomPasswd);
					vec.add(vo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(con,pstmt,rs);
			}
			return vec;
		}
		//방정보하나방정보를 받아오는 것
				public Vector<RoomVO> selectedRoom(int roomNum) {
					Connection con=getConnection();
					PreparedStatement pstmt =null;
					ResultSet rs=null;
					String sql="select * from secretroomtbl where roomnumber=?";
					Vector<RoomVO> vec=new Vector<>();
					try {
						con = getConnection();
						pstmt=con.prepareStatement(sql);
						pstmt.setInt(roomNum,1);
						rs=pstmt.executeQuery();
						while(rs.next()) {
							int roomNumber =rs.getInt(1);
							String roomName=rs.getString(2);
							String roomPasswd=rs.getString(3);
							RoomVO vo=new RoomVO(roomNumber,roomName,roomPasswd);
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
