package project4;

import java.sql.ResultSet;
import java.sql.*;
import java.util.*;

public class SQLFormatter {
	public static synchronized String getHTMLRows(ResultSet result) throws SQLException{
		StringBuffer htmlRows = new StringBuffer();
		ResultSetMetaData metaData = result.getMetaData();
		int colCnt = metaData.getColumnCount();
		
		htmlRows.append("<tr bgcolor=#FF0000 align=center>");
		for(int i=1; i<= colCnt; i++) {
			htmlRows.append("<td><b>" + metaData.getColumnName(i) + "</td>");
		}
		htmlRows.append("</tr>");
		int cnt =0;
		while(result.next()) {
			
			if(cnt % 2 == 0) {
				htmlRows.append("<tr bgcolor=#cccccc color=black>");
			}else {
				htmlRows.append("<tr bgcolor=#Black color=black>");
			}
			cnt++;
			for(int i = 1; i<= colCnt; i++) {
				htmlRows.append("<td align=cneter>"+ result.getString(i) + "</td>");
			}
			
		}
		htmlRows.append("</tr>");
		
		return htmlRows.toString();
	}
}
