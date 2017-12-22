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
				//String url="jdbc:mysql://192.168.35.229:3306/javadb?useSSL=true";//DB로 접속하는거
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
		//방정보를 받아서 생성하는 부분
		public int roomMake(int roomNumber, String roomName,String roomPasswd) {
			PreparedStatement pstmt=null;
			Connection con=null;
			String sql="insert into secretroomtbl(roomnumber, roomname, roompasswd,count) values(?,?,?,?)";
			int result=0;
			try {
				con = getConnection();
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, roomNumber);
				pstmt.setString(2, roomName);
				pstmt.setString(3, roomPasswd);
				pstmt.setInt(4, 0);
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
					int count=rs.getInt(4);
					RoomVO vo=new RoomVO(roomNumber,roomName,roomPasswd,count);
					vec.add(vo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(con,pstmt,rs);
			}
			return vec;
		}
		
		//방번호를 받고 해당하는 정보를 출력
				public RoomVO selectedRoom(int roomNumber) {
					Connection con=getConnection();
					PreparedStatement pstmt =null;
					ResultSet rs=null;
					String sql="select * from secretroomtbl where roomnumber="+roomNumber;
					RoomVO vo=null;
					try {
						con = getConnection();
						pstmt=con.prepareStatement(sql);
						rs=pstmt.executeQuery();
						while(rs.next()) {
							roomNumber=rs.getInt(1);
							String roomName=rs.getString(2);
							String roomPasswd=rs.getString(3);
							int count=rs.getInt(4);
							vo=new RoomVO(roomNumber,roomName,roomPasswd,count);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}finally {
						close(con,pstmt,rs);
					}
					return vo;
				}
				//방인원수 증감/
				public void update_count(RoomVO vo,int count, int roomNumber) {
					PreparedStatement pstmt=null;
					Connection con=null;
					String sql="update secretroomtbl set count=? where roomnumber=?";
					try {
						con = getConnection();
						pstmt=con.prepareStatement(sql);
						int newCount=vo.getCount();
						newCount=newCount+count;
						pstmt.setInt(1, newCount);
						pstmt.setInt(2, roomNumber);
						pstmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally {
						close(con,pstmt);
					}
				}
				//빈방삭제
				public boolean deleteRoom() {
					PreparedStatement pstmt=null;
					Connection con=null;
					String sql="delete from secretroomtbl where count=0";
					try {
						con = getConnection();
						pstmt=con.prepareStatement(sql);
						pstmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}finally {
						close(con,pstmt);
					}return true;
				}
				public boolean deleteRoomAll() {
					PreparedStatement pstmt=null;
					Connection con=null;
					String sql="delete from secretroomtbl where roomnumber<=100";
					try {
						con = getConnection();
						pstmt=con.prepareStatement(sql);
						pstmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}finally {
						close(con,pstmt);
					}return true;
				}
				
}
