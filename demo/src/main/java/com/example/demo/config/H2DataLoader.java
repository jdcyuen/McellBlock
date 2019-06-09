package com.example.demo.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.repository.ItemRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class H2DataLoader {
	
	@Autowired
	JdbcTemplate h2JdbcTemplate;
	
	@Autowired
	ItemRepository itemRepo;	
	
	@PostConstruct
	public void initDatabase() {
		
		h2JdbcTemplate.execute("DROP TABLE IF EXISTS Item");
		h2JdbcTemplate.execute("create table Item (id  long auto_increment, name varchar, description varchar, category varchar, price decimal)");				
						
	}

}
