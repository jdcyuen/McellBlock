package com.example.demo.repository;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.repository.entity.Item;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTest {
	
	@Autowired
	ItemRepository itemRepo;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void saveTest() {
		Item item = new Item("Everlane", "Baccarate Cocktail Party in a Box", "Crystal", "1995.00");
		itemRepo.save(item);
		List<Item> items = itemRepo.findByCategory("Crystal");		
		Assert.assertTrue( items.size() > 0);
	}

}
