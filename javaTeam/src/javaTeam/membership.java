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
			public void keyTyped(KeyEvent e) { //키가 타입될때마다 키이벤트 실행함
				String id=textID.getText();  //id입력부분에 값을받아서
				if(id.length()>15 || id.length()<4) { //15글자이상 4글자 이하라면
					idLabel.setText("아이디는 4글자 이상 15글자 이하 여야합니다."); //옆에 라벨에 이렇게띄워줌
				}else if(id.length()<=15 || id.length()>=4) { //아니라면
					idLabel.setText(""); //아무것도 안띄움
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
		panel_3.add(btnOverlap);
		//액션리스너하는부분
		btnOverlap.addActionListener(this); 
		btnConfirm.addActionListener(this);
		btnCancle.addActionListener(this);
		//남 혹은 여 를 눌렀을때 값(남 or 여)를 gender에 넣어주는 것
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
		//버튼종류에 따라 실행하는 것이 다름 여기가 세번쨰의 첫번쨰
		if(btn==btnConfirm) {	//회원가입버튼	
			String id=textID.getText();
			char[] password=textPWD.getPassword();
			String pwd=new String(password, 0, password.length);
			String name=textName.getText();
			String strNum=textNum.getText();
			String strAge=textAge.getText();//각각 입력된 정보를 각각 타입에 맞는 변수에 저장
			int age;
			int	phonenum;
			try {
				age=Integer.parseInt(strAge); //예외처리 242번쨰줄 NumberFormatExecption 부분
			
			try {				
				phonenum=Integer.parseInt(strNum);  //예외처리 234번쨰줄 NumberFormatExecption 부분
					try {
						
						if(idOverlap()) {
							JOptionPane.showMessageDialog(this, "아이디가 중복됩니다"); //중복확인부분
						
						//빈칸이 있는지 확인하는 부분
						}else if(age<0){		    	
						    JOptionPane.showMessageDialog(this,"나이를 다시 입력해주세요");//혹시몰라서 해놓은거 나이가 -살이면 이상하잖아? 벤자민도아니고
						    ageLabel.setText("제대로 입력해 주세요");							
						}else if(id.equals("") || pwd.equals("") || name.equals("") || gender.equals(null)) {//값이 없으면 각각 라벨에 비어있다고 띄워주는거임
							if(id.equals("")) {idLabel.setText("id를 입력해주세요");//중첩 IF문 해당 라벨에만 띄워주는 것
							pwLabel.setText("");
							nameLabel.setText("");
							ageLabel.setText("");
							numLabel.setText("");
							}
							else if(pwd.equals("")) {pwLabel.setText("비밀번호를 입력해주세요");
							idLabel.setText("");
							nameLabel.setText("");
							ageLabel.setText("");
							numLabel.setText("");
							}
							else if(name.equals("")) {nameLabel.setText("이름을 입력해주세요");
							idLabel.setText("");
							pwLabel.setText("");
							ageLabel.setText("");
							numLabel.setText("");							
							}							
							JOptionPane.showMessageDialog(this, "공란 없이 입력해주세요");	//공란이 있을경우 띄워준다.							
						}else if(id.length()>15 || id.length()<4) {  //아이디가 너무길거나 짧은지 확인하기
							JOptionPane.showMessageDialog(this, "아이디는 4~15글자 사이여야합니다.");
						}else {	//위조건에 문제가 없을 경우에 DB에 회원자료 집어넣기
							int result=dao.login_Insert(id, pwd, name,age, phonenum, gender); //잘집어넣었음 리턴값받음
							if(result==1) {//리턴값여부에따라 회원가입완료
								JOptionPane.showMessageDialog(this, "회원가입 완료");
								dispose();//회원가입창 닫기
							}
						}
					}catch(Exception e3) {JOptionPane.showMessageDialog(this, "성별을 선택해 주세요");}  //gender.equals(null)에대한 예외처리//
			}catch(NumberFormatException nfe) {numLabel.setText("전화번호를 입력하세요"); 
				JOptionPane.showMessageDialog(this, "공란없이 입력해주세요");
				idLabel.setText("");
				pwLabel.setText("");
				nameLabel.setText("");			
				ageLabel.setText("");	
			} //나이에 이상한거 또는 공란일경우 예외처리
			
			}catch(NumberFormatException nfe2) {ageLabel.setText("나이를 입력하세요");
			JOptionPane.showMessageDialog(this, "공란없이 입력해주세요");//폰번호에 이상한거 또는 공란일경우 들어갔을때 예외처리
			idLabel.setText("");
			pwLabel.setText("");
			nameLabel.setText("");
			numLabel.setText("");			
			}
		}else if(btn==btnOverlap) { //중복확인부분 여기가 세번쨰의 두번쨰
			String id=textID.getText(); //아이디입력한 거 받아서
			if(idOverlap()) {//if문으로 비교 위에거랑 중복이므로 설명생략
				JOptionPane.showMessageDialog(this, "중복된 아이디입니다.");
			}else if(id.length()>15 || id.length()<4) {  //아이디가 너무길거나 짧은지 확인하기
				JOptionPane.showMessageDialog(this, "아이디는 4~15글자 사이여야합니다.");
				idLabel.setText("아이디는 4~15글자 이여야 합니다.");
			}else {
				JOptionPane.showMessageDialog(this, "사용가능한 아이디입니다.");
			}
		}else if(btn==btnCancle) {//여기가 세번쨰의 마지막 취소버튼
			textID.setText("");  //다비워주고
			textPWD.setText("");
			textName.setText("");
			textAge.setText("");
			textNum.setText("");
			idLabel.setText("");
			dispose(); //창닫기
		}
	}
	
	public boolean idOverlap() {
		Vector<String> vec =new Vector<>();
		try {
			vec=dao.getId();//id리스트를 받아오기
			String ID=textID.getText(); //가입하고자하는 아이디
				for(int i=0;i<=vec.size();i++) {					
					String dbID=vec.get(i);//DB에있는아이디불러오기
					if(ID.equals(dbID)) { //db에있는 아이디랑 가입하고자하는 아이디랑 비교
						return true;//참값출력
					}
				}
		}catch(Exception e2) {}//idLabel.setText("사용가능한 아이디입니다.");
		return false;
	}
}



