package com.example.demo.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
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

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ItemController {
	
	@Autowired
    private ItemServiceImpl itemService;
	
	public static HashMap<String, ItemTracker> itemMap = new HashMap<String, ItemTracker>();
	public static Map<String, Map> user2ItemMap = new HashMap<>();
	
	@PostMapping(value = "/register", consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody String register(@RequestBody RegistrationForm form) {
		
		String message = "You have been successfully registers as a Gilded Rose merchant";
		
        return message;
    }
	
	@GetMapping(value = "/inventory")
    public @ResponseBody List<Item> findAll(HttpServletRequest request) {		
				
		String username = request.getUserPrincipal().getName();		
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
	public @ResponseBody Item findCategoryItem(@PathVariable String category, @PathVariable String id, HttpServletRequest request) {			
		
		String username = request.getUserPrincipal().getName();		
		if(user2ItemMap.isEmpty()) {
			
			//Create itemMap & Add to user2ItemMap
			ItemTracker itemTracker = new ItemTracker(new Date(), 1);
			itemMap.put(id, itemTracker);
			user2ItemMap.put(username, itemMap);
						
		}else {
			
			// Get from user2ItemMap
			HashMap<String, ItemTracker> itemMap = (HashMap<String, ItemTracker>) user2ItemMap.get(username);
			if(!itemMap.isEmpty() && itemMap.containsKey(id)) {
				ItemTracker itemTracker = itemMap.get(id);
				Date startDatePlusOneHr = DateUtils.addHours(itemTracker.getStartTime(), 1);
				
				//Has 1 hour expired? No
				if(startDatePlusOneHr.after(new Date())) {
						int count = itemTracker.getViewCount();
						itemTracker.setViewCount(count+1);
						
						itemMap.put(id, itemTracker);
						user2ItemMap.put(username, itemMap);												
				}else {
					//Yes, restart hour
					itemMap.put(id, new ItemTracker(new Date(), 1));
					user2ItemMap.put(username, itemMap);
				}									
				
			}else if(!itemMap.isEmpty() && !itemMap.containsKey(id)) {
				itemMap.put(id, new ItemTracker(new Date(), 1));
				user2ItemMap.put(username, itemMap);
			}
			
		}
		
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
	public @ResponseBody String purchaseItem(@PathVariable String id, HttpServletRequest request) {
		
		/*		 		 
		 - When this endpoint is called the information from the itemTracker will be used to calculate the pricing.
		 
		 - This where a credit card service would be called to verify the credit card information, check the credit balance
		   and submit the purchase to the credit card service. If any errors occur, then throw the appropriatge exception
		   
		 - Also the check of the inventory to see if the number of items in the inventory is > 0, otherwise throw 
		   InsufficientInventoryException
		   
		 - An improvement would be to add a shopping cart so that the merchant may purchase multiple items.
		 
		 Surge pricing algorithm
		 =======================
		 -Whenever findCategory is called to view an item, the itemTracker will save the information on who viewed it, how many
		 times viewed in the last hour. Viewing the complete inventory will not count as viewing an individual item and so will not
		 be including in surge pricing.
		 
		 */
		
		String username = request.getUserPrincipal().getName();
		
		@SuppressWarnings("unchecked")
		Map<String, ItemTracker> tracker = user2ItemMap.get(username);
		if(tracker == null) {
			ItemTracker itemTracker = new ItemTracker(new Date(), 1);
			itemMap.put(id, itemTracker);
			user2ItemMap.put(username, itemMap);
			tracker = user2ItemMap.get(username);
		}						
		
		Item item = itemService.getItemById(Long.valueOf(id));
		int remainingCnt = item.getAvailable();
		String message = "";
		if(remainingCnt > 0) {
			
			if(tracker.get(id).getViewCount() >= 10) {
				String price = item.getPrice();
				BigDecimal bd1 =  new BigDecimal(price);
				BigDecimal bd2 = new BigDecimal("1.1");				
				BigDecimal bd = bd1.multiply(bd2);
				
				item.setAvailable(remainingCnt - 1);
				itemService.saveItem(item);
				System.out.println("Surge pricing applied...");
				//message = "Your purchase of " + item.getName() + " for the price of " + bd1.multiply(bd2).toString() + " has been submitted. ";
				message = "Your purchase of " + item.getName() + " for the price of " +  String.format("%.2f", bd) + " has been submitted. ";
			}else {
				item.setAvailable(remainingCnt - 1);
				itemService.saveItem(item);
				message = "Your purchase of " + item.getName() + " for the price of " + item.getPrice() + " has been submitted. ";
			}
		}else {
			message = "The item " + item.getName() + " you wish to purchase is out of stock. We apologize for any inconvenience.";
		}
		
        return message;
    }			
}
