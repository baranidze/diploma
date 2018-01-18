package db;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import logic.OrderDocument;
import logic.Rubric;

public class DBWorker {
	
	private static DBWorker instance;
	
	private static final String username = "adminpp";
	private static final String password = "adminpp";
	private static final String connectionString = "jdbc:mysql://10.12.53.1:3306/orderdocument?useUnicode=true&characterEncoding=utf-8";
	
	private Connection connection = null;	
	private Statement statement = null;
	
	private DBWorker() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionString, username, password);
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			shutdown();
			JOptionPane.showMessageDialog(null,
				    "Не вдалося приєднатися до бази даних!",
				    "Помилка з'єднання з БД",
				    JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		} 
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public static synchronized DBWorker getInstance() throws SQLException {
		if (instance == null) {
			instance = new DBWorker();
		}
		return instance;
	}
	
	public void updateDocument(OrderDocument document){
		try {
			statement = connection.createStatement();
			statement.execute("UPDATE order_document SET rubricationStatus = 1 WHERE doc_code='" + document.getCode() + "';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateDocumentRubric(OrderDocument document, String rubric) throws SQLException{
		statement = connection.createStatement();
		statement.execute("UPDATE order_document SET id_heading = (SELECT id_heading FROM heading WHERE name_heading ='" + rubric + "')"
				+ " WHERE doc_code='" + document.getCode() + "'");
	}
	
	public void insertDocument(OrderDocument document) throws SQLException, ParseException {
		String dateToInsert = new SimpleDateFormat("yyyy-MM-dd").format(document.getNotFormattedDate());
		statement = connection.createStatement();
		statement.execute("INSERT INTO order_document(name_order,date_order,id_doc_type,rubricationStatus,outStatus,id_type,id_heading,doc_code)" + 
						" VALUES ('" + document.getName() + "', '" + dateToInsert +"',0,0,0,0,0,'" + document.getCode() + "')");
		//System.out.println("inserted");
	}
	
	public ResultSet getDocuments() throws SQLException{
		statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT name_order FROM order_document");
		return result;
	}
	
	public ResultSet getDocumentsNameAndCode() throws SQLException{
		statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT doc_code,name_order FROM order_document");
		return result;
	}
	
	public void insertRubricationFact(OrderDocument doc) throws SQLException{
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		statement = connection.createStatement();
		statement.execute("INSERT INTO headings (heading_date,id_order) VALUES('" + currentDate + "', (SELECT id_order FROM order_document "
				+ "WHERE name_order = '" + doc.getName() + "' AND doc_code = '" + doc.getCode() + "'))");
	}
	
	public void insertRubric(Rubric rubric) throws SQLException{
		statement = connection.createStatement();
		statement.execute("INSERT INTO heading (name_heading, key_words, logicalSequence) VALUES ('" + rubric.getName() + "', '" + rubric.getKeyWords()
								+ "', " + rubric.getLogicalSequence() + ")");
		
	}
	
//	public ResultSet getDocumentsByPeriod(String startDate, String finishDate) throws SQLException{
//		statement = connection.createStatement();
//		ResultSet result = statement.executeQuery("SELECT document_code,name_order,heading_name FROM order_document,heading,headings "
//				+ "WHERE heading_date BETWEEN " + startDate + " AND " + finishDate +);
//		return result;
//	}
	
	public ResultSet getRurics() throws SQLException{
		statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM heading");
		return result;
	}
	
	private void shutdown() throws SQLException {
		try {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				DriverManager.getConnection(connectionString, username, password);
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Couldn't shutdown connection");
			throw e;
		}

	}

	public String getRubricById(int id) throws SQLException{
		
		String rubricName = null;
		
		statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT name_heading FROM heading WHERE id_heading=" +id);
		if(resultSet.next()){
			rubricName = resultSet.getString("name_heading");
		}
		
		resultSet.close();
		statement.close();
		
		return rubricName;
	}

}
