package fol.template;

import fol.template.excel.ArrayElement;
import fol.template.excel.AttributeElement;
import fol.template.excel.SimpleAttributeElement;

public class RootParser extends ConfigParser {

	public AttributeElement getRootAttributes() {
		ArrayElement<AttributeElement> attributeList = getAttributeList();
		if(attributeList.isEmpty()) {
			return new SimpleAttributeElement();
		}
		return attributeList.get(0);
	}

	
}
