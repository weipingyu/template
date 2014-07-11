package fol.template;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.mvel2.ParserContext;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import fol.template.util.MathEx;

public class Template {

	private CompiledTemplate expression;
	
	private final static ThreadLocal<ParserContext> PARSER_CONTEXT_LOCAL = new ThreadLocal<ParserContext>(){

		@Override
		protected ParserContext initialValue() {
			ParserContext context = new ParserContext();

			for (final Method method : Math.class.getMethods())
				context.addImport(method.getName(), method);
			for (final Method method : MathEx.class.getMethods())
				context.addImport(method.getName(), method);
			context.addImport(ArrayList.class);
			context.addImport(HashSet.class);
			context.addImport(HashMap.class);
			return context;
		}
		
	};
	
	public Template(String templatePath) throws Exception {
		InputStream in = null;
		String template = null;
		try {
			in = new FileInputStream(templatePath);
			template = IOUtils.toString(in, GameConstant.ENCODING);
		}catch(FileNotFoundException e){
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(templatePath);
			if(in == null) {
				throw e;
			}
			template = IOUtils.toString(in, GameConstant.ENCODING);
		} finally {
			IOUtils.closeQuietly(in);
		}
		expression = TemplateCompiler
				.compileTemplate(template, PARSER_CONTEXT_LOCAL.get());
	}
	

	public Template(InputStream in) throws Exception {
		String template = IOUtils.toString(in, GameConstant.ENCODING);
		expression = TemplateCompiler
				.compileTemplate(template, PARSER_CONTEXT_LOCAL.get());
	}

	public String getContent(Map<String, Object> attributes) {
		return TemplateRuntime.execute(expression,
				attributes).toString();
	}
	
	public InputStream getContentStream(Map<String, Object> attributes) throws Exception {
		String template = getContent(attributes);
		return new ByteArrayInputStream(template.getBytes(GameConstant.ENCODING));
	}

}
