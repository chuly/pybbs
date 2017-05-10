package cn.tomoya.task;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.util.StringUtils;

import cn.tomoya.common.config.SiteConfig;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class StaticHtmlTask {
	private static final Logger log = Logger.getLogger(StaticHtmlTask.class);
	private static Configuration config = new Configuration();
	
	@Autowired
    private TopicService topicService;
	@Autowired
    private SiteConfig siteConfig;

	// @Scheduled(cron = "0 0/5 * * * ?") // 每5分钟执行一次，正式环境用
//	@Scheduled(cron = "0/5 * * * * ?")
	// 每1分钟执行一次，测试环境用
	public void ftl2Html() {
		System.out.println("ffff=============ftl2Html");
		log.info("==========================321");
		
		String tab=null;
		Integer p= null;
		String sectionName = tab;
        if (StringUtils.isEmpty(tab)) tab = "全部";
        if (tab.equals("全部") || tab.equals("精华") || tab.equals("等待回复")) {
            sectionName = "版块";
        }
        Map root = new HashMap();
        Page<Topic> page = topicService.page(p == null ? 1 : p, siteConfig.getPageSize(), tab);
        root.put("page", page);
        root.put("tab", tab);
        root.put("sectionName", sectionName);
        root.put("user", null);
//        String templateName = "/templates"+siteConfig.getTheme() + "/index.ftl";
//        String templateName =  "C:/Users/chuly/git/pybbs/src/main/resources/templates/bootstrap/index.ftl";
        String templateName = siteConfig.getTheme() + "/index.ftl";
        Writer out = null;
		try {
			out = new FileWriter("d:/tmp/index.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
        processTemplate(templateName, root, out);
	}

	/**
	 * @param templateName
	 *            模板名字
	 * @param root
	 *            模板根 用于在模板内输出结果集
	 * @param out
	 *            输出对象 具体输出到哪里
	 */
	public static void processTemplate(String templateName, Map<?, ?> root, Writer out) {
		log.info("templateName="+templateName+",root="+root+",out="+out);
		try {
//			config.setTemplateLoader(templateLoader);
//			config.setTemplateConfigurations(templateConfigurations);
//			TemplateLoader templateLoader = new SpringTemplateLoader(resourceLoader, templateLoaderPath);
			FreeMarkerConfigurationFactoryBean ff =  new FreeMarkerConfigurationFactoryBean();
			ff.setTemplateLoaderPath("file:C:/Users/chuly/git/pybbs/src/main/resources/templates/");
			
			//WEB
//			Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
//			return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			//NONE WEB
			Template t2 = ff.createConfiguration().getTemplate(templateName);
//			return FreeMarkerTemplateUtils.processTemplateIntoString(t2, model);
			
			// 获得模板
			Template template = t2;//config.getTemplate(templateName, "utf-8");
			// 生成文件（这里是我们是生成html）
			template.process(root, out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
