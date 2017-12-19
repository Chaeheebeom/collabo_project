package javaTeam;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		
		public SecretChat(LoginVO lvo) {
			this.lvo=lvo;
			 //비밀대화 UI 시작
			setTitle("비밀대화방만들기");
			setBounds(100, 100, 400, 180);
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
		    okBtn=new JButton("방들어가기");
		    cencelBtn=new JButton("취소");
		    s_panel.add(makeBtn);
		    s_panel.add(okBtn);
		    s_panel.add(cencelBtn);
		    main.add(s_panel, BorderLayout.SOUTH);		    
		    
		    JScrollPane scrollPane = new JScrollPane();
		    main.add(scrollPane, BorderLayout.CENTER);
		    
		    table = new JTable();
		    scrollPane.setViewportView(table);
		    
		    Vector<RoomVO> vec=new Vector<>();
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
		    
			
			makeBtn.addActionListener(this);
		    okBtn.addActionListener(this);
		    cencelBtn.addActionListener(this);
		    //비밀대화 UI 끝
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn=(JButton)e.getSource();
			if(btn==makeBtn) {
				String roomName=null;
				String roomPasswd=null;;
				if(roomNameText.getText().equals(null)) {
					roomName="default";
				}else if(roomPasswdText.getPassword()==null) {
					JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요");
				}else {
					roomName=roomNameText.getText();
				}
				char pass[]=roomPasswdText.getPassword();
				roomPasswd=new String(pass,0,pass.length);
				int roomNumber=(int)(Math.random()*100);
			//패스워드랑 룸이름 룸넘버 다 받아옴
				RoomDAO dao=new RoomDAO();
				dao.roomMake(roomNumber, roomName, roomPasswd);//방정보 넣어버리기
			//대화창으로 넘어가기	
				Vector<RoomVO> vec=new Vector<>();
				RoomVO vo=new RoomVO();
				vec=dao.selectedRoom(roomNumber);
				vo=vec.get(0);
				S_chatMain frame=new S_chatMain(lvo,vo);
				frame.setVisible(true);
			dispose();
			//대화창으로 들어가기
			}else if(btn==okBtn) {
				//비밀번호입력받기
				String roomPasswd=JOptionPane.showInputDialog(this, "비밀번호입력", "비밀번호입력", JOptionPane.CLOSED_OPTION);
				//테이블에있는값 가져오기		
				TableModel model=table.getModel();
				int row=table.getSelectedRow();
				String strRoomNum=String.valueOf(model.getValueAt(row, 0));//방번호
				int roomNum=Integer.parseInt(strRoomNum);
				
				Vector<RoomVO> vec=new Vector<>();
				RoomDAO dao=new RoomDAO();
				vec=dao.selectedRoom(roomNum);
				RoomVO vo=new RoomVO();
				vo=vec.get(0);
				System.out.println(roomPasswd);
				System.out.println(vo.getRoomName()+vo.getRoomNumber()+vo.getRoomPasswd());
				//비밀번호 비교하기
				if(vo.getRoomPasswd().equals(roomPasswd)) {
					//방띄우기
					S_chatMain frame=new S_chatMain(lvo,vo);
					frame.setVisible(true);
					dispose();
				}else {
					JOptionPane.showMessageDialog(this, "비밀번호를 확인해주세요");
				}
			}else if(btn==cencelBtn) {
				dispose();
			}
		}			
	}	

