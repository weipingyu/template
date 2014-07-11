package fol.template;

import java.io.File;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
	
	public static File getFile(String filePath) {
		File file = new File(filePath);
		if(!file.exists()) {
			URL url = FileUtils.class.getClassLoader().getResource(filePath);
			file = new File(url.getFile());
			if(!file.exists()) {
				LOGGER.error("找不到文件:{}",filePath);
			}
		}
		return file;
	}
}
