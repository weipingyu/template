package fol.template.excel.jxl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import fol.template.excel.GCell;
import fol.template.excel.GRow;

public class JXLRow implements GRow {
	
	private int rowNum;
	
	private List<GCell> cellList;
	

	public JXLRow(int rowNum, List<Cell> cellList) {
		super();
		this.rowNum = rowNum;
		List<GCell> gcellList = new ArrayList<GCell>(cellList.size());
		for(Cell cell : cellList) {
			gcellList.add(new JXLCell(cell));
		}
		this.cellList = gcellList;
	}

	@Override
	public Iterator<GCell> iterator() {
		return cellList.iterator();
	}

	@Override
	public int getRowIndex() {
		return rowNum;
	}

	@Override
	public GCell getCell(int cellIndex) {
		return cellIndex >= getCelNum() ? null : cellList.get(cellIndex);
	}

	@Override
	public int getCelNum() {
		return cellList.size();
	}

	@Override
	public boolean isEmpty() {
		return cellList.size() == 0;
	}
	
}
