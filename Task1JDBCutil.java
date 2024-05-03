package in.JdbcUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Task1JDBCutil {

	public static Connection getJdbcConnection() throws IOException , SQLException {
		FileInputStream FIS = new FileInputStream("OnlineReservationSystem");
		Properties p = new Properties();
		p.load(FIS);
		
		String url =  p.getProperty("url");
		String UserName =  p.getProperty("user");
		String Password =  p.getProperty("password");
		
		System.out.println(url);
		System.out.println(UserName);
		System.out.println(Password);
		
		Connection cn =  DriverManager.getConnection(url, UserName, Password);
		
		return cn;
	}
	
	public static void CloseResourse(Connection cn,PreparedStatement st,ResultSet result) throws SQLException {
		if(cn != null) {
			cn.close();
		}
		if(st != null) {
			st.close();
		}
		if(result != null) {
			result.close();
		}
	}

}
