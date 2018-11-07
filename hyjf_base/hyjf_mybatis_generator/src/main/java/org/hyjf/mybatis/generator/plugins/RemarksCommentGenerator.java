package org.hyjf.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * @author xiasq
 * @version RemarksCommentGenerator, v0.1 2018/11/7 9:20
 * 自动生成的Model增加注释
 */
public class RemarksCommentGenerator extends DefaultCommentGenerator {

    /**
     * 定义一个是否使用修改后的模式的标识
     */
    private boolean suppressAllComments = true;

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        if (suppressAllComments) {
            if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
                field.addJavaDocLine("/**");
                field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
                addJavadocTag(field, false);
                field.addJavaDocLine(" */");
            }
        } else {
            super.addFieldComment(field, introspectedTable, introspectedColumn);
        }
    }
}
