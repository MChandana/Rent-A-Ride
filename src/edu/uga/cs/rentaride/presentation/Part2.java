package edu.uga.cs.rentaride.presentation;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

	public class Part2{
		
		private String ServletADir;
		private String ServletAName;
		private Configuration configuration;
		
		public Part2(String templateDir, ServletContext servletContext) {
			this.ServletADir = templateDir;
			configuration = new Configuration(Configuration.VERSION_2_3_25);
			configuration.setServletContextForTemplateLoading(servletContext, templateDir);
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		}
		
		public void processTemplate(String templateName, SimpleHash root, HttpServletRequest request, HttpServletResponse response) {
			this.ServletADir  = templateName;
			Template template = null;
			try {
				template = configuration.getTemplate(templateName);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				Writer out = response.getWriter();
				response.setContentType("text/html");
				template.process(root, out);
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} // end of processTemplate	
		
		

		/**
		 * @return the templateDir
		 */
		public String getAgDir() {
			return ServletADir;
		}

		/**
		 * @param templateDir the templateDir to set
		 */
		public void setAgDir(String templateDir) {
			this.ServletADir = templateDir;
		}

		/**
		 * @return the templateName
		 */
		public String getTemplateName() {
			return ServletADir;
		}

		/**
		// * @param templateName the templateName to set
		 */
		public void setTemplateName(String ServtemplateName) {
			this.ServletADir = ServtemplateName;
		}

		/**
		 * @return the configuration
		 */
		public Configuration getConfiguration() {
			return configuration;
		}

		/**
		 * @param configuration the configuration to set
		 */
		public void setConfiguration(Configuration configuration) {
			this.configuration = configuration;
		}
		
		

	}


