package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.repository.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	
	public List<Item> findByCategory(String category);
	public Item findById(Long id);
	public Item findByCategoryAndId(String category, Long id);
	
	
	@Query(nativeQuery = true, value = "select distinct category from Item")
	public List<String> findAllCategories();
	
	
	
}
