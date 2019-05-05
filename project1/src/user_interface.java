

import java.awt.Color;
import database.*;
//import sensor.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class user_interface 
{

	public user_interface() 
	{

		JDBC.connect_database();
		//JDBC.clear_data();
		
		JFrame frame = new JFrame("user interface");

		JPanel jp = new JPanel();
		jp.setBounds(0,0,800,600);
		jp.setLayout(null);
		jp.setBackground(new Color(152,202,205));
		frame.add(jp);

		JLabel jl1 = new JLabel("");
		jl1.setBounds(10, 20, 700, 90);
		jl1.setFont(new Font("Brush Script MT",Font.BOLD,30));
		jp.add(jl1);

		JLabel jl2 = new JLabel(" Cluster-Based Wireless Sensor Networks");
		jl2.setBounds(90, 80, 700, 90);

		jl2.setFont(new Font("Brush Script MT",Font.BOLD,30));
		jp.add(jl2);

		 final JButton dc = new JButton("Data center");
		dc.setBounds(300, 240,150, 30);
		jp.add(dc);

		JLabel dcl = new JLabel(new ImageIcon("images/dc.png"));
		dcl.setBounds(320, 150,100, 100);
		jp.add(dcl);

//		final JButton tc = new JButton("Trusted center");
//		tc.setBounds(4, 350,150, 30);
//		jp.add(tc);
////
//		JLabel tcl = new JLabel(new ImageIcon("images/ta.png"));
//		tcl.setBounds(30, 240,100, 100);
//		jp.add(tcl);

		final JButton fs = new JButton("Fire sensor");
		fs.setBounds(190, 450,150, 30);
		jp.add(fs);

		JLabel fsl = new JLabel(new ImageIcon("images/fire.jpg"));
		fsl.setBounds(220, 330,100, 100);
		jp.add(fsl);

		final	JButton bs = new JButton("Bomb sensor");
		bs.setBounds(370, 450,150, 30);
		jp.add(bs);

		JLabel bsl = new JLabel(new ImageIcon("images/bomb.png"));
		bsl.setBounds(400, 330,100, 100);
		jp.add(bsl);

		final	JButton es = new JButton("Earthquake sensor");
		es.setBounds(560, 450,150, 30);
		jp.add(es);

		JLabel esl = new JLabel(new ImageIcon("images/eq.jpg"));
		esl.setBounds(590, 330,100, 100);
		jp.add(esl);

		frame.setLayout(null);
		frame.setVisible(true);
		frame.setSize(800,600);

		
		dc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				dc.setEnabled(false);
			//	TrustedCenter t = new TrustedCenter();
//				t.start();
			}});

		fs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
					fs.setEnabled(false);
	//				Thread t = new Thread(new fire());
		//			t.start();
				
			}});

		bs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				
					bs.setEnabled(false);
			//		Thread t =new Thread(new bomb());
		   //	t.start();
				
			}});

		es.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
			
				
					es.setEnabled(false);
			//		Thread t = new Thread(new earthquake());
			//		t.start();
				
			}});
	}

	public static void main(String[] args)  
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");

		}
		catch (Exception ex)

		{
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
		}
		new user_interface();
	}
}
