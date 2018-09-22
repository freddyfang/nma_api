package org.group7.medical.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "org.freddyfang.template.repositories")
@PropertySources({
	@PropertySource("classpath:db.properties"), 
	@PropertySource("classpath:application.properties"), 
})
public class PersistenceConfig {
	@Autowired
	private Environment env;
	
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		dataSource.setInitialSize(env.getProperty("db.initialSize", Integer.class));
		dataSource.setMaxTotal(env.getProperty("db.maxTotal", Integer.class));
		dataSource.setMaxIdle(env.getProperty("db.maxIdle", Integer.class));
		dataSource.setTestWhileIdle(env.getProperty("db.testWhileIdle", Boolean.class));
		dataSource.setTestOnBorrow(env.getProperty("db.testOnBorrow", Boolean.class));
		dataSource.setTestOnReturn(env.getProperty("db.testOnReturn", Boolean.class));
		dataSource.setMinEvictableIdleTimeMillis(env.getProperty("db.minEvictableIdleTimeMillis", Long.class));
		dataSource.setTimeBetweenEvictionRunsMillis(env.getProperty("db.timeBetweenEvictionRunsMillis", Long.class));
		dataSource.setNumTestsPerEvictionRun(env.getProperty("db.numTestsPerEvictionRun", Integer.class));
		dataSource.setValidationQuery(env.getProperty("db.validationQuery"));
		
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean emf(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setPackagesToScan("org.group7.medical");
		emf.setJpaProperties(this.getJPAProperties());
		
		return emf;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(emf);

		return tm;
	}
	
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator(); // Note that this is the hibernate4 translator
	}
	
	/*---------------------
	 * Private Methods
	 ---------------------*/
	private Properties getJPAProperties() {
		Properties jpaProp = new Properties();
		
		jpaProp.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		jpaProp.setProperty("hibernate.max_fetch_depth", env.getProperty("hibernate.max_fetch_depth"));
		jpaProp.setProperty("hibernate.jdbc.fetch_size", env.getProperty("hibernate.jdbc.fetch_size"));
		jpaProp.setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
		jpaProp.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		jpaProp.setProperty("javax.persistence.validation.mode", env.getProperty("javax.persistence.validation.mode"));
		
		return jpaProp;
	}
}
