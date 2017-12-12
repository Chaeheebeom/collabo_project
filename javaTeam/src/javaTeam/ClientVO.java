package javaTeam;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

public class ClientVO {
	
		private Socket socket=new Socket();
		private InetAddress inet=null;
		
		
		public ClientVO(Socket socket,InetAddress inet) {
			this.socket=socket;
			this.inet=inet;
		}
		public ClientVO() {
		}
		public Socket getSocket() {
			return socket;
		}
		public InetAddress getInet() {
			return inet;
		}
		
		
}
