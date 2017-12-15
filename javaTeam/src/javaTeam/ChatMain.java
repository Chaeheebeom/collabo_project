package javaTeam;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JList list = new JList();
		list.setBackground(Color.WHITE);
		panel_1.add(list);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(list, popupMenu);
		
		JMenuItem mntInfoFriend = new JMenuItem("내 친구 정보보기");
		mntInfoFriend.setFont(new Font("", Font.BOLD, 16));
		popupMenu.add(mntInfoFriend);
		
		JMenuItem mntDelFriend = new JMenuItem("친구 삭제하기");
		mntDelFriend.setFont(new Font("", Font.BOLD, 16));
		popupMenu.add(mntDelFriend);
		
		JMenuItem mntSendMs = new JMenuItem("쪽지 보내기");
		mntSendMs.setFont(new Font("", Font.BOLD, 16));
		popupMenu.add(mntSendMs);
		
		JMenuItem mntChat = new JMenuItem("대화화기");
		mntChat.setFont(new Font("", Font.BOLD, 16));
		popupMenu.add(mntChat);
		
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setListData(listStr); 
	    list.setFont(new Font("���� ���",Font.BOLD,16));
	    
	    JPanel panel_2 = new JPanel();
	    contentPane.add(panel_2, BorderLayout.SOUTH);
	        
	    Menuexit.addActionListener(this);
	    Menuinfo.addActionListener(this);
	    MenuAddFriend.addActionListener(this);
	    Menulogout.addActionListener(this);
	        
	    //채팅스레드를 실행시키는 부분
	    startClient();
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
			
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
