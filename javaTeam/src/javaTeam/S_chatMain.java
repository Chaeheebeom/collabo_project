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
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
	   
	    chatPanel.add(north_panel,BorderLayout.NORTH);
	    
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
		@Override//엔터입력했을때
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				String id=lvo.getId();
				String passwd=rvo.getRoomPasswd();
				String data=chatField.getText();
				send(id+"-"+data+"-"+passwd);
				chatField.setText("");
			}
		}
		@Override//전송버튼눌렀을때
		public void actionPerformed(ActionEvent e) {
			String id=lvo.getId();
			String passwd=rvo.getRoomPasswd();
			String data=chatField.getText();
			send(id+"-"+data+"-"+passwd); //뒤에 아이디 붙잉기
			chatField.setText("");
		}
	}	
	
	//클라이언트가 실행되는 부분
		Socket socket;
		//시작되는 부분
		void startClient() {
			Thread thread=new Thread() { //스레드생성
				@Override
				public void run() {
					try {
						socket = new Socket();
						socket.connect(new InetSocketAddress("192.168.0.67", 5001)); //접속하는 부분
						chatArea.append("연결되었습니다 "+socket.getRemoteSocketAddress()+"\n");
						String data=lvo.getId()+"-"+lvo.getId()+"님이 입장하셨습니다.-"+"-"+rvo.roomPasswd;//다른 사람에게 입장을 알리는 것
						send(data);
					}catch(Exception e) {
						//mainText.append("서버와 통신안됨\n");
						if(!socket.isClosed())
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
				//mainText.append("접속종료\n");
				if(!socket.isClosed() && socket!=null)
					socket.close(); //소켓이 닫혀인징않거나 비어있지않다면 닫기
				//toggleBtn.setText("종료");
				//toggleBtn.setSelected(false);
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
								if(newdata.length==3) {
									if(!(newdata[0].equals(lvo.getId()))) 
										chatArea.append(newdata[0]+">"+newdata[1]+"\n");
								}
							//mainText.append("상대방"+data+"\n");
						}catch(Exception e) {e.printStackTrace();
							//mainText.append("클라reecive안됨\n");
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
						String[] newdata=data.split("-");
						chatArea.append("나>"+newdata[1]+"\n");
						os.write(byteArr);
						os.flush();
						//mainText.append("전송완료\n");
					}catch(Exception e) {
						//mainText.append("클라send안됨\n");
						stopClient();
					}
				}
			};thread.start();
		}
	
}	


