package org.group7.medical.config;

import javax.servlet.Filter;

import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMVCInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{SpringRootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{SpringWebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter charEncodingFilter = new CharacterEncodingFilter();
		charEncodingFilter.setEncoding("UTF-8");
		charEncodingFilter.setForceEncoding(true);
		
		AbstractRequestLoggingFilter logFilter = new CommonsRequestLoggingFilter();
		logFilter.setIncludePayload(true);
//		logFilter.setIncludeQueryString(true);
		
		return new Filter[] {charEncodingFilter, logFilter};
	}
}
