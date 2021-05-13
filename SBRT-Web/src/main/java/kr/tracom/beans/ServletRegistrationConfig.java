package kr.tracom.beans;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class ServletRegistrationConfig
{
	@Bean
	public ServletRegistrationBean getServletRegistrationBean()
	{
		ServletRegistrationBean websquareDispatcher = new ServletRegistrationBean(new websquare.http.DefaultRequestDispatcher());
		websquareDispatcher.addUrlMappings("*.wq");
		return websquareDispatcher;
	}
}