package br.com.albergue.controller.dto;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.domain.Page;

import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;
import br.com.albergue.domain.Reservation;

public class CustomerDto {
	
	private Long id;
	private String title;
	private String name;
	private String lastname;
	private LocalDate birthday;
	private Address address;
	private String username;
	private String password;
	private boolean admin;
    private Set<Reservation> reservations;
    
    public CustomerDto() {
	}

	public CustomerDto(Customer customer) {
		this.id = customer.getId();
		this.title = customer.getTitle();
		this.name = customer.getName();
		this.lastname = customer.getLastName();
		this.birthday = customer.getBirthday();
		this.address = customer.getAddress();
		this.username = customer.getUsername();
		this.password = customer.getPassword();
		this.admin = customer.isAdmin();
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
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public static Page<CustomerDto> converter(Page<Customer> customer) {
		//fazendo um map de topico para topicoDto
		//TopicoDto::new -> recebe o proprio construtor que recebe um topico como parametro
		//collect() -> transforma essa saida em uma lista
//		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	
		return customer.map(CustomerDto::new);
	}
}