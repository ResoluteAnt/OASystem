package com.gec.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * @Author : JingJie
 * @Date : 2021/12/15 13:10
 * @Description : 使用反向工程创建文档中心类
 * @Version : 1.0
 */
public class GeneratorDocument {
	public void generateMappers() throws Exception {
		List<String> warning = new ArrayList();
		//{1} 获取类加截器
		ClassLoader loader = GeneratorDocument.class.getClassLoader();
		//创建读取: generatorConfig.xml 的输入流对象
		//class/generatorConfig.xml
		InputStream is = loader.getResourceAsStream("generatorConfig.xml");
		//{1} 创建配置文件解析器
		ConfigurationParser cp = new ConfigurationParser( warning );
		//{2} 解析配置文件
		Configuration config = cp.parseConfiguration( is );
		//{3} 创建默认回调对象
		DefaultShellCallback callback = new DefaultShellCallback( true );
		//{4} 创建创建器
		MyBatisGenerator myGenerator = new MyBatisGenerator( config,
				callback, warning );
		//{5} 执行创建
		myGenerator.generate( null );
		System.out.println("{ps}Mapper文件生成成功!");
	}

//	public static void main(String[] args) throws Exception {
//		GeneratorDocument gen = new GeneratorDocument();
//		gen.generateMappers();
//	}

}
