package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.entity.Item;

@Service
public class ItemServiceImpl {
	
	@Autowired
	ItemRepository itemRepo;
	
	public List<Item> getAll(){
		return itemRepo.findAll();
	}
	
	public List<Item> getItemsByCategory(String category){
		return itemRepo.findByCategory(category);
	}
	
	public List<String> getAllCategories(){
		return itemRepo.findAllCategories();
	}
	
	public Item getItemByCategoryAndId(String category, Long id) {
		return itemRepo.findByCategoryAndId(category, id);
	}
	
	public Item getItemById(Long id) {
		return itemRepo.findById(id);
	}
	
	public void saveItem(Item item) {
		itemRepo.save(item);
	}
	
	
	

}
