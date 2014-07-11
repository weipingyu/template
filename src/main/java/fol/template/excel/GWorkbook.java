package fol.template.excel;

import java.util.Collection;

public interface GWorkbook extends Iterable<GSheet>{

	Collection<String> getSheetNames();
	
	GSheet getSheet(String sheetName);
}
