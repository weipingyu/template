package fol.template;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fol.template.excel.ArrayElement;
import fol.template.excel.AttributeElement;
import fol.template.excel.GCell;
import fol.template.excel.GRow;
import fol.template.excel.GSheet;
import fol.template.excel.Parser;
import fol.template.excel.SimpleArrayElement;
import fol.template.excel.SimpleAttributeElement;

public class ConfigParser extends Parser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger("ConfigParser");
	
	private List<Key> keyList = new ArrayList<Key>();

	private SimpleArrayElement<AttributeElement> attributeList = new SimpleArrayElement<AttributeElement>();


	public ConfigParser() {
	}
	

	@Override
	public void parse(GRow row) {
		if (row.getRowIndex() == 1) {
			parseHead(row);
		} else if(row.getRowIndex() > 1){
			parseContent(row);
		}
	}
	
	public void finish(GSheet sheet) {
		attributeList.clearEmptyElement();
		LOGGER.debug("所有属性：{}", attributeList);
	}

	private void parseHead(GRow row) {
		for (GCell cell : row) {
			String head = cell.getContent();
			if(StringUtils.isBlank(head)) {
				keyList.add(Key.EMPTY_KEY);
			} else {
				keyList.add(Key.getKey(head));
			}
		}
	}
	
	
	private boolean validate(GRow row) {
		if(row == null || row.isEmpty()) {
			return false;
		}
		String cellValue = row.getCell(0).getContent();
		if(StringUtils.isBlank(cellValue)) {
			return false;
		}
		return true;
	}

	private void parseContent(GRow row) {
		LOGGER.debug("处理第{}行", row.getRowIndex());
		if(!validate(row)) {
			return;
		}
		AttributeElement attributes = new SimpleAttributeElement();
		int colNum = 0;
		for(Key key : keyList) {
			if(key.isEmpty()) {
				colNum++;
				continue;
			}
			String cellValue = null;
			GCell cell = row.getCell(colNum++);
			if(cell != null) {
				cellValue = StringUtils.trimToNull(cell.getContent());
			}
			dealKey(key, attributes, cellValue);
		}
		attributeList.add(attributes);
		LOGGER.debug("处理第{}行结束,属性为{}", row.getRowIndex(),attributes);
	}
	
	
	@SuppressWarnings("unchecked")
	private void dealKey(Key key, AttributeElement attributeElement, Object value) {
		if(key.isPlurality()) {
			ArrayElement<Object> arrayElement = (ArrayElement<Object>)attributeElement.get(key.getMainKey());
			if(arrayElement == null) {
				arrayElement = new SimpleArrayElement<Object>();
			}
			if((arrayElement.size() + 1) < key.getIndex()) {
				throw new RuntimeException(MessageFormat.format("复数键{0}的序号跳跃", key.getMainKey() + "$" + key.getIndex()));
			}
			
			if(key.hasViceKey()) {
				AttributeElement subElement = null;
				if(arrayElement.size() >= key.getIndex()) {//已经存在复合键的Map
					subElement = (AttributeElement)arrayElement.get(key.getIndex() - 1);
				} else {
					subElement = new SimpleAttributeElement();
					arrayElement.add(subElement);
				}
				dealKey(key.getViceKey(), subElement, value);
			} else {
				arrayElement.add(value);
			}
			value = arrayElement;
		}else if(key.hasViceKey()) {
			AttributeElement subElement = (AttributeElement)attributeElement.get(key.getMainKey());
			if(subElement == null) {
				subElement = new SimpleAttributeElement();
			}
			dealKey(key.getViceKey(), subElement, value);
			value = subElement;
		}
		
		attributeElement.put(key.getMainKey(), value);
	}
	
	public ArrayElement<AttributeElement> getAttributeList() {
		return attributeList;
	}
	
}
