package sensor;
import javax.swing.*;
import database.*;
import secure.*;
import java.math.BigInteger;
import java.net.*;
import java.lang.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;


public class attacker_sensor implements ActionListener,Runnable
{

	JFrame jf,jf2;
	JPanel jp1,jp2,jp3,jp4,jp5;
	JTextArea jt,jt1;
	JLabel jl1,jl3,temp;
	JSeparator jsep;
	JButton jb,jb1,jb2;
	static JLabel jl2;
	JScrollPane js2;
	int i;
	static HumanProgressBar progressBar;
	static int status = 60;
	String place,t,g;
	String str,un,result="Eventtype	villageName 	   Area 	     Date&Time\n";
	String sevent[]={"Bannerghatta","Mysore","Bandipura","NagarHole","Kakanakote","kerala","Dakshina Kannada"};
	String tree[]={"100","200","300","400","500","600","1000"};
	String gas[]={"co2","oxygen","helium","nitrogen","carbonmonoxide","Hydrogen","ch2"};
	void init()
	{
		JDBC.connect_database();
		jf=new JFrame("attacker fire sensor");
		jf.setLayout(null);

		jp1=new JPanel();
		jp1.setLayout(null);
		jp1.setBounds(10,10, 520,580);
		jp1.setBackground(Color.black);

		jp2=new JPanel();
		jp2.setLayout(null);
		jp2.setBounds(10,10,475,320);
		//jp2.setBackground(new Color(238,232,170));
		jp2.setBackground(Color.white);

		jp3=new JPanel();
		jp3.setLayout(null);
		jp3.setBounds(10,345,110,55);
		jp3.setBackground(Color.red);

		jp4=new JPanel();
		jp4.setLayout(null);
		jp4.setBackground(Color.white);
		jp4.setBounds(130,345,340,150);


		jt=new JTextArea("Sensing...");
		jt.setBounds(5,5,100,45);
		//	jt.setBackground(new Color(0,255,255));
		jp3.add(jt);

		jt1=new JTextArea("                        ******Information******\n");
		jt1.append("---------------------------------------------------------------------------------\n");
		jt1.setFont(new Font("Serif", Font.BOLD, 13));
		js2=new JScrollPane(jt1);
		js2.setBounds(5,5,330,140);
		jp4.add(js2);

		jb=new JButton("ALERT !");
		jb.setBounds(10,405,100,40);
		jb.setFont(new Font(null,Font.BOLD,15));
		Border thatBorder1 = new LineBorder(Color.RED);
		jb.setBorder(thatBorder1);
		jb.addActionListener(this);

		jb1=new JButton("Refresh");
		jb1.setBounds(10,455,100,40);
		jb1.setFont(new Font(null,Font.BOLD,15));
		jb1.setBorder(thatBorder1);
		jb1.addActionListener(this);

		jb2=new JButton("Exit");
		jb2.setBounds(10,500,100,40);
		jb2.setFont(new Font(null,Font.BOLD,15));
		jb2.setBorder(thatBorder1);
		jb2.addActionListener(this);

		final JLabel atta = new JLabel("Attacker fire sensor",JLabel.CENTER);
		atta.setBounds(0,250,475,30);
		atta.setFont(new Font(null,Font.BOLD,30));
		atta.setForeground(Color.white);
		jp2.add(atta);

		temp=new JLabel(new ImageIcon("images/f3.jpg"),JLabel.LEFT);
		temp.setBounds(5,5,465,310);
		jp2.add(temp);

		jl2=new JLabel(new ImageIcon(""));
		jl3=new JLabel(new ImageIcon("images/forf3.jpg"));
		jl2.setBounds(0,0,465,310);
		jl2.setVisible(false);
		jl3.setVisible(false);
		jp4.add(jl3);

		jp1.add(jp2);
		jp1.add(jp3);
		jp1.add(jb);
		jp1.add(jb1);
		jp1.add(jb2);
		jf.add(jp1);
		jf.setSize(520,600);




		progressBar = new HumanProgressBar();
		progressBar.setValue(status);

		progressBar.setStringPainted(true);
		Border border = BorderFactory.createTitledBorder("Battery indicator");
		progressBar.setBorder(border);
		progressBar.setBounds(130, 524, 340, 20);
		progressBar.setForeground(Color.green);
		progressBar.setFont(new Font("Serif", Font.BOLD, 16));
		jp1.add(progressBar, BorderLayout.NORTH);

		JLabel sts = new JLabel("Energy status");
		sts.setBounds(130, 500, 100, 20);
		sts.setFont(new Font("Serif", Font.BOLD, 16));
		sts.setForeground(Color.white);
		jp1.add(sts);



		jf.setVisible(true); 

		jp1.add(jp4);	

		Thread att = new Thread() 
		{
			public void run()
			{
				while(true)
				{
					try{
						atta.setVisible(false);
						Thread.sleep(600);
						atta.setVisible(true);
						Thread.sleep(600);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}

				}
			}};
			att.start();


			Thread stat = new Thread() 
			{
				public void run()
				{
					while(true)
					{
						try
						{
							this.sleep(4000);
							if(status<100)
								status ++;
							if(status>60)
								progressBar.setForeground(Color.green);
							else if(status>30)
								progressBar.setForeground(Color.blue);
							else
								progressBar.setForeground(Color.RED);
							progressBar.setValue(status);
						}
						catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}};
				stat.start();
	}

	private void send_data_to_bs(String sensed_sensor,String sensed_data, String time)
	{
		try
		{
			Socket soc1=new Socket("localhost",5005);
			DataOutputStream don=new DataOutputStream(soc1.getOutputStream());
//			Map types_and_keys = JDBC.get_type_keys();
//			types_and_keys.put("private", SET_IBS_PROTOCOL.getnerate_private_key(new BigInteger(types_and_keys.get("public").toString()),new BigInteger(types_and_keys.get("pi").toString())));
//			String sign = SET_IBS_PROTOCOL.SET_IBS_SIGN(sensed_data, types_and_keys, time);
			don.writeUTF(sensed_sensor);
			System.out.println(sensed_data);
			don.writeUTF(sensed_data);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}



	public void actionPerformed(ActionEvent ae)
	{

		if(ae.getSource()==jb)
		{

			if(status<20)
				status=0;
			else
				status -=20;
			if(status>60)
				progressBar.setForeground(Color.green);
			else if(status>30)
				progressBar.setForeground(Color.blue);
			else
				progressBar.setForeground(Color.RED);
			progressBar.setValue(status);

			temp.setVisible(false);
			temp=new JLabel(new ImageIcon("images/fire.gif"),JLabel.LEFT);
			temp.setBounds(5,5,465,310);
			jp2.add(temp);

			jt.setText("fire event \ngenerated by \n attacker");
			jp4.setBackground(Color.red);
			jl3.setVisible(true);
			jl3.setBounds(0,0,200,175);
			jp4.add(jl3);
			jb.setBackground(Color.red);
			//jb.setForeground(Color.red);

			Random rnd=new Random();
			i=rnd.nextInt(7);

			place=sevent[i];
			t=tree[i]; 
			g=gas[i]; 
			Date d=new Date();
			System.out.println(d);
			String s=d.toString();
			jt1.append("Occured Date & Time is  "+s +"\n");
			jt1.append("Fire Occured Place is "+place +"\n");    
			jt1.append("No of Trees Destroyed "+ t +"\n");	
			jt1.append("gas Released "+ g  +"\n");
			String time = util.get_time();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try{
				String firesensorinfo="fire"+","+"forest"+","+place+","+t+","+g;
				send_data_to_bs("fire sensor",firesensorinfo, time);
				JOptionPane.showMessageDialog(null,"Fire ocurred in Place  " + place,"Place",JOptionPane.PLAIN_MESSAGE);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		if(ae.getSource()==jb1)
		{
			jb.setBackground(Color.white);
			jt.setText("Sensing...");
			jt1.setText("         ******Information******\n");
			jt1.append("----------------------------------------\n");
			temp.setVisible(false);
			temp=new JLabel(new ImageIcon("images/f3.jpg"),JLabel.LEFT);
			temp.setBounds(5,5,465,310);
			jp2.add(temp);
		}
		if(ae.getSource()==jb2)
		{
			System.exit(0);
		}
	}

	@Override
	public void run() {
		init();
	}
	public static void main(String args[])
	{
		Thread t = new Thread(new attacker_sensor());
		t.start();

	}
}
