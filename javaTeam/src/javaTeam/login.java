package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

public class login extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField txtid;
	private JTextField txtpwd;
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
		
		txtpwd = new JTextField();
		txtpwd.setColumns(10);
		panel_1.add(txtpwd);
		
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
			member.setVisible(true);			
		} else if(btn==btnFind) {
			find.setVisible(true);
		}
		
	}

}