package br.com.noemi.blog.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringWebXmlConfig implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		
		AnnotationConfigWebApplicationContext webContext = 
											new AnnotationConfigWebApplicationContext();
		
		servletContext.addListener(new ContextLoaderListener(webContext));
		
		webContext.register(SpringMvcConfig.class);
		webContext.setServletContext(servletContext);
		
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
		
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		
		ServletRegistration.Dynamic  reDynamic = 
						servletContext.addServlet("dispacher", dispatcherServlet);
		
		reDynamic.setLoadOnStartup(1);
		reDynamic.addMapping("/");
		
		FilterRegistration.Dynamic encodingFilter = 
				servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		encodingFilter.setInitParameter("encoding", "UTF-8");
		encodingFilter.setInitParameter("forceEncoding", "true");
		encodingFilter.addMappingForUrlPatterns(null, true, "/*");
		
		FilterRegistration.Dynamic inViewSession = 
				servletContext.addFilter(
						"Spring OpenEntityManagerInViewFilter", 
						new OpenEntityManagerInViewFilter());
		inViewSession.setAsyncSupported(Boolean.TRUE);
		inViewSession.addMappingForUrlPatterns(null, true, "/*");
		
		FilterRegistration.Dynamic securityFilter = 
				servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
		securityFilter.setAsyncSupported(Boolean.TRUE);
		securityFilter.addMappingForUrlPatterns(null, true, "/*");
	}

	
}
