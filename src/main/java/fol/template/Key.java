package fol.template;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Key {

	private static final Map<String, Key> keyMap = new ConcurrentHashMap<String, Key>();

	private static final char SEPARATOR = '.';

	private static final char PLURALITY_SEPARATOR = '$';

	private boolean plurality;

	private int index;// 基数为1

	private boolean composite;

	/**
	 * 主键
	 */
	private String mainKey;

	/**
	 * 副键
	 */
	private Key viceKey;
	
	public static final Key EMPTY_KEY = new Key("");

	private Key(String key) {
		int index = key.indexOf(SEPARATOR);
		if (index > 0) {
			this.composite = true;
			this.mainKey = key.substring(0, index);
			this.viceKey = getKey(key.substring(index + 1));
		} else {
			this.composite = false;
			this.mainKey = key;
		}
		index = this.mainKey.indexOf(PLURALITY_SEPARATOR);
		if(index > 0) {
			this.plurality = true;
			this.index = Integer.parseInt(mainKey.substring(index+1));
			this.mainKey = mainKey.substring(0, index);
		} else {
			this.plurality = false;
		}
	}
	
	/**
	 * 是否为复数
	 * @return
	 */
	public boolean isPlurality() {
		return plurality;
	}
	
	public int getIndex() {
		return this.index;
	}

	/**
	 * 是否是复合键
	 * 
	 * @return
	 */
	public boolean isComposite() {
		return composite;
	}

	public String getMainKey() {
		return this.mainKey;
	}

	public boolean hasViceKey() {
		return this.viceKey != null;
	}

	public Key getViceKey() {
		return this.viceKey;
	}

	public static Key getKey(String val) {
		Key key = keyMap.get(val);
		if (key == null) {
			key = new Key(val);
			keyMap.put(val, key);
		}
		return key;
	}
	
	public boolean isEmpty(){
		return this == EMPTY_KEY;
	}

	@Override
	public String toString() {
		return "Key [composite=" + composite + ", mainKey=" + mainKey
				+ ", viceKey=" + viceKey + "]";
	}

}
