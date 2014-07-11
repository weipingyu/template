package fol.template;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.SuffixFileFilter;

public class FileSytemConfigBuilder {
	
	private static final FileFilter excelFileFilter = new SuffixFileFilter(
			new String[] {"xls","xlsx"});

	public static void main(String[] args) {
		if(args == null || args.length == 0) {
			return;
		}
		String path = args[0];
		File file = FileUtils.getFile(path);
		produceConfigFile(file);
	}
	
	/**
	 * 批量生成配置文件
	 * 
	 * @param dir
	 */
	private static void produceConfigFile(File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(File tempFile : files) {
				produceConfigFile(tempFile);
			}
		} else if(excelFileFilter.accept(file)) {
			try {
				ConfigFileUtil.dealConfigFile(file);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
		}
		
	}

}
