

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import database.*; 

import javax.swing.*;



public class TrustedCenter extends Thread
{



	static void init()
	{
		JDBC.connect_database();
		JLabel jLabel1 = new JLabel("",JLabel.CENTER);
		JLabel jLabel2 = new JLabel("",JLabel.CENTER);
		jLabel1.setFont(new Font("Brush Script M", Font.BOLD, 20));
		jLabel2.setFont(new Font("Brush Script M", Font.BOLD, 16));
		jLabel1.setForeground(new Color(120,20,60));
		jLabel2.setForeground(new Color(120,20,60));


		JLabel jLabel3 = new JLabel("TRUSTED CENTER",JLabel.CENTER);
		jLabel3.setFont(new Font("Brush Script M", Font.BOLD, 18));
		jLabel3.setForeground(new Color(120,20,60));

		final JTextArea jTextArea1 = new JTextArea();
		JScrollPane jScrollPane1 = new JScrollPane();
		//jButton1 = new JButton();
		JButton jButton2 = new JButton();
		JButton exit = new JButton("Exit");
		JButton jButton3 = new JButton();
		JFrame contentPane = new JFrame();
		contentPane.setBackground(Color.cyan);

		jScrollPane1.setViewportView(jTextArea1);

		jButton2.setText("View data");

		jButton3.setText("CLEAR");

		JLabel back = new JLabel(new ImageIcon("images/f.jpg"));

		JLabel status = new JLabel("Status");
		status.setFont(new Font("Brush Script M", Font.BOLD, 18));
		status.setForeground(new Color(41,36,33));

		contentPane.setLayout(null);
		addComponent(contentPane, jLabel1, 0, 0, 450, 30);
		addComponent(contentPane, jLabel2, 0, 30, 450, 30);
		addComponent(contentPane, jLabel3, 0, 60, 450, 30);
		addComponent(contentPane, status, 20, 90, 100, 30);
		addComponent(contentPane, jScrollPane1, 35, 120, 360, 320);
		addComponent(contentPane, jButton2, 30, 454, 100, 40);
		addComponent(contentPane, jButton3, 170, 454, 100, 40);
		addComponent(contentPane, exit,300, 454, 100, 40);
		addComponent(contentPane, back, 0, 0, 450, 545);
		contentPane.setLocation(new Point(0, 0));
		contentPane.setSize(new Dimension(450, 545));
		contentPane.setVisible(true);

		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				jTextArea1.setText(null);
			}});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}});

		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				view_data frame = new view_data();
				frame.pack();
				frame.setVisible(true);
			}});

		try
		{
			ServerSocket server = new ServerSocket(5005);	
			System.out.println("data center server waiting at "+server.getLocalPort());
			while (true)
			{

				Socket client_socket = server.accept();
				DataInputStream in = new DataInputStream(client_socket.getInputStream());
				DataOutputStream don=new DataOutputStream(client_socket.getOutputStream());

				String data = in.readUTF();
				if(data.equals("get best cloud"))
				{
					Set service_providers = JDBC.get_unique_service_providers();
					HashMap rating_of_cloud = new HashMap();
					for(Object ser_pro : service_providers)
					{
						String ser = ser_pro.toString();
						int cost = JDBC.get_avg_cost(ser);
						int capacity = JDBC.get_avg_capacity(ser);
						int avg = cost/capacity;
						int avg_rating = JDBC.get_avg_rating(ser);
						int ranking = avg*avg_rating;
						rating_of_cloud.put(ser, ranking);


						System.out.println("ser=="+ser);
						System.out.println("cost=="+cost);
						System.out.println("capacity=="+capacity);
						System.out.println("avg=="+avg);
						System.out.println("avg_rating=="+avg_rating);
						System.out.println("ranking=="+ranking);


					}
					rating_of_cloud = sortByValues(rating_of_cloud);
					Set set2 = rating_of_cloud.entrySet();
					Iterator iterator2 = set2.iterator();
					while(iterator2.hasNext()) {
						Map.Entry me2 = (Map.Entry)iterator2.next();

						don.writeUTF( me2.getKey().toString());
						break;
					}
				}
				client_socket.close();
			}		
		}
		catch(Exception e)

		{
			e.printStackTrace();

		}
	}

	private static HashMap sortByValues(HashMap map) { 
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
				.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		} 
		return sortedHashMap;
	}


	private static void csp2(String data_read) {
		String path = ".//resources//csp2//";
		//path += file_name;
		File file = new File(path);
		try
		{
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data_read);
			bw.close();
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}

	}
	private static void csp3(String data_read) {
		String path = ".//resources//csp3//";
		//path += file_name;
		File file = new File(path);
		try
		{
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data_read);
			bw.close();
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}



	}


	private static void addComponent(Container container, Component c, int x, int y,int width, int height) 
	{
		c.setBounds(x, y, width, height);
		container.add(c);
	}


	public	static String get_date_time()
	{
		Date d=new Date();
		return d.toString();
	}
	@Override
	public void run() {
		init();
	}

	public static void main(String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			//			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		}
		catch (Exception ex)
		{
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
		}
		TrustedCenter t = new TrustedCenter();
		t.start();
	}

	public static void csp1(String data)
	{
		String path = ".//resources//csp1//";
		//path += file_name;
		File file = new File(path);
		try
		{
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}

	}
}
