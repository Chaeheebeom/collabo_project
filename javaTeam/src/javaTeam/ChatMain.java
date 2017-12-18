package javaTeam;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.awt.Color;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChatMain extends JFrame implements ActionListener{

	private JPanel contentPane;
	private String[] listStr = {"[minseok]","[jiung] ","[heebum]"};
    private JMenuItem Menuexit,Menulogout,MenuAddFriend,Menuinfo;
	private FriendFind find=new FriendFind();
	private Information info=new Information();
	JMenuItem mntInfoFriend,mntDelFriend,mntSendMs,mntChat;
	JPanel chatPanel;
	JPanel mainPanel;
	JTextField chatField;//채팅창 입력하기
	JTextArea chatArea;//출력 하기
	
	public ChatMain() {
		setTitle("OO\uD1A1");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100, 100, 300, 590);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		mnNewMenu.setFont(new Font("", Font.BOLD, 20));
		menuBar.add(mnNewMenu);
		
		Menuinfo = new JMenuItem("내 정보 보기");
		mnNewMenu.add(Menuinfo);
		
		MenuAddFriend = new JMenuItem("친구추가");
		mnNewMenu.add(MenuAddFriend);
		
		Menulogout = new JMenuItem("로그아웃");
		mnNewMenu.add(Menulogout);
		
		Menuexit = new JMenuItem("종료");
		mnNewMenu.add(Menuexit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JList list = new JList();
		list.setBackground(Color.WHITE);
		mainPanel.add(list);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(list, popupMenu);  //popmenudp 리스트
		//우클릭했을때 뜨는것들
		mntInfoFriend = new JMenuItem("내 친구 정보보기");
		mntInfoFriend.setFont(new Font("", Font.BOLD, 16));
		popupMenu.add(mntInfoFriend);
		
		mntDelFriend = new JMenuItem("친구 삭제하기");
		mntDelFriend.setFont(new Font("", Font.BOLD, 16));
		popupMenu.add(mntDelFriend);
		
		mntSendMs = new JMenuItem("쪽지 보내기");
		mntSendMs.setFont(new Font("", Font.BOLD, 16));
		popupMenu.add(mntSendMs);
		
		mntChat = new JMenuItem("대화화기");
		mntChat.setFont(new Font("", Font.BOLD, 16));
		popupMenu.add(mntChat);
		//popupMenu에 속해있는 것들
		//mntInfoFriend.addMouseListener(new Mouse());
		//mntDelFriend.addMouseListener(new Mouse());
		//mntSendMs.addMouseListener(new Mouse());
		mntChat.addMouseListener(new Mouse());//마우스이벤트 설정하기
		
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setListData(listStr);  //리스트에 집어넣기
	    list.setFont(new Font("���� ���",Font.BOLD,16));
	    
	    JPanel panel_2 = new JPanel();
	    contentPane.add(panel_2, BorderLayout.SOUTH);
	        
	    Menuexit.addActionListener(this);
	    Menuinfo.addActionListener(this);
	    MenuAddFriend.addActionListener(this);
	    Menulogout.addActionListener(this);
	     
	    //채팅 UI
	    chatPanel =new JPanel();
	    chatPanel.setLayout(new BorderLayout(0,0));
	    JScrollPane chatScroll=new JScrollPane();
	    
	    JPanel north_panel=new JPanel();
	    north_panel.setLayout(new BorderLayout(0,0));
	    JButton backBtn=new JButton("이전");
	    north_panel.add(backBtn,BorderLayout.EAST);
	    chatPanel.add(north_panel,BorderLayout.NORTH);
	    backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(chatPanel);
				contentPane.add(mainPanel); 
				setBounds(100, 100, 300, 590);
				contentPane.repaint();
			}
		});
	    
	    chatArea=new JTextArea();
	    chatScroll.setViewportView(chatArea);
	    chatPanel.add(chatScroll,BorderLayout.CENTER);
	    
	    JPanel chat_south_panel=new JPanel();
	    chat_south_panel.setLayout(new GridLayout(0,2));
	    chatField=new JTextField();
	    JButton sendBtn=new JButton("전송");
	    chat_south_panel.add(chatField);
	    chat_south_panel.add(sendBtn);
	    chatPanel.add(chat_south_panel,BorderLayout.SOUTH);
	    chatField.addKeyListener(new Enter());//232줄
	    sendBtn.addActionListener(new Enter());
	    
	    //채팅스레드를 실행시키는 부분
	    startClient();
	}
	private static void addPopup(Component component, final JPopupMenu popup) { //84번째줄
			
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JMenuItem jm = (JMenuItem) e.getSource();
		
		if(jm == Menuexit) {
			//종료
			int result=JOptionPane.showConfirmDialog(this, "종료하시겠습니까?", "프로그램종료", JOptionPane.YES_NO_OPTION);
				if(result==0) {
					System.exit(0);
				}
		}else if(jm == MenuAddFriend) {
			//친구추가
			find.setVisible(true);			
		}else if(jm == Menuinfo) {
			//정보보기
			info.setVisible(true);
		}else if(jm == Menulogout) {
			//로그아웃
			stopClient();//채팅스레드닫기
			dispose();//이창닫기
			login frame=new login();
			frame.setVisible(true);//로그인창 띄우기			
		}
	}
	//마우스실행부분
	class Mouse extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			JMenuItem mnt=(JMenuItem)e.getSource();
			if(mnt==mntInfoFriend) {//친구정보보기
				
			}else if(mnt==mntDelFriend) { //친구삭제하기
				
			}else if(mnt==mntChat) { //채팅하기
				contentPane.remove(mainPanel);
				contentPane.add(chatPanel);
				setBounds(100, 100, 300, 600);
				contentPane.repaint();
			}		
		}
	}
	//전송하는부분
	class Enter extends KeyAdapter implements ActionListener{
		@Override//엔터입력했을때
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				String data=chatField.getText();
				chatArea.append("나>"+data+"\n");
				send(data);
				chatField.setText("");
			}
		}
		@Override//전송버튼눌렀을때
		public void actionPerformed(ActionEvent e) {
			String data=chatField.getText();
			chatArea.append(data+"\n");
			send(data);
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
						//mainText.append("연결되었습니다 "+socket.getRemoteSocketAddress()+"\n");
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
							chatArea.append("상대방>"+data+"\n");
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
