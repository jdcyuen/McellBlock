package com.example.demo.config;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@EnableJdbcRepositories
public class H2DataSourceConfig {
	
	@Bean(name = "h2DataSource")
	@ConfigurationProperties(prefix="spring.datasource")
	@Primary
	public DataSource h2DataSource() {	
		return DataSourceBuilder.create().build();
	 }		
	
	@Bean(name="h2JdbcTemplate")
	public JdbcTemplate h2JdbcTemplate(@Qualifier("h2DataSource") DataSource h2DS){
		return new JdbcTemplate(h2DS);
	}
	
	@Bean(name = "h2NamedParameterJdbcTemplate")	
	public NamedParameterJdbcTemplate h2NamedParameterJdbcTemplate( @Qualifier("h2DataSource") DataSource h2DS){
		return new NamedParameterJdbcTemplate(h2DS);
	}	
}
