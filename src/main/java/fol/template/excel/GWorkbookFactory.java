package fol.template.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import jxl.read.biff.BiffException;

import fol.template.excel.jxl.JXLWorkbook;
import fol.template.excel.poi.POIWorkbook;

public class GWorkbookFactory {
	
	private static final WorkbookType TEMP_WORKBOOK_TYPE = WorkbookType.JXL;

	public static GWorkbook create(InputStream inputStream) throws BiffException, IOException, InvalidFormatException {
		return create(inputStream, NoneSheetFilter.getInstance(), TEMP_WORKBOOK_TYPE);
	}
	
	public static GWorkbook create(InputStream inputStream, SheetFilter filter) throws BiffException, IOException, InvalidFormatException {
		return create(inputStream, filter, TEMP_WORKBOOK_TYPE);
	}
	
	public static GWorkbook create(InputStream inputStream, SheetFilter filter, WorkbookType type) throws BiffException, IOException, InvalidFormatException {
		GWorkbook workbook = null;
		switch (type) {
		case POI:
			workbook = new POIWorkbook(inputStream, filter);
			break;
		case JXL:
			workbook = new JXLWorkbook(inputStream, filter);
			break;
		default:
			workbook = new JXLWorkbook(inputStream, filter);
			break;
		}
		return workbook;
	}

}
