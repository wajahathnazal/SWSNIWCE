
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import database.JDBC;
import enums.ports;


public class GoogleDrive implements Runnable
{

	private JTextArea ta1,ta2;
	private JComboBox jc;
	private JTextField tf1;
	static String cloud_name = "Google Drive";

	public void init() 
	{

		JDBC.connect_database();

		JFrame jf=new JFrame("Google Drive :: Service provider");
		jf.setLayout(null);
		jf.setVisible(true);
		jf.setSize(450,420);

		JLabel jLabel1 = new JLabel("Google Drive",JLabel.CENTER);
		jLabel1.setFont(new Font("caliber", Font.BOLD, 20));
		jLabel1.setForeground(Color.white);
		jLabel1.setBounds(0,10,450,30);
		jf.add(jLabel1);


		ta1=new JTextArea();
		ta1.setBounds(20, 100, 390, 200);
		jf.add(ta1);

		ta2=new JTextArea();
		ta2.setBounds(20, 100, 150, 200);
	//	jf.add(ta2);

		JButton b1=new JButton("View file");
		b1.setBounds(10, 320, 120, 30);
		jf.add(b1);

		JButton b3=new JButton("Add catalog");
		b3.setBounds(150, 320, 120, 30);
		jf.add(b3);

		JButton b2=new JButton("Exit");
		b2.setBounds(300, 320, 120, 30);
		jf.add(b2);

		ImageIcon icon2= new ImageIcon("images/driv.jpg");
		JLabel a1 = new JLabel(icon2);
		a1.setBounds(0, 0, 450, 420);
		jf.add(a1);

		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}});

		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				view_cloud_files(cloud_name);
			}});
		
		b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		//		new Service_provider_info(cloud_name);
			}});

		create_directory(cloud_name);

	try
		{

			ServerSocket server = new ServerSocket();
			System.out.println("data center server waiting at "+server.getLocalPort());
			while (true)
			{
				Socket client_socket = server.accept();
				DataInputStream in = new DataInputStream(client_socket.getInputStream());
				String data = in.readUTF();
				String data_read = "";
				String date = get_date_time();

				if(data.equalsIgnoreCase("bomb sensor"))
				{

										ta1.setText("Message : Recevied data from bomb sensor\nDate : "+date+"\n---------------------------------------------------------------------------------------\n"+ta1.getText());

				}
				else if(data.equals("upload"))
				{	
					String fileName = in.readUTF();
					String fileData =  in.readUTF();
					createFile(fileName,fileData);
					continue;
				}

				else if(data.equalsIgnoreCase("fire sensor"))
				{
					ta1.setText("Message : Recevied data from bomb sensor\nDate : "+date+"\n---------------------------------------------------------------------------------------\n"+ta1.getText());
				}

				else
				{
					ta1.setText("Message : Recevied data from bomb sensor\nDate : "+date+"\n---------------------------------------------------------------------------------------\n"+ta1.getText());


				}
				data_read = in.readUTF();
				csp1(data,data_read);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createFile(String fileName, String fileData) {
		// TODO Auto-generated method stub
		try
		{
			File f = new File(".//resources//Google Drive//"+fileName);
			if(!f.exists())
				f.createNewFile();
			FileWriter fw = new FileWriter(f.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fileData);
			bw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void csp1(String data,String sensed_data)
	{
		String path = ".//resources//Google Drive//"+data+".txt";
		//path += file_name;
		File file = new File(path);
		try
		{
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sensed_data);
			bw.close();
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}

	}

	public	static String get_date_time()
	{
		Date d=new Date();
		return d.toString();
	}

	private void store_file(String fileName, String fileData)
	{
		try
		{
			fileData = ECC.encryption(fileData);
			String add=null;
			add=".\\resources\\"+cloud_name+"\\"+fileName;
			FileWriter ryt=new FileWriter(add);
			BufferedWriter out=new BufferedWriter(ryt);
			out.write(fileData);
			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	private void view_cloud_files(String cloud_name)
	{
		DefaultListModel files = new DefaultListModel();
		String path = ".\\resources\\"+cloud_name+"\\";
		File directory = new File(path);
		File[] list_of_files = directory.listFiles();
		for(File f : list_of_files)
			files.addElement(f.getName());

		JFrame jf1 = new JFrame();
		jf1.setLayout(null);

		ImageIcon icon2= new ImageIcon("images/server_info.jpg");
		JLabel l6 = new JLabel(icon2,JLabel.CENTER);

		JLabel l1=new JLabel("List of files:",JLabel.CENTER);
		l1.setFont(new Font("Serif",Font. BOLD,20));

		JList ta1 = new JList();
		JScrollPane jsp = new JScrollPane(ta1,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);


		final JButton b11 = new JButton("Exit");


		addComponent(jf1,l1,10,20,300,20);
		addComponent(jf1,jsp,10,50,250,180);
		addComponent(jf1,b11,100,235,80,25);
		addComponent(jf1,l6,0,0,300,300);
		jf1.setSize(300, 300);
		jf1.setVisible(true);
		ta1.setModel(files);


		b11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

				Container frame = b11.getParent();
				do 
					frame = frame.getParent(); 
				while (!(frame instanceof JFrame));                                      
				((JFrame) frame).dispose();

			}
		});


	}

	private static void addComponent(Container container, Component c, int x, int y,int width, int height) 
	{
		c.setBounds(x, y, width, height);
		container.add(c);
	}




	private void create_directory(String directory_name)
	{
		String path = ".\\resources\\";
		path+=directory_name;
		File dir = new File(path);
		if(!dir.exists())
		{
			dir.mkdir();
		}
		else
		{
			File[] files = dir.listFiles();
			for(File file : files)
				file.delete();
		}
	}

	@Override
	public void run() {
		init();		
	}

	public static void main(String[] args) {

		GoogleDrive obj = new GoogleDrive();
		Thread t = new Thread(obj);
		t.start();

	}

}
