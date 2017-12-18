package javaTeam;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

public class login extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField txtid;
	private JPasswordField txtpwd;
	private Find find=new Find();
	private membership member=new membership();
	private JButton btnLogin,btnMake,btnFind;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblOo = new JLabel("OO\uD1A1");
		panel.add(lblOo);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("\uC544\uC774\uB514");
		lblNewLabel.setFont(new Font("����", Font.BOLD, 12));
		panel_1.add(lblNewLabel);
		
		txtid = new JTextField();
		txtid.setColumns(10);
		panel_1.add(txtid);
		
		JLabel label_1 = new JLabel("\uBE44\uBC00\uBC88\uD638");
		label_1.setFont(new Font("����", Font.BOLD, 12));
		panel_1.add(label_1);
		
		txtpwd= new JPasswordField();
		txtpwd.setColumns(10);
		txtpwd.setEchoChar('*');
		panel_1.add(txtpwd);
		txtpwd.addKeyListener(new Enter());
	
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnLogin = new JButton("\uB85C\uADF8\uC778");
		panel_2.add(btnLogin);
		
		btnMake = new JButton("\uD68C\uC6D0\uAC00\uC785");
		panel_2.add(btnMake);
		
		btnFind = new JButton("\uC544\uC774\uB514/\uBE44\uBC00\uBC88\uD638\uCC3E\uAE30");
		panel_2.add(btnFind);
		
		pack();
		
		btnMake.addActionListener(this);
		btnFind.addActionListener(this);
		btnLogin.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn=(JButton) e.getSource();
		if(btn==btnMake) {
			member.setVisible(true);//회원가입			
		} else if(btn==btnFind) {
			find.setVisible(true); //아이디,비번찾기
		} else if(btn==btnLogin) {//로그인구현
			login();
		}
		
	}
	class Enter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				login();
			}
		}
		
	}
	public void login() {
		Vector<LoginVO> vec=new Vector<>();
		LoginDAO dao=new LoginDAO();
		LoginVO vo=new LoginVO();
		String id=txtid.getText();
		char[] pw=txtpwd.getPassword();//입력한 아이디
		String passwd=new String(pw,0,pw.length); //입력한 비밀번호
		vec=dao.get_pw_id();
		for(int i=0;i<vec.size();i++) {
			vo=vec.get(i); //DB에 있는 것
				if(vo.getId().equals(id) && vo.getPwd().equals(passwd)) { //비교하기
					ChatMain frame=new ChatMain(vo);//내꺼 정보넘기기
					frame.setVisible(true); //메인창 띄우기
					dispose();//로그인창 닫기
					break;
				}else {
					JOptionPane.showMessageDialog(this, "아이디/비밀번호를 잘못 입력하셨습니다.");
					break;
				}
		}
	}


}
