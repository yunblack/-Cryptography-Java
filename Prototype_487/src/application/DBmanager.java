package application;

/*
 * Jongsun Park
 */

import java.sql.*;
import java.util.ArrayList;

public class DBmanager {
	public int numbers = 0;
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // JDBC Driver
	static final String DB_URL = "jdbc:mysql://124.111.34.56/cse487?characterEncoding=UTF-8&serverTimezone=UTC"; // DB Location
	// input database's name in databasename
	static final String USERNAME = "jsp"; // DB ID
	static final String PASSWORD = "1234"; // DB Password

	public void uploadE(int numcount, String email, String shares, String tau) {
		Connection conn = null; // Set connection conn to null
		//Statement stmt = null; // set statement stmt with null
		PreparedStatement pst = null; // set PreparedStatement pst with null
		String sql = "insert into test(numcount, email, shares, tau) values(?, ?, ?, ?)"; // sql query
		
		System.out.print("User Table Access : "); // Debug statement
		try {
			Class.forName(JDBC_DRIVER); 
			// Class Ŭ������ forName()�Լ��� �̿��ؼ� �ش� Ŭ������ �޸𸮷� �ε� �ϴ� ���Դϴ�. 
			// Using forName() in Class to loading memory of this class. (This is the translated comment of above comment)
			// URL, ID, password�� �Է��Ͽ� �����ͺ��̽��� �����մϴ�.
			// Using URL, ID, Password to access DB (This is the translated comment of above comment)
			
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); // set Connection with DB_URL which is the location of database, USERNAME = ID, PASSWORD = PASSWORD
			pst = conn.prepareStatement(sql); // set preparedstatement with the sql query 
			//stmt = conn.createStatement(); // createstatement
			
			pst.setInt(1, numcount); // set first ? in the values(?, ?, ?, ?) with int which is the parameter of uploadE function (AccessStructure Number)
			pst.setString(2, email); // set second ? in the values(?, ?, ?, ?) with String which is the parameter of uploadE function (Email Address)
			pst.setString(3, shares); // set third ? in the values(?, ?, ?, ?) with String which is the parameter of uploadE function (Shares)
			pst.setString(4, tau); // set last ? in the values(?, ?, ?, ?) with String which is the parameter of uploadE function (Tau Value)
			pst.executeUpdate(); // execute sql query
			//ResultSet rs = stmt.executeQuery(sql);
			
			//while(rs.next()) {
			//	System.out.println(rs.getString(1));
			//}
			// ���Ӱ���� ����մϴ�. print the result
			if (conn != null) {
				System.out.println("����"); // if successfully connected, then print success(����)
			} else 
				System.out.println("����"); // else print failure(����)
			//rs.close();
			//stmt.close();
			pst.close(); // close prepared statement
			conn.close(); // close connection with database

			/*
			 * Exception Space For Printing Errors
			 */
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exection");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
			e.printStackTrace();
		}

	}
	
	public int getAccessNumber() {
		Connection conn = null; // Set connection conn to null
		Statement stmt = null; // set statement stmt with null
		//PreparedStatement pst = null; // set PreparedStatement pst with null
		String sql = "select * from test"; // sql query
		
		System.out.print("User Table Access : "); // Debug statement
		try {
			Class.forName(JDBC_DRIVER); 
			// Class Ŭ������ forName()�Լ��� �̿��ؼ� �ش� Ŭ������ �޸𸮷� �ε� �ϴ� ���Դϴ�. 
			// Using forName() in Class to loading memory of this class. (This is the translated comment of above comment)
			// URL, ID, password�� �Է��Ͽ� �����ͺ��̽��� �����մϴ�.
			// Using URL, ID, Password to access DB (This is the translated comment of above comment)
			
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); // set Connection with DB_URL which is the location of database, USERNAME = ID, PASSWORD = PASSWORD 
			//stmt = conn.createStatement(); // createstatement
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				numbers = Integer.parseInt(rs.getString(0));
				System.out.println(rs.getString(0));
			}
			// ���Ӱ���� ����մϴ�. print the result
			if (conn != null) {
				System.out.println("����"); // if successfully connected, then print success(����)
			} else 
				System.out.println("����"); // else print failure(����)
			rs.close();
			stmt.close();
			conn.close(); // close connection with database

			/*
			 * Exception Space For Printing Errors
			 */
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exection");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return numbers;
	}
	
	public ArrayList<String> getDB(int numcount) {
		ArrayList<String> addre = new ArrayList();
		Connection conn = null; // Set connection conn to null
		Statement stmt = null; // set statement stmt with null
		//PreparedStatement pst = null; // set PreparedStatement pst with null
		String sql = "select * from test where numcount = " + numcount; // sql query
		
		System.out.print("User Table Access : "); // Debug statement
		try {
			Class.forName(JDBC_DRIVER); 
			// Class Ŭ������ forName()�Լ��� �̿��ؼ� �ش� Ŭ������ �޸𸮷� �ε� �ϴ� ���Դϴ�. 
			// Using forName() in Class to loading memory of this class. (This is the translated comment of above comment)
			// URL, ID, password�� �Է��Ͽ� �����ͺ��̽��� �����մϴ�.
			// Using URL, ID, Password to access DB (This is the translated comment of above comment)
			
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); // set Connection with DB_URL which is the location of database, USERNAME = ID, PASSWORD = PASSWORD 
			//stmt = conn.createStatement(); // createstatement
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				addre.add(rs.getString(1));
				System.out.println(rs.getString(1));
			}
			// ���Ӱ���� ����մϴ�. print the result
			if (conn != null) {
				System.out.println("����"); // if successfully connected, then print success(����)
			} else 
				System.out.println("����"); // else print failure(����)
			rs.close();
			stmt.close();
			conn.close(); // close connection with database

			/*
			 * Exception Space For Printing Errors
			 */
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exection");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return addre;
	}

}