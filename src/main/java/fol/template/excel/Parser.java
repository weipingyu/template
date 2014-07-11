package fol.template.excel;


public abstract class Parser {

	public void parse(GCell cell){}

	public void parse(GRow row) {
		for(GCell cell : row) {
			parse(cell);
		}
	}
	
	public void parse(GSheet sheet) {
		for(GRow row : sheet) {
			parse(row);
		}
		finish(sheet);
	}
	
	public void finish(GSheet sheet){};
}
