package com.gil.whatsnew.config;

import java.util.Properties;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@EnableTransactionManagement
public class UtilsConfiguration {

	@Bean
	public DispatcherServlet dispatcherServlet() {
	    DispatcherServlet dispatcherServlet = new DispatcherServlet();
	    dispatcherServlet.setThreadContextInheritable(true);
	    return dispatcherServlet;
	}
	
    
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
