package fol.template.excel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleAttributeElement implements AttributeElement {
	
	private Map<String, Object> elementMap = new HashMap<String, Object>();

	@Override
	public int size() {
		return elementMap.size();
	}

	@Override
	public boolean isEmpty() {
		return elementMap.isEmpty();
	}
	
	

	@Override
	public boolean isEmptyElement() {
		for(Object value : elementMap.values()) {
			if(value != null && (!(value instanceof Element) || !((Element)value).isEmptyElement())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean containsKey(Object key) {
		return elementMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return elementMap.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return elementMap.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return elementMap.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return elementMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		elementMap.putAll(m);
	}

	@Override
	public void clear() {
		elementMap.clear();
	}

	@Override
	public Set<String> keySet() {
		return elementMap.keySet();
	}

	@Override
	public Collection<Object> values() {
		return elementMap.values();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return elementMap.entrySet();
	}

	@Override
	public void clearEmptyElement() {
		for(Entry<String, Object> entry : elementMap.entrySet()) {
			Object value = entry.getValue();
			if(value != null && value instanceof Element) {
				Element element = (Element)value;
				element.clearEmptyElement();
				if(!(element instanceof ArrayElement) && element.isEmptyElement()) {
					elementMap.put(entry.getKey(), null);
				}
			}
		}
		
	}
	
	@Override
	public String toString() {
		return "SimpleAttributeElement [elementMap=" + elementMap + "]";
	}

}
