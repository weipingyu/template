package fol.template.excel.jxl;

import jxl.Cell;
import fol.template.excel.GCell;

public class JXLCell implements GCell {
	private Cell cell;
	
	public JXLCell(Cell cell) {
		super();
		this.cell = cell;
	}

	@Override
	public String getContent() {
		return cell.getContents();
	}

	@Override
	public int getColumnIndex() {
		return cell.getColumn();
	}

	@Override
	public int getRowIndex() {
		return cell.getRow();
	}

}
