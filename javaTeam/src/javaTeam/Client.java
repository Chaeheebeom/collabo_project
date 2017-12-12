package javaTeam;

	import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.OutputStream;
	import java.net.InetAddress;
	import java.net.Socket;
	import java.util.Scanner;
import java.util.StringTokenizer;

	public class Client {

		public static void main(String[] args) {
			ClientFrame frame=new ClientFrame();
			frame.setVisible(true);
			
			Socket socket;
			InetAddress inet;
			byte ip[]=new byte[30];
	
			
			try {
				socket=new Socket("192.168.0.17",7777);
				
				inet=socket.getInetAddress();
				ip=inet.getAddress();
				
				if(socket.isConnected())
					frame.mainText.append("접속완료\n");
				ClientThread userThread=new ClientThread(socket, ip,frame);
				userThread.start();
				sendThread sendThread=new sendThread(socket, frame);
				sendThread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class ClientThread extends Thread{
		Socket socket;
		byte ip[];
		InputStream is;
		DataInputStream dis;
		ClientFrame frame;
		public ClientThread(Socket socket, byte ip[],ClientFrame frame) {
			this.socket=socket;
			this.ip=ip;
			this.frame=frame;
		}
		
		@Override
		public void run() {
			try {
				while(true) {
				is=socket.getInputStream();
				dis=new DataInputStream(is);
				
				String readData=dis.readUTF();
				String stData[]=new String[2];
				StringTokenizer st=new StringTokenizer(readData,"-");
				InetAddress inet=null;
				
				while(st.hasMoreTokens());{
						int count=st.countTokens();
						System.out.println(count);
						stData[count]=st.nextToken();
						System.out.println(stData[count]);
					}
				frame.mainText.append(stData[0]+">"+stData[1]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class sendThread extends Thread{
		Socket socket;
		ClientFrame frame;
		OutputStream os=null;
		DataOutputStream dos=null;
		
		public sendThread(Socket socket,ClientFrame frame){
			this.socket=socket;
			this.frame=frame;
			try {
				os=socket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dos=new DataOutputStream(os);
		}
		
		@Override
		public void run() {
			frame.btnSend.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String data=frame.btnSend.getText();
					try {
						dos.writeUTF(data);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		
	
		
		
	}
	
	
	
