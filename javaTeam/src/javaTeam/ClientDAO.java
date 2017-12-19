package javaTeam;

import java.net.Socket;
import java.util.Vector;

public class ClientDAO {

	Vector<Socket> vec=null;
	
	
	public Vector<Socket> getConnectUser(){
		//유저를 돌려주는 메소드
		return vec;
	}
	
	//누적한 것을 저장하는 메소드
	public void addConnectuser(Vector<Socket> vec) {
		this.vec=vec;
		
	}
	
}
