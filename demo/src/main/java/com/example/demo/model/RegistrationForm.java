package com.example.demo.model;

import com.example.demo.repository.entity.Item;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RegistrationForm {
	
	private String name;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String creditCardNumber;

}
