package logic;

public class Rubric {
	
	private int id;
	private String name;
	private String keyWords;
	private int logicalSequence;
	
	public Rubric (int id, String name, String keyWords, int logSeq){
		this.id = id;
		this.name = name;
		this.keyWords = keyWords;
		this.logicalSequence = logSeq;
	}
	
	public Rubric (String name, String keyWords, int logSeq){
		this.name = name;
		this.keyWords = keyWords;
		this.logicalSequence = logSeq;
	}
	
	public String getKeyWords() {return keyWords;}
	public int getLogicalSequence() {return logicalSequence;}
	public String getName() {return name;}
}
