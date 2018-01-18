package logic;

import java.sql.SQLException;

import db.DBWorker;

public class Rubrication {
	
	Rubric[] rubrics;
	DBWorker dbWorker;
	
	public String headingCode(String code) {
		
		String result;
				
		if(code.contains("Р"))
			result = "Розпорядження";
		else if(code.contains("КН"))
			result = "По факультету";
		else if(code.contains("Ка"))
			result = "Особовий склад";
		else if(code.contains("СтЗ"))
			result = "Заочники";
		else if(code.contains("А"))
			result = "Аспірантура";
		else if(code.contains("К"))
			result = "Кваліфікація";
		else
			result = "Нема рубрики";
		return result;
	}
	
	public String headingName(String name) throws SQLException {
		
		dbWorker = DBWorker.getInstance();
		
		rubrics = Converter.convertDBData(dbWorker.getRurics());
		
		name = name.toLowerCase();
		
		String result = "Нема рубрики";
		
		boolean rubricationIsFinished = false;
		
		String[] keyWords;
		
		for(int i = 0; i < rubrics.length; i++){
			if(rubrics[i].getKeyWords().equals("")) {continue;}
			keyWords = rubrics[i].getKeyWords().split(",");
			rubricationIsFinished = name.contains(keyWords[0].toLowerCase());
			for(int j = 0; j < keyWords.length; j++){
				if(rubrics[i].getLogicalSequence() == 1){
					rubricationIsFinished &= name.contains(keyWords[j].toLowerCase());
				}
				else {
					rubricationIsFinished |= name.contains(keyWords[j].toLowerCase());
				}
			}
			if(rubricationIsFinished){
				result = dbWorker.getRubricById(i+1);
				return result;
			}
		}
		return result;
	}
	
	public String folderName(String rubric){
		
		return rubric.toUpperCase();
		
		/*String result;
		
		switch(rubric){
			case "Розпорядження":
				result = "розпорядження";
				break;
			case "По факультету":
				result = "факультет";
				break;
			case "Особовий склад":
				result = "собовий склад";
				break;
			case "Заочники":
				result = "заочники";
				break;
			case "Аспірантура":
				result = "аспіратнура";
				break;
			case "Квалификация":
				result = "квалификация";
				break;
			case "�?снструктаж":
				result = "инструктажи";
				break;
			case "Научная деятельность":
				result = "научная деятельность";
				break;
			case "Ученый Совет":
				result = "ученый совет";
				break;
			case "�?зменение в приказе":
				result = "изменения в приказах";
				break;
			default:
				result = null;*/
					
	}
		
}

