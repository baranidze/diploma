package logic;

import java.util.Comparator;

public class DocumentComporator implements Comparator <OrderDocument>{

	@Override
	public int compare(OrderDocument doc1, OrderDocument doc2) {
		
		
		if(doc1.getCode().equals(doc2.getCode()) && doc1.getName().equals(doc2.getName())) return 0;
		else
			return 1;
	}

}
