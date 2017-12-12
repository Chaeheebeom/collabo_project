package javaTeam;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.ClosedByInterruptException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.omg.PortableInterceptor.ClientRequestInfoOperations;


public class server  {
	
	static Vector<ClientVO> vec=new Vector<>();
	
	public static void main(String[] args) throws IOException {
		ServerFrame frame=new ServerFrame();
		frame.setVisible(true);
		Socket socket = null;
		ServerSocket serverSocket =null;
		try {
			frame.mainText.setText("서버오픈\n");
			serverSocket=new ServerSocket(7777);
			while(true) {
				socket=serverSocket.accept();
				if(socket.isConnected()) {
				Connect con=new Connect(socket,frame);
				con.start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			if(socket!=null)	
				socket.close();
			if(serverSocket!=null)
				serverSocket.close();			
		}
	}	
}


	class Connect extends Thread{
			
		Socket socket;
		InetAddress inet;
		String ip;
		ServerFrame frame;
		
		public Connect (Socket socket,ServerFrame frame){
			this.socket=socket;
			this.frame=frame;
		}
		
		@Override
		public void run() {
			//Vector<ClientVO> vec=new Vector<>();
			
			inet=socket.getInetAddress();
			frame.setText("접속: 이름-"+inet.getHostName()+", 주소-"+inet.getHostAddress());
			UserThread userThread=new UserThread(socket, inet, frame);
			userThread.start();
			if(socket.isClosed())
				frame.mainText.append("접속종료");
		}	
	}

	class UserThread extends Thread{
		ServerFrame frame;
		Socket socket;
		InetAddress inet;
		InputStream is;
		DataInputStream dis;
		String readData;
		Vector<ClientVO> ipVec=new Vector<>();
		
		public UserThread(Socket socket, InetAddress inet,ServerFrame frame) {
			this.socket=socket;
			this.inet=inet;
			this.frame=frame;
			ClientVO vo=new ClientVO(socket, inet);
			ipVec.add(vo);
		}
		
		@Override
		public void run() {	
			
			try {
				is=socket.getInputStream();
				dis=new DataInputStream(is);
				
				while(true) {
				readData=dis.readUTF();
						send(readData);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void recieve(InetAddress inet) {
			
		}
		public void send(String readData) {
			Socket sendSocket=null;
			String stData[]=new String[2];
			StringTokenizer st=new StringTokenizer(readData,"-");
			InetAddress inet=null;
			ClientVO vo=new ClientVO();
			
			while(st.hasMoreTokens());{
					int count=st.countTokens();
					System.out.println(count);
					stData[count]=st.nextToken();
					System.out.println(stData[count]);
				}
			
			for(int i=0;i<ipVec.size();i++) {
				vo=ipVec.get(i);  
				inet=vo.getInet();
				if(stData[0].equals(inet.getHostAddress())) { 
				try {
					sendSocket=vo.getSocket();
					OutputStream os=sendSocket.getOutputStream();
					DataOutputStream dos=new DataOutputStream(os);
					dos.writeUTF(inet.getHostAddress()+"-"+stData[1]);
					frame.setText(inet.getHostAddress()+">"+stData[1]+">"+stData[0]);
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}}
			}
			
			
		}
	}
	