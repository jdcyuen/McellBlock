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
public class ItemRepositoryFT {
	
	@Autowired
	ItemRepository itemRepo;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void findByCategoryTest() {		
		
		List<Item> items = itemRepo.findByCategory("Crystal");		
		Assert.assertTrue( items.size() > 0);
		
		for(Item i : items) {
			System.out.println(i.toString());
		
		}
		System.out.println("===============================");
	}
	
	/*@Test
	public void findAll() {
				
		List<Item> items = itemRepo.findAll();		
		Assert.assertTrue( items.size() > 0);
		
		for(Item i : items) {
			System.out.println(i.toString());
		
		}
		System.out.println("===============================");
	}*/
	
	/*@Test
	public void findAllCategories() {
		
		List<String> categories = itemRepo.findAllCategories();		
		Assert.assertTrue( categories.size() > 0);
		
		for(String category : categories) {
			System.out.println(category);
		
		}
		System.out.println("===============================");
	}*/
	
	/*@Test
	public void findByCategoryAndId() {
		
		Item item = itemRepo.findByCategoryAndId("Crystal", 1L);		
		Assert.assertNotNull(item);		
		System.out.println(item.toString());
		System.out.println("===============================");
	}*/

}
