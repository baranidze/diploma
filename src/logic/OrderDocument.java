package logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderDocument {
		
		private String code;
		private Date date;
		private String name;
		private String rubricName;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat ("dd.MM.yyyy");
		
		public OrderDocument(String code, String name){
			this.code = code;
			this.name = name;
		}
				
		public OrderDocument(String information){
			splitString(information);
		}
			
		
		private static StringBuilder addToBuffer(StringBuilder sb, String what) {
		    return sb.append(what).append(' ');  // char is even faster here! ;)
		}
		
		public String getCode() {return code;}
		public Date getNotFormattedDate() {return date;}
		public String getDate() {return dateFormat.format(date);}
		public String getName() {return name;}
		public String getRubricName() {return rubricName;}
		public void setRubricName(String name) {this.rubricName = name;}
		
		private void splitString(String document){
			String fileName = document.substring(0, document.lastIndexOf('.'));
			String [] splittedString = fileName.split(" ");
			code = splittedString[0];
////			for( int i = 0; i < splittedString.length; i++){
////				System.out.print(splittedString[i] + ", ");
////			}
//			System.out.println();
//			//System.out.println(splittedString[2]);
			
			try {
				date = dateFormat.parse(splittedString[2]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			StringBuilder tempName = new StringBuilder();
			for(int i = 3; i < splittedString.length; i++)
				addToBuffer(tempName, splittedString[i]);
			name = tempName.toString();
			
//			System.out.println(code);
//			System.out.println(date);
//			System.out.println(name);
		}
		
		public String toString(){
			return code + ", " + name;
		}
}
