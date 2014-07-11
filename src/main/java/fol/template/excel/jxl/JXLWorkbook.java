package fol.template.excel.jxl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import fol.template.excel.GSheet;
import fol.template.excel.GWorkbook;
import fol.template.excel.NoneSheetFilter;
import fol.template.excel.SheetFilter;

public class JXLWorkbook implements GWorkbook {
	private Map<String, GSheet> nameSheetMap;
	
	
	public JXLWorkbook(InputStream inputStream, SheetFilter filter) throws BiffException, IOException {
		this(Workbook.getWorkbook(inputStream), filter);
		
	}
	
	public JXLWorkbook(InputStream inputStream) throws BiffException, IOException {
		this(Workbook.getWorkbook(inputStream));
	}
	
	public JXLWorkbook(Workbook workbook) {
		this(workbook, NoneSheetFilter.getInstance());
	}
	
	public JXLWorkbook(Workbook workbook, SheetFilter filter) {
		Map<String, GSheet> nameSheetMap = new HashMap<String, GSheet>();
		for(Sheet sheet : workbook.getSheets()) {
			if(filter.accept(sheet.getName())) {
				JXLSheet jxlSheet = new JXLSheet(sheet);
				nameSheetMap.put(jxlSheet.getName(), jxlSheet);
			}
		}
		this.nameSheetMap = nameSheetMap;
	}

	@Override
	public Iterator<GSheet> iterator() {
		return nameSheetMap.values().iterator();
	}

	@Override
	public Collection<String> getSheetNames() {
		return Collections.unmodifiableSet(nameSheetMap.keySet());
	}

	@Override
	public GSheet getSheet(String sheetName) {
		return nameSheetMap.get(sheetName);
	}

}
