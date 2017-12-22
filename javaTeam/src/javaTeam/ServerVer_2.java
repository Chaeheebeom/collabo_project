package javaTeam;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class ServerVer_2 extends JFrame implements ActionListener{	
			//UI구현
			public static void main(String[] args)  {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						ServerVer_2 frame=new ServerVer_2();
						frame.setVisible(true);
					}
				});
				
			}
			private JPanel contentPane;
			private JTextArea mainText;
			private JToggleButton togglebtn;
			JTextArea s_mainText; 
			
			public ServerVer_2() {
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBounds(100, 100, 450, 300);
				contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				contentPane.setLayout(new BorderLayout(0, 0));
				setContentPane(contentPane);
				
				JPanel panel = new JPanel();
				contentPane.add(panel, BorderLayout.NORTH);
				
				JLabel lblNewLabel = new JLabel("공개채팅Server / 비밀대화채팅Server");
				panel.add(lblNewLabel);
				
				JPanel panel_1 = new JPanel();
				contentPane.add(panel_1, BorderLayout.SOUTH);
				
				togglebtn = new JToggleButton("서버정지");
				panel_1.add(togglebtn);
				
				JPanel panel_2 = new JPanel();
				contentPane.add(panel_2, BorderLayout.CENTER);
				panel_2.setLayout(new GridLayout(0, 2, 0, 0));
				
				JScrollPane scrollPane = new JScrollPane();
				panel_2.add(scrollPane);
				
				mainText = new JTextArea();
				mainText.setLineWrap(true);
				scrollPane.setViewportView(mainText);
				
				JScrollPane scrollPane_1 = new JScrollPane();
				panel_2.add(scrollPane_1);
				
				s_mainText = new JTextArea();
				scrollPane_1.setViewportView(s_mainText);
				togglebtn.addActionListener(this); //액션리스너
			}
			//버튼동작부분
			@Override
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle=(JToggleButton)e.getSource();
				S_server start=new S_server();
				if(toggle.isSelected()) {
					toggle.setText("서버실행중");
					startServer();
					start.startServer();
				}else {
					stopServer();
					start.stopServer();
				}
			}
			
			
	//서버동작부분
		ExecutorService executorService; //스레드풀 메소드
		ServerSocket serverSocket; //서버소켓
		List<Client> connections = new Vector<Client>(); //연결된 클라이언트를 저장하는 것
		RoomDAO dao=new RoomDAO();
		//서버시작
		void startServer() {
			//executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); 
			//ExecutorService 객체를 얻을려면 Executors.newFixedThreadPool 사용 Runtime.getRuntime().availableProcessors()==cpu코어수 만큼 스레드 생성
			executorService = Executors.newFixedThreadPool(100);
			try {
				serverSocket=new ServerSocket(); //객체생성
				serverSocket.bind(new InetSocketAddress(5001)); //5001번포트와 연결
			}catch(Exception e) {
				if(!serverSocket.isClosed())
					stopServer();
				return; //예외처리
			}
			//스레드로 실행하는 부분
			Runnable runnable=new Runnable() {
				@Override
				public void run() {
					mainText.append("서버시작\n");
					while(true) {
						try {
							Socket socket=serverSocket.accept(); //접속
							String message = "접속 : "+socket.getRemoteSocketAddress()
							+Thread.currentThread().getName();
							mainText.append(message+"\n");//메인텍스트에 뿌려주기
							Client client=new Client(socket);//클라이언트객체생성
							connections.add(client);//벡터에저장
							mainText.append("[접속자수 :"+connections.size()+"명]\n");
						}catch(Exception e) {
							if(!serverSocket.isClosed())
								stopServer();
							break;//위와 같은 예외처리
						}
					}
				}
			};
			executorService.submit(runnable);  //스레드풀에서처리
		}		
		//서버정지
		void stopServer()  {
			Iterator<Client> iterator=connections.iterator(); //반복자 얻어내기
			try {
				while(iterator.hasNext()) {
					Client client = iterator.next();
					client.socket.close(); //하나씩 클라이언트에 집어넣어 소켓닫기
					iterator.remove(); //그리구 지우기
				}
				if(!serverSocket.isClosed())
					serverSocket.close();//서버닫기
				if(executorService!=null && !executorService.isShutdown())
					executorService.shutdown(); //스레드풀닫기
				mainText.append("일반서버종료\n");
			}catch(Exception e) {}
			//togglebtn.setText("서버정지");
			//togglebtn.setSelected(false);
		}
		//클라이언트가 접속할때마다 데이터를 받고 보내는 스레드생성
		class Client{
			Socket socket;
			
			Client(Socket socket){
				this.socket=socket;
				receive();
			}
			//데이터받는 것
			void receive() {
				Runnable runnable=new Runnable() {
					@Override
					public void run() {
						try {
							while(true) {
								byte[] byteArr=new byte[100];
								InputStream is=socket.getInputStream();  
								int readByte=is.read(byteArr);//입력받는부분
								if(readByte==-1) {throw new IOException();}//예외처리
								String message=socket.getRemoteSocketAddress()+"에서 요청처리함\n";
								String Data=new String(byteArr, 0, readByte,"UTF-8");//보내기위환 변환처리
								mainText.append(message+"[내용: "+Data+"]\n");//메인창에 띄우기
								for(Client client: connections) 
									client.send(Data);	
								}
						}catch(Exception e) {
							try {
								connections.remove(Client.this);
								String message = "[통신종료 : "+socket.getRemoteSocketAddress()+"]\n";
								connections.remove(socket);
								mainText.append(message);
								mainText.append("[접속자수 :"+connections.size()+"명]\n");
								socket.close();
							}catch(Exception E) {}
						}
						
					}
				};
				executorService.submit(runnable);
			}
			//데이터보내는것
			void send(String data) {
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						try {
							byte[] byteArr=data.getBytes("UTF-8"); //보낸것을 받기
							OutputStream os=socket.getOutputStream(); 
							os.write(byteArr); //받아서 보내기
							os.flush(); //메모리풀어주기
						}catch(Exception e) {
							try {
								String message="[송신불가 : "+socket.getRemoteSocketAddress()+"]\n";
								mainText.append(message);
								connections.remove(Client.this);
								socket.close();
							}catch(Exception e2) {}
						}
					}
				};
				executorService.submit(runnable);
			}
		}
		//비밀대화방을위한
		class S_server{
			//서버동작부분
			ExecutorService s_executorService; //스레드풀 메소드
			ServerSocket serverSocket; //서버소켓
			List<Client> connections = new Vector<Client>(); //연결된 클라이언트를 저장하는 것
			RoomDAO dao=new RoomDAO();
			//서버시작
			void startServer() {
				//s_executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); 
				//ExecutorService 객체를 얻을려면 Executors.newFixedThreadPool 사용 Runtime.getRuntime().availableProcessors()==cpu코어수 만큼 스레드 생성
				s_executorService = Executors.newFixedThreadPool(100);
				try {
					serverSocket=new ServerSocket(); //객체생성
					serverSocket.bind(new InetSocketAddress(5004)); //5004번포트와 연결
				}catch(Exception e) {
					if(!serverSocket.isClosed())
						stopServer();
					return; //예외처리
				}
				//스레드로 실행하는 부분
				Runnable runnable=new Runnable() {
					@Override
					public void run() {
						s_mainText.append("비밀대화서버시작\n");
						while(true) {
							try {
								Socket socket=serverSocket.accept(); //접속
								String message = "접속 : "+socket.getRemoteSocketAddress()
								+Thread.currentThread().getName();
								s_mainText.append(message+"\n");//메인텍스트에 뿌려주기
								Client client=new Client(socket);//클라이언트객체생성
								connections.add(client);//벡터에저장
								s_mainText.append("[비밀대화방 이용자 수 :"+connections.size()+"명]\n");
							}catch(Exception e) {
								if(!serverSocket.isClosed())
									stopServer();
								break;//위와 같은 예외처리
							};
						}
					}
				};
				s_executorService.submit(runnable);  //스레드풀에서처리
			}		
			//서버정지
			void stopServer()  {
				RoomDAO dao=new RoomDAO();
				dao.deleteRoomAll();
				Iterator<Client> iterator=connections.iterator(); //반복자 얻어내기
				try {
					while(iterator.hasNext()) {
						Client client = iterator.next();
						client.socket.close(); //하나씩 클라이언트에 집어넣어 소켓닫기
						iterator.remove(); //그리구 지우기
					}
					if(!serverSocket.isClosed())
						serverSocket.close();//서버닫기
					if(executorService!=null && !executorService.isShutdown())
						executorService.shutdown(); //스레드풀닫기
					s_mainText.append("비밀대화서버종료\n");
				}catch(Exception e) {}
			}
			//클라이언트가 접속할때마다 데이터를 받고 보내는 스레드생성
			class Client{
				Socket socket;
				
				Client(Socket socket){
					this.socket=socket;
					receive();
				}
				//데이터받는 것
				void receive() {
					Runnable runnable=new Runnable() {
						@Override
						public void run() {
							try {
								while(true) {
									byte[] byteArr=new byte[100];
									InputStream is=socket.getInputStream();  
									int readByte=is.read(byteArr);//입력받는부분
									if(readByte==-1) {throw new IOException();}//예외처리
									String message=socket.getRemoteSocketAddress()+"에서 요청처리함\n";
									String Data=new String(byteArr, 0, readByte,"UTF-8");//보내기위환 변환처리
									String newData[]=Data.split("-");
									s_mainText.append(message+"[방이름 : "+newData[1]+"\n"+newData[0]+">"+newData[2]+"]\n");//메인창에 띄우기
									for(Client client: connections) 
										client.send(Data);	
									}
							}catch(Exception e) {
								try {
									connections.remove(Client.this);
									String message = "[통신종료 : "+socket.getRemoteSocketAddress()+"]\n";
									connections.remove(socket);
									s_mainText.append(message);
									s_mainText.append("[비밀대화방 이용자수 :"+connections.size()+"명]\n");
									socket.close();
								}catch(Exception E) {}
							}
							
						}
					};
					s_executorService.submit(runnable);
				}
				//데이터보내는것
				void send(String data) {
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							try {
								byte[] byteArr=data.getBytes("UTF-8"); //보낸것을 받기
								OutputStream os=socket.getOutputStream(); 
								os.write(byteArr); //받아서 보내기
								os.flush(); //메모리풀어주기
							}catch(Exception e) {
								try {
									String message="[송신불가 : "+socket.getRemoteSocketAddress()+"]\n";
									s_mainText.append(message);
									connections.remove(Client.this);
									socket.close();
								}catch(Exception e2) {}
							}
						}
					};
					s_executorService.submit(runnable);
				}
			}
		}
		
}
