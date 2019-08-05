package com.example.demo.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.repository.entity.Item;
import com.example.demo.service.ItemServiceImpl;


@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemServiceImpl itemService;
    
    @InjectMocks
    private ItemController itemController;
    
    

    
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(itemController)
                .build();
    }

	@Test
	public void testGetInventory() throws Exception {				
		 Principal mockPrincipal = Mockito.mock(Principal.class);
		 Mockito.when(mockPrincipal.getName()).thenReturn("joe");
		 
		 RequestBuilder requestBuilder = MockMvcRequestBuilders
				 	.get("/inventory")
			        .principal(mockPrincipal);
        List<Item> items = Arrays.asList(
                new Item(Long.valueOf("1"), "mockName1", "mockDescription1", "mockcategory1", "1.00"),
                new Item(Long.valueOf("2"), "mockName2", "mockDescription2", "mockcategory2", "2.00"),
                new Item(Long.valueOf("3"), "mockName3", "mockDescription3", "mockcategory3", "3.00"),
                new Item(Long.valueOf("4"), "mockName4", "mockDescription4", "mockcategory4", "4.00"),
                new Item(Long.valueOf("5"), "mockName5", "mockDescription5", "mockcategory5", "5.00"),
                new Item(Long.valueOf("6"), "mockName6", "mockDescription6", "mockcategory6", "6.00"));
            
        
        Mockito.when(itemService.getAll()).thenReturn(items);
        
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(6)))
                
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Is.is("mockName1")))
                
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Is.is("mockName2")));
        
        Mockito.verify(itemService, Mockito.times(1)).getAll();
        Mockito.verifyNoMoreInteractions(itemService);
	}
	
	@Test
	public void testFindAllCategories() throws Exception {
		
		List<String> categories = Arrays.asList("category1", "category2", "category3");
		
		Mockito.when(itemService.getAllCategories()).thenReturn(categories);
		mockMvc.perform(MockMvcRequestBuilders.get("/inventory/categories"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))        
        .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Is.is("category1")))       
        .andExpect(MockMvcResultMatchers.jsonPath("$[1]", Is.is("category2")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2]", Is.is("category3")));		
	}
	
	@Test
	public void testFindAllCategoryItems() throws Exception {
		
		 List<Item> items = Arrays.asList(
	                new Item(Long.valueOf("1"), "mockName1", "mockDescription1", "mockcategory1", "1.00"),
	                new Item(Long.valueOf("2"), "mockName2", "mockDescription2", "mockcategory1", "2.00"));		 
		 
		 Mockito.when(itemService.getItemsByCategory("mockcategory1")).thenReturn(items);
		 mockMvc.perform(MockMvcRequestBuilders.get("/inventory/{category}/items", "mockcategory1"))
		 .andExpect(MockMvcResultMatchers.status().isOk())
         .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
         .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
         
         .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(1)))
         .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Is.is("mockName1")))
         
         .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Is.is(2)))
         .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Is.is("mockName2")));
         
         Mockito.verify(itemService, Mockito.times(1)).getItemsByCategory("mockcategory1");
		 Mockito.verifyNoMoreInteractions(itemService);
	
	}
	
	
	@Test
	public void testFindCategoryItem() throws Exception {
		
		
		Principal mockPrincipal = Mockito.mock(Principal.class);
		 Mockito.when(mockPrincipal.getName()).thenReturn("joe");
		 
		 RequestBuilder requestBuilder = MockMvcRequestBuilders
				 	.get("/inventory/{category}/item/{id}", "mockcategory1", "1.00")
			        .principal(mockPrincipal);
		
		Item item = new Item(Long.valueOf("1"), "mockName1", "mockDescription1", "mockcategory1", "1.00");
		
		Mockito.when(itemService.getItemByCategoryAndId("mockcategory1", Long.valueOf("1"))).thenReturn(item);
			
		mockMvc.perform(requestBuilder)
		 .andExpect(MockMvcResultMatchers.status().isOk())
		 .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		 .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
		 .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("mockName1")))
		 .andExpect(MockMvcResultMatchers.jsonPath("$.description", Is.is("mockDescription1")))
		 .andExpect(MockMvcResultMatchers.jsonPath("$.category", Is.is("mockcategory1")))
		 .andExpect(MockMvcResultMatchers.jsonPath("$.price", Is.is("1.00")));
		
		Mockito.verify(itemService, Mockito.times(1)).getItemByCategoryAndId("mockcategory1", Long.valueOf("1"));
		Mockito.verifyNoMoreInteractions(itemService);
		 
	}
	
}
