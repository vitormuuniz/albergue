package br.com.hostel.controller.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.hostel.domain.Address;
import br.com.hostel.domain.Customer;
import br.com.hostel.domain.Reservation;

public class CustomerDto {
	
	private Long id;
	private String title;
	private String name;
	private String lastname;
	private LocalDate birthday;
	private Address address;
	private String email;
	private String password;
    private Set<Reservation> reservations;
    
    public CustomerDto() {}

	public CustomerDto(Customer customer) {
		this.id = customer.getId();
		this.title = customer.getTitle();
		this.name = customer.getName();
		this.lastname = customer.getLastName();
		this.birthday = customer.getBirthday();
		this.address = customer.getAddress();
		this.email = customer.getEmail();
		this.password = customer.getPassword();
		this.reservations = customer.getReservations();
	}
	
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getName() {
		return name;
	}

	public String getLastname() {
		return lastname;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public Address getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Set<Reservation> getReservations() {
		return reservations;
	}

	public static List<CustomerDto> converter(List<Customer> customersList) {

		List<CustomerDto> customersDtoList = new ArrayList<>();
		for(Customer c : customersList) {
			customersDtoList.add(new CustomerDto(c));
		}
		
		return customersDtoList;
	}
}