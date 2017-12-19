package javaTeam;

public class RoomVO {
	int roomNumber;
	String roomName;
	String roomPasswd;
	public RoomVO(int roomNumber, String roomName, String roomPasswd) {
		super();
		this.roomNumber = roomNumber;
		this.roomName = roomName;
		this.roomPasswd = roomPasswd;
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
	
	
}
