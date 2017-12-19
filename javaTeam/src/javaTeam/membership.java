package javaTeam;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;

public class membership extends JFrame implements ActionListener{	
	
	private JPanel contentPane;
	private JTextField textID;
	private JPasswordField textPWD;
	private JTextField textName;
	private JTextField textNum;
	private JButton btnConfirm, btnCancle,btnOverlap;
	private JRadioButton rdoman, rdogirl;
	private JLabel idLabel,pwLabel,nameLabel,numLabel ;
	ButtonGroup gen;
	String gender=null;
	LoginDAO dao=new LoginDAO();
	private JLabel label_2;
	private JTextField textAge;
	private JLabel ageLabel;
	
	public membership() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //x버튼눌렀을때 로그인화면으로가기
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblOo = new JLabel("OO\uD1A1 \uD68C\uC6D0\uAC00\uC785");
		panel.add(lblOo);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel lblNewLabel = new JLabel("\uC544\uC774\uB514");
		panel_1.add(lblNewLabel);
		
		textID = new JTextField();
		panel_1.add(textID);
		textID.setColumns(10);
		//아이디입력받는부분
		textID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String id=textID.getText();
				if(id.length()>15 || id.length()<4) {
					idLabel.setText("아이디는 4글자 이상 15글자 이하 여야합니다.");
				}else if(id.length()<=15 || id.length()>=4) {
					idLabel.setText("");
				}
			}
		});
		
	
		idLabel = new JLabel("");
		panel_1.add(idLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\uBE44\uBC00\uBC88\uD638");
		panel_1.add(lblNewLabel_1);
		
		textPWD = new JPasswordField();
		panel_1.add(textPWD);
		textPWD.setColumns(20);
		textPWD.setEchoChar('*');
		
		pwLabel = new JLabel("");
		panel_1.add(pwLabel);
		
		JLabel label = new JLabel("\uC774\uB984");
		panel_1.add(label);
		
		textName = new JTextField();
		textName.setColumns(10);
		panel_1.add(textName);
		
		nameLabel = new JLabel("");
		panel_1.add(nameLabel);
		
		label_2 = new JLabel("나이");
		panel_1.add(label_2);
		
		textAge = new JTextField();
		textAge.setColumns(10);
		panel_1.add(textAge);
		
		ageLabel = new JLabel("");
		panel_1.add(ageLabel);
		
		JLabel label_1 = new JLabel("\uC804\uD654\uBC88\uD638");
		panel_1.add(label_1);
		
		textNum = new JTextField();
		textNum.setColumns(10);
		panel_1.add(textNum);
		
		numLabel = new JLabel("");
		panel_1.add(numLabel);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("\uC131\uBCC4");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_2);
		
		rdoman = new JRadioButton("\uB0A8");
		rdoman.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(rdoman);
		
		rdogirl = new JRadioButton("\uC5EC");
		rdogirl.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(rdogirl);
		
		btnConfirm = new JButton("\uD655\uC778");
		panel_2.add(btnConfirm);
		
		JLabel lblNewLabel_3 = new JLabel("");
		panel_2.add(lblNewLabel_3);
		
		btnCancle = new JButton("\uCDE8\uC18C");
		panel_2.add(btnCancle);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.EAST);
		
		btnOverlap = new JButton("중복확인");
		btnOverlap.addActionListener(this); 
		panel_3.add(btnOverlap);
		
		btnConfirm.addActionListener(this);
		btnCancle.addActionListener(this);
		
		rdoman.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gender=rdoman.getActionCommand();
			}
		});
		rdogirl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gender=rdogirl.getActionCommand();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();		
		
		if(btn==btnConfirm) {		
			String id=textID.getText();
			char[] password=textPWD.getPassword();
			String pwd=new String(password, 0, password.length);
			String name=textName.getText();
			String strNum=textNum.getText();
			String strAge=textAge.getText();
			int age=0;
			int	phonenum=0;
			try {
				age=Integer.parseInt(strAge);
				phonenum=Integer.parseInt(strNum);  
					try {
						if(idOverlap()) {//중복확인
							idLabel.setText("중복된 아이디입니다.");
							//빈칸이 있는지 확인하는 부분
						}else if(age<0){
							JOptionPane.showMessageDialog(this,"나이를 다시 입력해주세요");
							ageLabel.setText("제대로 입력해 주세요");
						}else if(id.equals(null) || pwd.equals(null) || name.equals(null) || phonenum==0  || gender.equals(null)) {
							JOptionPane.showMessageDialog(this, "공란 없이 입력해주세요");	//공란이 있을경우 띄워준다.
							if(textPWD.equals(null)) {pwLabel.setText("입력해주세요");}
							else if(textName.equals(null)) {nameLabel.setText("입력해주세요");}
							else if(textNum.equals(null)) {numLabel.setText("입력해주세요");}
						}else if(id.length()>15 || id.length()<4) {  //아이디가 너무길거나 짧은지 확인하기
							JOptionPane.showMessageDialog(this, "아이디는 4~15글자 사이여야합니다.");
						}else {	//위조건에 문제가 없을 경우에 DB에 회원자료 집어넣기
							int result=dao.login_Insert(id, pwd, name,age, phonenum, gender);
							if(result==1) {
								JOptionPane.showMessageDialog(this, "회원가입 완료");
								dispose();
							}
						}
					}catch(Exception e3) {JOptionPane.showMessageDialog(this, "성별을 선택해 주세요");}  //gender.equals(null)에대한 예외처리//
				}catch(ClassCastException cce) {numLabel.setText("숫자만 입력하세요"); //폰번호에 이상한거 들어갔을때 예외처리
			}
		}else if(btn==btnOverlap) { //중복확인부분
			if(idOverlap())
				idLabel.setText("중복된 아이디입니다.");
		}else if(btn==btnCancle) {
			dispose();
		}
	}
	
	public boolean idOverlap() {
		Vector<String> vec =new Vector<>();
		try {
			vec=dao.getId();//id리스트를 받아오기
			String ID=textID.getText(); //가입하고자하는 아이디
				for(int i=0;i<=vec.size();i++) {					
					String dbID=vec.get(i);//DB에있는아이디
					if(ID.equals(dbID)) { //db에있는 아이디랑 가입하고자하는 아이디랑 비교
						return true;
					}
				}
		}catch(Exception e2) {idLabel.setText("입력해주세요");}
		return false;
	}
}


