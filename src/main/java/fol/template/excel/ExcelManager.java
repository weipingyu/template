package fol.template.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;


public class ExcelManager {
	
	private GWorkbook wb;

	public ExcelManager(String path, SheetFilter filter) throws Exception  {
		InputStream in = null;
		try {
			in = new FileInputStream(path);
			wb = GWorkbookFactory.create(in, filter);
		} catch(FileNotFoundException e){
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			if(in == null) {
				throw e;
			}
			wb = GWorkbookFactory.create(in, filter);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
	
	public ExcelManager(InputStream in, SheetFilter filter) throws Exception {
		wb = GWorkbookFactory.create(in, filter);
	} 

	
	/**
	 * 处理每行
	 * @param sheetName
	 * @param parser
	 */
	public void parse(String sheetName, Parser parser) {
		GSheet sheet = wb.getSheet(sheetName);
		if(sheet != null) {
			parser.parse(sheet);
		}
	}
	
	
	public String[] getSheetNames() {
		return wb.getSheetNames().toArray(new String[0]);
	}
	
}
