package br.com.albergue.tests.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;
import br.com.albergue.repository.AddressRepository;
import br.com.albergue.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CustomerGetTests2 {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	AddressRepository addressRepository;

	private URI uri;
	private Address address = new Address();
	private Customer customer = new Customer();

	@Before
	public void init() throws URISyntaxException {

		uri = new URI("/api/customers");

		// setting address to put into the customer paramseters
		address.setId(1L);
		address.setAddressName("rua tal");
		address.setCity("alguma");
		address.setCountry("algum");
		address.setState("XXX");
		address.setZipCode("xxxx-xxx");

		// setting customer
		customer.setId(1L);
		customer.setLastName("aaaaaaaaaa");
		customer.setAddress(address);
		customer.setBirthday(LocalDate.of(1900, 12, 12));
		customer.setEmail("washington@orkut.com");
		customer.setName("Washington");
		customer.setTitle("MRS.");
		customer.setPassword("1234567");
	}

	@Test
	public void shouldReturnOneCustomerWithoutParamAndStatusOk() throws Exception {
		List<Customer> cust = new ArrayList<>();
		cust.add(customer);
		when(customerRepository.findAll()).thenReturn(cust);

		MvcResult result = 
				mockMvc.perform(get(uri))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		assertTrue(content.contains("\"name\":\"Washington\""));

	}

	@Test
	public void shouldReturnOneCustomerByParamAndStatusOk() throws Exception {

		List<Customer> cust = new ArrayList<>();
		cust.add(customer);

		when(customerRepository.findByName(eq(customer.getName()))).thenReturn(cust);
		MvcResult result = 
				mockMvc.perform(get(uri + "?name=Washington"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		assertTrue(content.contains("\"name\":\"Washington\""));
	}

	@Test
	public void shouldReturnOneCustomerByIdAndStatusOk() throws Exception {

		Optional<Customer> opcust = Optional.of(customer);
		when(customerRepository.findById(eq(customer.getId()))).thenReturn(opcust);

		MvcResult result = 
				mockMvc.perform(get(uri+"/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		assertTrue(content.contains("\"name\":\"Washington\""));

	}

	@Test
	public void shouldNotReturnAnyCustomerByWrongParamAndStatusNotFound() throws Exception {

		List<Customer> cust = new ArrayList<>();
		cust.add(customer);

		when(customerRepository.findByName(eq(customer.getName()))).thenReturn(cust);
		mockMvc.perform(get(uri + "?name=Washington222"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andReturn();
	}
}