package logic;

import java.util.Comparator;

import logic.Rubric;

public class RubricComporator implements Comparator <Rubric> {

	@Override
	public int compare(Rubric rubric1, Rubric rubric2) {
		// TODO Auto-generated method stub
		
		if(rubric1.getName().equals(rubric2.getName())) {return 0;}
		else {
			return 1;
		}
		
	}

	
	
}
