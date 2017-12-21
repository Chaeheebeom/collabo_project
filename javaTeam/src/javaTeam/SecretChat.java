package javaTeam;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

	//비밀대화 생성하기
	public class SecretChat extends JDialog implements ActionListener{
			
		private JTextField roomNameText;
		private JPasswordField roomPasswdText;
		private JTable table;
		private JButton makeBtn,okBtn,cencelBtn;
		private DefaultTableModel model;
		private LoginVO lvo=null;
		private String randomRoom[]= {"잘생긴 사람 여기여기모여라!!","선남선녀방","실론즈를 위한 핵심강좌","안녕하세요~? 반갑습니다.","오늘 만난 각설이 내일도 만나네","Drop the Beat A~"
				,"하하하하하하","뚝배기 한사바리 깨실래예?","난.... 흑화한다...","자 이제 내턴 인가?"};
		public SecretChat(LoginVO lvo) {
			this.lvo=lvo;
			 //비밀대화 UI 시작
			setTitle("비밀대화방만들기");
			setBounds(100, 100, 400, 500);
			JPanel main=new JPanel();
		    
		    main.setBorder(new EmptyBorder(5, 5, 5, 5));
		    main.setLayout(new BorderLayout(0,0));
		    setContentPane(main);
			JPanel sec_main_panel=new JPanel();
			sec_main_panel.setLayout(new GridLayout(0, 2));
			JLabel roomNameLabel=new JLabel("방이름");
		    roomNameText=new JTextField();
		    JLabel roomPasswdLabel=new JLabel("비밀번호");
		    roomPasswdText=new JPasswordField();
		    sec_main_panel.add(roomNameLabel);
		    sec_main_panel.add(roomNameText);
		    sec_main_panel.add(roomPasswdLabel);
		    sec_main_panel.add(roomPasswdText);
		    main.add(sec_main_panel,BorderLayout.NORTH);
		    JPanel s_panel = new JPanel();
		    makeBtn=new JButton("방생성하기");
		    makeBtn.setEnabled(false);
		    okBtn=new JButton("방들어가기");
		    cencelBtn=new JButton("취소");
		    s_panel.add(makeBtn);
		    s_panel.add(okBtn);
		    s_panel.add(cencelBtn);
		    main.add(s_panel, BorderLayout.SOUTH);		    
		    
		    JButton btnNew = new JButton("새로고침");
		    s_panel.add(btnNew);
		    btnNew.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					table.removeAll();//기존의 것을 다 지워버림					
					roomReset();//다시 테이블에출력
				}
			});
		    
		    JScrollPane scrollPane = new JScrollPane();
		    main.add(scrollPane, BorderLayout.CENTER);
		    
		    table = new JTable();
		    scrollPane.setViewportView(table);
		    roomReset();
		    
			roomPasswdText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) { //비밀번호 입력여부에따라 방만들기 창이 켜졌다 꺼졋다함
					char pass[]=roomPasswdText.getPassword();
					String roomPasswd=new String(pass,0,pass.length);
					if(roomPasswd.equals("")) {
						makeBtn.setEnabled(false);
					}else {
						makeBtn.setEnabled(true);
					}
				}
				
			});
			
			makeBtn.addActionListener(this);
		    okBtn.addActionListener(this);
		    cencelBtn.addActionListener(this);
		    //비밀대화 UI 끝
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn=(JButton)e.getSource();
			//비밀대화창 만들기
			if(btn==makeBtn) {
				String roomName=null;
				String roomPasswd=null;;
				//방이름 설정
				roomName=roomNameText.getText();
				if(roomName.equals("")) {
					int i=(int)(Math.random()*10);
					roomName=randomRoom[i];
				}//방제목을 안정하면 랜덤으로 생김
				
				char pass[]=roomPasswdText.getPassword();
				roomPasswd=new String(pass,0,pass.length);
				//비밀번호를 스트링타입으로 바꿈
				int roomNumber=(int)(Math.random()*100);
				//방번호를 랜덤으로 정하는 것임
				//패스워드랑 룸이름 룸넘버 다 받아온상태
				RoomDAO dao=new RoomDAO();
				
				dao.roomMake(roomNumber, roomName, roomPasswd);//방정보를 DB에 넣어버리기
				//대화창으로 넘어가기	
				
				RoomVO vo=dao.selectedRoom(roomNumber);//방번호에 해당하는 이름,비밀번호정보를 받아옴
				if(vo.getRoomPasswd().equals(roomPasswd)) {//내가입력한 패스워드와 맞다면
					S_chatMain frame=new S_chatMain(lvo,vo);//비밀대화창 출력함
					frame.setVisible(true);		
					dispose();
				}	
	
			//대화창으로 들어가기
			}else if(btn==okBtn) {
				//비밀번호입력받기
				String roomPasswd=JOptionPane.showInputDialog(this, "비밀번호입력", "비밀번호입력", JOptionPane.CLOSED_OPTION);
				//테이블에있는값 가져오기		
				TableModel model=table.getModel();
				int row=table.getSelectedRow();
				try {
					String strRoomNum=String.valueOf(model.getValueAt(row, 0));//방번호예외처리 152번쨰줄
					int roomNum=Integer.parseInt(strRoomNum);//방번호 받아온것임
					RoomDAO dao=new RoomDAO();
					RoomVO vo=dao.selectedRoom(roomNum);//DB에서 방번호에맞는 방이름,비밀번호 받아왔음
					if(vo.getRoomPasswd().equals(roomPasswd)) {//내가입력한 비밀번호와 맞는지 확인하여
						S_chatMain frame=new S_chatMain(lvo,vo);//맞다면 방으로 넘어가고
						frame.setVisible(true);		
						dispose();//이창을 닫음
					}else {
						JOptionPane.showMessageDialog(this, "비밀번호를 확인해주세요");//아니라면 확인해달라고 출력
					}
				}catch(ArrayIndexOutOfBoundsException arrayE) {
					JOptionPane.showMessageDialog(this, "방을 선택해주세요", "오류", JOptionPane.ERROR_MESSAGE);
					}
			}else if(btn==cencelBtn) {
				dispose();//취소버튼이창닫음
			}
		}
		public void roomReset() {//새로고침을 위한 메소드
			Vector<RoomVO> vec=new Vector<RoomVO>();
			RoomDAO dao=new RoomDAO();
		    vec=dao.roomList();
			String columns[]= {"방번호","방이름"};
			DefaultTableModel model=new DefaultTableModel(columns, 0);
			//테이블에 뿌리기
			for(int i=0;i<vec.size();i++) {
				RoomVO vo=vec.get(i);
				String row[]= {String.valueOf(vo.roomNumber),vo.roomName};
				model.addRow(row);
			}
			table.setModel(model);
		}
	}	

