package com.example.demo.config;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.entity.Item;

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
		
		ArrayList<Item> items = new ArrayList<Item>();
		Item item = new Item("Everlane", "Baccarate Cocktail Party in a Box", "Crystal", "1995.00");
		items.add(item);
		
		item = new Item("KRIS BEAR", "KRIS BEAR - A LOVELY SURPRISE", "Crystal", "195.00");
		items.add(item);
		
		item = new Item("NECKLACE", "MIX NECKLACE, WHITE, RHODIUM PLATING", "Jewelry", "399.00");
		items.add(item);
		

		item = new Item("EMERALD NECKLACE", "Pear Shaped Emerald Teardrop Necklace in Yellow Gold from Angara.com", "Jewelry", "798.00");
		items.add(item);
		
		item = new Item("Amethyst Cocktail Ring", "Vintage Inspired Solitaire Cushion Amethyst Cocktail Ring", "Jewelry", "509.00");
		items.add(item);
		
		
		
		itemRepo.save(items);
		
						
	}

}
