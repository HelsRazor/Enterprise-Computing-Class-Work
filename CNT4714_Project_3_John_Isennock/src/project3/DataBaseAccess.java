package project3;

import java.sql.*;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;


public class DataBaseAccess extends AbstractTableModel {
	
	private MysqlDataSource dataSource = null;
	private Connection connection = null;
	private DatabaseMetaData dbMetaData = null;
	private boolean connectedToDatabase = false;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private ResultSetMetaData rMetaData = null;
	private int numberOfRows;
	private PreparedStatement pStatement = null;
	
	public boolean getConnectedToDatabase() {
		return connectedToDatabase;
	}
	
	public DataBaseAccess(String UN, char[] PW, Object url) {
		try {
			dataSource = new MysqlDataSource();
			dataSource.setUrl((String) url);
			dataSource.setUser(UN);
			dataSource.setPassword(String.valueOf(PW));
			connection = dataSource.getConnection();
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			connectedToDatabase = true;
		}catch(SQLException sqlE){
			JOptionPane.showMessageDialog(null, sqlE.getMessage(),"Database Error", JOptionPane.ERROR_MESSAGE);
			connectedToDatabase = false;
		}
	}
	
	

		
		
		
		

	
	public Class getColumnClass(int column) throws IllegalStateException
	{
		if(!connectedToDatabase)
		{
			throw new IllegalStateException("Not Connected to Database");
		}
		
		try
		{
			String className = rMetaData.getColumnClassName(column + 1);
			
			return Class.forName(className);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return Object.class;
	}
	
	public int getColumnCount() throws IllegalStateException
	{
		if(!connectedToDatabase)
		{
			throw new IllegalStateException("Not Connected to Database");
		}
		
		try {
			return rMetaData.getColumnCount();
		}
		catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
		return 0;
	}
	
	public String getColumnName(int column) throws IllegalStateException
	{
		if(!connectedToDatabase)
		{
			throw new IllegalStateException("Not Connected to Database");
		}
		
		try {
			return rMetaData.getColumnName(column +1 );
		}
		catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
		return "";
	}
	
	public int getRowCount() throws IllegalStateException
	{
		if(!connectedToDatabase)
		{
			throw new IllegalStateException("Not Connected to Database");
		}
		return numberOfRows;
	}
	
	public Object getValueAt(int row,int column) throws IllegalStateException
	{
		if(!connectedToDatabase)
		{
			throw new IllegalStateException("Not Connected to Database");
		}
		
		try {
			resultSet.next();
			resultSet.absolute(row + 1);
			return resultSet.getObject(column + 1);
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return "";
	}
	
	public void setQuery(String query) throws SQLException, IllegalStateException
	{
		if(!connectedToDatabase) {
			throw new IllegalStateException("Not Connected to Database");
		}
		
		resultSet = statement.executeQuery(query);
		
		rMetaData = resultSet.getMetaData();
		
		resultSet.last();
		numberOfRows = resultSet.getRow();
		
		fireTableStructureChanged();
	}
	
	public void setUpdate(String query) throws SQLException, IllegalStateException
	{
		int res;
		
		if(!connectedToDatabase)
		{
			throw new IllegalStateException("Not Connected to Database");
		}
		
		
		pStatement = connection.prepareStatement(query);
			
		res = pStatement.executeUpdate(query);
		
		
		
		fireTableStructureChanged();
	}
	
	public void disconnectFromDatabase()
	{
		if(!connectedToDatabase)
		{
			return;
		}
		
		try {
			statement.close();
			connection.close();
		}
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		finally
		{
			connectedToDatabase = false;
		}
	}
	
	public void makeInquery(String q) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		
		ResultSet resultSet = statement.executeQuery(q);
		
		ResultSetMetaData rMetaData = resultSet.getMetaData();
		
		int numOfColumns = rMetaData.getColumnCount();
		
		
		
		System.out.printf("Table has %d Columns\n",numOfColumns);
		
		for(int i= 1; i<= numOfColumns; i++) {
			
			System.out.printf("%-20s\t", rMetaData.getColumnName(i));
			
		}
		System.out.println();
		
		for(int i = 1; i<= numOfColumns;i++) {
			
			System.out.printf("%-20s\t", "-------------------");
			
		}
		
		System.out.println();
		
		while(resultSet.next())
		{
			
			for(int i = 1; i<= numOfColumns;i++)
			{
				System.out.printf("%-20s\t", resultSet.getObject(i));
			}
			System.out.println();
		}
		
		
	}
	
	   
}
