


import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class view_data extends JFrame{

	public view_data() {
		Connection cn=null;
		Statement st=null;
		ResultSet rs=null;

		Vector columnNames = new Vector();
		Vector data = new Vector();

		try
		{

			try{

				Class.forName("oracle.jdbc.driver.OracleDriver");
				cn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","system");
				System.out.println("Connected to Database...");
			}

			catch(Exception e)
			{
				System.out.println(e);
			}	


			//  Read data from a table

			String sql = "Select sensed_type,country,area,damage from secure_trans_sensor";
			Statement stmt = cn.createStatement();
			rs = stmt.executeQuery( sql );
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			//  Get column names

			for (int i = 1; i <= columns; i++)
			{
				columnNames.addElement(md.getColumnName(i));
			}

			//  Get row data

			while (rs.next())
			{
				Vector row = new Vector(columns);

				for (int i = 1; i <= columns; i++)
				{
					if(rs.getObject(i).toString().equals("java"))
						row.add("");
					else
						row.addElement(rs.getObject(i)); 
				}

				data.addElement( row );
			}

			// rs.close();
			//stmt.close();
		}
		catch(Exception e)
		{
			System.out.println( e );
		}

		//  Create table with database data

		JTable table = new JTable(data, columnNames);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add( scrollPane );

		JPanel buttonPanel = new JPanel();
		getContentPane().add( buttonPanel, BorderLayout.SOUTH );
	}
	
	public static void main(String[] args) 
	{
		view_data frame = new view_data();
		frame.pack();
		frame.setVisible(true);
	}

}
