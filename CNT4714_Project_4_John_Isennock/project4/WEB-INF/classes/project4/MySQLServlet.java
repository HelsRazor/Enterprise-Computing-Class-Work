/* Name: John Isennock
    Course: CNT 4714-Spring2021-Project Four
    Assignment title: A Three-Tier Distributed Web-Based Application
    Date: Friday April 30,2021
*/
package project4;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.mysql.jdbc.Driver;

public class MySQLServlet extends HttpServlet {
	public Connection connection;
	public Statement statement;
	
	@Override
	public void init() throws ServletException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dbURL = "jdbc:mysql://localhost:3306/project4?characterEncoding=utf8";
			String username = "root";
			String password = "root";
			connection = DriverManager.getConnection(dbURL, username, password);
		}catch(ClassNotFoundException e) {
			System.out.println("Driver Loading Error: "+ e.getMessage());
		}catch(SQLException e2) {
			System.out.println("Error accessing the DB: " + e2.getMessage());
		}
	}
	
	
	public void destory()
	{
		try {connection.close();}
		catch(SQLException e3) {
			System.out.println("Error Cloing the DB connection: "+ e3.getMessage());
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

		String sqlStatement = request.getParameter("sqlStatement");
		String message = "";
		String logicStatement = "update suppliers set status = status + 5 where snum in (select snum from shipments where quantity >=100);";
		try {
//			if(this.connection == null) {
//				try {
//					Class.forName("com.mysql.jdbc.Driver");
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				String dbURL = "jdbc:mysql://localhost:3306/project4";
//				String username = "root";
//				String password = "root";
//				connection = DriverManager.getConnection(dbURL, username, password);
//				}
			Statement statement = connection.createStatement();
			sqlStatement = sqlStatement.trim();
			String sqlType = sqlStatement.substring(0, 6);
			if(sqlType.equalsIgnoreCase("select")) {
				ResultSet rs = statement.executeQuery(sqlStatement);
				message = SQLFormatter.getHTMLRows(rs);
			}else {
				int i = statement.executeUpdate(sqlStatement);
				if(i==0)
				{
					message ="<tr bgcolor=#00FF00><td align=center><font color = #000000><b>"+
							"The statement executed successfully." + "</td></tr></font></b>";
				}else {
					int q = statement.executeUpdate(logicStatement);
					message ="<tr bgcolor=#00FF00><td align=center><font color = #000000><b>"+
								"The statement executed successfully.<br>"+ i + " row(s) affected." +
								"<p><b>Buesiness Logic Detected!</b> - Updating Supplier Status</p>"+
								"<p>Business Logic updated " + q + " supplier status marks.</p>" + 
								"</td></tr></font></b>";
				}
			}
			statement.close();
		}catch(SQLException e4) {
			message = "<tr bgcolor=#ff0000><td><font color =#ffffff><b>Error executing the SQL statement:</b><br>" + e4.getMessage()+ "</tr></td></font>";
		}
		HttpSession session = request.getSession();
		session.setAttribute("sqlStatement", sqlStatement);
		session.setAttribute("message", message);
		RequestDispatcher disp = getServletContext().getRequestDispatcher("/index.jsp");
		disp.forward(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request,response);
	}
	
}
