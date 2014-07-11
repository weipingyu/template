package fol.template.excel;

import org.apache.commons.lang3.StringUtils;

public class StringElement implements Element {
	
	private String element;
	
	public StringElement(String element) {
		super();
		this.element = element;
	}

	@Override
	public boolean isEmptyElement() {
		return StringUtils.isBlank(element);
	}

	@Override
	public void clearEmptyElement() {
		this.element = StringUtils.trimToNull(element);
	}

	@Override
	public String toString() {
		return element;
	}
	
}
