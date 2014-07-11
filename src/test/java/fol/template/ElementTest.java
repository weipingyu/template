package fol.template;

import org.apache.poi.poifs.storage.SmallBlockTableReader;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import fol.template.excel.ArrayElement;
import fol.template.excel.SimpleArrayElement;
import fol.template.excel.SimpleAttributeElement;

public class ElementTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SimpleAttributeElement attributeElement = new SimpleAttributeElement();
		
		
		ArrayElement arrayElement = new SimpleArrayElement();
		
		arrayElement.add(null);
		
		attributeElement.put("array", arrayElement);

		CompiledTemplate expression = TemplateCompiler.compileTemplate("array:\r\n@foreach{a:array}a:@{a} @end{}");
		
		System.out.println(TemplateRuntime.execute(expression,
				attributeElement).toString());

	}

}
