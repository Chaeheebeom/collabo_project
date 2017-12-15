package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import java.awt.Color;
import javax.swing.JButton;

public class Friend extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;


	public Friend() {
		setFont(new Font("Dialog", Font.BOLD, 16));
		setTitle("\uC815\uBCF4 \uBCF4\uAE30");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel lblNewLabel = new JLabel("\uC544\uC774\uB514");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("±¼¸²", Font.BOLD, 16));
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\uC774\uB984");
		lblNewLabel_1.setFont(new Font("±¼¸²", Font.BOLD, 16));
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\uB098\uC774");
		lblNewLabel_2.setFont(new Font("±¼¸²", Font.BOLD, 16));
		panel.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label = new JLabel("\uC131\uBCC4");
		label.setFont(new Font("±¼¸²", Font.BOLD, 16));
		panel.add(label);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		panel.add(textField_3);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("\uC790\uAE30 \uC18C\uAC1C");
		lblNewLabel_3.setBounds(0, 0, 566, 33);
		lblNewLabel_3.setFont(new Font("±¼¸²", Font.BOLD, 16));
		panel_1.add(lblNewLabel_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(0, 31, 566, 192);
		panel_1.add(textField_4);
		textField_4.setColumns(10);
		
		JButton btnNewButton = new JButton("\uCE5C\uAD6C \uB4F1\uB85D");
		btnNewButton.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		btnNewButton.setBounds(0, 220, 566, 39);
		panel_1.add(btnNewButton);
	}
}
