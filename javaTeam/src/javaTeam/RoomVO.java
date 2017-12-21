package javaTeam;

public class RoomVO {
	int roomNumber;
	String roomName;
	String roomPasswd;
	int count;
	public RoomVO(int roomNumber, String roomName, String roomPasswd,int count) {
		super();
		this.roomNumber = roomNumber;
		this.roomName = roomName;
		this.roomPasswd = roomPasswd;
		this.count=count;
	}
	public RoomVO() {
		
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomPasswd() {
		return roomPasswd;
	}
	public void setRoomPasswd(String roomPasswd) {
		this.roomPasswd = roomPasswd;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
