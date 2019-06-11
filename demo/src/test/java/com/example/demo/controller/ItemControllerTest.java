package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
                //.addFilters(new CORSFilter())
                .build();
    }

	@Test
	public void testGetInventory() throws Exception {
               
        List<Item> items = Arrays.asList(
                new Item(Long.valueOf("1"), "mockName1", "mockDescription1", "mockcategory1", "1.00"),
                new Item(Long.valueOf("2"), "mockName2", "mockDescription2", "mockcategory2", "2.00"),
                new Item(Long.valueOf("3"), "mockName3", "mockDescription3", "mockcategory3", "3.00"),
                new Item(Long.valueOf("4"), "mockName4", "mockDescription4", "mockcategory4", "4.00"),
                new Item(Long.valueOf("5"), "mockName5", "mockDescription5", "mockcategory5", "5.00"),
                new Item(Long.valueOf("6"), "mockName6", "mockDescription6", "mockcategory6", "6.00"));
            
        
        Mockito.when(itemService.getAll()).thenReturn(items);
        mockMvc.perform(MockMvcRequestBuilders.get("/inventory"))
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

}
