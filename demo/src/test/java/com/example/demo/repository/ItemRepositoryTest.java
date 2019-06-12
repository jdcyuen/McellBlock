package com.example.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.repository.entity.Item;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {
	
	@Autowired    
	TestEntityManager entityManager;   

	@Autowired
	ItemRepository itemRepo;
	
	private ItemRepository itemRepositoryMock;
	
	@Before
	public void setUp() throws Exception {
		itemRepositoryMock = Mockito.mock(ItemRepository.class);
	}

	@Test
    public void findAllTest() {
		
        List<Item> items = new ArrayList<Item>(); 
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        Mockito.when(itemRepositoryMock.findAll()).thenReturn(items);
        
        List allItems = itemRepositoryMock.findAll();
        assertNotNull(allItems);
        assertEquals(5, allItems.size());
        
    }
	
	@Test
    public void findByCategoryTest() {
		
		List<Item> items = new ArrayList<Item>(); 
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        
        Mockito.when(itemRepositoryMock.findByCategory("mockCategory")).thenReturn(items);
		List categoryItems = itemRepositoryMock.findByCategory("mockCategory");
		assertNotNull(categoryItems);
        assertEquals(3, categoryItems.size());        
	}
	
	@Test
    public void findByCategoryAndIdTest() {
		
		String mockItemId = "3";
		
		Item mockItem = new Item();
		Mockito.when(itemRepositoryMock.findByCategoryAndId("mockCategory", Long.valueOf(mockItemId))).thenReturn(mockItem);
		
		Item item = itemRepositoryMock.findByCategoryAndId("mockCategory", Long.valueOf(mockItemId));
		assertNotNull(item);		
	}
	
	@Test
    public void findAllCategoriesTest() {
		
		List<String> mockCategories = new ArrayList<String>();
		mockCategories.add("category1");
		mockCategories.add("category2");
		mockCategories.add("category3");
		mockCategories.add("category4");
		mockCategories.add("category5");
		mockCategories.add("category6");
		mockCategories.add("category7");
		
		Mockito.when(itemRepositoryMock.findAllCategories()).thenReturn(mockCategories);
		List categoryList = itemRepositoryMock.findAllCategories();
		
		assertNotNull(categoryList);
        assertEquals(7, categoryList.size());
        Assert.assertTrue(categoryList.contains("category6"));
	
	}
	
	@Test
    public void findByIdTest() {
		
		String mockItemId = "3";
		
		Item mockItem = new Item();
		Mockito.when(itemRepositoryMock.findById(Long.valueOf(mockItemId))).thenReturn(mockItem);
		
		Item item = itemRepositoryMock.findById(Long.valueOf(mockItemId));
		assertNotNull(item);		
	}

}
