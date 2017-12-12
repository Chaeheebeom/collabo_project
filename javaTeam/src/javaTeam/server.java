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
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.sound.midi.Receiver;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.omg.PortableInterceptor.ClientRequestInfoOperations;


public class server  {
	
	public static void main(String[] args) throws IOException {
		
		Vector<ClientVO> vec=new Vector<>();//접속한 클라이언트정보를 저장할 것(소켓으로 저장)
		ClientVO vo=null;
		Socket socket = null;
		ServerSocket serverSocket =null;
		//프레임보여주기
		ServerFrame frame=new ServerFrame();
		frame.setVisible(true);
		
		try {
			frame.mainText.setText("서버오픈\n");
			serverSocket=new ServerSocket(7777);
			while(true) {
				socket=serverSocket.accept();
				vo=new ClientVO(socket);
				vec.add(vo); //접속한 클라이언트 누적.
				InetAddress inet=socket.getInetAddress(); //소켓안에있는 ip주소 inet안에는 호스트의 ip주소 이름 다 있음
				frame.mainText.append(inet.getHostAddress()+"접속\n");
				receive receive=new receive(socket, frame); 
				receive.start();//받는 쓰레드 실행
			}
			
		}catch(Exception e) {}
	}		
}
//받는 쓰레드
	class receive extends Thread{
	//소켓을 받자.
		Socket socket=null;
		ServerFrame frame=null;
		InputStream is=null;
		DataInputStream dis=null;
		
		public receive(Socket socket,ServerFrame frame){
			this.socket=socket; 
			this.frame=frame;
			try {
				is=socket.getInputStream();
				dis=new DataInputStream(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//생성자 끝
		@Override
		public void run() {
			try {//읽어온다음에 값을읽어서 텍스트필드로 나타낸뒤 센드로보내자
				while(true){  //계속읽어야하기떄문에 무한반복문
				String data=dis.readUTF();
				InetAddress inet=socket.getInetAddress();
				//값을나누는 부분 받는사람ip와 내용
				String spData[]=data.split("-");
				//중간에 뿌려주기
				frame.mainText.append(inet.getHostAddress()+">"+spData[1]+">"+spData[0]+"\n");
				}
				} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	//보내는 쓰레드
	class send extends Thread{
		Socket socket=null;
		ServerFrame frame=null;
		String spData[]=null;
		OutputStream os=null;
		DataOutputStream dos=null;
		//보내는사람 소켓 데이타 받기
		public send(Socket socket, ServerFrame frame,String spData[]){
			this.socket=socket; 
			this.frame=frame; 
			this.spData=spData;//받는사람ip[0]와 내용[1]
		}
		@Override
		public void run() {
			
			
		}
		
		
	}