package javaTeam;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ServerFrame extends JFrame {

	private JPanel contentPane;
	JTextField textadmin;
	JTextArea mainText ;

	public ServerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		mainText = new JTextArea();
		mainText.setLineWrap(true);
		scrollPane.setViewportView(mainText);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Server");
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		textadmin = new JTextField();
		panel_1.add(textadmin);
		textadmin.setColumns(20);
		
		JButton btnSend = new JButton("\uC804\uC1A1");
		panel_1.add(btnSend);
		
		server server=new server();
	
	}

	public void setText(String textStr) {
		mainText.append(textStr+"\n");
	}

	

}
