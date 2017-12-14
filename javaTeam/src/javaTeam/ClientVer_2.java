package javaTeam;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

public class ClientVer_2 extends JFrame implements ActionListener {
	//UI부분
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnSend;
	private JTextArea mainText;
	private JToggleButton toggleBtn;
	public static void main(String[] args)  {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				ClientVer_2 frame=new ClientVer_2();
				frame.setVisible(true);
			}
		});
	}
	public ClientVer_2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		mainText = new JTextArea();
		scrollPane.setViewportView(mainText);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(25);
		
		btnSend = new JButton("\uC804\uC1A1");
		panel.add(btnSend);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Client");
		panel_1.add(lblNewLabel);
		
		toggleBtn = new JToggleButton("실행하기");
		panel_1.add(toggleBtn);
		toggleBtn.addActionListener(this);
		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String data=textField.getText();
				send(data);
				textField.setText("");
			}
		});
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JToggleButton btn=(JToggleButton)e.getSource();
		if(btn.isSelected()) {
			startClient();
			btn.setText("실행중");
		}else {
			stopClient();
		}
	}

	//클라이언트가 실행되는 부분
	Socket socket;
	
	//시작되는 부분
	void startClient() {
		Thread thread=new Thread() { //스레드생성
			@Override
			public void run() {
				try {
					socket = new Socket();
					socket.connect(new InetSocketAddress("192.168.0.67", 5001)); //접속하는 부분
					mainText.append("연결되었습니다 "+socket.getRemoteSocketAddress()+"\n");
				}catch(Exception e) {
					mainText.append("서버와 통신안됨\n");
					if(!socket.isClosed())
						stopClient();
					return;
				}receive(); //서버에서 보낸것 받기
			} 
		};
		thread.start(); //스레드실행
	}
	//정지하는 부분
	void stopClient() {
		try {
			mainText.append("접속종료\n");
			if(!socket.isClosed() && socket!=null)
				socket.close(); //소켓이 닫혀인징않거나 비어있지않다면 닫기
			toggleBtn.setText("종료");
			toggleBtn.setSelected(false);
		}catch(Exception e) {}
	}
	//서버에서 보낸것을 받는부분
	void receive() {
				
		while(true) {
					try {
						byte[] byteArr =new byte[100];
						InputStream is=socket.getInputStream();
						int readByte=is.read(byteArr); //값을 받는부분
						if(readByte==-1) {throw new IOException();}//읽을것이없을경우 예외던지기
						String data=new String(byteArr, 0, readByte,"UTF-8");//화면에 출력하기위한 변환
						mainText.append("상대방"+data+"\n");
					}catch(Exception e) {e.printStackTrace();
						mainText.append("클라reecive안됨\n");
						stopClient();
						break;
					}
				}
	}
	//서버에 보내는 부분
	void send(String data) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					byte byteArr[]=data.getBytes("UTF-8");
					OutputStream os=socket.getOutputStream();
					os.write(byteArr);
					os.flush();
					mainText.append("전송완료\n");
				}catch(Exception e) {
					mainText.append("클라send안됨\n");
					stopClient();
				}
			}
		};thread.start();
	}
		
	
}
