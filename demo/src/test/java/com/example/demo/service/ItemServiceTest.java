package com.example.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.entity.Item;

import junit.framework.Assert;

public class ItemServiceTest {
	
	@InjectMocks
	ItemServiceImpl itemServiceMock;
     
    @Mock
    ItemRepository  itemRepo;

	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testGetAll() {
		List<Item> items = new ArrayList<Item>(); 
		items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        Mockito.when(itemServiceMock.getAll()).thenReturn(items);
        List allItems = itemServiceMock.getAll();
        assertNotNull(allItems);
        assertEquals(5, allItems.size());
        
	}
	
	@Test
	public void testGetAllCategories() {
		
		List<String> mockCategories = new ArrayList<String>();
		mockCategories.add("category1");
		mockCategories.add("category2");
		mockCategories.add("category3");
		mockCategories.add("category4");
		mockCategories.add("category5");
		mockCategories.add("category6");
		mockCategories.add("category7");
		Mockito.when(itemServiceMock.getAllCategories()).thenReturn(mockCategories);
		
		List categoryList = itemServiceMock.getAllCategories();
		assertNotNull(categoryList);
        assertEquals(7, categoryList.size());
        Assert.assertTrue(categoryList.contains("category4"));
	}
	
	@Test
	public void testGetItemsByCategory() {
		
		List<Item> items = new ArrayList<Item>(); 
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        
        Mockito.when(itemServiceMock.getItemsByCategory("mockCategory")).thenReturn(items);
		List categoryItems = itemServiceMock.getItemsByCategory("mockCategory");
		assertNotNull(categoryItems);
        assertEquals(3, categoryItems.size());    
	}
	
	@Test
    public void findByCategoryAndIdTest() {
		
		String mockItemId = "4";
		
		Item mockItem = new Item();
		Mockito.when(itemServiceMock.getItemByCategoryAndId("mockCategory", Long.valueOf(mockItemId))).thenReturn(mockItem);
		
		Item item = itemServiceMock.getItemByCategoryAndId("mockCategory", Long.valueOf(mockItemId));
		assertNotNull(item);		
	}

}
