package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.entity.Item;
import com.example.demo.service.ItemServiceImpl;

@RestController
@RequestMapping("/inventory")
public class ItemController {
	
	@Autowired
    private ItemServiceImpl itemService;
	
	@GetMapping
    public @ResponseBody List<Item> findAll() {						
        return itemService.getAll();
    }
	
	@GetMapping(value = "/categories")
    public @ResponseBody List<String> findAllCategories() {		
        return itemService.getAllCategories();
    }
	
	@GetMapping(value = "/{category}/items")
	public @ResponseBody List<Item> findAllCategoryItems(@PathVariable String category) {				
        return itemService.getItemsByCategory(category);
    }
	
	
	@GetMapping(value = "/{category}/item/{id}")
	public @ResponseBody Item findCategoryItem(@PathVariable String category, @PathVariable String id) {		
        return itemService.getItemByCategoryAndId(category, Long.valueOf(id));
    }
	
	
	
	/*@GetMapping(value = "/category/item/{id]/purchase")
	public Item purchaseItem(String itemId) {
        return RestPreconditions.checkFound(service.findById(id));
    }*/
	
	
	
}
