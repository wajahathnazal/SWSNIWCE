
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentInformation;
import org.pdfbox.util.PDFTextStripper;

import database.JDBC;
import enums.IpAddress;
import enums.ports;



public class user {
	private static String aString;
	private JFrame jf;
	private JTextArea ta1;
	private JButton b1,b3,b4,b5,b6;
	private JButton b2;
	static PDFParser parser;
	static String parsedText;
	static PDFTextStripper pdfStripper;
	static PDDocument pdDoc;
	static COSDocument cosDoc;
	static PDDocumentInformation pdDocInf;
	private JLabel l1;
	String file_name;

	public user() 
	{
		JDBC.connect_database();
		jf = new JFrame();
		jf.setLayout(null);

		ta1=new JTextArea();
		final JScrollPane jsp1=new JScrollPane(ta1,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp1.setBounds(20, 20, 240, 280);
		ta1.setEditable(false);
		jf.add(jsp1);

		b1=new JButton("Browse");
		b1.setBounds(310,30,116,30);
		jf.add(b1);


		b2=new JButton("upload");
		b2.setBounds(310,80,116,30);
		jf.add(b2);

		b3=new JButton("Download");
		b3.setBounds(310,130,116,30);
		jf.add(b3);

		JButton b8 = new JButton("Review");
		b8.setBounds(310,180,116,30);
		jf.add(b8);



		b6=new JButton("clear");
		b6.setBounds(310,230,116,30);
		jf.add(b6);



		b5=new JButton("Exit");
		b5.setBounds(310,280,116,30);
		jf.add(b5);

		l1=new JLabel(new ImageIcon("images/User.png"));
		l1.setBounds(0,0,450,350);
		jf.add(l1);

		jf.setVisible(true);
		jf.setSize(450, 350);

		b8.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
//				Desktop desktop = Desktop.getDesktop();
//				File dirToOpen = null;
//				try {
//					dirToOpen = new File(new File("E:\\workspace\\juno 2014\\NCCloud_final_code\\downloaded file\\").getAbsolutePath());
//					desktop.open(dirToOpen);
//				} catch (IllegalArgumentException iae) {
//					System.out.println("File Not Found");
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
			//	new Review();
			}});

		b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				{
					DefaultListModel files = new DefaultListModel();
					files = JDBC.get_files();

					final JFrame jf1 = new JFrame();
					jf1.setLayout(null);

					ImageIcon icon2= new ImageIcon("images/server_info.jpg");
					JLabel l6 = new JLabel(icon2,JLabel.CENTER);

					JLabel l1 = new JLabel("List of files:",JLabel.CENTER);
					l1.setFont(new Font("Serif",Font. BOLD,20));

					final JList ta1 = new JList();
					JScrollPane jsp = new JScrollPane(ta1,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);


					final JButton b11 = new JButton("Exit");

					final JButton b12 = new JButton("Download file");

					addcomponent(jf1,l1,10,20,300,20);
					addcomponent(jf1,jsp,10,50,250,180);
					addcomponent(jf1,b11,170,235,80,25);
					addcomponent(jf1,b12,20,235,120,25);
					addcomponent(jf1,l6,0,0,300,300);
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


						}});

					b12.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e){
							String data1 = "";
							String file_to_download = ta1.getSelectedValue().toString();
							try
							{
								System.out.println("sending request to cloud server");
								Socket client = new Socket();
								System.out.println("cloud server accepted the request");
								DataOutputStream out = new DataOutputStream(client.getOutputStream());
								DataInputStream in = new DataInputStream(client.getInputStream());
								out.writeUTF("download");
								out.writeUTF(file_to_download);
								data1 = in.readUTF();
								save_in_file(file_to_download,data1);
								out.close();
								client.close();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
							jf1.setVisible(false);
							user.this.ta1.setText("file name : "+file_to_download+"\n file data : \n "+data1);

						}});

				}}});


		b5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf.setVisible(false);
				//new Review();
			}});

		b6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ta1.setText(null);
			}});

		b1.addActionListener(new ActionListener(){
			private String path;
			private String extension;

			public void actionPerformed(ActionEvent e){
				JFileChooser fc = new JFileChooser();
				int option = fc.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION)
				{
					try {
						String sf = fc.getSelectedFile().getAbsolutePath();
						File fn1 = new File(sf);
						file_name = fn1.getName();
						//jt2.setText(file_name);

						System.out.println(sf);
						path = sf;
						//SimplePDFSearch ob = new SimplePDFSearch();
						System.out.println("File Name :-----" + path);

						extension = path.substring(path.lastIndexOf('.'));
						System.out.println("Extension  ::" + extension);
						if (extension.equalsIgnoreCase(".rtf")) 
						{
							String my = "";
							try {
								Reader reader = new InputStreamReader(
										new FileInputStream(path));
								BufferedReader fin = new BufferedReader(reader);
								String tempf;

								while ((tempf = fin.readLine()) != null) {
									my = my + tempf;
								}
								fin.close();

								System.out.println("Result2:=>" + my);

							} catch (FileNotFoundException e2) {
								System.out.println("File not found");
							} catch (IOException e1) {
								System.out.println("I/O error");
							}


							ta1.setText(new String(my));
							setString(my);
							System.out.println("Original Word RTF    : " + my);

							// System.out.println("Decryption  Word RTF    : "+sd);
						}
						else if (extension.equalsIgnoreCase(".doc"))
						{

							String s = readDocFile(path);
							ta1.setText(new String(s));
							setString(s);
							System.out.println("Original Word Doc  :: " + s);

						}
						else if (extension.equalsIgnoreCase(".txt")) {
							/******** Text / Log File Encryption / Decryption Method Cal ***********************/
							String stxt = readtxtFile(path);

							ta1.setText(new String(stxt));
							setString(stxt);
							System.out.println("Original Text  :: " + stxt);

						}
						else if (extension.equalsIgnoreCase(".pdf")) {


							try {




								String pageText = pdftoText(path);
								ta1.setText(new String(pageText));
								setString(pageText);
								System.out
								.println("Original  PDF    : " + pageText);

							} catch (Exception e1) {
								System.out.println(" error   ::" + e);
							} catch (OutOfMemoryError exc) {
								System.out.println(" OutOfMemoryError   ::" + exc);

							}

						} else {
							System.out
							.println(" File not found ....Please Check file extension ");
						}



					} catch (Exception ee) {
						JOptionPane.showMessageDialog(null, "Reload File Again.."
								+ ee);
					}
				}
			}});

		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(ta1.getText().length()!=0)
				{
					//					if(JDBC.file_already_exits(file_name))
					//					{
					try 
					{
						Socket client = null;
						String cloud = JOptionPane.showInputDialog("Enter the cloud name");
						System.out.println("sending request to cloud server");
						if(cloud.equalsIgnoreCase("amazon"))
							client = new Socket();
						else if(cloud.equalsIgnoreCase("dropbox"))
							client = new Socket();
						else
							client = new Socket();
						System.out.println("cloud server accepted the request");
						DataOutputStream out = new DataOutputStream(client.getOutputStream());
						out.writeUTF("upload");
						out.writeUTF(file_name);
						String data = (ECC.encryption(ta1.getText()));
						out.writeUTF(data);
						ta1.setText(null);
						out.close();
						client.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}
				else
				{
					JOptionPane.showMessageDialog(null, "Select a file");

				}
			}});


	}

	public static String getString() {
		return aString;
	}


	public static void setString(String s) {
		aString = s;
	}

	public static void save_in_file(String file_name,String data)
	{

		try
		{
			String path = ".\\downloaded file\\";

			File file = new File(path+file_name);

			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


	}

	public static String getExtension(File f) {
		String ext = null;
		String ss = f.getName();
		int i = ss.lastIndexOf('.');

		if (i > 0 && i < ss.length() - 1) {
			ext = ss.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public static String pdftoText(String filename) {
		System.out.println("Parsing text from PDF file " + filename + "....");
		File f = new File(filename);
		if (!f.isFile()) {
			System.out.println("File " + filename + " does not exist.");
			return null;
		}
		try {
			parser = new PDFParser(new FileInputStream(f));
		} catch (Exception e) {
			System.out.println("Unable to open PDF Parser.");
			return null;
		}
		try 
		{
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (Exception e) {
			System.out
			.println("An exception occured in parsing the PDF Document.");
			e.printStackTrace();
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}
		System.out.println("Done.");
		return parsedText;
	}

	public static String readtxtFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}

	}

	public static String readDocFile(String path1) {
		File docFile = null;
		WordExtractor docExtractor = null;
		// WordExtractor exprExtractor = null ;
		try {
			docFile = new File(path1);
			// A FileInputStream obtains input bytes from a file.
			FileInputStream fis = new FileInputStream(docFile.getAbsolutePath());

			// A HWPFDocument used to read document file from FileInputStream
			HWPFDocument doc = new HWPFDocument(fis);
			docExtractor = new WordExtractor(doc);

		} catch (Exception exep) {
			System.out.println(exep.getMessage());
		}

		// This Array stores each line from the document file.
		String[] docArray = docExtractor.getParagraphText();

		for (int i = 0; i < docArray.length; i++) {
			if (docArray[i] != null)
				System.out.println("Line " + i + " : " + docArray[i]);
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < docArray.length; i++) {
			result.append(docArray[i]);
			// result.append( optional separator );
		}
		String mynewstring = result.toString();
		return mynewstring;
	}

	private void addcomponent(Container cp,Component c, int startx, int starty, int width, int height) {
		// TODO Auto-generated method stub
		c.setBounds(startx,starty,width,height);
		cp.add(c);

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
		new user();
	}

}
