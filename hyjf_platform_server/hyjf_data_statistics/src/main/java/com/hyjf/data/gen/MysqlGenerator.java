package com.hyjf.data.gen;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 * @author jobob
 * @since 2018-09-12
 */
public class MysqlGenerator {

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        String packageName = "com.hyjf.data";

        //用户 market
		 String moduleName = "market";

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String curPath = MysqlGenerator.class.getClassLoader().getResource("").getFile();
        File curDir = new File(curPath);
        curPath = curDir.getParentFile().getParentFile().getParentFile().getParent();
     
        
        curPath = curPath+File.separator+"hyjf_platform_server"+File.separator+"hyjf_data_statistics";
        String projectPath = curPath;
        
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("auto");
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://47.104.244.26:33306/hyjf_market?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("admin_s");
        dsc.setPassword("uCLeilNVcMxM5GRR");
        mpg.setDataSource(dsc);
        
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(packageName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null).setController(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.ncbx.common.vo.BaseEntity");
        strategy.setEntityLombokModel(true);
//        strategy.setSuperControllerClass("com.baomidou.mybatisplus.samples.generator.common.BaseController");
        //TODO:
        //strategy.setInclude("sssys_.*");
        
        /*order*/
		/*
		 * strategy.setInclude("nt_product_category","nt_attribute",
		 * "nt_attribute_value",
		 * "nt_policy_config","nt_insurance_kind","nt_order_beneficiary",
		 * "nt_order_holder","nt_order_insured","nt_order_kind", "nt_bg_region_dic" ,
		 * "nt_bg_common_dic" , "nt_bg_condition_dic", "nt_bg_kind_dic", "nt_order");
		 */
        /*user*/
        /*user*/
		/* strategy.setInclude("nt_client_move"); */
        
        /*content*/
/*        strategy.setInclude("nt_user_business_card","nt_user_main_product","nt_poster","nt_moments_article",
        		"nt_news","nt_forward","nt_forward_read","nt_about","nt_workbench","nt_wechat_token","nt_app_version","nt_param_name");*/

		//strategy.setInclude("nt_attribute_value");

        //strategy.setSuperEntityColumns("id"); 注释掉可以在表结构生成主键
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
