package kr.tracom.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import kr.tracom.cm.interceptor.SessionCheckInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
	@Value("${spring.mvc.view.prefix}")
	private String viewPrefix;
	
	@Value("${spring.mvc.view.suffix}")
	private String viewSuffix;

	@Value("${static.resource.location}")
	private String staticResouceLocation;

	@Value("${fileupload.location}")
	protected String fileuploadLocation;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/websquare/**").addResourceLocations("/websquare/");
		registry.addResourceHandler("/fileUpload/**").addResourceLocations(fileuploadLocation);
		registry.addResourceHandler("/cm/**").addResourceLocations(staticResouceLocation+"/static/cm/");
		registry.addResourceHandler("/ui/**").addResourceLocations(staticResouceLocation+"/static/ui/");
		registry.addResourceHandler("/ClipReport/**").addResourceLocations(staticResouceLocation+"/static/ClipReport/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/").resourceChain(false);        
		super.addResourceHandlers(registry);
	}

	@Bean
	public BeanNameViewResolver beanNameViewResolver() {
		BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		beanNameViewResolver.setOrder(0);
		return beanNameViewResolver;
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setViewClass(InternalResourceView.class);
		internalResourceViewResolver.setPrefix(viewPrefix);
		internalResourceViewResolver.setSuffix(viewSuffix);
		internalResourceViewResolver.setOrder(2);
		return internalResourceViewResolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionCheckInterceptor())
		.excludePathPatterns("/ClipReport/**")
		.excludePathPatterns("/cm/**")
		.excludePathPatterns("/fileupload/**")
		.excludePathPatterns("/lang/**")
		.excludePathPatterns("/ui/**")
		.excludePathPatterns("/I18N")
		.excludePathPatterns("/main/login")
		.excludePathPatterns("/websquare/**");
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*");
	}
}