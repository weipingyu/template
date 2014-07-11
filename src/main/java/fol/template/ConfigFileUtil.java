package fol.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class ConfigFileUtil {
	
	/**
	 * 模板后缀
	 */
	private static final String TMP_SUFFIX = ".mvl";
	
	private static final String logFileName = "log.txt";

	public static void dealConfigFile(File config) throws TemplateException {
		InputStream in = null;
		File dir = config.getParentFile();
		List<String> errorList = new ArrayList<String>();
		try {
			in = new FileInputStream(config);
			ConfigDeal deal = new ConfigDeal(in);
			InputStream contentStream = null;
			for(String sheetName : deal.getTplSheetNameList()) {
				try {
					String filePath = convertPath(sheetName);
					String path = FilenameUtils.getPath(filePath);
					String baseName = FilenameUtils.getBaseName(filePath);
					String mvlName = baseName + TMP_SUFFIX;//模板文件名
					Template template = new Template(new File(dir, mvlName).getAbsolutePath());
					contentStream = template.getContentStream(deal.parseAttributes(sheetName));
					File xmlFile = new File(dir, path + baseName + "." + GameConstant.SUFFIX_MAP.get(FilenameUtils.getExtension(filePath)));
					OutputStream xmlStream = null;
					try {
						xmlStream = FileUtils.openOutputStream(xmlFile);
						IOUtils.copy(contentStream, xmlStream);
					} finally {
						IOUtils.closeQuietly(xmlStream);
					}
				} catch (Exception e) {
					e.printStackTrace();
					errorList.add(convertExceptionMsg("处理Excel文件[" + config.getName() + "]的子表[" + sheetName + "]出错", e));
					log(e);
				} finally {
					IOUtils.closeQuietly(contentStream);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			errorList.add(convertExceptionMsg("处理Excel文件[" + config.getName() + "]出错，错误：\n" + e1.getMessage(), e1));
			log(e1);
		} finally {
			IOUtils.closeQuietly(in);
			if(!errorList.isEmpty()) {
				throw new TemplateException(errorList.toString());
			}
		}
	}
	
	private static void log(Exception e) {
		PrintStream print = null;
		try {
			print = new PrintStream(new FileOutputStream(logFileName, true), false, GameConstant.ENCODING);
			e.printStackTrace(print);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			IOUtils.closeQuietly(print);
		}
	}
	
	private static String convertPath(String sheetName) {
		String[] parts = StringUtils.split(sheetName, '.');
		if(parts.length == 2) {
			return sheetName;
		}
		String temp = StringUtils.join(Arrays.copyOf(parts, parts.length - 1), '/');
		return temp + "." + parts[parts.length -1];
		
		
	}
	
	private static String convertExceptionMsg(String msg, Exception e) {
		StringWriter stringWriter = new StringWriter();
		stringWriter.append(msg);
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		IOUtils.closeQuietly(printWriter);
		return stringWriter.toString();
	}
}
