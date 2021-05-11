package project3;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.Box;
import java.awt.Font;


public class GUI extends JFrame
{
	
	static final String DEFAULT_QUERY = "SELECT * FROM bikes";
	
	private DataBaseAccess tableModel;
	
	private JTextArea queryArea;
	
	public GUI() {
		
		super("SQL Client App - (JAI - CNT 4714 - Spring 2021");
		
		setLayout(null);
		
		JLabel enterDBInfoL = new JLabel();
		enterDBInfoL.setText("Enter Database Information");
		enterDBInfoL.setBounds(10, 5, 175, 20);
		enterDBInfoL.setForeground(Color.BLUE);
		add(enterDBInfoL);
		
		JComboBox JDBC_DriverCB = new JComboBox();
		
		
		try {
			FileInputStream fileIn = new FileInputStream("db.properties");
			Properties prop = new Properties();
			prop.load(fileIn);
			JDBC_DriverCB.addItem(prop.getProperty("MYSQL_DB_DRIVER_CLASS"));
		} catch (IOException ioe1) {
			// TODO Auto-generated catch block
			ioe1.printStackTrace();
		}
		
		
		JLabel driverL = new JLabel("JDBC Driver");
		driverL.setBounds(10,30,118,20);
		driverL.setBackground(Color.LIGHT_GRAY);
		driverL.setOpaque(true);
		add(driverL);
		
		JDBC_DriverCB.setBounds(130, 30, 350, 20);
		add(JDBC_DriverCB);
		
		
		JLabel dbURLLab = new JLabel("Database URL");
		dbURLLab.setBounds(10,53,118,20);
		dbURLLab.setBackground(Color.LIGHT_GRAY);
		dbURLLab.setOpaque(true);
		add(dbURLLab);
		
		
		JComboBox dbURLCB = new JComboBox();
		
		
		JLabel userNameLabel = new JLabel("Username");
		
		userNameLabel.setBounds(10, 76,118,20);
		userNameLabel.setBackground(Color.LIGHT_GRAY);
		userNameLabel.setOpaque(true);
		add(userNameLabel);
		
		
		JTextField userNameField = new JTextField();
		userNameField.setEditable(true);
		userNameField.setBounds(130,76,350,20);
		add(userNameField);
		
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 99,118,20);
		passwordLabel.setBackground(Color.LIGHT_GRAY);
		passwordLabel.setOpaque(true);
		add(passwordLabel);
		
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(130,99,350,20);
		add(passwordField);
		
		
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream("db.properties");
			Properties prop = new Properties();
			prop.load(fileIn);
			dbURLCB.addItem(prop.getProperty("MYSQL_DB_URL"));
		} catch (IOException ioe2) {
			// TODO Auto-generated catch block
			ioe2.printStackTrace();
		}
		
		dbURLCB.setBounds(130, 53, 350, 20);
		add(dbURLCB);
		
		
		JLabel enterSQLCommandLabel = new JLabel("Enter An SQL Command");
		enterSQLCommandLabel.setBounds(500,5,175,20);
		enterSQLCommandLabel.setForeground(Color.BLUE);
		add(enterSQLCommandLabel);
		
		JTextField statusWindow = new JTextField();
		statusWindow.setEditable(false);
		statusWindow.setText("No Connection Now");
		statusWindow.setBackground(Color.BLACK);
		statusWindow.setForeground(Color.RED);
		statusWindow.setBounds(10,230,300,30);
		add(statusWindow);
		
		
		
		JButton connect = new JButton("Connect to Database");
		connect.setBackground(Color.BLUE);
		connect.setForeground(Color.YELLOW);
		connect.setBounds(330,230,175,30);
		connect.addActionListener(e->{
			tableModel = new DataBaseAccess(userNameField.getText(),passwordField.getPassword(),dbURLCB.getSelectedItem());
			if(tableModel.getConnectedToDatabase()==true)
			{
				statusWindow.setText("Connected to " + dbURLCB.getSelectedItem());
			}else
			{
				statusWindow.setText("No Connection Now");
			}
		});
		
		add(connect);
		
		
		
		JButton clearCommand = new JButton("Clear SQL Command");
		clearCommand.setBackground(Color.WHITE);
		clearCommand.setForeground(Color.RED);
		clearCommand.setBounds(515,230,175,30);
		
		
		
		JButton exeSQLCommand = new JButton("Execute SQL Command");
		exeSQLCommand.setBackground(Color.GREEN);
		exeSQLCommand.setBounds(700,230,175,30);
		add(exeSQLCommand);
		
		
		
		JLabel outPutLabel = new JLabel("SQL Exceution Result Window");
		outPutLabel.setForeground(Color.BLUE);
		outPutLabel.setBounds(30,260,175,20);
		add(outPutLabel);
		
		JButton clearOutPut = new JButton("Clear Result Window");
		clearOutPut.setBackground(Color.YELLOW);
		clearOutPut.setBounds(20,487,175,30);
		add(clearOutPut);
		
		
		queryArea = new JTextArea();
		queryArea.setWrapStyleWord(true);
		queryArea.setLineWrap(true);
		
		
		
		JScrollPane scrollPane = new JScrollPane(queryArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		Box box = Box.createHorizontalBox();
		box.add(scrollPane);
		box.setBounds(500,30,360,150);
		JTable resultTable = new JTable(tableModel);
		JScrollPane outPutPane = new JScrollPane(resultTable);
		outPutPane.setBounds(30, 280, 850, 200);
		add(box);
		add(outPutPane);
		
		
		exeSQLCommand.addActionListener(e->{
			
			try {
				if(queryArea.getText().toLowerCase().startsWith("select")) 
				{
					tableModel.setQuery(queryArea.getText());
					resultTable.setModel(tableModel);
					outPutPane.setViewportView(resultTable);
				}
				else {
					tableModel.setUpdate(queryArea.getText());
					//resultTable.setModel(tableModel);
					//outPutPane.setViewportView(resultTable);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
			}
			
			
		});
		
		
		clearCommand.addActionListener(e->{
			queryArea.setText("");
		});
		add(clearCommand);
		
		clearOutPut.addActionListener(e->{
			
			outPutPane.setViewportView(null);
			
		});
		
		setSize(1000,570);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					

	}

}