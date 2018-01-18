package logic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Converter {
	
	public static Rubric[] convertDBData(ResultSet data) throws SQLException{
		
		Rubric[] rubrics;
		
		int rowCount = 0;
		try {
		    data.last();
		    rowCount = data.getRow();
		    data.beforeFirst();
		} catch(SQLException e){
			System.out.println("No rows are selected");
		}
		
		rubrics = new Rubric[rowCount];
		
		int counter = 0;
		while(data.next()){
			rubrics[counter] = new Rubric(data.getInt(1), data.getString(2), data.getString(4), data.getInt(5));
			counter++;
		}
		
		return rubrics;
		
	}
	
public static OrderDocument[] convertDBDocuments(ResultSet data) throws SQLException{
		
		OrderDocument[] documents;
		
		int rowCount = 0;
		try {
		    data.last();
		    rowCount = data.getRow();
		    if(rowCount == 0){
		    	return null;
		    }
		    data.beforeFirst();
		} catch(SQLException e){
			System.out.println("No rows are selected");
		}
		
		documents = new OrderDocument[rowCount];
		
		int counter = 0;
		while(data.next()){
			documents[counter] = new OrderDocument(data.getString(1), data.getString(2));
			counter++;
		}
		
		return documents;
		
	}

}
