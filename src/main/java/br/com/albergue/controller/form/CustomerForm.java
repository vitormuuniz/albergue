package br.com.albergue.controller.form;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;
import br.com.albergue.repository.AddressRepository;

public class CustomerForm {
	
	@NotNull 
	private String title;
	@NotNull 
	private String name;
	@NotNull 
	private String lastname;
	@NotNull 
	private LocalDate birthday;
	@NotNull 
	private Address address;
	@NotNull 
	private String username;
	@NotNull 
	private boolean admin;
	@NotNull 
	private String password;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastname;
	}
	
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Customer returnCustomer(AddressRepository addressRepository) {
		addressRepository.save(address);
		return new Customer(title, name, lastname, birthday, address, username, new BCryptPasswordEncoder().encode(password), admin);
	}
}
