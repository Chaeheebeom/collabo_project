package javaTeam;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class Find extends JFrame {
	//아이디랑 비밀번호찾기
	private JPanel contentPane;
	private JButton btnIdFind,btnPwFind;
	
	public Find() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x버튼눌렀을때 로그인화면으로가기
		setTitle("\uC544\uC774\uB514/\uBE44\uBC00\uBC88\uD638 \uCC3E\uAE30");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("\uC544\uC774\uB514/\uBE44\uBC00\uBC88\uD638 \uCC3E\uAE30");
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		btnIdFind = new JButton("아이디로 찾기");
		panel_1.add(btnIdFind);
		
		btnPwFind = new JButton("비밀번호로 찾기");
		btnPwFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_1.add(btnPwFind);
		pack();
		btnIdFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				FindId id=new FindId();
				id.setVisible(true);
			}
		});
		btnPwFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				FindPw pw=new FindPw();
				pw.setVisible(true);
			}
		});
	}
	
	//아이디찾는 것
	class FindId extends JFrame implements ActionListener{
		private JPanel contentPane;
		private JButton btnFind,btnCancel;
		private JLabel mainLabel,nameLabel,numLabel;
		private JTextField nameText,numText;
		public FindId() {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x버튼눌렀을때 로그인화면으로가기
			setTitle("아이디찾기");
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			
			JPanel panel = new JPanel();
			contentPane.add(panel, BorderLayout.NORTH);
			mainLabel=new JLabel("아이디 찾기");
			panel.add(mainLabel);
			
			JPanel centerPanel=new JPanel();
			centerPanel.setLayout(new GridLayout(0, 2, 0, 0));
			contentPane.add(centerPanel,BorderLayout.CENTER);		
			nameLabel=new JLabel("이름");
			nameText=new JTextField();
			nameText.setColumns(20);
			numLabel=new JLabel("핸드폰번호");
			numText=new JTextField();
			numText.setColumns(20);
			centerPanel.add(nameLabel);
			centerPanel.add(nameText);
			centerPanel.add(numLabel);
			centerPanel.add(numText);
			
			JPanel southPanel=new JPanel();
			contentPane.add(southPanel,BorderLayout.SOUTH);
			btnFind=new JButton("찾기");
			btnCancel=new JButton("취소");
			southPanel.add(btnFind);
			southPanel.add(btnCancel);
			pack(); //UI구현 끝
			btnFind.addActionListener(this);
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose(); //취소버튼 
				}
			});
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//찾기하는 부분 입력된 이름과 핸드폰 번호와 dB에있는것들을 대조하는 것
			String name=nameText.getText();//입력한 이름
			String strnum=numText.getText();//입력한 핸드폰 번호
			String id=null;
			int num=0;
			try {
				num=Integer.parseInt(strnum);
			}catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "숫자만 입력해 주세요");//예외처리
				nameText.selectAll();
				}
			LoginDAO dao=new LoginDAO();
			LoginVO vo=new LoginVO();
			Vector<LoginVO> vec=new Vector<>();
			vec=dao.userlist();//DB에서 받아오기
			for(int i=0;i<vec.size();i++) {
				vo=vec.get(i);
					if(vo.getName().equals(name) && vo.getPhonenum()==num) { //비교하는 부분
						id=vo.getId();
						JOptionPane.showMessageDialog(this, "당신의 아이디는 : "+id+" 입니다");
						dispose();
						break;
					}else {
						JOptionPane.showMessageDialog(this, "이름 혹은 전화번호를 정확히 입력해 주세요");
						break;
					}
			}
		}	
	}
	//비밀번호찾는 것 만들어야됨
	class FindPw extends JFrame implements ActionListener{
		private JPanel contentPane;
		private JButton btnFind,btnCancel;
		private JLabel mainLabel,nameLabel,idLabel;
		private JTextField nameText,idText;
		
		public FindPw() {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x버튼눌렀을때 로그인화면으로가기
			setTitle("비밀번호찾기");
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			
			JPanel panel = new JPanel();
			contentPane.add(panel, BorderLayout.NORTH);
			mainLabel=new JLabel("비밀번호 찾기");
			panel.add(mainLabel);
			
			JPanel centerPanel=new JPanel();
			centerPanel.setLayout(new GridLayout(0, 2, 0, 0));
			contentPane.add(centerPanel,BorderLayout.CENTER);		
			nameLabel=new JLabel("이름");
			nameText=new JTextField();
			nameText.setColumns(20);
			idLabel=new JLabel("아이디");
			idText=new JTextField();
			idText.setColumns(20);
			centerPanel.add(nameLabel);
			centerPanel.add(nameText);
			centerPanel.add(idLabel);
			centerPanel.add(idText);

			JPanel southPanel=new JPanel();
			contentPane.add(southPanel,BorderLayout.SOUTH);
			btnFind=new JButton("찾기");
			btnCancel=new JButton("취소");
			southPanel.add(btnFind);
			southPanel.add(btnCancel);
			pack(); //UI구현 끝
			btnFind.addActionListener(this);
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose(); //취소버튼 
				}
			});
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//찾기하는 부분 입력된 이름과 핸드폰 번호와 dB에있는것들을 대조하는 것
			String name=nameText.getText();//입력한 이름
			String strId=idText.getText();//입력한 핸드폰 번호
			String pwd=null;
			
			LoginDAO dao=new LoginDAO();
			LoginVO vo=new LoginVO();
			Vector<LoginVO> vec=new Vector<>();
			vec=dao.userlist();//DB에서 받아오기
			for(int i=0;i<vec.size();i++) {
				vo=vec.get(i);
					if(vo.getName().equals(name) && vo.getId()==strId) { //비교하는 부분
						pwd=vo.getPwd();
						JOptionPane.showMessageDialog(this, "당신의 비밀번호는 : "+pwd+" 입니다");
						dispose();
						break;
					}else {
						JOptionPane.showMessageDialog(this, "이름 혹은 아이디를 정확히 입력해 주세요");
						break;
					}
			}
		}	
	}
}


