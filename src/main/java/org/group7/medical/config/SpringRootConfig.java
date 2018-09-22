package org.group7.medical.config;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@EnableCaching
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = { "org.group7.medical" }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
public class SpringRootConfig {
	@Autowired
	private Environment env;
	
//	@Bean
//	public ReloadableResourceBundleMessageSource messageSource() {
//		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
//		
//		ms.setBasename("classpath:lang/messages");
//		ms.setCacheSeconds(5);
//		ms.setDefaultEncoding("UTF-8");
//		
//		return ms;
//	}
	
//	@Bean
//	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
//		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
//		validator.setValidationMessageSource(messageSource);
//		
//		return validator;
//	}
	
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
	
	@Bean
	public DateTimeFormatter standardFormatter() {
		return DateTimeFormatter.ofPattern(env.getProperty("format.datetime"));
	}
	
	@Bean
	public AntPathMatcher pathMatcher() {
		return new AntPathMatcher();
	}
	
	@Bean
	public ObjectMapper jsonMapper() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.indentOutput(true)
			.serializationInclusion(JsonInclude.Include.NON_NULL);
		
		return builder.build();
	}

}