package logic;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import db.DBWorker;

public class Check {
	
	static DBWorker db;

	public static void checkAndInsert(ArrayList<OrderDocument> realDocs) throws SQLException, ParseException{
		db = DBWorker.getInstance();
		OrderDocument[] docs = Converter.convertDBDocuments(db.getDocumentsNameAndCode());
		if(docs == null){
			for(int i = 0; i < realDocs.size(); i++){
				db.insertDocument(realDocs.get(i));
			}
			return;
		}
		else{
			DocumentComporator cmp = new DocumentComporator();
			int needToInsert = 1;

			for(int i = 0; i < realDocs.size(); i++){
				for(int j = 0; j < docs.length; j++){
					needToInsert *= cmp.compare(realDocs.get(i), docs[j]);
				}
				if(needToInsert == 1){
					db.insertDocument(realDocs.get(i));
				}
				needToInsert = 1;
			}
		}
	}
	
}
