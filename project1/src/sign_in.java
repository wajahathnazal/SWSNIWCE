
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import database.JDBC;


public class sign_in {

	private JFrame f1;
	private JLabel l2,l3,l4,l1,l5;
	JTextField t1;
	JPasswordField p1;
	JButton b1,b2;
	static String username;

	public sign_in(String user) 
	{
		
		JDBC.connect_database();

		final JFrame frame = new JFrame("Sign in");
		frame.setLayout(null);
		frame.setSize(250,250);
		frame.setVisible(true);

		l1=new JLabel("Username:");
		l1.setBounds(10, 35, 80, 20);
		l1.setFont(new Font("Serif",Font.BOLD,17));

		l5=new JLabel("Sign in",JLabel.CENTER);
		l5.setBounds(0, 0, 250, 20);
		l5.setFont(new Font("Serif",Font.BOLD,20));

		l2=new JLabel("Password:");
		l2.setFont(new Font("Serif",Font.BOLD,17));
		l2.setBounds(10,80, 80, 20);

		t1=new JTextField(user);
		t1.setBounds(90,38, 130, 20);

		p1=new JPasswordField();
		p1.setBounds(90,81, 130, 20);

		b1=new JButton("Sign in");
		b1.setBounds(20,130, 80, 20);


		b2=new JButton("Sign up");
		b2.setBounds(120,130, 80, 20);

		ImageIcon icon2= new ImageIcon("images/sign.jpg");
		l3= new JLabel(icon2,JLabel.CENTER);
		l3.setBounds(-30, -30, 300, 300);


		frame.add(b1);
		frame.add(b2);
		frame.add(l5);
		frame.add(t1);
		frame.add(p1);
		frame.add(l1);
		frame.add(l2);
		frame.add(l3);


		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setVisible(false);
				new Registration();
			}});

		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String uname,password = null,check="";
				char[] pass;
				String res="";
				uname=t1.getText();
				pass=p1.getPassword();
				for(int i=0;i<pass.length;i++)
					check+=Character.toString(pass[i]);

				if( JDBC.authenticate(uname,check))
				{
					JOptionPane.showMessageDialog(frame,"Login successfull...!!"," ",
							JOptionPane.PLAIN_MESSAGE);
					new user();
				}
				else
				{
					JOptionPane.showMessageDialog(frame,"password/username  entered is incorrect"," ",
							JOptionPane.PLAIN_MESSAGE);
					t1.setText(null);
					p1.setText(null);
				}
			}});
	}

	public static void main(String[] args)
	{

		try
		{

			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");

		}
		catch (Exception ex)
		{

			System.out.println("Failed loading L&F: ");
			System.out.println(ex);

		}
	new sign_in("");
	}

}
