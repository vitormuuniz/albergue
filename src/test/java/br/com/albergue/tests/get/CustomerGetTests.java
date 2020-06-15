package br.com.albergue.tests.get;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.albergue.controller.dto.CustomerDto;
import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;
import br.com.albergue.repository.AddressRepository;
import br.com.albergue.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerGetTests {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	CustomerRepository customerRepository;
	
	@MockBean
	AddressRepository addressRepository;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private URI uri;
	private Address address = new Address();
	private Customer customer = new Customer();
	private List<Customer> customersList = new ArrayList<>();

	@Before
	public void init() throws URISyntaxException {
		
		uri = new URI("/api/customers");

		// setting address to put into the customer paramseters
		address.setAddressName("rua x");
		address.setCity("Amparo");
		address.setCountry("Brasil");
		address.setState("SP");
		address.setZipCode("13900-000");

		//setting customer
		customer.setLastName("aaaaaaaaaa");
		customer.setAddress(address);
		customer.setBirthday(LocalDate.of(1900, 12, 12));
		customer.setEmail("washington@orkut.com");
		customer.setName("Washington");
		customer.setTitle("MRS.");
		customer.setPassword("1234567");
		
		customersList.add(customer);
	}

	@Test
	public void shouldReturnOneCustomerAndStatusOkWithoutParam() throws URISyntaxException, JsonMappingException, JsonProcessingException {
		
		Customer customer2 = new Customer();
		customer2.setName("Antonio");
		customersList.add(customer2);
		
		Mockito.when(customerRepository.findAll()).thenReturn(customersList);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		String contentAsString = result.getBody();

		CustomerDto[] customerObjResponse = objectMapper.readValue(contentAsString, CustomerDto[].class);
		
		/// Verify request succeed
		assertEquals(customerObjResponse.length, 2);
		assertEquals(customerObjResponse[0].getName(), "Washington");
		assertEquals(customerObjResponse[0].getAddress().getCity(), "Amparo");
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void shouldReturnOneCustomerAndStatusOkByParam() throws URISyntaxException, JsonMappingException, JsonProcessingException {
		
		Mockito.when(customerRepository.findByName("Washington")).thenReturn(customersList);

		ResponseEntity<String> result = restTemplate.getForEntity(uri + "?name=Washington", String.class);

		String contentAsString = result.getBody();

		CustomerDto[] customerObjResponse = objectMapper.readValue(contentAsString, CustomerDto[].class);
		
		/// Verify request succeed
		assertEquals(customerObjResponse[0].getName(), "Washington");
		assertEquals(customerObjResponse[0].getAddress().getCity(), "Amparo");
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void shouldReturnOneCustomerAndStatusOkById() throws URISyntaxException, JsonMappingException, JsonProcessingException {
		
		Mockito.when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));

		ResponseEntity<String> result = restTemplate.getForEntity(uri + "/2", String.class);

		String contentAsString = result.getBody();

		CustomerDto customerObjResponse = objectMapper.readValue(contentAsString, CustomerDto.class);
		
		/// Verify request succeed
		assertEquals(customerObjResponse.getName(), "Washington");
		assertEquals(customerObjResponse.getAddress().getCity(), "Amparo");
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void shouldReturnNotFoundStatusAndNullBodyByWrongParam() throws URISyntaxException {
		
		Mockito.when(customerRepository.findByName("Washington")).thenReturn(customersList);

		ResponseEntity<String> result = restTemplate.getForEntity(uri + "?name=Washington222", String.class);

		// Verify request succeed
		Assert.assertEquals(404, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody(), null);
	}

}