package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Font;

public class Find extends JFrame {

	private JPanel contentPane;
	private JTextField textidName;
	private JTextField textidNum;
	private JTextField textpwdName;
	private JTextField textpwdNum;
	private JTextField textpwdID;

	
	public Find() {
		setTitle("\uC544\uC774\uB514/\uBE44\uBC00\uBC88\uD638 \uCC3E\uAE30");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JLabel lblNewLabel_1 = new JLabel("\uC544\uC774\uB514 \uCC3E\uAE30");
		lblNewLabel_1.setFont(new Font("±¼¸²", Font.BOLD, 12));
		panel_1.add(lblNewLabel_1);
		
		JLabel label = new JLabel("");
		panel_1.add(label);
		
		JLabel lblNewLabel_2 = new JLabel("\uC774\uB984");
		panel_1.add(lblNewLabel_2);
		
		textidName = new JTextField();
		panel_1.add(textidName);
		textidName.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("\uC804\uD654\uBC88\uD638");
		panel_1.add(lblNewLabel_3);
		
		textidNum = new JTextField();
		textidNum.setColumns(10);
		panel_1.add(textidNum);
		
		JButton btnfind1 = new JButton("\uCC3E\uAE30");
		panel_1.add(btnfind1);
		
		JButton btncancle1 = new JButton("\uCDE8\uC18C");
		panel_1.add(btncancle1);
		
		JLabel lblNewLabel_4 = new JLabel("");
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("");
		panel_1.add(lblNewLabel_5);
		
		JLabel label_1 = new JLabel("\uBE44\uBC00\uBC88\uD638 \uCC3E\uAE30");
		label_1.setFont(new Font("±¼¸²", Font.BOLD, 12));
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("");
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("\uC774\uB984");
		panel_1.add(label_3);
		
		textpwdName = new JTextField();
		textpwdName.setColumns(10);
		panel_1.add(textpwdName);
		
		JLabel label_5 = new JLabel("\uC544\uC774\uB514");
		panel_1.add(label_5);
		
		textpwdID = new JTextField();
		textpwdID.setColumns(10);
		panel_1.add(textpwdID);
		
		JLabel label_4 = new JLabel("\uC804\uD654\uBC88\uD638");
		panel_1.add(label_4);
		
		textpwdNum = new JTextField();
		textpwdNum.setColumns(10);
		panel_1.add(textpwdNum);
		
		JButton btnfind2 = new JButton("\uCC3E\uAE30");
		panel_1.add(btnfind2);
		
		JButton btncancle2 = new JButton("\uCDE8\uC18C");
		panel_1.add(btncancle2);
	}

}
