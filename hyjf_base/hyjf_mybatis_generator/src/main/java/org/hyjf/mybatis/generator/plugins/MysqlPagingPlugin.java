package org.hyjf.mybatis.generator.plugins;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class MysqlPagingPlugin extends PluginAdapter {

	@Override	
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		addFeild(topLevelClass, introspectedTable, "limitStart");
		addFeild(topLevelClass, introspectedTable, "limitEnd");
		
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		XmlElement pagingElement = new XmlElement("if");
		pagingElement.addAttribute(new Attribute("test", "limitStart >= 0"));
		pagingElement.addElement(new TextElement("limit #{limitStart} , #{limitEnd}"));
		element.addElement(element.getElements().size(), pagingElement);

		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}
	
	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
	    XmlElement pagingElement = new XmlElement("if");
        pagingElement.addAttribute(new Attribute("test", "limitStart >= 0"));
        pagingElement.addElement(new TextElement("limit #{limitStart} , #{limitEnd}"));
        element.addElement(element.getElements().size(), pagingElement);

	    return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
	}

	private void addFeild(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
		CommentGenerator commentGenerator = context.getCommentGenerator();
		
		//generate filed
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(FullyQualifiedJavaType.getIntInstance());
		field.setName(name);
		field.setInitializationString("-1");
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		
		//generate setter
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		
		//generate getter
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

	public boolean validate(List<String> arg0) {
		return true;
	}

}
