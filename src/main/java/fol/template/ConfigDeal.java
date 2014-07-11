package fol.template;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fol.template.excel.ExcelManager;
import fol.template.excel.SheetFilter;

public class ConfigDeal {


	private static final String ROOT_SHEET_NAME = "root";

	private static final String ROOT_ITEM_LIST_KEY = "itemList";
	
	private ExcelManager excelManager;
	
	private RootParser rootParser;
	
	private volatile List<String> tplSheetNameList;
	
//	private static final int SPLIT_NUM = Integer.parseInt(System.getProperty("hash.split.num","30"));
	
	private SheetFilter filter = new SheetFilter() {
		
		@Override
		public boolean accept(String sheetName) {
			for(String suffixName : GameConstant.SUFFIX_MAP.keySet()) {
				if(sheetName.endsWith(suffixName)) {
					return true;
				}
			}
			if(sheetName.equals(ROOT_SHEET_NAME)) {
				return true;
			}
			return false;
		}
	};
	
	public ConfigDeal(String path) throws Exception {
		excelManager = new ExcelManager(path, filter);
		// 添加其它根属性
		rootParser = new RootParser();
		excelManager.parse(ROOT_SHEET_NAME, rootParser);
	}
	
	public ConfigDeal(InputStream in) throws Exception {
		excelManager = new ExcelManager(in, filter);
		// 添加其它根属性
		rootParser = new RootParser();
		excelManager.parse(ROOT_SHEET_NAME, rootParser);
	}
	
	public List<String> getTplSheetNameList() {
		if(tplSheetNameList != null) {
			return tplSheetNameList;
		}
		List<String> tplList = new ArrayList<String>();
		String[] sheetNames = excelManager.getSheetNames();
		for (String sheetName : sheetNames) {
			for(String suffixName : GameConstant.SUFFIX_MAP.keySet()) {
				if(sheetName.endsWith(suffixName)) {
					tplList.add(sheetName);
				}
			}
		}
		tplSheetNameList = tplList;
		return tplSheetNameList;
	}

	public Map<String, Object> parseAttributes(String sheetName) {

		ConfigParser configParser = new ConfigParser();
		excelManager.parse(sheetName, configParser);
		Map<String, Object> rootAttribute = new HashMap<String, Object>();
		rootAttribute.put(ROOT_ITEM_LIST_KEY, configParser.getAttributeList());

		// 添加其它根属性
		rootAttribute.putAll(rootParser.getRootAttributes());
		return rootAttribute;
	}
	
	/*public List<Map<String, Object>> parseAttributesList(String sheetName) {
		return parseAttributesList(sheetName, SPLIT_NUM);
	}*/
	
	/*public List<Map<String, Object>> parseAttributesList(String sheetName, int num) {
		ConfigParser configParser = new ConfigParser();
		excelManager.parse(sheetName, configParser);
		List<Map<String, Object>> attrList = new ArrayList<Map<String, Object>>();
		for(List<Map<String, Object>> attributes : CollectionUtils.split(configParser.getAttributeList(), num)) {
			Map<String, Object> rootAttribute = new HashMap<String, Object>();
			rootAttribute.put(ROOT_ITEM_LIST_KEY, attributes);
			rootAttribute.putAll(rootParser.getRootAttributes());
			attrList.add(rootAttribute);
		}
		
		return attrList;
	}*/

}
