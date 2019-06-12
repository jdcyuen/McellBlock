package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ItemTracker;
import com.example.demo.model.RegistrationForm;
import com.example.demo.repository.entity.Item;
import com.example.demo.service.ItemServiceImpl;

@RestController
//@RequestMapping("/inventory")
public class ItemController {
	
	@Autowired
    private ItemServiceImpl itemService;
	
	public static HashMap<String, HashMap<Long, ItemTracker>> user2ItemMap = new HashMap<>();
	
	@PostMapping(value = "/register", consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody String register(@RequestBody RegistrationForm form) {
		
		String message = "You have been successfully registers as a Gilded Rose merchant";
		
        return message;
    }
	
	@GetMapping(value = "/inventory")
    public @ResponseBody List<Item> findAll() {						
        return itemService.getAll();
    }
	
	@GetMapping(value = "/inventory/categories")
    public @ResponseBody List<String> findAllCategories() {		
        return itemService.getAllCategories();
    }
	
	@GetMapping(value = "/inventory/{category}/items")
	public @ResponseBody List<Item> findAllCategoryItems(@PathVariable String category) {				
        return itemService.getItemsByCategory(category);
    }
	
	
	@GetMapping(value = "/inventory/{category}/item/{id}")
	public @ResponseBody Item findCategoryItem(@PathVariable String category, @PathVariable String id) {	
		
		/*
		Retrieve username from token and make sure it matches a username in the user database
		If there is no match return a unauthorized user exception
		If there is a match on the username then check the user2ItemMap 
				* lookup user and get Hashmap<id for item, ItemTracker> for the user, if there isn't one create a username/HashMap pair
				* use item id get ItemTracker, if there isn't one create one, record timestamp in the ItemTracker and increment viewCount
				* if there is one, subtract time in the item tracker with current time to see if one hour has past, if it has then clear field and save current time
				* if one hour has not passed then save current time and increment viewCount				  
		 */		
		
        return itemService.getItemByCategoryAndId(category, Long.valueOf(id));
    }
	
	
	
	@GetMapping(value = "/inventory/category/item/{id}/purchase")
	public @ResponseBody String purchaseItem(@PathVariable String id) {
		
		/*
		 Surge pricing algorithm
		 =======================
		 -Whenever findCategory is called to view an item, the itemTracker will save the infomation on who viewed it, how many
		 times viewed in the last hour
		 
		 - When this endpoint is called the information from the itemTracker will be used to calculate the pricing.
		 
		 - This where a credit card service would be called to verify the credit card information, check the credit balance
		   and submit the purchase to the credit card service.
		   
		 - An improvement would be to add a shopping cart so that the merchant may purchase multiple items.
		 
		 */
		
		Item item = itemService.getItemById(Long.valueOf(id));
		
		
		String message = "Your purchase of " + item.getName() + " has been submitted.";
		
        return message;
    }
	
	
	
}
