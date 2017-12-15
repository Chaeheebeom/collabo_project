package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FriendFind extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnop;
	private Friend fr = new Friend();

	public FriendFind() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("\uC544\uC774\uB514");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		btnop = new JButton("\uAC80\uC0C9");
		
		
		
		
		btnop.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				JButton jb = (JButton) e.getSource();
				if(jb == btnop) {
					fr.setVisible(true);
				}
				
				
			}
		});
		panel.add(btnop);
		
		pack();
	}

	
}
