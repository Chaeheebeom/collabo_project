package javaTeam;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Vector;
import java.awt.Color;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.javafx.collections.MappingChange;

import javafx.scene.control.Labeled;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

public class S_chatMain extends JFrame{

	private JPanel contentPane;
    private JMenuItem Menuexit,Menulogout;
	//private FriendFind find=new FriendFind();
	JMenuItem mntInfoFriend,mntDelFriend,mntSendMs,mntChat;
	JPanel chatPanel;
	JPanel mainPanel;
	JTextField chatField;//채팅창 입력하기
	JTextArea chatArea;//출력 하기
	private LoginVO lvo=new LoginVO();
	private RoomVO rvo=new RoomVO();
	JPanel s_chatPanel;
	JTextField s_chatField;
	JTextArea s_chatArea;
	
	public S_chatMain(LoginVO lvo,RoomVO rvo) {
		this.lvo=lvo;
		this.rvo=rvo;
		setTitle("OO\uD1A1");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 300, 590);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
			   
	    //채팅 UI
	    chatPanel =new JPanel();
	    chatPanel.setLayout(new BorderLayout(0,0));
	    JScrollPane chatScroll=new JScrollPane();
	    
	    JPanel north_panel=new JPanel();
	    north_panel.setLayout(new BorderLayout(0,0));
	    JLabel mainLabel=new JLabel("비밀대화");
	    
	    north_panel.add(mainLabel);
	    
	    JLabel roomLabel = new JLabel("");
	    roomLabel.setText(rvo.getRoomName());
	    north_panel.add(roomLabel, BorderLayout.NORTH);
	   
	    chatPanel.add(north_panel,BorderLayout.NORTH);
	    
	    JButton btnExit = new JButton("비밀대화종료");
	    north_panel.add(btnExit, BorderLayout.EAST);
	    btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomDAO dao=new RoomDAO(); //종료버튼
				stopClient();
				dispose();
			}
		});
	    
	    chatArea=new JTextArea();  //서로 대화하는 곳
	    chatScroll.setViewportView(chatArea);
	    chatPanel.add(chatScroll,BorderLayout.CENTER);
	    
	    JPanel chat_south_panel=new JPanel();
	    chatField=new JTextField();
	    JButton sendBtn=new JButton("전송");
	    chat_south_panel.setLayout(new BoxLayout(chat_south_panel, BoxLayout.X_AXIS));
	    chat_south_panel.add(chatField);
	    chat_south_panel.add(sendBtn);
	    chatPanel.add(chat_south_panel,BorderLayout.SOUTH);
	    chatField.addKeyListener(new Enter());//232줄
	    sendBtn.addActionListener(new Enter());
	    contentPane.add(chatPanel, BorderLayout.CENTER);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    contentPane.add(scrollPane, BorderLayout.EAST);

	    //채팅UI
	    
	    //채팅스레드를 실행시키는 부분
	    startClient();
	}
	
	//전송하는부분
	class Enter extends KeyAdapter implements ActionListener{
		@Override//엔터입력했을때//공개채팅과비슷하지만 여기서는 비밀번호값을 같이넘김 이유: 서버에서는 모든 클라이언트에 포딩을 하기떄문에비밀대화방에서만 출력할것과 공개채팅에서 출력할 것을 구분할 필요가 있음 
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				String id=lvo.getId();
				int roomNumber=rvo.getRoomNumber();
				String data=chatField.getText();
				send(id+"-"+roomNumber+"-"+data); //0: 보낸아이디 1:방넘버 2:내용
				chatField.setText("");
				chatArea.append("나>"+data+"\n");
			}
		}
		@Override//전송버튼눌렀을때
		public void actionPerformed(ActionEvent e) {
			String id=lvo.getId();
			int roomNumber=rvo.getRoomNumber();
			String data=chatField.getText();
			send(id+"-"+roomNumber+"-"+data); //이하동문
			chatField.setText("");
			chatArea.append("나>"+data+"\n");
		}
	}	
	
	//클라이언트가 실행되는 부분
		Socket socket;
		//시작되는 부분
		void startClient() {
			Thread thread=new Thread() { //스레드생성
				@Override
				public void run() {
					RoomDAO dao=new RoomDAO();
					try {
						socket = new Socket();
						socket.connect(new InetSocketAddress("192.168.0.67", 5004)); //접속하는 부분
						chatArea.append("연결되었습니다 "+socket.getRemoteSocketAddress()+"\n");
						String data=lvo.getId()+"-"+lvo.getId()+"님이 입장하셨습니다.-"+"-"+rvo.roomPasswd;//다른 사람에게 입장을 알리는 것//여기서도 패스워드를 날려버림
						dao.update_count(rvo, 1, rvo.getRoomNumber());//방DB에 접속헀을때 1카운트함
						send(data);                                    //이유:DB카운트가 0일경우삭제하기 위함
					}catch(Exception e) {
						if(!socket.isClosed())
							dao.update_count(rvo, -1, rvo.getRoomNumber());
							stopClient();
						return;
					}receive(); //서버에서 보낸것 받기
				} 
			};
			thread.start(); //스레드실행
		}
		//정지하는 부분
		void stopClient() {
			try {
				RoomDAO dao=new RoomDAO();
				dao.update_count(rvo, -1, rvo.getRoomNumber());
				if(!socket.isClosed() && socket!=null)
					socket.close(); //소켓이 닫혀인징않거나 비어있지않다면 닫기
			}catch(Exception e) {}
		}
		//서버에서 보낸것을 받는부분
		void receive() {
					
			while(true) {
						try {
							byte[] byteArr =new byte[100];
							InputStream is=socket.getInputStream();
							int readByte=is.read(byteArr); //값을 받는부분
							if(readByte==-1) {throw new IOException();}//읽을것이없을경우 예외던지기
							String data=new String(byteArr, 0, readByte,"UTF-8");//화면에 출력하기위한 변환
							String[] newdata=data.split("-");
								if(newdata[1].equals(String.valueOf(rvo.getRoomNumber()))) {//이 비밀대화창에 보낸것이 맞는가 확이나는 것
									if(!(newdata[0].equals(lvo.getId()))) 
										chatArea.append(newdata[0]+">"+newdata[2]+"\n");
								}
						}catch(Exception e) {e.printStackTrace();
							chatArea.append("[시스템오류:통신안됨]");
							stopClient();
							break;
						}
					}
		}
		//서버에 보내는 부분
		void send(String data) {
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						byte byteArr[]=data.getBytes("UTF-8");
						OutputStream os=socket.getOutputStream();
						os.write(byteArr);
						os.flush();
					}catch(Exception e) {
						chatArea.append("[시스템오류:통신안됨]");
						stopClient();
					}
				}
			};thread.start();
		}
}	


