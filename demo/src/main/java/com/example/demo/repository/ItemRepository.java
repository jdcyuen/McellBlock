package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.repository.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	public List<Item> findByCategory(String category);
}
