package javaTeam;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

public class ClientVO {
		//소켓을 받는 것
		private Socket socket=new Socket();
				
		public ClientVO(Socket socket) {
			this.socket=socket;
		}
		public ClientVO() {}
		public Socket getSocket() {
			return socket;
		}		
		
		
}
