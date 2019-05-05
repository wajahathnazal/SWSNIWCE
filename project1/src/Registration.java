
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import database.JDBC;


public class Registration 
{

	private JFrame fram;
	private JLabel l1,l2,l3,l4,l5,l6,l7;
	private JTextField t1,t2,t3,t4,t5;
	private JButton b1,b2;
	private boolean valid=false;
	private boolean valid1=false;
	private boolean valid2;

	public Registration() {

		JDBC.connect_database();

		fram = new JFrame("Rigester Form");
		fram.setLayout(null);

		l1=new JLabel("Name : ");
		l1.setBounds(10, 70, 50, 20);
		l1.setFont(new Font("Serif",Font.BOLD,14));

		t1=new JTextField();
		t1.setBounds(130, 72,150, 20);

		l2=new JLabel("User name : ");
		l2.setBounds(10, 100, 80, 20);
		l2.setFont(new Font("Serif",Font.BOLD,14));

		t3=new JTextField();
		t3.setBounds(130, 102,150, 20);

		l3=new JLabel("Password : ");
		l3.setBounds(10, 130, 80, 20);
		l3.setFont(new Font("Serif",Font.BOLD,14));

		t4=new JPasswordField();
		t4.setBounds(130, 132,150, 20);


		l4=new JLabel("confirm Password : ");
		l4.setBounds(10, 160,128, 20);
		l4.setFont(new Font("Serif",Font.BOLD,14));

		t2=new JPasswordField();
		t2.setBounds(130, 162,150, 20);

		l5=new JLabel("email id : ");
		l5.setBounds(10, 190, 80, 20);
		l5.setFont(new Font("Serif",Font.BOLD,14));

		t5=new JTextField();
		t5.setBounds(130, 192,150, 20);

		l6=new JLabel("Enter the details below to sign up ",JLabel.CENTER);
		l6.setBounds(0, 20, 300, 25);
		l6.setFont(new Font("Serif",Font.BOLD,18));

		b1=new JButton("Sign up");
		b1.setBounds(50, 230, 80, 20);

		b2=new JButton("Clear");
		b2.setBounds(150, 230, 80, 20);

		ImageIcon icon2= new ImageIcon("images/rback.jpg");
		l7 = new JLabel(icon2,JLabel.CENTER);
		l7.setBounds(0,0, 300,350);

		fram.add(l1);
		fram.add(b1);
		fram.add(t2);
		fram.add(t3);
		fram.add(t4);
		fram.add(t5);
		fram.add(t1);
		fram.add(b2);
		fram.add(l2);
		fram.add(l3);
		fram.add(l4);
		fram.add(l5);
		fram.add(l6);
		fram.add(l7);
		fram.setSize(300, 350);
		fram.setVisible(true);

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				t1.setText(null);	t2.setText(null);
				t3.setText(null);	t4.setText(null);
				t5.setText(null);
			}
		});
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

				if(!t1.getText().equals("") && !t2.getText().equals("") && !t3.getText().equals("") && !t4.getText().equals("") && !t5.getText().equals(""))
				{
					if(valid&&valid1&&valid2)
					{
						if(t2.getText().equals(t4.getText()))
						{
							
							String name = t1.getText();
							String username = t3.getText();
							String password = t2.getText();
							String email = t5.getText();
							JDBC.update_sts(name, username, password, email);
							fram.setVisible(false);
							JOptionPane.showMessageDialog(fram,"User created sucessfully \n Please login to continue"," ",JOptionPane.PLAIN_MESSAGE);
							new user();
							//new sign_in(username);
							
						}
						else

							JOptionPane.showMessageDialog(fram,"password did't match"," ",JOptionPane.WARNING_MESSAGE);
					}else
						JOptionPane.showMessageDialog(fram,"enter valid email id"," ",JOptionPane.WARNING_MESSAGE);

				}
				else{
					JOptionPane.showMessageDialog(fram,"Text fields can't be blank"," ",JOptionPane.WARNING_MESSAGE);
				}
			}});
		FocusAdapter fl = new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent fe)
			{
				super.focusGained(fe);
				((JTextComponent) fe.getSource()).setBackground(new Color(175,238,238));
			}

			@Override
			public void focusLost(FocusEvent fe) {
				super.focusLost(fe);
				if (fe.getSource().equals(t5)) {
					validationForEmail(t5);
				}else if (fe.getSource().equals(t1)) {
					validationForname(t1);}
				else
					validationForUsername(t3);

			}};

			t5.addFocusListener(fl);
			t1.addFocusListener(fl);
			t3.addFocusListener(fl);

	}

	private void validationForUsername(JTextField t3)
	{
		String text = t3.getText();
		
		if (JDBC.usernameAvaliable(text))
		{
			
			setGreen(t3);
			valid2=true;
			
		} 
		else 
		{
			
			valid2=false;
			setRed(t3);
			JOptionPane.showMessageDialog(fram,"username already exists in database"," ",JOptionPane.WARNING_MESSAGE);
		
		}
	}

	private void validationForname(JTextField comp) {
		// TODO Auto-generated method stub
		String text = comp.getText();
		if (text.matches("[A-Za-z][A-Za-z]*")) {
			setGreen(comp);
			valid1=true;
		} 
		else 
		{
			valid1=false;
			setRed(comp);
			JOptionPane.showMessageDialog(fram,"can't enter number in name column"," ",JOptionPane.WARNING_MESSAGE);

		}
	}
	public void validationForEmail(JTextComponent comp) {
		String text = comp.getText();
		if (text.matches("[^@]+@([^.]+\\.)+[^.]+")) {
			setGreen(comp);
			valid=true;
		} 
		else 
		{
			setRed(comp);
			valid=false;
		}
	}

	private void setRed(JTextComponent comp) {
		comp.setBackground(Color.RED);
	}

	private void setGreen(JTextComponent comp) {
		comp.setBackground(Color.GREEN);
	}

	public static void main(String[] args) {
		new Registration();
	}

}
