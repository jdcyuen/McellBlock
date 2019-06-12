package com.example.demo.service;

import java.util.List;

import com.example.demo.repository.entity.Item;

public interface IItemService {
	
	public List<Item> getAll();
	public List<String> getAllCategories();
	public List<Item> getItemsByCategory(String category);
	public Item getItemByCategoryAndId(String category, Long id);
	public Item getItemById(Long id);
}
