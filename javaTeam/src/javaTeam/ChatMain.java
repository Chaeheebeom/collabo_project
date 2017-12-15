package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChatMain extends JFrame implements ActionListener{

	private JPanel contentPane;
	private String[] listStr = {"¿Â¶óÀÎ  ¿À¹Î¼®  [minseok] (¿À´Ã ³Ê¹« Èûµé´Ù)","¿ÀÇÁ¶óÀÎ À¯Áö¿õ  [jiung] (¹Î¼®ÀÌ ¹Ùº¸)","¿Â¶óÀÎ  Ã¤Èñ¹ü  [heebum] (ÀÚ¹ÙÀÇ ½Å)"};
    private JMenuItem Menuexit,Menulogout,Menufind,Menuinfo;
	private FriendFind find=new FriendFind();
	private Information info=new Information();
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatMain frame = new ChatMain();
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
	public ChatMain() {
		setTitle("OO\uD1A1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 590);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		mnNewMenu.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		menuBar.add(mnNewMenu);
		
		Menuinfo = new JMenuItem("\uB0B4 \uC815\uBCF4 \uBCF4\uAE30");
		mnNewMenu.add(Menuinfo);
		
		Menufind = new JMenuItem("\uCE5C\uAD6C \uCC3E\uAE30");
		mnNewMenu.add(Menufind);
		
		Menulogout = new JMenuItem("\uB85C\uADF8 \uC544\uC6C3");
		mnNewMenu.add(Menulogout);
		
		Menuexit = new JMenuItem("\uC885\uB8CC");
		mnNewMenu.add(Menuexit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JList list = new JList();
		list.setBackground(Color.WHITE);
		panel_1.add(list);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(list, popupMenu);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("\uCE5C\uAD6C \uC815\uBCF4 \uBCF4\uAE30");
		mntmNewMenuItem_4.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		popupMenu.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("\uCE5C\uAD6C \uC0AD\uC81C");
		mntmNewMenuItem_5.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		popupMenu.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("\uCABD\uC9C0 \uBCF4\uB0B4\uAE30");
		mntmNewMenuItem_6.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		popupMenu.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("\uB300\uD654 \uC2E0\uCCAD");
		mntmNewMenuItem_7.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		popupMenu.add(mntmNewMenuItem_7);
		
		
		    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        list.setListData(listStr); 
	        list.setFont(new Font("¸¼Àº °íµñ",Font.BOLD,16));
	        
	        Menuexit.addActionListener(this);
	        Menuinfo.addActionListener(this);
	        Menufind.addActionListener(this);
	        Menulogout.addActionListener(this);
	        
	        
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
			
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JMenuItem jm = (JMenuItem) e.getSource();
		
		if(jm == Menuexit) {
			//Á¾·á
			System.exit(0);
		}else if(jm == Menufind) {
			//Ä£±¸ Ã£±â
			find.setVisible(true);			
			
		}else if(jm == Menuinfo) {
			//Á¤º¸ º¸±â 
			info.setVisible(true);
			
		}else if(jm == Menulogout) {
			//·Î±× ¾Æ¿ô
			
			
			
		}
		
	}
}
