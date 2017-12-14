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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;

public class membership extends JFrame implements ActionListener{	
	
	private JPanel contentPane;
	private JTextField textID;
	private JTextField textPWD;
	private JTextField textName;
	private JTextField textNum;
	private JButton btnConfirm, btnCancle;
	private JRadioButton rdoman, rdogirl;
	private JLabel idlabel ;
	ButtonGroup gen;
	String gender=null;
	LoginDAO dao=new LoginDAO();
	
	public membership() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		textID.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				String id=textID.getText();
				if(id.length()>15 || id.length()<4) {
					idlabel.setText("아이디는 4글자 이상 15글자 이하 여야합니다.");
				}else if(id.length()<=15 || id.length()>=4) {
					idlabel.setText("");
				}
			}
		});
		
		idlabel = new JLabel("");
		panel_1.add(idlabel);
		
		JLabel lblNewLabel_1 = new JLabel("\uBE44\uBC00\uBC88\uD638");
		panel_1.add(lblNewLabel_1);
		
		textPWD = new JTextField();
		panel_1.add(textPWD);
		textPWD.setColumns(20);
		
		JLabel lblNewLabel_5 = new JLabel("");
		panel_1.add(lblNewLabel_5);
		
		JLabel label = new JLabel("\uC774\uB984");
		panel_1.add(label);
		
		textName = new JTextField();
		textName.setColumns(10);
		panel_1.add(textName);
		
		JLabel lblNewLabel_6 = new JLabel("");
		panel_1.add(lblNewLabel_6);
		
		JLabel label_1 = new JLabel("\uC804\uD654\uBC88\uD638");
		panel_1.add(label_1);
		
		textNum = new JTextField();
		textNum.setColumns(10);
		panel_1.add(textNum);
		
		JLabel lblNewLabel_7 = new JLabel("");
		panel_1.add(lblNewLabel_7);
		
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
		
		JButton btnOverlap = new JButton("중복확인");//확인하는부분
		btnOverlap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<String> vec =new Vector<>();
				LoginDAO dao=new LoginDAO();
				vec=dao.getId();//id리스트를 받아오기
				String ID=textID.getText(); //가입하고자하는 아이디
				for(int i=0;i<=vec.size();i++) {					
					String dbID=vec.get(i);//DB에있는아이디
					if(ID.equals(dbID)) { //db에있는 아이디랑 가입하고자하는 아이디랑 비교
						textID.selectAll();
					}else {
	
					}
				}
				
			}
		});
		btnOverlap.setFont(new Font("����", Font.BOLD, 12));
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
		gen = new ButtonGroup();
		gen.add(rdoman);
		gen.add(rdogirl);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();		
		
		if(btn==btnConfirm) {		
		String id=textID.getText();
		String pwd=textPWD.getText();
		String name=textName.getText();
		String phonenum=textNum.getText();		
		if(id.equals(null) || pwd.equals(null) || name.equals(null) || phonenum.equals(null)) {
			JOptionPane.showMessageDialog(this, "공란 없이 입력해주세요");			
		}else if(id.length()>15 || id.length()<4) {
			JOptionPane.showMessageDialog(this, "아이디는 4~15글자 사이여야합니다.");
		}
		
		LoginDAO dao=new LoginDAO();
		int result=dao.login_Insert(id,pwd,name,phonenum,gender);
		if(result>0) {
			JOptionPane.showMessageDialog(this, "회원으로 가입되셨습니다.");	
			dispose();
		}else {
			JOptionPane.showMessageDialog(this, "회원등록실패");
		}
		}
			
		
	}
}
