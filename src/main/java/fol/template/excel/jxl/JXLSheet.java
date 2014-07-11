package fol.template.excel.jxl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import jxl.Sheet;
import fol.template.excel.GRow;
import fol.template.excel.GSheet;

public class JXLSheet implements GSheet {
	
	private Sheet sheet;
	
	private List<GRow> rowList;
	
	public JXLSheet(Sheet sheet) {
		this.sheet = sheet;
		List<GRow> rowList = new ArrayList<GRow>(sheet.getRows());
		for(int rowNum=0; rowNum < sheet.getRows(); rowNum++) {
			rowList.add(new JXLRow(rowNum, Arrays.asList(sheet.getRow(rowNum))));
		}
		this.rowList = rowList;
	}

	@Override
	public Iterator<GRow> iterator() {
		return rowList.iterator();
	}

	@Override
	public String getName() {
		return sheet.getName();
	}

	@Override
	public GRow getRow(int rowIndex) {
		return rowIndex >= getRowNum() ? null : rowList.get(rowIndex);
	}

	@Override
	public int getRowNum() {
		return rowList.size();
	}

}
